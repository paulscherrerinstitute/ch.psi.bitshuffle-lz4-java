package ch.psi.bitshuffle;

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

import java.nio.ByteBuffer;

import ch.psi.bitshuffle.util.Native;

/**
 * JNI bindings to the original C implementation.
 */
enum BitShuffleLZ4JNI {
  ;

  static {
    Native.load();
    init();
  }

  static native void init();

  static native int Bitshuffle_bitshuffle(byte[] srcArray,
      ByteBuffer srcBuffer, int srcOff, byte[] destArray,
      ByteBuffer destBuffer, int destOff, int nElements, int bytesPerElement,
      int blockSize);

  static native int Bitshuffle_bitunshuffle(byte[] srcArray,
      ByteBuffer srcBuffer, int srcOff, byte[] destArray,
      ByteBuffer destBuffer, int destOff, int nElements, int bytesPerElement,
      int blockSize);

  static native int Bitshuffle_LZ4_compress(byte[] srcArray,
      ByteBuffer srcBuffer, int srcOff, byte[] destArray,
      ByteBuffer destBuffer, int destOff, int nElements, int bytesPerElement,
      int blockSize);

  static native int Bitshuffle_LZ4_decompress(byte[] srcArray,
      ByteBuffer srcBuffer, int srcOff, byte[] destArray,
      ByteBuffer destBuffer, int destOff, int nElements, int bytesPerElement,
      int blockSize);

  static native int Bitshuffle_LZ4_bound(int nElements, int bytesPerElement,
      int blockSize);

  static native int Bitshuffle_default_block_size(int bytesPerElement);
}
