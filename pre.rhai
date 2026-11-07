fn subst(text, from, to) {
    let out = "";
    let rest = text;
    loop {
        let idx = rest.index_of(from);
        if idx < 0 {
            out += rest;
            return out;
        }
        out += rest.sub_string(0, idx);
        out += to;
        rest = rest.sub_string(idx + from.len);
    }
}

fn split_commas(text) {
    let out = [];
    let rest = text;
    loop {
        let idx = rest.index_of(",");
        if idx < 0 {
            let last = rest.trim();
            if last != "" {
                out.push(last);
            }
            return out;
        }
        let piece = rest.sub_string(0, idx).trim();
        if piece != "" {
            out.push(piece);
        }
        rest = rest.sub_string(idx + 1);
    }
}

let abis = variable::get("abis");
let use_arm64 = false;
let use_armv7 = false;
let use_x86_64 = false;
let use_x86 = false;
if type_of(abis) == "string" {
    abis = split_commas(abis);
}
if type_of(abis) == "string" {
    throw "Pick at least one ABI";
} else {
    if abis.len == 0 {
        throw "Pick at least one ABI";
    }
    use_arm64 = "arm64-v8a" in abis;
    use_armv7 = "armeabi-v7a" in abis;
    use_x86_64 = "x86_64" in abis;
    use_x86 = "x86" in abis;
}
variable::set("use_arm64", use_arm64);
variable::set("use_armv7", use_armv7);
variable::set("use_x86_64", use_x86_64);
variable::set("use_x86", use_x86);

let package_name = `${variable::get("package_name")}`;
let jni = subst(package_name, "_", "_1");
variable::set("jni_prefix", subst(jni, ".", "_"));

let parts = [];
let rest = package_name;
loop {
    let idx = rest.index_of(".");
    if idx < 0 {
        parts.push(rest);
        break;
    }
    parts.push(rest.sub_string(0, idx));
    rest = rest.sub_string(idx + 1);
}
if parts.len > 6 {
    throw "Package names in this template can have at most 6 segments";
}
let pkg_depth = parts.len;
while parts.len < 6 {
    parts.push("zzunused00");
}
variable::set("pkg_depth", `${pkg_depth}`);
variable::set("s0", `${parts[0]}`);
variable::set("s1", `${parts[1]}`);
variable::set("s2", `${parts[2]}`);
variable::set("s3", `${parts[3]}`);
variable::set("s4", `${parts[4]}`);
variable::set("s5", `${parts[5]}`);

let app_name = `${variable::get("app_name")}`;
let theme = subst(app_name, " ", "");
theme = subst(theme, "-", "");
theme = subst(theme, "_", "");
theme = subst(theme, ".", "");
if theme == "" {
    theme = "App";
} else {
    let first = theme.sub_string(0, 1);
    if first < "A" || (first > "Z" && first < "a") || first > "z" {
        theme = `T${theme}`;
    }
}
variable::set("theme_name", theme);
