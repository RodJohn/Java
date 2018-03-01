
# HashTable

	不推荐的
	线程安全的
	不允许null键null值得
	基于数组和链表结构的
	Map实现类


# HashMap比较HashTale

# # 相同

	类结构(Map)
	类成员(Entry数组)
	数据模型(内部用Entry数组实现哈希表,用链表解决hash冲突))
	

# # 时间

	HashTable产生于JDK 1.1，而HashMap产生于JDK 1.2。

# # 不推荐使用HashTable

以下描述来自于HashTable的类注释：

	If a thread-safe implementation is not needed, it is recommended to use HashMap in place of Hashtable. 
	If a thread-safe highly-concurrent implementation is desired, then it is recommended to use java.util.concurrent.ConcurrentHashMap in place of Hashtable.

# # 类结构

hashMap

```
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {
```

hashtable
```
public class Hashtable<K,V>
    extends Dictionary<K,V>
    implements Map<K,V>, Cloneable, java.io.Serializable
```


# # Null Key & Null Value

HashMap是支持null键和null值的，而HashTable在遇到null时，会抛出NullPointerException异常。

	因为HashMap在实现时对null做了特殊处理，将null的hashCode值定为了0，从而将其存放在哈希表的第0个bucket中。


```
以下代码及注释来自java.util.HashTable
 
public synchronized V put(K key, V value) {
 
    // 如果value为null，抛出NullPointerException
    if (value == null) {
        throw new NullPointerException();
    }
 
    // 如果key为null，在调用key.hashCode()时抛出NullPointerException
 
    // ...
}
 

```

```
以下代码及注释来自java.util.HasMap
 
public V put(K key, V value) {
    if (table == EMPTY_TABLE) {
        inflateTable(threshold);
    }
    // 当key为null时，调用putForNullKey特殊处理
    if (key == null)
        return putForNullKey(value);
    // ...
}
 
private V putForNullKey(V value) {
    // key为null时，放到table[0]也就是第0个bucket中
    for (Entry<K,V> e = table[0]; e != null; e = e.next) {
        if (e.key == null) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    modCount++;
    addEntry(0, null, value, 0);
    return null;
}
```

# # 平均分配的算法


# # # 容量

```
以下代码及注释来自java.util.HashTable
 
// 哈希表默认初始大小为11
public Hashtable() {
    this(11, 0.75f);
}
 
protected void rehash() {
    int oldCapacity = table.length;
    Entry<K,V>[] oldMap = table;
 
    // 每次扩容为原来的2n+1
    int newCapacity = (oldCapacity << 1) + 1;
    // ...
}
 
 
以下代码及注释来自java.util.HashMap
 
// 哈希表默认初始大小为2^4=16
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
 
void addEntry(int hash, K key, V value, int bucketIndex) {
    // 每次扩充为原来的2n 
    if ((size >= threshold) && (null != table[bucketIndex])) {
       resize(2 * table.length);
}
```

# # # 分配

java.util.HashTable
```
// hash 不能超过Integer.MAX_VALUE 所以要取其最小的31个bit
int hash = hash(key);
int index = (hash & 0x7FFFFFFF) % tab.length;
 
// 直接计算key.hashCode()
private int hash(Object k) {
    // hashSeed will be zero if alternative hashing is disabled.
    return hashSeed ^ k.hashCode();
}
 
```
	哈希表的大小为素数时，简单的取模哈希的结果会更加均匀


java.util.HashMap
```
int hash = hash(key);
int i = indexFor(hash, table.length);
 
// 在计算了key.hashCode()之后，做了一些位运算来减少哈希冲突
final int hash(Object k) {
    int h = hashSeed;
    if (0 != h && k instanceof String) {
        return sun.misc.Hashing.stringHash32((String) k);
    }
 
    h ^= k.hashCode();
 
    // This function ensures that hashCodes that differ only by
    // constant multiples at each bit position have a bounded
    // number of collisions (approximately 8 at default load factor).
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
 
// 取模不再需要做除法
static int indexFor(int h, int length) {
    // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
    return h & (length-1);
}
```
	HashMap由于使用了2的幂次方，所以在取模运算时不需要做除法，只需要位的与运算就可以了。但是由于引入的hash冲突加剧问题，HashMap在调用了对象的hashCode方法之后，又做了一些位运算在打散数据。



# #  线程安全

	HashTable是同步的，是直接对大部分的方法加同步锁.
	
```
public synchronized V get(Object key) {
    Entry tab[] = table;
    int hash = hash(key);
    int index = (hash & 0x7FFFFFFF) % tab.length;
    for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
        if ((e.hash == hash) && e.key.equals(key)) {
            return e.value;
        }
    }
    return null;
}
```