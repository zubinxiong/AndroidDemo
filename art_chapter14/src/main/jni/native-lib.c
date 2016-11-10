#include <jni.h>

void callJavaMethod(JNIEnv *env, jobject instance) {
    jclass cls = (*env)->FindClass(env, "me/ewriter/art_chapter14/MainActivity");
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "methodCalledByJni", "(Ljava/lang/String;)V");

    jstring msg = (*env)->NewStringUTF(env, "msg send by callJavaMethod in native-lib.c");
    (*env)->CallStaticVoidMethod(cls, mid, msg);
}

JNIEXPORT jstring JNICALL
Java_me_ewriter_art_1chapter14_MainActivity_stringFromJNI(JNIEnv *env, jobject instance) {

//    callJavaMethod(env, instance);
    return (*env)->NewStringUTF(env, "Hello from JNI");
}

