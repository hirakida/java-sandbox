#include "com_example_HelloJNI.h"

JNIEXPORT void JNICALL Java_com_example_HelloJNI_hello(JNIEnv *env, jobject obj) {
  printf("Hello!\n");
}
