package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private TextView targetTextView;
    private Switch switchJustify;
    private RadioGroup radioGroupFont;
    private Button btnBlack, btnBlue, btnRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // Handle System Bar Insets
        // Uses R.id.root_layout from the XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views & Listeners (Text Styling Logic)
        initializeViews();

        // Ensure text is set (referencing the string resource)
        targetTextView.setText(R.string.sample_text);

        setupJustificationListener();
        setupFontListeners();
        setupColorListeners();
    }

    /**
     * Finds and assigns views from the layout XML.
     */
    private void initializeViews() {
        targetTextView = findViewById(R.id.targetTextView);
        switchJustify = findViewById(R.id.switchJustify);
        radioGroupFont = findViewById(R.id.radioGroupFont);

        btnBlack = findViewById(R.id.btnColorBlack);
        btnBlue = findViewById(R.id.btnColorBlue);
        btnRed = findViewById(R.id.btnColorRed);
    }

    /**
     * Sets up the toggle switch for Text Justification.
     */
    @SuppressLint("WrongConstant") // Suppress warning to allow using Layout constants for API 26+ compatibility
    private void setupJustificationListener() {
        switchJustify.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Check if device supports justification (API 26+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (isChecked) {
                    targetTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                    Toast.makeText(this, "Justification Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    targetTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_NONE);
                    Toast.makeText(this, "Justification Disabled", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Justification requires Android 8.0+", Toast.LENGTH_SHORT).show();
                buttonView.setChecked(false);
            }
        });
    }

    private void setupFontListeners() {
        radioGroupFont.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSans) {
                targetTextView.setTypeface(Typeface.SANS_SERIF);
            } else if (checkedId == R.id.rbSerif) {
                targetTextView.setTypeface(Typeface.SERIF);
            } else if (checkedId == R.id.rbMono) {
                targetTextView.setTypeface(Typeface.MONOSPACE);
            }
        });
    }

    private void setupColorListeners() {
        btnBlack.setOnClickListener(v -> targetTextView.setTextColor(Color.BLACK));
        btnBlue.setOnClickListener(v -> targetTextView.setTextColor(Color.parseColor("#2196F3")));
        btnRed.setOnClickListener(v -> targetTextView.setTextColor(Color.parseColor("#F44336")));
    }
}