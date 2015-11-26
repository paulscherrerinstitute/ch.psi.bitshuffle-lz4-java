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

public interface BitShuffler {

  /**
   * Performs a bit-shuffle from src into dest.
   *
   * {@link ByteBuffer} positions remain unchanged.
   * 
   * @param src
   *          The src ByteBuffer
   * @param srcOff
   *          The src offset (where bit shuffle should start)
   * @param dest
   *          The dest ByteBuffer
   * @param destOff
   *          The dest offset (where the shuffled bytes should be stored)
   * @param nElements
   *          The number of elements in src
   * @param bytesPerElement
   *          The bytes used to represent one element
   * @param blockSize
   *          The block size to use (recommended to use 0 for auto selection)
   * @return int The processed bytes
   */
  public int shuffle(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff,
      int nElements, int bytesPerElement, int blockSize);

  /**
   * Performs a bit-unshuffle from src into dest.
   *
   * {@link ByteBuffer} positions remain unchanged.
   * 
   * @param src
   *          The src ByteBuffer
   * @param srcOff
   *          The src offset (where bit-unshuffle should start)
   * @param dest
   *          The dest ByteBuffer
   * @param destOff
   *          The dest offset (where the unshuffled bytes should be stored)
   * @param nElements
   *          The number of elements in src
   * @param bytesPerElement
   *          The bytes used to represent one element
   * @param blockSize
   *          The block size to use (recommended to use 0 for auto selection)
   * @return int The processed bytes
   */
  public int unshuffle(ByteBuffer src, int srcOff, ByteBuffer dest,
      int destOff, int nElements, int bytesPerElement, int blockSize);
}
