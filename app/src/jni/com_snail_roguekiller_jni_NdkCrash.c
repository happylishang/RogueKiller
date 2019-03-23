//
// Created by personal on 2019/3/23.
//
#include "com_snail_roguekiller_jni_NdkCrash.h"

JNIEXPORT void JNICALL Java_com_snail_roguekiller_jni_NdkCrash_nativecrash
  (JNIEnv * env , jclass jj){

    int i=9;
    int j=0;

    i/j;
  }