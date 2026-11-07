fn seg(i) {
    if i == 0 { return `${variable::get("s0")}`; }
    if i == 1 { return `${variable::get("s1")}`; }
    if i == 2 { return `${variable::get("s2")}`; }
    if i == 3 { return `${variable::get("s3")}`; }
    if i == 4 { return `${variable::get("s4")}`; }
    `${variable::get("s5")}`
}

fn prefix(n) {
    let path = "";
    let i = 0;
    while i < n {
        if i > 0 {
            path += "/";
        }
        path += seg(i);
        i += 1;
    }
    path
}

let depth = parse_int(`${variable::get("pkg_depth")}`);
let lang = `${variable::get("language")}`;
let src = "java";
let ext = "java";
if lang == "kotlin" {
    src = "kotlin";
    ext = "kt";
}
if depth < 6 {
    let full = prefix(6);
    let real = prefix(depth);
    let extra = prefix(depth + 1);
    file::rename(
        `app/src/main/${src}/${full}/MainActivity.${ext}`,
        `app/src/main/${src}/${real}/MainActivity.${ext}`
    );
    file::delete(`app/src/main/${src}/${extra}`);
    file::rename(
        `app/src/test/${src}/${full}/ExampleUnitTest.${ext}`,
        `app/src/test/${src}/${real}/ExampleUnitTest.${ext}`
    );
    file::delete(`app/src/test/${src}/${extra}`);
    file::rename(
        `app/src/androidTest/${src}/${full}/ExampleInstrumentedTest.${ext}`,
        `app/src/androidTest/${src}/${real}/ExampleInstrumentedTest.${ext}`
    );
    file::delete(`app/src/androidTest/${src}/${extra}`);
}

let git_repo = variable::get("git_repo");
let want_git = false;
if type_of(git_repo) == "bool" {
    want_git = git_repo;
} else {
    let answer = `${git_repo}`;
    want_git = answer == "true";
}
if want_git {
    let dest = env::destination_directory;
    system::command("git", ["init", "-b", "main", `'${dest}'`]);
}
