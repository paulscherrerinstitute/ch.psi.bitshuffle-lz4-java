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
