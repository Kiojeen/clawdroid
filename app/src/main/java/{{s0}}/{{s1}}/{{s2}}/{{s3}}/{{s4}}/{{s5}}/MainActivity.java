package {{package_name}};

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import {{package_name}}.databinding.ActivityMainBinding;

/**
 * Sample calculator for {{app_name}}. The keypad is Java. Each operation is
 * computed by the Rust library loaded below.
 */
public class MainActivity extends AppCompatActivity {

    //<RustJNI>
    // auto-generated code

    public static native String compute(String left, String op, String right);

    static { System.loadLibrary("{{lib_name}}"); }

    //</RustJNI>

    private ActivityMainBinding binding;
    private String left = "";
    private String op = "";
    private boolean fresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] digits = {
                R.id.digit_0, R.id.digit_1, R.id.digit_2, R.id.digit_3, R.id.digit_4,
                R.id.digit_5, R.id.digit_6, R.id.digit_7, R.id.digit_8, R.id.digit_9
        };
        for (int id : digits) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> onDigit(((Button) v).getText().toString()));
        }
        findViewById(R.id.key_dot).setOnClickListener(v -> onDot());
        findViewById(R.id.key_clear).setOnClickListener(v -> clear());
        findViewById(R.id.key_plus).setOnClickListener(v -> onOp("+"));
        findViewById(R.id.key_minus).setOnClickListener(v -> onOp("-"));
        findViewById(R.id.key_times).setOnClickListener(v -> onOp("*"));
        findViewById(R.id.key_div).setOnClickListener(v -> onOp("/"));
        findViewById(R.id.key_eq).setOnClickListener(v -> equals());
    }

    private String shown() {
        CharSequence text = binding.display.getText();
        return text == null ? "0" : text.toString();
    }

    private void onDigit(String digit) {
        String current = shown();
        if (fresh || current.equals("0") || current.equals("Error")) {
            binding.display.setText(digit);
            fresh = false;
            return;
        }
        binding.display.setText(current + digit);
    }

    private void onDot() {
        if (fresh || shown().equals("Error")) {
            binding.display.setText("0.");
            fresh = false;
            return;
        }
        if (!shown().contains(".")) {
            binding.display.setText(shown() + ".");
        }
    }

    private void onOp(String next) {
        if (!op.isEmpty() && !fresh) {
            equals();
        }
        left = shown();
        op = next;
        fresh = true;
    }

    private void equals() {
        if (op.isEmpty() || left.isEmpty()) {
            return;
        }
        String right = fresh ? left : shown();
        String result = compute(left, op, right);
        binding.display.setText(result);
        left = "";
        op = "";
        fresh = true;
    }

    private void clear() {
        left = "";
        op = "";
        fresh = true;
        binding.display.setText("0");
    }
}
