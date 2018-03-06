概述
Java还提供了一种弱形式的同步，即使用了volatile关键字。该关键字确保了对一个变量的更新对其他线程可见。当一个变量被声明为volatile时候，线程写入时候不会把值缓存在寄存器或者或者在其他地方，当线程读取的时候会从主内存重新获取最新值，而不是使用当前线程的拷贝内存变量值。




Java 语言中的 volatile 变量可以被看作是一种 “程度较轻的 synchronized”；与 synchronized 块相比，volatile 变量所需的编码较少，并且运行时开销也较少，但是它所能实现的功能也仅是 synchronized 的一部分。
只能在有限的一些情形下使用 volatile 变量替代锁
可见
volatile boolean asleep;
    ...
        while(!asleep){
            ...
        }
1
2
3
4
5
volatile变量通常用做某个操作完成，发生中断或者作为状态的标志。volatile的语义不足以确保递增操作的原子性。也就是说，加锁机制既可以确保可见性又可以确保原子性，而volatile变量只能确保可见性。

参考
http://www.jianshu.com/p/15f9f54f8e3f 
https://www.ibm.com/developerworks/cn/java/j-jtp06197.html 
https://www.cnblogs.com/peterxiao/p/6715684.html