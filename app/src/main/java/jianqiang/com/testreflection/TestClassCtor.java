package jianqiang.com.testreflection;

import android.util.Log;

public class TestClassCtor {
    private String name;


    private static String address = "abc";

    public TestClassCtor() {
        name = "baobao";
    }

    public TestClassCtor(int a) {

    }

    public TestClassCtor(int a, String b) {
        name = b;
    }

    private TestClassCtor(int a, double c) {

    }

    private String doSOmething(String d) {
        Log.v("baobao", "TestClassCtor, doSOmething");

        return "abcd";
    }

    private static void work() {
        Log.v("baobao", "TestClassCtor, work");
    }

    public String getName() {
        return name;
    }

    public static void printAddress() {
        Log.v("baobao", address);
    }
}
