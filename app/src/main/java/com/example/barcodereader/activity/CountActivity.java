package com.example.barcodereader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.barcodereader.R;
import com.example.barcodereader.adapter.CountedProductAdapter;
import com.example.barcodereader.databinding.ActivityCountBinding;
import com.example.barcodereader.fragment.AddProductFragment;
import com.example.barcodereader.retrofit.ApiService;
import com.example.barcodereader.retrofit.response.SingleCountedProductResponse;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.util.AppExecutors;
import com.example.barcodereader.util.MyConnectivityManager;
import com.example.barcodereader.util.util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.barcodereader.activity.QRCodeScannerActivity.EXTRA_ADDRESS;

public class CountActivity extends AppCompatActivity {

    ActivityCountBinding b;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    private List<CountedProduct> productList = new ArrayList<>();
    private CountedProductAdapter adapter;
    private CountedProductAdapter.OnClickCallBack onClickCallBack;
    private static final String TAG = "CountActivity";
    private boolean isFinishesLoadingProducts = false;
    private ApiService service;
    private MyConnectivityManager connectivityManager;
    private Product searchproduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_count);

        appDatabase = AppDatabase.getInstance(this);
        appExecutors = AppExecutors.getInstance();
        service = util.getApiService();
        connectivityManager = MyConnectivityManager.getInstance(this);

        b.btnSearch.setOnClickListener(v -> showAddProductDialog());

        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountedProductAdapter(productList, this);

        CountedProductAdapter.OnClickCallBack onClickCallBack = (p, position) -> {
            Log.e(TAG, "onItemClick: " + position);
            Intent intent = new Intent(CountActivity.this, ConsultActivity.class);
            intent.putExtra("isNew", true);
            intent.putExtra("counted_product", p);
            startActivity(intent);
        };

        adapter.setOnClickCallBack(onClickCallBack);

        b.recyclerView.setAdapter(adapter);

        loadCountedProducts();

        b.scan.setOnClickListener(v -> {
            Intent i = new Intent(CountActivity.this, QRCodeScannerActivity.class);
            startActivityForResult(i, 1);
        });

        b.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        b.btnUploadProducts.setOnClickListener(v -> {
            if (connectivityManager.isOnline()) {
                uploadProducts();
            } else {
                Snackbar.make(b.appbar, "No Internet Connection", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

    }

    private void uploadProducts() {

        if (productList.size() == 0) {
            Toast.makeText(this, "No counted products", Toast.LENGTH_SHORT).show();
            return;
        }

        util.showView(b.rlProgress, true);
        if (isFinishesLoadingProducts) {
            if (productList.size() > 0) {
                int count = 0;
                int total = productList.size();
                for (CountedProduct countedProduct : productList) {
                    count++;
                    b.tvProgress.setText("Uploading product " + count + "/" + total);

                    service.saveSingleProduct(countedProduct)
                            .enqueue(new Callback<SingleCountedProductResponse>() {
                                @Override
                                public void onResponse(Call<SingleCountedProductResponse> call, Response<SingleCountedProductResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (!response.body().getError()) {
                                            Log.e(TAG, "onResponse: Product uploaded successfully");

                                            appExecutors.diskIO().execute(() -> {
                                                appDatabase.countedProductDao().setProductIsUploaded(1, countedProduct.getProduct_id());
                                            });

                                        } else {
                                            Log.e(TAG, "onResponse: failed to upload product " + countedProduct.getProduct_id());
                                        }
                                    } else {
                                        Log.e(TAG, "onResponse: Unsuccessful failed to upload product " + countedProduct.getProduct_id());
                                    }
                                }

                                @Override
                                public void onFailure(Call<SingleCountedProductResponse> call, Throwable t) {
                                    Log.e(TAG, "onFailure: Fatal product_id " + countedProduct.getProduct_id() + t.getLocalizedMessage());
                                }
                            });
                }

                util.hideView(b.rlProgress, true);
                Toast.makeText(this, "Products uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loadCountedProducts() {
        isFinishesLoadingProducts = false;

        appDatabase.countedProductDao().getAllCountedProducts().observe(this, countedProducts -> {
            productList.clear();
            productList.addAll(countedProducts);
            isFinishesLoadingProducts = true;
            adapter.notifyDataSetChanged();

            b.tvAllProductsCount.setText("" + productList.size());

            if (countedProducts.size() > 0) {
                util.hideView(b.lytNoResult, true);
            } else {
                util.showView(b.lytNoResult, true);
            }
        });
    }

    private void showAddProductDialog() {
        AddProductFragment addProductFragment = AddProductFragment.newInstance();
        addProductFragment.show(getSupportFragmentManager(), "Add_product_fragment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Product product1 = getProductByBarCode(data.getStringExtra(EXTRA_ADDRESS));
                    if (product1 != null) {
                        findOrFail(product1);
                        b.scanResult.setText(data.getStringExtra(EXTRA_ADDRESS));
                    } else {
                        Toast.makeText(CountActivity.this, "Product Not found!", Toast.LENGTH_LONG).show();
                        b.scanResult.setText("Product Not found!");
                    }

                } catch (Exception ex) {
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // code if there's no result

            }
        }
    }

    private Product getProductByBarCode(String barcode) {
        appExecutors.diskIO().execute(() -> {
            searchproduct = appDatabase.productDao().getProductByBarCode(barcode);
            Log.e(TAG, "getProductByBarCode: " + searchproduct);
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

            Intent intent = new Intent(CountActivity.this, ConsultActivity.class);
            intent.putExtra("isNew", true);
            intent.putExtra("counted_product", countedProduct);
            startActivity(intent);
            finish();
        });
    }

}
