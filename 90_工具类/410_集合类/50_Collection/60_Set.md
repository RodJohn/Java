

#概述

	
	Set是一个不允许有重复元素的集合
	Set的实现类都是基于Map来实现的。(value是new Object())


#类结构

![这里写图片描述](http://img.blog.csdn.net/20171215093500198?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)





(01) Set 是继承于Collection的接口。它是一个不允许有重复元素的集合。
(02) AbstractSet 是一个抽象类，它继承于AbstractCollection，AbstractCollection实现了Set中的绝大部分函数，为Set的实现类提供了便利。
(03) HastSet 和 TreeSet 是Set的两个实现类。
        HashSet依赖于HashMap，它实际上是通过HashMap实现的。HashSet中的元素是无序的。
        TreeSet依赖于TreeMap，它实际上是通过TreeMap实现的。TreeSet中的元素是有序的。



#HashSet


类结构

![这里写图片描述](http://img.blog.csdn.net/20171215093700303?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

	public class HashSet<E>
	    extends AbstractSet<E>
	    implements Set<E>, Cloneable, java.io.Serializable{
	    private static final Object PRESENT = new Object();

分析
	
	HashSet 是一个没有重复元素的集合。
	它是由HashMap实现的，
	不保证元素的顺序，
	而且HashSet允许使用 null 元素。
	HashSet是非同步的。


#TreeSet

	

类结构


	public class TreeSet<E> extends AbstractSet<E>
	    implements NavigableSet<E>, Cloneable, java.io.Serializable{
	    private static final Object PRESENT = new Object();

![这里写图片描述](http://img.blog.csdn.net/20171215093949578?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

分析

	TreeSet是一个没有重复元素的集合。
	它是由TreeMap实现的，
	保证元素的顺序，
	TreeMap是否允许null 元素(依靠comparator)。
	TreeMap是非同步的。