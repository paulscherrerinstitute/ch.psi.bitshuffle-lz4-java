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

import java.nio.ByteBuffer;

public interface BitShuffleLZ4Compressor {
   // LZ4 stuff
   public static final int MAX_INPUT_SIZE = 0x7E000000;
   // Bitshuffle stuff
   public static final int BSHUF_MIN_RECOMMEND_BLOCK = 128;
   public static final int BSHUF_BLOCKED_MULT = 8; // Block sizes must be multiple of this.
   public static final int BSHUF_TARGET_BLOCK_SIZE_B = 8192;

   /**
    * Compress src into dest and return the compressed length.
    *
    * {@link ByteBuffer} positions remain unchanged.
    * 
    * @param src The src ByteBuffer
    * @param srcOff The src offset (where compression should start)
    * @param dest The dest ByteBuffer
    * @param destOff The dest offset (where the compressed bytes should be stored)
    * @param nElements The number of elements in src
    * @param bytesPerElement The bytes used to represent one element
    * @param blockSize The block size to use (recommended to use 0 for auto selection)
    * @return int The compressed bytes
    */
   public int compress(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff,
         int nElements, int bytesPerElement, int blockSize);

   /**
    * The max possible size after compression.
    * 
    * @param nElements The number of elements in src
    * @param bytesPerElement The bytes used to represent one element
    * @param blockSize The block size to use (recommended to use 0 for auto selection)
    * @return int The max size
    */
   public int maxCompressedLength(int nElements, int bytesPerElement,
         int blockSize);

   /**
    * The default block size.
    * 
    * @param bytesPerElement The bytes used to represent one element
    * @return int The default block size
    */
   public int getDefaultBlockSize(int bytesPerElement);

}
