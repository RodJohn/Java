


# 3 创建Stream

## 3.1 静态工厂方法

### 3.1.1 empty

作用

    生成空的Stream
    
示例

     Stream.empty()    

### 3.1.2 of (*)

作用

    生成指定长度的Stream
    
示例

    Stream<Integer> integerStream = Stream.of(1, 2, 3);
    Stream<String> stringStream = Stream.of("A");

### 3.1.3 generator

作用

    生成一个无限长度的Stream

示例

    Stream<Double> generateB = Stream.generate(()-> java.lang.Math.random());

### 3.1.4 iterate

## 3.2 Collection stream()

    new ArrayList<>().stream();
    new HashSet<>().stream();

## 3.3 Array stream()    

    int ids[] = new int[]{1, 2, 3, 4};
    Arrays.stream(ids)      

## 3.5 其他


Pattern.splitAsStream(java.lang.CharSequence)


# Intermediate

concat
concat方法将两个Stream连接在一起，合成一个Stream。若两个输入的Stream都时排序的，则新Stream也是排序的；若输入的Stream中任何一个是并行的，则新的Stream也是并行的；若关闭新的Stream时，原两个输入的Stream都将执行关闭处理。


# 参考

https://blog.csdn.net/io_field/article/details/54971761