package {{package_name}}

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import {{package_name}}.databinding.ActivityMainBinding

/**
 * Sample screen for {{app_name}}. Each answer comes from the Rust library loaded below.
 */
class MainActivity : AppCompatActivity() {

    //<RustJNI>
    // auto-generated code

    external fun play(action: String, text: String): String

    init { System.loadLibrary("{{lib_name}}") }

    //</RustJNI>

    private lateinit var binding: ActivityMainBinding
    private var ink: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ink = binding.result.currentTextColor

        wire(R.id.action_roll, "roll")
        wire(R.id.action_coin, "coin")
        wire(R.id.action_time, "time")
        wire(R.id.action_id, "id")
        wire(R.id.action_reverse, "reverse")
        wire(R.id.action_shuffle, "shuffle")
        wire(R.id.action_shout, "shout")
        wire(R.id.action_whisper, "whisper")
        wire(R.id.action_color, "color")
        wire(R.id.action_morse, "morse")
        wire(R.id.action_count, "count")
        wire(R.id.action_crab, "crab")
    }

    private fun wire(id: Int, action: String) {
        val button = findViewById<Button>(id)
        button.setOnClickListener { show(action) }
    }

    private fun show(action: String) {
        val current = binding.result.text
        val next = play(action, current?.toString() ?: "")
        binding.result.text = next
        if (next.length == 7 && next[0] == '#') {
            try {
                binding.result.setTextColor(Color.parseColor(next))
                return
            } catch (_: IllegalArgumentException) {
            }
        }
        binding.result.setTextColor(ink)
    }
}
