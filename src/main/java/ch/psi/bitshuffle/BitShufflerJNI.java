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

import ch.psi.bitshuffle.util.ByteBufferUtils;

public class BitShufflerJNI implements BitShuffler {

  @Override
  public int shuffle(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff,
      int nElements, int bytesPerElement, int blockSize) {
    ByteBufferUtils.checkNotReadOnly(dest);
    ByteBufferUtils.checkRange(src, srcOff);
    ByteBufferUtils.checkRange(dest, destOff);
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

    final int result = BitShuffleLZ4JNI.Bitshuffle_bitshuffle(srcArr, srcBuf,
        srcOff, destArr, destBuf, destOff, nElements, bytesPerElement,
        blockSize);
    if (result < 0) {
      throw new BitShuffleLZ4Exception("Error shuffling offset "
          + (srcOff - result) + " of input buffer");
    }
    return result;
  }

  @Override
  public int unshuffle(ByteBuffer src, int srcOff, ByteBuffer dest,
      int destOff, int nElements, int bytesPerElement, int blockSize) {
    ByteBufferUtils.checkNotReadOnly(dest);
    ByteBufferUtils.checkRange(src, srcOff);
    ByteBufferUtils.checkRange(dest, destOff, nElements * bytesPerElement);

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

    final int result = BitShuffleLZ4JNI.Bitshuffle_bitunshuffle(srcArr, srcBuf,
        srcOff, destArr, destBuf, destOff, nElements, bytesPerElement,
        blockSize);
    if (result < 0) {
      throw new BitShuffleLZ4Exception("Error shuffling offset "
          + (srcOff - result) + " of input buffer");
    }
    return result;
  }
}
