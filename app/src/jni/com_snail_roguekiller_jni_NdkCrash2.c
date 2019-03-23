#include <jni.h>
#include <stddef.h>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include<fcntl.h>
#include <signal.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_ERROR,"lishang",__VA_ARGS__)
#define PROT  PROT_WRITE|PROT_READ
//这里其实主要是检测是不是在x86或者在arm上运行
void (*asmcheck)(void);
  void check(){

             char code[] =
                     "\xF0\x41\x2D\xE9"
                             "\x00\x70\xA0\xE3"
                             "\x0F\x80\xA0\xE1"
                             "\x00\x40\xA0\xE3"
                             "\x01\x70\x87\xE2"
                             "\x00\x50\x98\xE5"
                             "\x01\x40\x84\xE2"
                             "\x0F\x80\xA0\xE1"
                             "\x0C\x80\x48\xE2"
                             "\x00\x50\x88\xE5"
                             "\x0A\x00\x54\xE3"
                             "\x02\x00\x00\xAA"
                             "\x0A\x00\x57\xE3"
                             "\x00\x00\x00\xAA"
                             "\xF6\xFF\xFF\xEA"
                             "\x04\x00\xA0\xE1"
                             "\xF0\x81\xBD\xE8";

             void *exec = mmap(NULL, (size_t) getpagesize(), PROT, MAP_ANONYMOUS | MAP_PRIVATE, -1,
                               (off_t) 0);

                            asmcheck=      (void *) exec;
                            asmcheck();
                                        int fd = fopen("/dev/zero", "w+");
  }

  void check1(){
  check();
  }

JNIEXPORT void JNICALL Java_com_snail_roguekiller_jni_NdkCrash2_nativecrash
  (JNIEnv * env , jclass jj){

    int i=9;
    int j=0;
    int c;
check1();
    LOGI(" mmap zero %x %x %x", i/0, i/0, i/0);

  }


