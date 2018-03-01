
# 概述


	实现List接口的，
	底层采用数组实现
	允许使用NULL值
	不是线程安全的


# 类构造

## 类定义图

```
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {}
```

![这里写图片描述](http://img.blog.csdn.net/20171214225712300?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


## 属性

	//内部维护的数组
    transient Object[] elementData;
    //包含元素的个数.
    private int size;



## 构造方法

     ArrayList()：默认构造函数，提供初始容量为10的空列表。
     ArrayList(int initialCapacity)：构造一个具有指定初始容量的空列表。

```
	/**
     * 构造一个初始容量为 10 的空列表
     */
    public ArrayList() {
        this(10);
    }

    /**
     * 构造一个具有指定初始容量的空列表。
     */
    public ArrayList(int initialCapacity) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "
                    + initialCapacity);
        this.elementData = new Object[initialCapacity];
    }
```


# 功能实现

## add

add(E e)：将指定的元素添加到此列表的尾部。

```
public boolean add(E e) {
    ensureCapacity(size + 1);  
    elementData[size++] = e;
    return true;
    }
```

add(int index, E element)：将指定的元素插入此列表中的指定位置。

```
public void add(int index, E element) {
        //判断索引位置是否正确
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: "+index+", Size: "+size);
        //扩容检测
        ensureCapacity(size+1);  
        /*
         * 对源数组进行复制处理（位移），从index + 1到size-index。
         * 主要目的就是空出index位置供数据插入，
         * 即向右移动当前位于该位置的元素以及所有后续元素。 
         */
        System.arraycopy(elementData, index, elementData, index + 1,
                 size - index);
        //在指定位置赋值
        elementData[index] = element;
        size++;
        }
```


### 扩容

ensureCapacity(int minCapacity)

```
public void ensureCapacity(int minCapacity) {
        //修改计时器
        modCount++;
        //ArrayList容量大小
        int oldCapacity = elementData.length;
        /*
         * 若当前需要的长度大于当前数组的长度时，进行扩容操作
         */
        if (minCapacity > oldCapacity) {
            Object oldData[] = elementData;
            //计算新的容量大小，为当前容量的1.5倍
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            //数组拷贝，生成新的数组
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
```


	在这里有一个疑问，为什么每次扩容处理会是1.5倍，而不是2.5、3、4倍呢？
	通过google查找，发现1.5倍的扩容是最好的倍数。
	因为一次性扩容太大(例如2.5倍)可能会浪费更多的内存(1.5倍最多浪费33%，而2.5被最多会浪费60%，3.5倍则会浪费71%……)。
	但是一次性扩容太小，需要多次对数组重新分配内存，对性能消耗比较严重。
	所以1.5倍刚刚好，既能满足性能需求，也不会造成很大的内存消耗。


## remove

 remove(int index)：移除此列表中指定位置上的元素。

```
public E remove(int index) {
        //位置验证
        RangeCheck(index);

        modCount++;
        //需要删除的元素
        E oldValue = (E) elementData[index];   
        //向左移的位数
        int numMoved = size - index - 1;
        //若需要移动，则想左移动numMoved位
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        //置空最后一个元素
        elementData[--size] = null; // Let gc do its work

        return oldValue;
    }
```

## set
 set(int index, E element)：用指定的元素替代此列表中指定位置上的元素。

```
public E set(int index, E element) {
        //检测插入的位置是否越界
        RangeCheck(index);

        E oldValue = (E) elementData[index];
        //替代
        elementData[index] = element;
        return oldValue;
    }
```


## get

 ArrayList提供了get(int index)用读取ArrayList中的元素。

```
public E get(int index) {
        RangeCheck(index);

        return (E) elementData[index];
    }
```




