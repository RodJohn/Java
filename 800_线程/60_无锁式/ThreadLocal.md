ThreadLocal与多线程
多线程的目的是

ThreadLocal是为每一个线程创建一个单独的变量副本，故而每个线程都可以独立地改变自己所拥有的变量副本，而不会影响其他线程所对应的副本。
原理
ThreadLocal的实现是这样的：

每个Thread 维护一个 ThreadLocalMap 映射表，这个映射表的 key 是 ThreadLocal 实例本身，value 是真正需要存储的 Object。
也就是说 ThreadLocal 本身并不存储值，它只是作为一个 key 来让线程从 ThreadLocalMap 获取 value。
这里写图片描述

Set方法
ThreadLocal的set方法分析

   private void set(ThreadLocal<?> key, Object value) {

        ThreadLocal.ThreadLocalMap.Entry[] tab = table;
        int len = tab.length;

        // 根据 ThreadLocal 的散列值，查找对应元素在数组中的位置
        int i = key.threadLocalHashCode & (len-1);

        // 采用“线性探测法”，寻找合适位置
        for (ThreadLocal.ThreadLocalMap.Entry e = tab[i];
            e != null;
            e = tab[i = nextIndex(i, len)]) {

            ThreadLocal<?> k = e.get();

            // key 存在，直接覆盖
            if (k == key) {
                e.value = value;
                return;
            }

            // key == null，但是存在值（因为此处的e != null），说明之前的ThreadLocal对象已经被回收了
            if (k == null) {
                // 用新元素替换陈旧的元素
                replaceStaleEntry(key, value, i);
                return;
            }
        }

        // ThreadLocal对应的key实例不存在也没有陈旧元素，new 一个
        tab[i] = new ThreadLocal.ThreadLocalMap.Entry(key, value);

        int sz = ++size;

        // cleanSomeSlots 清楚陈旧的Entry（key == null）
        // 如果没有清理陈旧的 Entry 并且数组中的元素大于了阈值，则进行 rehash
        if (!cleanSomeSlots(i, sz) && sz >= threshold)
            rehash();
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
范例
public class SeqCount {

    private static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>(){
        // 实现initialValue()
        public Integer initialValue() {
            return 0;
        }
    };

    public int nextSeq(){
        seqCount.set(seqCount.get() + 1);

        return seqCount.get();
    }

    public static void main(String[] args){
        SeqCount seqCount = new SeqCount();

        SeqThread thread1 = new SeqThread(seqCount);
        SeqThread thread2 = new SeqThread(seqCount);
        SeqThread thread3 = new SeqThread(seqCount);
        SeqThread thread4 = new SeqThread(seqCount);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private static class SeqThread extends Thread{
        private SeqCount seqCount;

        SeqThread(SeqCount seqCount){
            this.seqCount = seqCount;
        }

        public void run() {
            for(int i = 0 ; i < 3 ; i++){
                System.out.println(Thread.currentThread().getName() + " seqCount :" + seqCount.nextSeq());
            }
        }
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
参考
http://cmsblogs.com/?p=2442 
http://www.jianshu.com/p/dde92ec37bd1