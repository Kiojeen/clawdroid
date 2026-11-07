plugins {
    alias(libs.plugins.android.application) apply false
    {%- if language == "kotlin" %}
    alias(libs.plugins.kotlin.android) apply false
    {%- endif %}
}
