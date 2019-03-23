LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := JNISample
LOCAL_SRC_FILES :=com_snail_roguekiller_jni_NdkCrash.c

include $(BUILD_SHARED_LIBRARY)