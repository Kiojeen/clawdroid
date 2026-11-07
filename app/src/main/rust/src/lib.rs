use jni::objects::{JClass, JString};
use jni::JNIEnv;
//<RustJNI>
// primitive imports
use jni::sys::jstring;
//</RustJNI>

fn java_string(env: &mut JNIEnv, value: jstring) -> String {
    let java = unsafe { JString::from_raw(value) };
    let text = env
        .get_string(&java)
        .expect("calculator value must be a Java string");
    std::borrow::Cow::from(&*text).into_owned()
}

fn parse_number(text: &str) -> Option<f64> {
    let trimmed = text.trim();
    if trimmed.is_empty() || trimmed == "Error" {
        return None;
    }
    trimmed.parse::<f64>().ok()
}

fn format_number(value: f64) -> String {
    if !value.is_finite() {
        return "Error".to_string();
    }
    let rounded = (value * 1_000_000_000.0).round() / 1_000_000_000.0;
    if rounded.fract() == 0.0 && rounded.abs() < 1e15 {
        return format!("{}", rounded as i64);
    }
    let text = format!("{rounded}");
    text
}

/// Evaluates one calculator step. The buttons live in Java. The arithmetic is here.
#[no_mangle]
pub extern "C" fn Java_{{jni_prefix}}_MainActivity_compute(
    mut env: JNIEnv,
    _class: JClass,
    left: jstring,
    op: jstring,
    right: jstring,
) -> jstring {
    let left = java_string(&mut env, left);
    let op = java_string(&mut env, op);
    let right = java_string(&mut env, right);

    let message = match (parse_number(&left), parse_number(&right)) {
        (Some(a), Some(b)) => match op.as_str() {
            "+" => format_number(a + b),
            "-" => format_number(a - b),
            "*" => format_number(a * b),
            "/" if b == 0.0 => "Error".to_string(),
            "/" => format_number(a / b),
            _ => "Error".to_string(),
        },
        _ => "Error".to_string(),
    };

    env.new_string(message)
        .expect("Couldn't create Java string!")
        .into_raw()
}
