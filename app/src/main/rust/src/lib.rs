use jni::objects::{JClass, JString};
use jni::JNIEnv;
//<RustJNI>
// primitive imports
use jni::sys::jstring;
//</RustJNI>

/// Builds the line shown on the template screen.
#[no_mangle]
pub extern "C" fn Java_{{jni_prefix}}_MainActivity_greet(
    mut env: JNIEnv,
    _class: JClass,
    name: jstring,
) -> jstring {
    let java_name = unsafe { JString::from_raw(name) };
    let rust_name = env
        .get_string(&java_name)
        .expect("name must be a Java string");
    let name = std::borrow::Cow::from(&*rust_name);
    let who = match name.trim() {
        "" => "there",
        trimmed => trimmed,
    };
    let message = format!("Hello, {who}. This reply was built in Rust.");

    env.new_string(message)
        .expect("Couldn't create Java string!")
        .into_raw()
}
