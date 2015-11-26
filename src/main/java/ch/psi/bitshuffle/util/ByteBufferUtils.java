package ch.psi.bitshuffle.util;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public enum ByteBufferUtils {
  ;

  public static void checkRange(ByteBuffer buf, int off, int len) {
    SafeUtils.checkLength(len);
    if (len > 0) {
      checkRange(buf, off);
      checkRange(buf, off + len - 1);
    }
  }

  public static void checkRange(ByteBuffer buf, int off) {
    if (off < 0 || off >= buf.capacity()) {
      throw new ArrayIndexOutOfBoundsException(off);
    }
  }

  public static void checkNotReadOnly(ByteBuffer buffer) {
    if (buffer.isReadOnly()) {
      throw new ReadOnlyBufferException();
    }
  }
}
