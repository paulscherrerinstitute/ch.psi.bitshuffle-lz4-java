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

import static ch.psi.bitshuffle.util.ByteBufferUtils.checkNotReadOnly;
import static ch.psi.bitshuffle.util.ByteBufferUtils.checkRange;

import java.nio.ByteBuffer;

/**
 * {@link BitShuffleLZ4Compressor} implemented with JNI bindings to the
 * original C implementation.
 */
public class BitShuffleLZ4JNICompressor implements BitShuffleLZ4Compressor {

  @Override
  public int compress(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff,
      int nElements, int bytesPerElement, int blockSize) {
    checkNotReadOnly(dest);
    checkRange(src, srcOff);
    checkRange(dest, destOff, nElements * bytesPerElement);

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
  public int maxCompressedLength(int size, int elemSize, int blockSize) {
    return BitShuffleLZ4JNI.Bitshuffle_LZ4_bound(size, elemSize, blockSize);
  }
}
