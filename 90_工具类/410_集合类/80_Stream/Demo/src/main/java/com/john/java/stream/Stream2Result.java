package com.john.java.stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stream2Result {



    /**
     * 结果处理
     */
    public void resultEach(){

        //遍历
        User.users.forEach(user -> System.out.println(user.getName()));

    }

    /**
     * Collector转为集合
     */
    public void resultCollection() {
        User.users.toArray();
        User.users.collect(Collectors.toSet());
        User.users.collect(Collectors.toList());
        User.users.collect(Collectors.toMap(User::getId, user -> user));
        //若Map中key重复，在运行时会报异常java.lang.IllegalStateException: Duplicate key **
    }


    /**
     * Collector计算结果
     */
    public void resultSingle() {
        User.users.collect(Collectors.maxBy(Comparator.comparing(item -> item.getId())));
        User.users.collect(Collectors.maxBy(Comparator.comparing(item -> item.getId())));
    }


    /**
     * Collector自定义分组
     */
    @Test
    public void resultGroup() {
        Map<Integer, List<User>> map =
                User.users.collect(Collectors.groupingBy(user -> user.getId()));
        System.out.println(map);
    }
    //{1=[User{id=1, name='John'}], 2=[User{id=2, name='lijun'}], 10=[User{id=10, name='John'}], 12=[User{id=12, name='lijun'}]}


    /**
     * Collector字符串数组拼接
     */
    @Test
    public void resultStringJoin() {
        String join = User.users.map(user -> user.getName()).collect(Collectors.joining(",", "[", "]"));
        System.out.println(join);
    }


}
