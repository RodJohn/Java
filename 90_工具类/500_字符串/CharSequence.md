CharSequence
这里写图片描述

对比
String

字符串常量
字符串不经常变化的场景中
StringBuffer

字符串变量（线程安全）
在频繁进行字符串的运算（拼接、替换、删除等）
StringBuilder

字符串变量（非线程安全）    
在频繁进行字符串的运算（拼接、替换、删除等）
安全的多线程的环境中，如SQL语句的拼装、JSON封装等
String
实现了CharSequence接口
String类为final，不能被继承
String内部维护一个不可变的char数组.所以几乎每一个修改String对象的操作，实际上都是创建了一个全新的String对象
类结构
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    private final char value[];
    private int hash; // Default to 0
}
1
2
3
4
String类是final的，不允许被继承
String类的内部就是维护了一个常量char数组；
构造
String() 
使用空字符串(“”)构造字符串对象

public String() {
  this.value = "".value;
}
1
2
3
4
String(char value[]) 
使用字符串数组构造字符串对象

public String(char value[]) {
   this.value = Arrays.copyOf(value, value.length);
}
1
2
3
方法
“对String对象的任何改变都不影响到原对象，相关的任何change操作都会生成新的对象”。

public String subString(int beginIndex)
返回一个新的字符串，它是此字符串的一个子字符串。

public String replace(CharSequence target，CharSequence replacement)
把原来的etarget子序列替换为replacement序列，返回新串。
equals
equals() 是object的方法，默认情况下，它与== 一样，比较的地址。 
但是String 类就重写了这个方法。改为数组遍历比较

public boolean equals(Object anObject) {
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
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
AbstractStringBuilder
实现了CharSequence接口
内部维护一个普通char[]
类结构
abstract class AbstractStringBuilder implements Appendable, CharSequence {

    char[] value;

    int count;

    AbstractStringBuilder() {
    }

    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
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
常用方法
  1、append()：追加指定内容到当前StringBuffer对象的末尾，类似于字符串的连接，这里StringBuffer对象的内容会发生改变。
  2、insert：该类方法主要是在StringBuffer对象中插入内容。
  3、delete：该类方法主要用于移除StringBuffer对象中的内容
StringBuilder/StringBuffer
StringBuilder和StringBuffer是AbstractStringBuilder的实现类;
拥有的成员属性以及成员方法基本相同，
区别是StringBuffer是线程安全的。
类结构
StringBuilder

public final class StringBuilder
    extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence
{

    static final long serialVersionUID = 4383685877147921099L;

    public StringBuilder() {
        super(16);
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
StringBuffer

 public final class StringBuffer
    extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence
{

    private transient char[] toStringCache;

    static final long serialVersionUID = 3388685877147921107L;

    public StringBuffer() {
        super(16);
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
功能实现
下面摘了2段代码分别来自StringBuffer和StringBuilder，insert方法的具体实现：

public StringBuilder insert(int index, char str[], int offset,int len)
  {
      super.insert(index, str, offset, len);
      return this;
  }
1
2
3
4
5
public synchronized StringBuffer insert(int index, char str[], int offset,int len)
    {
        super.insert(index, str, offset, len);
        return this;
    }
1
2
3
4
5
拼接
string–concat

创建新数组 
填充数组 
转化为新的String对象

public String concat(String str) {
    int otherLen = str.length();
    if (otherLen == 0) {
        return this;
    }
    char buf[] = new char[count + otherLen];
    getChars(0, count, buf, 0);
    str.getChars(0, otherLen, buf, count);
    return new String(0, count + otherLen, buf);
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
append

(创建新数组) 
填充数组 
返回本身

public AbstractStringBuilder append(String str) {
    if (str == null) str = "null";
        int len = str.length();
    if (len == 0) return this;
    int newCount = count + len;
    if (newCount > value.length)
        expandCapacity(newCount);
    str.getChars(0, len, value, count);
    count = newCount;
    return this;
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
+

String S3 = "simple";
String S4 = "test";
String S1 = S3 + S4;
编译器将提前编译成 String S1 = new StringBuilder(str).append(“b”).toString();

创建StringBuilder 
append 
转化为string

String S1 = “ simple" + “ test";
编译器将提前编译成String S1 = “ simpletest”;

有趣的面试题
http://www.cnblogs.com/zuoxiaolong/p/lang1.html

参考
http://www.importnew.com/16881.html 
http://www.jianshu.com/p/2f209af80f84