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

It prints the crab, then asks:

- Folder name. This is the directory, not the name under the icon. Example: `my-rust-app`.
- App name shown under the icon. Example: `ClawDroid`.
- Android package name and application id. Example: `com.example.clawdroid`.
- Native library name passed to `System.loadLibrary`. Example: `clawdroid`. The file is `lib<name>.so`.
- ABIs. Space toggles a row and enter confirms, so you can keep more than one. The choices are `arm64-v8a`, `armeabi-v7a`, `x86_64`, and `x86`. All four start selected.
- Minimum Android SDK. Example: `24`. The choices are 24, 26, 28, 33, and 34.

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
