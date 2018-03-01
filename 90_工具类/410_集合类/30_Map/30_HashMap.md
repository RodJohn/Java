#概述

	线程不安全的
	允许null键null值得
	基于数组和链表结构的
	Map实现类


#类结构

##继承关系

```
public class HashMap<K,V>
    extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable
```

##成员变量


    // 存储数据的Entry数组，长度是2的幂。
    // HashMap是采用拉链法实现的，每一个Entry本质上是一个单向链表
    transient Entry[] table;

    // HashMap的大小，它是HashMap保存的键值对的数量
    transient int size;

    // HashMap的阈值，用于判断是否需要调整HashMap的容量（threshold = 容量*加载因子）
    int threshold;

    // 加载因子实际大小
    final float loadFactor;

    // HashMap被改变的次数
    transient volatile int modCount;

###Entry

Entry，它包含了键key、值value、下一个节点next，以及hash值，这是非常重要的，正是由于Entry才构成了table数组的项为链表。

```
static class Entry<K,V> implements Map.Entry<K,V> {
    final K key;
    V value;
    // 指向下一个节点
    Entry<K,V> next;
    final int hash;

    // 构造函数。
    // 输入参数包括"哈希值(h)", "键(k)", "值(v)", "下一节点(n)"
    Entry(int h, K k, V v, Entry<K,V> n) {
        value = v;
        next = n;
        key = k;
        hash = h;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    // 判断两个Entry是否相等
    // 若两个Entry的“key”和“value”都相等，则返回true。
    // 否则，返回false
    public final boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry e = (Map.Entry)o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    // 实现hashCode()
    public final int hashCode() {
        return (key==null   ? 0 : key.hashCode()) ^
               (value==null ? 0 : value.hashCode());
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }

    // 当向HashMap中添加元素时，绘调用recordAccess()。
    // 这里不做任何处理
    void recordAccess(HashMap<K,V> m) {
    }

    // 当从HashMap中删除元素时，绘调用recordRemoval()。
    // 这里不做任何处理
    void recordRemoval(HashMap<K,V> m) {
    }
}
```


##构造方法

HashMap()

	 HashMap()：构造一个具有默认初始容量 (16) 和默认加载因子 (0.75) 的空 HashMap。

HashMap(int initialCapacity, float loadFactor)

	public HashMap(int initialCapacity, float loadFactor) {
	     //初始容量不能<0
	     if (initialCapacity < 0)
	         throw new IllegalArgumentException("Illegal initial capacity: "
	                 + initialCapacity);
	     //初始容量不能 > 最大容量值，HashMap的最大容量值为2^30
	     if (initialCapacity > MAXIMUM_CAPACITY)
	         initialCapacity = MAXIMUM_CAPACITY;
	     //负载因子不能 < 0
	     if (loadFactor <= 0 || Float.isNaN(loadFactor))
	         throw new IllegalArgumentException("Illegal load factor: "
	                 + loadFactor);

        // 计算出大于 initialCapacity 的最小的 2 的 n 次方值。
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;
        
        this.loadFactor = loadFactor;
        //设置HashMap的容量极限，当HashMap的容量达到该极限时就会进行扩容操作
        threshold = (int) (capacity * loadFactor);
        //初始化table数组
        table = new Entry[capacity];
        init();
    }


从源码中可以看出，
	
	每次新建一个HashMap时，都会初始化一个指定容量的table数组。table数组的元素为Entry节点。并初始化相应的加载因子
.


#数据结构

	HashMap采取数组加链表的存储方式来实现。
	对应类成员中的Entry数组

![这里写图片描述](http://img.blog.csdn.net/20171209154808197?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


##均匀分布

均匀分布

	对于HashMap的table而言，数据分布需要均匀（最好每项都只有一个元素，这样就可以直接找到），不能太紧也不能太松，太紧会导致查询速度慢，太松则浪费空间。


离散算法

	根据key键值类型自带的哈希函数进行离散。
	再通过hash优化散列,可以得到比较好的随机效果

```
   //使用key的hashcode的计算hash值
   int hash = hash(key.hashCode());                  
   //计算key hash 值在 table 数组中的位置
   int i = indexFor(hash, table.length); 
   
   //JDK1.8优化散列
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
```

平均分配


	对数组长度取模可以保证平均分配，但是由于取模的消耗较大，HashMap是这样处理的

	HashMap的底层数组长度总是2的n次方
	当length = 2^n时，length – 1 = 111..(每位上都是1)，
	那么进行低位&运算时，值总是与原来hash值相同，而进行高位运算时，其值等于其低位值。
	h&(length - 1)就相当于对length取模，


##hash碰撞

	多个元素分配在数组的的同一个位置,就叫hash碰撞;发生碰撞后采用链表这个数据结构来存储数据;

	如果两个hash值相等且key值相等,则用新的Entry的value覆盖原来节点的value。
	如果两个hash值相等但key值不等 ，则将该节点插入该链表的链头。具体的实现过程见addEntry方法，如下：

```
void addEntry(int hash, K key, V value, int bucketIndex) {
        //获取bucketIndex处的Entry
        Entry<K, V> e = table[bucketIndex];
        //将新创建的 Entry 放入 bucketIndex 索引处，并让新的 Entry 指向原来的 Entry 
        table[bucketIndex] = new Entry<K, V>(hash, key, value, e);
        //若HashMap中元素的个数超过极限了，则容量扩大两倍
        if (size++ >= threshold)
            resize(2 * table.length);
    }
```


###数组长度维护


	随着HashMap中元素的数量越来越多，发生碰撞的概率就越来越大，所产生的链表长度就会越来越长，这样势必会影响HashMap的速度，为了保证HashMap的效率，系统必须要在某个临界点进行扩容处理。该临界点在当HashMap中元素的数量等于table数组长度*加载因子。但是扩容是一个非常耗时的过程，因为它需要重新计算这些数据在新table数组中的位置并进行复制处理。所以如果我们已经预知HashMap中元素的个数，那么预设元素的个数能够有效的提高HashMap的性能。


	在这里提到了两个参数：初始容量，加载因子。这两个参数是影响HashMap性能的重要参数，其中容量表示哈希表中桶的数量，初始容量是创建哈希表时的容量，加载因子是哈希表在其容量自动增加之前可以达到多满的一种尺度，它衡量的是一个散列表的空间的使用程度，负载因子越大表示散列表的装填程度越高，反之愈小。对于使用链表法的散列表来说，查找一个元素的平均时间是O(1+a)，因此如果负载因子越大，对空间的利用更充分，然而后果是查找效率的降低；如果负载因子太小，那么散列表的数据将过于稀疏，对空间造成严重浪费。系统默认负载因子为0.75，一般情况下我们是无需修改的。


#功能实现


##put

```
public V put(K key, V value) {
        //当key为null，调用putForNullKey方法，保存null与table第一个位置中，这是HashMap允许为null的原因
        if (key == null)
            return putForNullKey(value);
        //使用key的hashcode的计算hash值
        int hash = hash(key.hashCode());                  ------(1)
        //计算key hash 值在 table 数组中的位置
        int i = indexFor(hash, table.length);             ------(2)
        //从i出开始迭代 e,找到 key 保存的位置
        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
            Object k;
            //判断该条链上是否有hash值相同的(key相同)
            //若存在相同，则直接覆盖value，返回旧value
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;    //旧值 = 新值
                e.value = value;
                e.recordAccess(this);
                return oldValue;     //返回旧值
            }
        }
        //修改次数增加1
        modCount++;
        //将key、value添加至i位置处
        addEntry(hash, key, value, i);
        return null;
    }
```


首先判断key是否为null，若为null，则直接调用putForNullKey方法。
若不为空则先计算key的hash值，然后根据hash值搜索在table数组中的索引位置，
如果table数组在该位置处有元素，则通过比较是否存在相同的key，若存在则覆盖原来key的value，否则将该元素保存在链头（最先保存的元素放在链尾）。
若table在该处没有元素，则直接保存。


      

###处理key==null

	   static final int hash(Object key) {
	      int h;
	      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
		}

	HashMap将“key为null”的元素都放在table的位置0处

###Index分配

        //使用key的hashcode的计算hash值
        int hash = hash(key.hashCode());                  
        //计算key hash 值在 table 数组中的位置
        int i = indexFor(hash, table.length); 
        
        //JDK1.8优化散列
	    static final int hash(Object key) {
	        int h;
	        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	    }
		
		static int indexFor(int h, int length) {
		        return h & (length-1);
		}


##get

```
public V get(Object key) {
        // 若为null，调用getForNullKey方法返回相对应的value
        if (key == null)
            return getForNullKey();
        // 根据该 key 的 hashCode 值计算它的 hash 码  
        int hash = hash(key.hashCode());
        // 取出 table 数组中指定索引处的值
        for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            Object k;
            //若搜索的key与查找的key相同，则返回相对应的value
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }
```

	在这里能够根据key快速的取到value除了和HashMap的数据结构密不可分外，还和Entry有莫大的关系，在前面就提到过，HashMap在存储过程中并没有将key，value分开来存储，而是当做一个整体key-value来处理的，这个整体就是Entry对象。同时value也只相当于key的附属而已。在存储的过程中，系统根据key的hashcode来决定Entry在table数组中的存储位置，在取的过程中同样根据key的hashcode取出相对应的Entry对象。


##clear

	clear() 的作用是清空HashMap。它是通过将所有的元素设为null来实现的。

```
public void clear() {
    modCount++;
    Entry[] tab = table;
    for (int i = 0; i < tab.length; i++)
        tab[i] = null;
    size = 0;
}
```


##遍历


```
// 返回一个“entry迭代器”
Iterator<Map.Entry<K,V>> newEntryIterator()   {
    return new EntryIterator();
}

// Entry的迭代器
private final class EntryIterator extends HashIterator<Map.Entry<K,V>> {
    public Map.Entry<K,V> next() {
        return nextEntry();
    }
}

// HashIterator是HashMap迭代器的抽象出来的父类，实现了公共了函数。
// 它包含“key迭代器(KeyIterator)”、“Value迭代器(ValueIterator)”和“Entry迭代器(EntryIterator)”3个子类。
private abstract class HashIterator<E> implements Iterator<E> {
    // 下一个元素
    Entry<K,V> next;
    // expectedModCount用于实现fast-fail机制。
    int expectedModCount;
    // 当前索引
    int index;
    // 当前元素
    Entry<K,V> current;

    HashIterator() {
        expectedModCount = modCount;
        if (size > 0) { // advance to first entry
            Entry[] t = table;
            // 将next指向table中第一个不为null的元素。
            // 这里利用了index的初始值为0，从0开始依次向后遍历，直到找到不为null的元素就退出循环。
            while (index < t.length && (next = t[index++]) == null)
                ;
        }
    }

    public final boolean hasNext() {
        return next != null;
    }

    // 获取下一个元素
    final Entry<K,V> nextEntry() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        Entry<K,V> e = next;
        if (e == null)
            throw new NoSuchElementException();

        // 注意！！！
        // 一个Entry就是一个单向链表
        // 若该Entry的下一个节点不为空，就将next指向下一个节点;
        // 否则，将next指向下一个链表(也是下一个Entry)的不为null的节点。
        if ((next = e.next) == null) {
            Entry[] t = table;
            while (index < t.length && (next = t[index++]) == null)
                ;
        }
        current = e;
        return e;
    }

    // 删除当前元素
    public void remove() {
        if (current == null)
            throw new IllegalStateException();
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        Object k = current.key;
        current = null;
        HashMap.this.removeEntryForKey(k);
        expectedModCount = modCount;
    }

}
```

当我们通过entrySet()获取到的Iterator的next()方法去遍历HashMap时，实际上调用的是 nextEntry() 。而nextEntry()的实现方式，先遍历Entry(根据Entry在table中的序号，从小到大的遍历)；然后对每个Entry(即每个单向链表)，逐个遍历。



#概述

	jdk 8 之前，其内部是由数组+链表来实现的，
	而 jdk 8 对于链表长度超过 8 的链表将转储为红黑树。

![这里写图片描述](http://img.blog.csdn.net/20171217152501541?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



#参考

http://www.jianshu.com/p/148492ac8374


#参考

http://cmsblogs.com/?p=176
http://www.cnblogs.com/skywang12345/p/3310835.html
https://www.zhihu.com/question/20733617
