package com.example.barcodereader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.barcodereader.databinding.ActivityQrModeBinding;

import static com.example.barcodereader.QRCodeScanner.EXTRA_ADDRESS;

public class QrMode extends AppCompatActivity  implements View.OnClickListener{

    ActivityQrModeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_mode);
        binding.scan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan:
                Intent i= new Intent(QrMode.this, QRCodeScanner.class);
                startActivityForResult(i, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                try {
                    binding.scanResult.setText(data.getStringExtra(EXTRA_ADDRESS));
                } catch (Exception ex) {
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // code if there's no result

            }
        }
    }
}
