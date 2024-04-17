package com.von.api.common.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemTest {
    @Test
    void testPrint() {
        Item s = new Item();
        String s3 = s.print();
    System.out.println("-->"+s3);
        String s2 = "Hello";
        Assertions.assertEquals(s.print(), "Hello");

    }

    void testAdd() {



        
    }
}
