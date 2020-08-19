package com.example.barcodereader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.barcodereader.R;
import com.example.barcodereader.databinding.ActivityQrModeBinding;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.util.AppExecutors;

import static android.content.ContentValues.TAG;
import static com.example.barcodereader.activity.QRCodeScannerActivity.EXTRA_ADDRESS;

public class QrModeActivity extends AppCompatActivity {

    ActivityQrModeBinding binding;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    Product searchproduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_mode);
        appDatabase = AppDatabase.getInstance(this);
        appExecutors = AppExecutors.getInstance();

        binding.scan.setOnClickListener(v -> {
            Intent i = new Intent(QrModeActivity.this, QRCodeScannerActivity.class);
            startActivityForResult(i, 1);
        });
        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                try {

                    Product product1 = getProductByBarCode(EXTRA_ADDRESS);
                    if (product1 != null){
                        findOrFail(product1);
                        binding.scanResult.setText(data.getStringExtra(EXTRA_ADDRESS));
                    }else {
                        Toast.makeText(QrModeActivity.this, "Product Not found!", Toast.LENGTH_LONG).show();
                        binding.scanResult.setText("Product Not found!");
                    }

                } catch (Exception ex) {
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // code if there's no result

            }
        }
    }

    private Product getProductByBarCode(String barcode){
        appExecutors.diskIO().execute(() -> {
             searchproduct = appDatabase.productDao().getProductByBarCode(barcode);
            Log.e(TAG, "getProductByBarCode: "+searchproduct );
        });
        return searchproduct;
    }

    private void findOrFail(Product p) {
        appExecutors.diskIO().execute(() -> {
            CountedProduct countedProduct = appDatabase.countedProductDao().findProduct(p.getId());

            if (countedProduct == null) {
                countedProduct = new CountedProduct();
                countedProduct.setProduct(p.getProduct());
                countedProduct.setCountedQuantity(0);
                countedProduct.setIdentificationCard("");
                countedProduct.setBusiness("");
                countedProduct.setBranchOffice("");
                appDatabase.countedProductDao().addProduct(countedProduct);
            }

            Intent intent = new Intent(QrModeActivity.this, ConsultActivity.class);
            intent.putExtra("isNew", true);
            intent.putExtra("counted_product", countedProduct);
            startActivity(intent);
        });
    }
}
