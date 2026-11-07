package {{package_name}};

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import {{package_name}}.databinding.ActivityMainBinding;

/**
 * Sample screen for {{app_name}}. The buttons are Java. Each answer is
 * produced by the Rust library loaded below.
 */
public class MainActivity extends AppCompatActivity {

    //<RustJNI>
    // auto-generated code

    public static native String play(String action, String text);

    static { System.loadLibrary("{{lib_name}}"); }

    //</RustJNI>

    private ActivityMainBinding binding;
    private int ink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ink = binding.result.getCurrentTextColor();

        wire(R.id.action_roll, "roll");
        wire(R.id.action_coin, "coin");
        wire(R.id.action_time, "time");
        wire(R.id.action_id, "id");
        wire(R.id.action_reverse, "reverse");
        wire(R.id.action_shuffle, "shuffle");
        wire(R.id.action_shout, "shout");
        wire(R.id.action_whisper, "whisper");
        wire(R.id.action_color, "color");
        wire(R.id.action_morse, "morse");
        wire(R.id.action_count, "count");
        wire(R.id.action_crab, "crab");
    }

    private void wire(int id, String action) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> show(action));
    }

    private void show(String action) {
        CharSequence current = binding.result.getText();
        String next = play(action, current == null ? "" : current.toString());
        binding.result.setText(next);
        if (next.length() == 7 && next.charAt(0) == '#') {
            try {
                binding.result.setTextColor(Color.parseColor(next));
                return;
            } catch (IllegalArgumentException ignored) {
            }
        }
        binding.result.setTextColor(ink);
    }
}
