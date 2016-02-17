/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "bitshuffle.h"
#include "ch_psi_bitshuffle_BitShuffleLZ4JNI.h"

static jclass OutOfMemoryError;

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_init
  (JNIEnv *env, jclass cls) {
  OutOfMemoryError = (*env)->FindClass(env, "java/lang/OutOfMemoryError");
}

static void throw_OOM(JNIEnv *env) {
  (*env)->ThrowNew(env, OutOfMemoryError, "Out of memory");
}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_bitshuffle
 * Signature: ([BLjava/nio/ByteBuffer;I[BLjava/nio/ByteBuffer;IIII)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1bitshuffle
  (JNIEnv *env, jclass cls, jbyteArray srcArray, jobject srcBuffer, jint srcOff, jbyteArray destArray, jobject destBuffer, jint destOff, jint nElements, jint bytesPerElement, jint blockSize) {

  char* in;
  char* out;
  jint bytesProcessed;

  if (srcArray != NULL) {
    in = (char*) (*env)->GetPrimitiveArrayCritical(env, srcArray, 0);
  } else {
    in = (char*) (*env)->GetDirectBufferAddress(env, srcBuffer);
  }

  if (in == NULL) {
    throw_OOM(env);
    return 0;
  }

  if (destArray != NULL) {
    out = (char*) (*env)->GetPrimitiveArrayCritical(env, destArray, 0);
  } else {
    out = (char*) (*env)->GetDirectBufferAddress(env, destBuffer);
  }

  if (out == NULL) {
    throw_OOM(env);
    return 0;
  }

  bytesProcessed = bshuf_bitshuffle(in + srcOff, out + destOff, nElements, bytesPerElement, blockSize);

  if (srcArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, srcArray, in, 0);
  }
  if (destArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, destArray, out, 0);
  }

  return bytesProcessed;
}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_bitunshuffle
 * Signature: ([BLjava/nio/ByteBuffer;I[BLjava/nio/ByteBuffer;IIII)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1bitunshuffle
  (JNIEnv *env, jclass cls, jbyteArray srcArray, jobject srcBuffer, jint srcOff, jbyteArray destArray, jobject destBuffer, jint destOff, jint nElements, jint bytesPerElement, jint blockSize) {

  char* in;
  char* out;
  jint bytesProcessed;

  if (srcArray != NULL) {
    in = (char*) (*env)->GetPrimitiveArrayCritical(env, srcArray, 0);
  } else {
    in = (char*) (*env)->GetDirectBufferAddress(env, srcBuffer);
  }

  if (in == NULL) {
    throw_OOM(env);
    return 0;
  }

  if (destArray != NULL) {
    out = (char*) (*env)->GetPrimitiveArrayCritical(env, destArray, 0);
  } else {
    out = (char*) (*env)->GetDirectBufferAddress(env, destBuffer);
  }

  if (out == NULL) {
    throw_OOM(env);
    return 0;
  }

  bytesProcessed = bshuf_bitunshuffle(in + srcOff, out + destOff, nElements, bytesPerElement, blockSize);

  if (srcArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, srcArray, in, 0);
  }
  if (destArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, destArray, out, 0);
  }

  return bytesProcessed;
}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_LZ4_compress
 * Signature: ([BLjava/nio/ByteBuffer;I[BLjava/nio/ByteBuffer;IIII)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1LZ4_1compress
  (JNIEnv *env, jclass cls, jbyteArray srcArray, jobject srcBuffer, jint srcOff, jbyteArray destArray, jobject destBuffer, jint destOff, jint nElements, jint bytesPerElement, jint blockSize) {

  char* in;
  char* out;
  jint compressed;

  if (srcArray != NULL) {
    in = (char*) (*env)->GetPrimitiveArrayCritical(env, srcArray, 0);
  } else {
    in = (char*) (*env)->GetDirectBufferAddress(env, srcBuffer);
  }

  if (in == NULL) {
    throw_OOM(env);
    return 0;
  }

  if (destArray != NULL) {
    out = (char*) (*env)->GetPrimitiveArrayCritical(env, destArray, 0);
  } else {
    out = (char*) (*env)->GetDirectBufferAddress(env, destBuffer);
  }

  if (out == NULL) {
    throw_OOM(env);
    return 0;
  }

  compressed = bshuf_compress_lz4(in + srcOff, out + destOff, nElements, bytesPerElement, blockSize);

  if (srcArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, srcArray, in, 0);
  }
  if (destArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, destArray, out, 0);
  }

  return compressed;

}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_LZ4_decompress
 * Signature: ([BLjava/nio/ByteBuffer;I[BLjava/nio/ByteBuffer;IIII)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1LZ4_1decompress
  (JNIEnv *env, jclass cls, jbyteArray srcArray, jobject srcBuffer, jint srcOff, jbyteArray destArray, jobject destBuffer, jint destOff, jint nElements, jint bytesPerElement, jint blockSize) {

  char* in;
  char* out;
  jint decompressed;
  
  if (srcArray != NULL) {
    in = (char*) (*env)->GetPrimitiveArrayCritical(env, srcArray, 0);
  } else {
    in = (char*) (*env)->GetDirectBufferAddress(env, srcBuffer);
  } 
  
  if (in == NULL) {
    throw_OOM(env);
    return 0;
  } 
    
  if (destArray != NULL) {
    out = (char*) (*env)->GetPrimitiveArrayCritical(env, destArray, 0);
  } else {
    out = (char*) (*env)->GetDirectBufferAddress(env, destBuffer);
  } 
  
  if (out == NULL) {
    throw_OOM(env);
    return 0;
  }

  decompressed = bshuf_decompress_lz4(in + srcOff, out + destOff, nElements, bytesPerElement, blockSize);

  if (srcArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, srcArray, in, 0);
  }
  if (destArray != NULL) {
    (*env)->ReleasePrimitiveArrayCritical(env, destArray, out, 0);
  }

  return decompressed;

}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_LZ4_bound
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1LZ4_1bound
  (JNIEnv *env, jclass cls, jint nElements, jint bytesPerElement, jint blockSize) {

  return bshuf_compress_lz4_bound(nElements, bytesPerElement, blockSize);

}

/*
 * Class:     ch_psi_bitshuffle_BitShuffleLZ4JNI
 * Method:    Bitshuffle_default_block_size
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_ch_psi_bitshuffle_BitShuffleLZ4JNI_Bitshuffle_1default_1block_1size
  (JNIEnv *env, jclass cls, jint bytesPerElement) {

  return bshuf_default_block_size(bytesPerElement);

}
