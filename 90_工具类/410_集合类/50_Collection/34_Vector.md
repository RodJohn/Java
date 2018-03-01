#Vector

	实现List接口的，
	底层采用数组实现
	允许使用NULL值
	是线程安全的
	始于1.0
	
#类结构/实现方法

	Vector的类结构和实现方法类似于ArrayList;
	
类结构

	public class Vector<E>
	    extends AbstractList<E>
	    implements List<E>, RandomAccess, Cloneable, java.io.Serializable{	

成员变量

    protected Object[] elementData;

    protected int elementCount;


	


##功能实现

###add

add(E e)：将指定元素添加到此向量的末尾。

```
public synchronized boolean add(E e) {
        modCount++;     
        ensureCapacityHelper(elementCount + 1);    
        elementData[elementCount++] = e; 
        return true;
    }
```

####容量


```
private void ensureCapacityHelper(int minCapacity) {
        //如果
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
    
    /**
     * 进行扩容操作
     * 如果此向量的当前容量小于minCapacity，则通过将其内部数组替换为一个较大的数组俩增加其容量。
     * 新数据数组的大小姜维原来的大小 + capacityIncrement，
     * 除非 capacityIncrement 的值小于等于零，在后一种情况下，新的容量将为原来容量的两倍，不过，如果此大小仍然小于 minCapacity，则新容量将为 minCapacity。
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;     //当前容器大小
        /*
         * 新容器大小
         * 若容量增量系数(capacityIncrement) > 0，则将容器大小增加到capacityIncrement
         * 否则将容量增加一倍
         */
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                         capacityIncrement : oldCapacity);
        
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    /**
     * 判断是否超出最大范围
     * MAX_ARRAY_SIZE：private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }
```

###remove

remove(Object o)

```
    public boolean remove(Object o) {
        return removeElement(o);
    }

    public synchronized boolean removeElement(Object obj) {
        modCount++;
        int i = indexOf(obj);   //计算obj在Vector容器中位置
        if (i >= 0) {
            removeElementAt(i);   //移除
            return true;
        }
        return false;
    }
    
    public synchronized void removeElementAt(int index) {
        modCount++;     //修改次数+1
        if (index >= elementCount) {   //删除位置大于容器有效大小
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }
        else if (index < 0) {    //位置小于 < 0
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = elementCount - index - 1;
        if (j > 0) {   
            //从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
            //也就是数组元素从j位置往前移
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        elementCount--;   //容器中有效组件个数 - 1
        elementData[elementCount] = null;    //将向量的末尾位置设置为null
    }
```
