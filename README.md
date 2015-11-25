# Bitshuffle-LZ4 Java

# Build

## Requirements

 - JDK version 7 or newer,
 - ant,
 - ivy.

If ivy is not installed yet, ant can take care of it for you, just run
`ant ivy-bootstrap`. The library will be installed under ${user.home}/.ant/lib.

## Instructions

Then run `ant`. It will:

 - generate a JAR file called lz4-${version}.jar under the `dist` directory.

The JAR file that is generated contains Java class files, the native library
and the JNI bindings. If you add this JAR to your classpath, the native library
will be copied to a temporary directory and dynamically linked to your Java
application.
