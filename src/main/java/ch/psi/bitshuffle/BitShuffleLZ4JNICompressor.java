package ch.psi.bitshuffle;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import static ch.psi.bitshuffle.util.ByteBufferUtils.checkNotReadOnly;
import static ch.psi.bitshuffle.util.ByteBufferUtils.checkRange;

import java.nio.ByteBuffer;

/**
 * {@link BitShuffleLZ4Compressor} implemented with JNI bindings to the original C implementation.
 */
public class BitShuffleLZ4JNICompressor implements BitShuffleLZ4Compressor {

   @Override
   public int compress(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff,
         int nElements, int bytesPerElement, int blockSize) {
      checkNotReadOnly(dest);
      checkRange(src, srcOff);
      checkRange(dest, destOff);
      // checkRange(dest, destOff, maxCompressedLength(nElements, bytesPerElement,
      // blockSize));

      byte[] srcArr = null, destArr = null;
      ByteBuffer srcBuf = null, destBuf = null;
      if (src.hasArray()) {
         srcArr = src.array();
         srcOff += src.arrayOffset();
      } else {
         assert src.isDirect();
         srcBuf = src;
      }
      if (dest.hasArray()) {
         destArr = dest.array();
         destOff += dest.arrayOffset();
      } else {
         assert dest.isDirect();
         destBuf = dest;
      }

      final int result = BitShuffleLZ4JNI.Bitshuffle_LZ4_compress(srcArr, srcBuf,
            srcOff, destArr, destBuf, destOff, nElements, bytesPerElement,
            blockSize);
      if (result <= 0) {
         throw new BitShuffleLZ4Exception("maxDestLen is too small");
      }
      return result;
   }

   @Override
   public int maxCompressedLength(int size, int bytesPerElement, int blockSize) {
      return maxCompressedLengthJava(size, bytesPerElement, blockSize);
   }

   public int maxCompressedLengthINI(int size, int bytesPerElement, int blockSize) {
      return BitShuffleLZ4JNI.Bitshuffle_LZ4_bound(size, bytesPerElement, blockSize);
   }

   public int maxCompressedLengthJava(int size, int elem_size, int block_size) {
      int bound, leftover;

      if (block_size == 0) {
          block_size = getDefaultBlockSizeJava(elem_size);
      }
      if (block_size < 0 || block_size % BSHUF_BLOCKED_MULT != 0) return -81;

      // Note that each block gets a 4 byte header.
      // Size of full blocks.
      bound = (maxCompressedLength_LZ4(block_size * elem_size) + 4) * (size / block_size);
      // Size of partial blocks, if any.
      leftover = ((size % block_size) / BSHUF_BLOCKED_MULT) * BSHUF_BLOCKED_MULT;
      if (leftover > 0){
         bound += maxCompressedLength_LZ4(leftover * elem_size) + 4;
      }
      // Size of uncompressed data not fitting into any blocks.
      bound += (size % BSHUF_BLOCKED_MULT) * elem_size;
      return bound;
   }

   private int maxCompressedLength_LZ4(int length) {
      if (length < 0) {
         throw new IllegalArgumentException("length must be >= 0, got " + length);
      } else if (length >= MAX_INPUT_SIZE) {
         throw new IllegalArgumentException("length must be < " + MAX_INPUT_SIZE);
      }
      return length + length / 255 + 16;
   }

   @Override
  public int getDefaultBlockSize(int bytesPerElement) {
    return getDefaultBlockSizeJava(bytesPerElement);
  }

   public int getDefaultBlockSizeJNI(int bytesPerElement) {
      return BitShuffleLZ4JNI.Bitshuffle_default_block_size(bytesPerElement);
   }

   public int getDefaultBlockSizeJava(int elem_size) {
      // This function needs to be absolutely stable between versions.
      // Otherwise encoded data will not be decodable.

      int block_size = BSHUF_TARGET_BLOCK_SIZE_B / elem_size;
      // Ensure it is a required multiple.
      block_size = (block_size / BSHUF_BLOCKED_MULT) * BSHUF_BLOCKED_MULT;
      return Math.max(block_size, BSHUF_MIN_RECOMMEND_BLOCK);
   }
}
