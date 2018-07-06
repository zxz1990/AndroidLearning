package com.example.zxz.androidtest.java;

import java.util.Stack;

public class RandomSequence {

    static void test() {
        String[] names = new String[20];
        for (int i = 0; i < names.length; ++i) {
            names[i] = "名字" + i;
        }

        Stack<String> stack = new Stack<>();
        int size = names.length;
        for(int i=0;i<names.length;++i) {
            int j = (int)(Math.random()*size);


            System.out.println(names[j]);


            stack.push(names[j]);

            names[j]= names[--size];



        }

        System.out.println("stack:");
        while(!stack.empty()) {
            System.out.println(stack.pop());
        }

    }

    public static void main(String[] args) {
        test();
    }
}
