package com.example.barcodereader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.barcodereader.databinding.ActivityQrModeBinding;
import com.example.barcodereader.retrofit.ApiService;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.util.AppExecutors;
import com.example.barcodereader.util.util;

import static com.example.barcodereader.QRCodeScanner.EXTRA_ADDRESS;

public class QrMode extends AppCompatActivity  implements View.OnClickListener{

    ActivityQrModeBinding binding;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    private ApiService service;
    private LiveData<Product> product = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_mode);
        binding.scan.setOnClickListener(this);

        appExecutors = AppExecutors.getInstance();
        appDatabase = AppDatabase.getInstance(this);
        service = util.getApiService();

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
                    productCountStore(data.getStringExtra(EXTRA_ADDRESS));
                } catch (Exception ex) {
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // code if there's no result

            }
        }
    }

//    private void productCountStore(final String barcode) {
//        appExecutors.diskIO().execute(() -> {
//            for (Product p : onlineProducts) {
//                Log.e(TAG, "writeToRoom: add product " + p.getId());
//                progress++;
//                runOnUiThread(() -> {
//                    b.tvProgress.setText("Saving products... " + progress + "/" + listSize);
//                });
//                product = appDatabase.productDao().getProductByBarCode(barcode);
//            }
//
//            runOnUiThread(() -> {
//                util.hideView(b.rlProgress, true);
//                Toast.makeText(Configuration.this, "Operation completed successfully!", Toast.LENGTH_SHORT).show();
//            });
//        });
//    }


}
