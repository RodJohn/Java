# LinkedList

	LinkedList是List接口
	使用双向链表的实现
	允许元素为null
	始于1.2


# 类结构

## 类定义

```
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

![这里写图片描述](http://img.blog.csdn.net/20171215092748488?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


## 属性

 在LinkedList中提供了两个基本属性size、header。
 size表示的LinkedList的大小，header表示链表的表头，Entry为节点对象
 
```
private transient Entry<E> header = new Entry<E>(null, null, null);
private transient int size = 0;
```

Entry为LinkedList的内部类，它定义了存储的元素。
该元素的前一个元素、后一个元素，这是典型的双向链表定义方式

```
private static class Entry<E> {
        E element;        //元素节点
        Entry<E> next;    //下一个元素
        Entry<E> previous;  //上一个元素

        Entry(E element, Entry<E> next, Entry<E> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
```

## 构造

```
public LinkedList() {
   header.next = header.previous = header;
}

将header节点的前一个元素、后一个元素都指向自身。
双向循环链表
```

# 功能实现

## get

```

    public E get(int index) {
        checkElementIndex(index);
        return entry(index).item;
    }
    
    private Entry<E> entry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
                    + size);
        //头部节点
        Entry<E> e = header;
        //判断遍历的方向
        // 获取index处的节点。
        // 若index < 双向链表长度的1/2,则从前先后查找;
        // 否则，从后向前查找。
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
                e = e.previous;
        }
        return e;
    }
```

## add


add(int index, E e)

```
// 在index前添加节点，且节点的值为element
public void add(int index, E element) {
        addBefore(element, (index==size ? header : entry(index)));
    }

private Entry<E> addBefore(E e, Entry<E> entry) {
        //利用Entry构造函数构建一个新节点 newEntry，
        Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
        //修改newEntry的前后节点的引用，确保其链表的引用关系是正确的
        //头插法
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
        //容量+1
        size++;
        //修改次数+1
        modCount++;
        return newEntry;
    }
```

addAll

```
public boolean addAll(Collection<? extends E> c) {
    return addAll(size, c);
}

/**
 * 将指定 collection 中的所有元素从指定位置开始插入此列表。其中index表示在其中插入指定collection中第一个元素的索引
 */
public boolean addAll(int index, Collection<? extends E> c) {
    //若插入的位置小于0或者大于链表长度，则抛出IndexOutOfBoundsException异常
    if (index < 0 || index > size)
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    Object[] a = c.toArray();
    int numNew = a.length;    //插入元素的个数
    //若插入的元素为空，则返回false
    if (numNew == 0)
        return false;
    //modCount:在AbstractList中定义的，表示从结构上修改列表的次数
    modCount++;
    //获取插入位置的节点，若插入的位置在size处，则是头节点，否则获取index位置处的节点
    Entry<E> successor = (index == size ? header : entry(index));
    //插入位置的前一个节点，在插入过程中需要修改该节点的next引用：指向插入的节点元素
    Entry<E> predecessor = successor.previous;
    //执行插入动作
    for (int i = 0; i < numNew; i++) {
        //构造一个节点e，这里已经执行了插入节点动作同时修改了相邻节点的指向引用
        //
        Entry<E> e = new Entry<E>((E) a[i], successor, predecessor);
        //将插入位置前一个节点的下一个元素引用指向当前元素
        predecessor.next = e;
        //修改插入位置的前一个节点，这样做的目的是将插入位置右移一位，保证后续的元素是插在该元素的后面，确保这些元素的顺序
        predecessor = e;
    }
    successor.previous = predecessor;
    //修改容量大小
    size += numNew;
    return true;
}
```


## remove


```
public E remove(int index) {
    return remove(entry(index));
}

private E remove(Entry<E> e) {
        if (e == header)
            throw new NoSuchElementException();
            
        //保留被移除的元素：要返回
        E result = e.element;
        
        //将该节点的前一节点的next指向该节点后节点
        e.previous.next = e.next;
        //将该节点的后一节点的previous指向该节点的前节点
        //这两步就可以将该节点从链表从除去：在该链表中是无法遍历到该节点的
        e.next.previous = e.previous;
        //将该节点归空
        e.next = e.previous = null;
        e.element = null;
        size--;
        modCount++;
        return result;
    }
```


## poll

```
     // 删除并返回第一个节点
     // 若LinkedList的大小为0,则返回null
     public E poll() {
         if (size==0)
             return null;
         return removeFirst();
     }

     // 删除LinkedList的第一个元素
     public E removeFirst() {
         return remove(header.next);
     }
```