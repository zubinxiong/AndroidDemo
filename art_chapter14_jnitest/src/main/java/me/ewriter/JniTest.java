package me.ewriter;

public class JniTest {

    static {
        System.loadLibrary("jni_test");
    }

    public static void main(String[] args) {
        JniTest jniTest = new JniTest();
        System.out.print(jniTest.get());
        jniTest.set("hello world");
    }

    public native String get();
    public native void set(String str);
}
