package com.example.barcodereader.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.barcodereader.R;
import com.example.barcodereader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.config.setOnClickListener(v -> {
            Intent config = new Intent(MainActivity.this, ConfigurationActivity.class);
            startActivity(config);
        });
        binding.consult.setOnClickListener(v -> {
            Intent consult = new Intent(MainActivity.this, ConsultActivity.class);
            startActivity(consult);
        });
        binding.qrmode.setOnClickListener(v -> {
            Intent qrmode = new Intent(MainActivity.this, CountActivity.class);
            startActivity(qrmode);
        });
        binding.count.setOnClickListener(v -> {
            Intent count = new Intent(MainActivity.this, CountActivity.class);
            startActivity(count);
        });
    }

}
