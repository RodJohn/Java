常识
字符串常量池

每当我们创建字符串常量时，JVM会首先检查字符串常量池，如果该字符串已经存在常量池中，那么就直接返回常量池中的实例引用。如果字符串不存在常量池中，就会实例化该字符串并且将其放到常量池中。
new

通过new关键字来生成对象是在堆区进行的，而在堆区进行对象生成的过程是不会去检测该对象是否已经存在的。因此通过new来创建对象，创建出的一定是不同的对象，即使字符串的内容是相同的。
赋值
字面量赋值
代码

public class StringTest {
    public static void main(String[] args) {
        String a = "java";
        String b = "java";
        String c = "ja" + "va";
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
反编译的字节码

这里写图片描述

分析

变量a、b和c都指向常量池的 "java" 字符串，
常量表达式 "ja" + "va" 在编译期间会把结果值"java"直接赋值给c。
对象传递
代码

public class StringTest {
    public static void main(String[] args) {
        String a = "java";
        String c = new String("java");
    }
}
1
2
3
4
5
6
反编译

这里写代码片

分析

第3行new指令，在Java堆上为String对象申请内存；
第7行ldc指令，尝试从常量池中获取"java"字符串，如果常量池中不存在，则在常量池中新建"java"字符串，并返回；
第9行invokespecial指令，调用构造方法，初始化String对象。
对象运算后传递
代码

public class StringTest {
    public static void main(String[] args) {
        String a = "hello ";
        String b = "world";
        String c = a + b;
        String d = "hello world";
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
反编译

这里写图片描述

分析

1、第6行new指令，在Java堆上为StringBuilder对象申请内存；
2、第10行invokespecial指令，调用构造方法，初始化StringBuilder对象；
3、第14、18行invokespecial指令，调用append方法，添加a和b字符串；
4、第21行invokespecial指令，调用toString方法，生成String对象。
final变量传递
代码

public class StringTest {
    public static void main(String[] args) {
        final String a = "hello ";
        final String b = "world";
        String c = a + b;
        String d = "hello world";
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
反编译

这里写图片描述

分析 
final声明后的对象, 
JVM直接操作常量池中的数据

图
这里写图片描述

参考
http://www.jianshu.com/p/2f209af80f84