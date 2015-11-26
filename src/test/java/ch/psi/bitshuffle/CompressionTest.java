package ch.psi.bitshuffle;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class CompressionTest {

  @Test
  public void testCompressor_01() {
    int nElements = 1000;
    int nBytes = Integer.BYTES;
    int blockSize = 0;

    int[] values = new int[nElements];
    for (int i = 0; i < nElements; ++i) {
      values[i] = Integer.MAX_VALUE / 2 + i;
    }

    BitShuffleLZ4Compressor compressor = new BitShuffleLZ4JNICompressor();
    BitShuffleLZ4Decompressor decompressor = new BitShuffleLZ4JNIDecompressor();
    int maxCompLength = compressor.maxCompressedLength(nElements, nBytes,
        blockSize);

    ByteBuffer heapBuf = ByteBuffer.allocate(nElements * nBytes);
    ByteBuffer heapBufComp = ByteBuffer.allocate(maxCompLength);
    ByteBuffer directBuf = ByteBuffer.allocateDirect(nElements * nBytes);
    ByteBuffer directBufComp = ByteBuffer.allocateDirect(maxCompLength);

    heapBuf.asIntBuffer().put(values);
    directBuf.asIntBuffer().put(values);

    int heapCompSize = compressor.compress(heapBuf, heapBuf.position(),
        heapBufComp, heapBufComp.position(), nElements, nBytes, blockSize);
    heapBufComp.limit(heapBufComp.position() + heapCompSize);

    int directCompSize = compressor.compress(directBuf, directBuf.position(),
        directBufComp, directBufComp.position(), nElements, nBytes, blockSize);
    directBufComp.limit(directBufComp.position() + directCompSize);

    assertEquals(heapCompSize, directCompSize);
    assertByteBufferEquals(heapBufComp, directBufComp);

    ByteBuffer heapDecompBuf = ByteBuffer.allocate(nElements * nBytes);
    ByteBuffer directDecompBuf = ByteBuffer.allocateDirect(nElements * nBytes);

    decompressor.decompress(heapBufComp, heapBufComp.position(), heapDecompBuf,
        heapDecompBuf.position(), nElements, nBytes, blockSize);
    decompressor.decompress(directBufComp, directBufComp.position(),
        directDecompBuf, directDecompBuf.position(), nElements, nBytes,
        blockSize);

    assertByteBufferEquals(heapBuf, heapDecompBuf);
    assertByteBufferEquals(directBuf, directDecompBuf);
    assertByteBufferEquals(heapDecompBuf, directDecompBuf);
  }

  private void assertByteBufferEquals(ByteBuffer buf1, ByteBuffer buf2) {
    assertEquals(buf1.position(), buf2.position());
    assertEquals(buf1.limit(), buf2.limit());

    for (int i = buf1.position(); i < buf1.limit(); ++i) {
      assertEquals("Test index " + i, buf1.get(i), buf2.get(i));
    }
  }
}
