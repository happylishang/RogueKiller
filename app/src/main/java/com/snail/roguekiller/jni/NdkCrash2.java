package com.snail.roguekiller.jni;

/**
 * Author: snail
 * Data: 2019/3/23.
 * Des:
 * version:
 */
public class NdkCrash2 {
    /**
     *
     */
    static {
        System.loadLibrary("JNISample2");
    }
    public   native void nativecrash();

}
