package {{package_name}};

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import {{package_name}}.databinding.ActivityMainBinding;

/**
 * Sample screen for {{app_name}}. Colors come from Theme.{{theme_name}}. The greeting
 * is produced by the Rust library loaded below.
 */
public class MainActivity extends AppCompatActivity {

    //<RustJNI>
    // auto-generated code

    public static native String greet(String name);

    static { System.loadLibrary("{{lib_name}}"); }

    //</RustJNI>

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.greetButton.setOnClickListener(v -> showGreeting());
        binding.nameInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showGreeting();
                return true;
            }
            return false;
        });

        showGreeting();
    }

    private void showGreeting() {
        CharSequence name = binding.nameInput.getText();
        binding.rustResult.setText(greet(name == null ? "" : name.toString()));
    }
}
