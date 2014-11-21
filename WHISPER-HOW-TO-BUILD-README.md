This is a gradle project whose jar we use for Android. To build it run and upgrade the dependency do the following:
1. set ANDROID_HOME to the path of your android sdk ex. mine is /Users/matthewpetersen/Code/adt-bundle-mac-x86_64-20140702/sdk
2. Consult the changelog for the version number
3. ./gradlew assemble
4. cd library/build/outputs/aar/
5. cp ./library.aar library.jar
6. jar xf ./library.jar
7. cp ./classes.jar ./quickreturn-felipecsl-<CHANGELOG VERSION NUMBER>.jar
8. cp ./quickreturn-felipecsl-<CHANGELOG VERSION NUMBER>.jar <ANDROID ROOT>/whisper/libs/
