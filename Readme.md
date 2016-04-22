# Overview
Java bitshuffle/lz4 library

# Build

Upload archive to artifactory

```bash
./gradlew uploadArchives
```

If you have to deal with the c code following commands are for you...

Create JNI header file
```bash
./gradlew makeNativeCode
```

Rebuild C code for current platform and replace packaged dll,so,dylib

```bash
./gradlew makeHeaders
```

## Windows

Before being able to compile the native library on Windows Cygwin need to be

```bash
call "C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat" amd64
set PATH="\Program Files\Java\jdk1.8.0_72\bin";%PATH%
```
