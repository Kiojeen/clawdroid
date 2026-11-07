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

let crate_name = `${variable::get("crate_name")}`;

if !variable::is_set("lib_name") {
    let lib_name = variable::prompt(
        "Native library name",
        "clawdroid",
        "^[a-z][a-z0-9_]*$"
    );
    variable::set("lib_name", lib_name);
}

if !variable::is_set("package_name") {
    let package_name = variable::prompt(
        "Android package name",
        `com.example.${crate_name}`,
        "^[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)+$"
    );
    variable::set("package_name", package_name);
}

if !variable::is_set("arch") {
    let arch = variable::prompt("ABIs", "all", [
        "all",
        "arm64-v8a",
        "armeabi-v7a",
        "x86_64",
        "x86",
    ]);
    variable::set("arch", arch);
}

if !variable::is_set("min_sdk") {
    let min_sdk = variable::prompt("minSdk", "24", [
        "24",
        "26",
        "28",
        "33",
        "34",
    ]);
    variable::set("min_sdk", min_sdk);
}

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

let project_name = `${variable::get("project-name")}`;
variable::set("app_name", project_name);

let theme = subst(project_name, " ", "");
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
