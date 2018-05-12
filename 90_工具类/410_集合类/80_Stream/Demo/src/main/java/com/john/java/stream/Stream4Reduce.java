package com.john.java.stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream4Reduce {

    /**
     * 第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素
     */
    @Test
    public void reduce1() {
        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : " + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult.get());
        System.out.println("--------");
    }
    //    acc : 1
    //    item: 2
    //    acc+ : 3
    //            --------
    //    acc : 3
    //    item: 3
    //    acc+ : 6
    //            --------
    //    acc : 6
    //    item: 4
    //    acc+ : 10
    //            --------
    //    accResult: 10
    //            --------

}
