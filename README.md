# ClawDroid

<p align="center">
  <img src="docs/clawdroid.png" width="128" height="128" alt="ClawDroid icon: an engraved crab on an orange tile">
</p>

ClawDroid is a Java Android app with a Rust library behind one native method. The sample screen takes a name and shows the greeting that library returns.

## Generate a project

```sh
cargo install cargo-generate
cargo generate --git https://github.com/Kiojeen/clawdroid.git
```

The first question is the project name. Then it asks for:

- Android package name. The default is `com.example.` plus the project name in snake case.
- Native library name. The default is `clawdroid`. The app calls `System.loadLibrary` with that name, and the file on disk is `lib<name>.so`.
- ABIs: all of `arm64-v8a`, `armeabi-v7a`, `x86_64`, and `x86`, or one of them.
- `minSdk`: 24, 26, 28, 33, or 34. The default is 24.

## Libraries

- Android Gradle Plugin 8.13.2
- Gradle 8.13
- RustJNI 0.1.0
- jni 0.21
- AndroidX AppCompat 1.7.1
- Material Components 1.13.0
- ConstraintLayout 2.2.1
- JUnit 4.13.2
- AndroidX JUnit 1.2.1
- Espresso 3.6.1

`compileSdk` and `targetSdk` are 37.

## License

MIT. See [LICENSE](LICENSE).
