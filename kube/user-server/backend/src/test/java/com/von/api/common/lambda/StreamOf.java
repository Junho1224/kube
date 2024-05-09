package com.von.api.common.lambda;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.*;

public class StreamOf {

    @Test
    public void test() {

        int arr[] = { 1, 2, 3, 4, 5 };
        IntStream intStream = Arrays.stream(arr);
        intStream.forEach(str -> System.out.print(str + " "));
        Stream<int[]> stream = Stream.of(arr);
        stream.forEach(str -> System.out.print(str + " "));

        // 1 2 3 4 5 [I@5af97850

    }

    @Test
    public void testDifferent() {

        int arr[] = { 1, 2, 3, 4, 5 };
        IntStream intStream = Arrays.stream(arr);
        intStream.forEach(str -> System.out.print(str + " "));
        System.out.println(" ");
        Stream<int[]> stream = Stream.of(arr);
        IntStream intStreamNew = stream.flatMapToInt(Arrays::stream);
        intStreamNew.forEach(str -> System.out.print(str + " "));

        // 결과 1 2 3 4 5 /n 1 2 3 4 5

    }
}
