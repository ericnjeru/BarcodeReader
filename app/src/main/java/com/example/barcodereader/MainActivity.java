package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.barcodereader.databinding.ActivityMainBinding;
import com.example.barcodereader.databinding.ActivityQrModeBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.config.setOnClickListener(this);
        binding.consult.setOnClickListener(this);
        binding.qrmode.setOnClickListener(this);
        binding.count.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qrmode:
                Intent qrmode =new Intent( MainActivity.this, QrMode.class );
                startActivity( qrmode);
                break;
            case R.id.consult:
                Intent consult=new Intent( MainActivity.this, Consult.class );
                startActivity( consult );
                break;
            case R.id.count:
                Intent count =new Intent( MainActivity.this, Count.class );
                startActivity( count );
                break;
            case R.id.config:
                Intent config =new Intent( MainActivity.this, Configuration.class );
                startActivity( config );
                break;
        }
    }
}
