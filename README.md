# ClawDroid

<p align="center">
  <img src="docs/clawdroid.png" width="128" height="128" alt="ClawDroid icon: an engraved crab on an orange tile">
</p>

ClawDroid is a cargo-generate template for a Java Android app with a Rust library. The sample is a calculator: the crab stays on the screen, and each operation is computed in Rust.

## Generate a project

```sh
cargo install cargo-generate
cargo generate --allow-commands --git https://github.com/Kiojeen/clawdroid.git
```

`--allow-commands` lets the Git repo answer run `git init`. The questions are:

- Folder name, the directory the project is created in. Example: `my-rust-app`.
- App name. Example: `ClawDroid`.
- Package name. Example: `com.example.clawdroid`.
- Library name. Example: `clawdroid`. The file is `lib<name>.so`.
- ABIs. Space toggles a row and enter confirms. `arm64-v8a`, `armeabi-v7a`, `x86_64`, and `x86` start selected.
- minSdk. Example: `24`. The choices are 24, 26, 28, 33, and 34.
- Git repo. The default is yes.

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

`compileSdk` and `targetSdk` are 37. Gradle runs on JDK 17.

## License

MIT. See [LICENSE](LICENSE).
