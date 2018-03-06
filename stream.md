
Stream
转换
常用方法
常用
MapReduce
数值流
参考

Stream
转换
转换为stream
1.定义流
Stream<String> stream = Stream.of("chaimm","peter","john");
2.数组转换为流
String[] names = {"chaimm","peter","john"};
Stream<String> stream = Arrays.stream(names);
3.list转流
List<Person> list = new ArrayList<Person>(); 
Stream<Person> stream = list.stream();
转换为集合
1.



常用方法
常用
过滤
filter函数接收一个Lambda表达式作为参数，该表达式返回boolean，
在执行过程中，流将元素逐一输送给filter，并筛选出执行结果为true的元素。 
如，筛选出所有学生：
List<Person> result = list.stream().filter(Person::isStudent).collect(toList());
去重
List<Person> result = list.stream().distinct().collect(toList());
截取
截取流的前N个元素：
List<Person> result = list.stream().limit(3).collect(toList());
跳过流的前n个元素：
List<Person> result = list.stream().skip(3).collect(toList());


					
判断
是否匹配任一元素：anyMatch
anyMatch用于判断流中是否存在至少一个元素满足指定的条件，
这个判断条件通过Lambda表达式传递给anyMatch，执行结果为boolean类型。 
如，判断list中是否有学生：
boolean result = list.stream().anyMatch(Person::isStudent);

 	是否匹配所有元素：allMatch
allMatch用于判断流中的所有元素是否都满足指定条件，
这个判断条件通过Lambda表达式传递给anyMatch，执行结果为boolean类型。 
如，判断是否所有人都是学生：
boolean result = list.stream().allMatch(Person::isStudent);

MapReduce
map
对流中的每个元素执行一个函数，使得元素转换成另一种类型输出。
流会将每一个元素输送给map函数，并执行map中的Lambda表达式，最后将执行结果存入一个新的流中。 
如，获取每个人的姓名(实则是将Perosn类型转换成String类型)：
List<String> result = list.stream().map(Person::getName).collect(toList());

reduce
在流中，reduce函数能实现归约。 
reduce函数接收两个参数：

初始值
进行归约操作的Lambda表达式
自定义Lambda表达式实现求和
int age = list.stream().reduce(0, (person1,person2)->person1.getAge()+person2.getAge());
reduce的第一个参数表示初试值为0； 
reduce的第二个参数为需要进行的归约操作，它接收一个拥有两个参数的Lambda表达式，reduce会把流中的元素两两输给Lambda表达式，最后将计算出累加之和。


数值流

采用reduce进行数值操作会涉及到基本数值类型和引用数值类型之间的装箱、拆箱操作，因此效率较低。 
当流操作为纯数值操作时，使用数值流能获得较高的效率。

 将普通流转换成数值流
StreamAPI提供了三种数值流：IntStream、DoubleStream、LongStream，也提供了将普通流转换成数值流的三种方法：mapToInt、mapToDouble、mapToLong。 
如，将Person中的age转换成数值流：
IntStream stream = list.stream().mapToInt(Person::getAge);

数值计算
每种数值流都提供了数值计算函数，如max、min、sum等。 
如，找出最大的年龄：
OptionalInt maxAge = list.stream() .mapToInt(Person::getAge) .max();
由于数值流可能为空，并且给空的数值流计算最大值是没有意义的，因此max函数返回OptionalInt，它是Optional的一个子类，能够判断流是否为空，并对流为空的情况作相应的处理。 
此外，mapToInt、mapToDouble、mapToLong进行数值操作后的返回结果分别为：OptionalInt、OptionalDouble、OptionalLong	


案例
list 转化为map
http://www.importnew.com/17313.html


举例
list转map 
public Map<Long, String> getIdNameMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getId, Account::getUsername));
}


参考
http://blog.csdn.net/u010425776/article/details/52344425
https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/							
