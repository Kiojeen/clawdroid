use jni::objects::{JClass, JString};
use jni::JNIEnv;
//<RustJNI>
// primitive imports
use jni::sys::jstring;
//</RustJNI>

use std::time::{SystemTime, UNIX_EPOCH};

fn java_string(env: &mut JNIEnv, value: jstring) -> String {
    let java = unsafe { JString::from_raw(value) };
    let text = env.get_string(&java).expect("button text must be a Java string");
    std::borrow::Cow::from(&*text).into_owned()
}

fn nanos() -> u64 {
    SystemTime::now()
        .duration_since(UNIX_EPOCH)
        .map(|d| d.as_nanos() as u64)
        .unwrap_or(1)
}

fn source(text: &str) -> String {
    let trimmed = text.trim();
    if trimmed.is_empty() || trimmed == "Tap one." {
        "clawdroid".to_string()
    } else {
        trimmed.to_string()
    }
}

fn roll() -> String {
    format!("d20  {}", (nanos() % 20) + 1)
}

fn coin() -> String {
    if nanos() % 2 == 0 {
        "Heads".to_string()
    } else {
        "Tails".to_string()
    }
}

fn now() -> String {
    let secs = nanos() / 1_000_000_000;
    let day = secs % 86_400;
    format!(
        "{:02}:{:02}:{:02} UTC",
        day / 3600,
        (day % 3600) / 60,
        day % 60
    )
}

fn ident() -> String {
    format!("{:08x}", nanos() ^ nanos().rotate_left(13))
}

fn reverse(text: &str) -> String {
    source(text).chars().rev().collect()
}

fn shuffle(text: &str) -> String {
    let mut chars: Vec<char> = source(text).chars().collect();
    let mut state = nanos() | 1;
    for i in (1..chars.len()).rev() {
        state = state
            .wrapping_mul(6364136223846793005)
            .wrapping_add(1);
        let j = (state >> 33) as usize % (i + 1);
        chars.swap(i, j);
    }
    chars.into_iter().collect()
}

fn shout(text: &str) -> String {
    source(text).to_uppercase()
}

fn whisper(text: &str) -> String {
    source(text).to_lowercase()
}

fn color() -> String {
    format!("#{:06x}", nanos() & 0x00ff_ffff)
}

fn morse_char(ch: char) -> &'static str {
    match ch {
        'a' => ".-",
        'b' => "-...",
        'c' => "-.-.",
        'd' => "-..",
        'e' => ".",
        'f' => "..-.",
        'g' => "--.",
        'h' => "....",
        'i' => "..",
        'j' => ".---",
        'k' => "-.-",
        'l' => ".-..",
        'm' => "--",
        'n' => "-.",
        'o' => "---",
        'p' => ".--.",
        'q' => "--.-",
        'r' => ".-.",
        's' => "...",
        't' => "-",
        'u' => "..-",
        'v' => "...-",
        'w' => ".--",
        'x' => "-..-",
        'y' => "-.--",
        'z' => "--..",
        '0' => "-----",
        '1' => ".----",
        '2' => "..---",
        '3' => "...--",
        '4' => "....-",
        '5' => ".....",
        '6' => "-....",
        '7' => "--...",
        '8' => "---..",
        '9' => "----.",
        ' ' => "/",
        _ => "",
    }
}

fn morse(text: &str) -> String {
    let coded = source(text)
        .to_lowercase()
        .chars()
        .filter_map(|ch| {
            let code = morse_char(ch);
            if code.is_empty() {
                None
            } else {
                Some(code)
            }
        })
        .collect::<Vec<_>>()
        .join(" ");
    if coded.is_empty() {
        "...".to_string()
    } else {
        coded
    }
}

fn count(text: &str) -> String {
    let letters = source(text).chars().filter(|ch| ch.is_alphanumeric()).count();
    format!("{letters} letters")
}

fn crab() -> String {
    "The crab keeps the buttons. Rust keeps the answers.".to_string()
}

/// One native call for every button. `action` picks the job, `text` is the line on screen.
#[no_mangle]
pub extern "C" fn Java_{{jni_prefix}}_MainActivity_play(
    mut env: JNIEnv,
    _class: JClass,
    action: jstring,
    text: jstring,
) -> jstring {
    let action = java_string(&mut env, action);
    let text = java_string(&mut env, text);
    let message = match action.as_str() {
        "roll" => roll(),
        "coin" => coin(),
        "time" => now(),
        "id" => ident(),
        "reverse" => reverse(&text),
        "shuffle" => shuffle(&text),
        "shout" => shout(&text),
        "whisper" => whisper(&text),
        "color" => color(),
        "morse" => morse(&text),
        "count" => count(&text),
        "crab" => crab(),
        _ => "Tap one.".to_string(),
    };

    env.new_string(message)
        .expect("Couldn't create Java string!")
        .into_raw()
}
