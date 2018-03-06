
#HashMap的不安全方面

##put丢失元素

多线程put的时候可能导致元素丢失。

	考虑在多线程下put操作时，执行addEntry(hash, key, value, i)，如果有产生哈希碰撞，导致两个线程得到同样的bucketIndex去存储，就可能会出现覆盖丢失的情况




##扩容时形成循环列表




###扩容方法

扩容包括建立新的数组和将原先的数据重新分配在新的数组中;

重新分配采用的是链表的依次头插法

	把原table的Entry取出来，
	将Entry的next元素,作为下次操作的元素
	计算Entry的新下标，
	将这个Entry放入新的table指定位置的链头，原来的Entry接在后面
	

![这里写图片描述](http://img.blog.csdn.net/20171217155521619?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


```
void resize(int newCapacity) {
472         Entry[] oldTable = table;
473         int oldCapacity = oldTable.length;
474         if (oldCapacity == MAXIMUM_CAPACITY) {
475             threshold = Integer.MAX_VALUE;
476             return;
477         }
478 
479         Entry[] newTable = new Entry[newCapacity];
480         transfer(newTable);
481         table = newTable;
482         threshold = (int)(newCapacity * loadFactor);
483     }
```

```
void transfer(Entry[] newTable) {
489         Entry[] src = table;
490         int newCapacity = newTable.length;
491         for (int j = 0; j < src.length; j++) {
492             Entry<K,V> e = src[j];
493             if (e != null) {
494                 src[j] = null;
495                 do {
496                     Entry<K,V> next = e.next;//用于判断后面循环是否继续
497                     int i = indexFor(e.hash, newCapacity);
498                     e.next = newTable[i];
499                     newTable[i] = e;
500                     e = next;
501                 } while (e != null);
502             }
503         }
504     }
```









###并发时的问题

	两条线程同时扩容同一个HashMap

![这里写图片描述](http://img.blog.csdn.net/20171217160602678?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

	
线程一 

	执行完 next = e.next;被阻塞;
	此时e= 3 ;next=7
线程二

	
	获取CPU后,使用头插法,完成对 3 7 的设置

![这里写图片描述](http://img.blog.csdn.net/20171217161149449?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

线程一 

	获得CPU,此时e= 3 ;next=7,
	完成剩下的头插法:将3插到线程一的位置
	接下来;头插7  
	头插7之前,明确一点(如上图),也就是7.next=3
	所以插完7 ,头插3
	形成闭环
	
![这里写图片描述](http://img.blog.csdn.net/20171217161955617?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


![这里写图片描述](http://img.blog.csdn.net/20171217162302033?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)





#线程安全的Map

##HashTable
	
	public synchronized V get(Object key){}
	public synchronized V put(K key, V value) {}
	public synchronized V remove(Object key){}
	
	单线程情况下，也会加锁


但由迭代器返回的 Iterator 和由所有 Hashtable 的“collection 视图方法”返回的 Collection 的 listIterator 方法都是快速失败的：在创建 Iterator 之后，如果从结构上对 Hashtable 进行修改，除非通过 Iterator 自身的移除或添加方法，否则在任何时间以任何方式对其进行修改，Iterator 都将抛出 ConcurrentModificationException。因此，面对并发的修改，Iterator 很快就会完全失败，而不冒在将来某个不确定的时间发生任意不确定行为的风险。由 Hashtable 的键和值方法返回的 Enumeration 不是快速失败的。


	
##synchronizedMap

返回由指定映射支持的同步（线程安全的）映射。为了保证按顺序访问，必须通过返回的映射完成对底层映射的所有访问。在返回的映射或其任意 collection 视图上进行迭代时，强制用户手工在返回的映射上进行同步：


##ConcurrentHashMap

支持检索的完全并发和更新的所期望可调整并发的哈希表。此类遵守与 Hashtable 相同的功能规范，并且包括对应于 Hashtable 的每个方法的方法版本。不过，尽管所有操作都是线程安全的，但检索操作不必锁定，并且不支持以某种防止所有访问的方式锁定整个表。此类可以通过程序完全与 Hashtable 进行互操作，这取决于其线程安全，而与其同步细节无关。



#参考

形成循环链表的原因
https://coolshell.cn/articles/9606.html

并发问题的原因
https://www.cnblogs.com/-new/p/7496323.html
https://www.cnblogs.com/andy-zhou/p/5402984.html

谢谢这些大佬,我看了好几次才看懂,同时还盗(阿Q说的)了他们的图


