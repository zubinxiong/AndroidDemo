//
// Created by Zubin on 2016/11/10.
//

#include "me_ewriter_JniTest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_me_ewriter_JniTest_get(JNIEnv *env, jobject thiz) {
    printf("invoke get in c++\n");
    return env->NewStringUTF("Hello from JNI !");
}

JNIEXPORT void JNICALL Java_me_ewriter_JniTest_set(JNIEnv *env, jobject thiz, jstring string) {
    printf("invoke set from c++\n");
    char* str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%s\n", str);
    env->ReleaseStringUTFChars(string, str);
}

