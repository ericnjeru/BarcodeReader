package com.example.barcodereader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.barcodereader.R;
import com.example.barcodereader.databinding.ActivityConfigurationBinding;
import com.example.barcodereader.retrofit.ApiService;
import com.example.barcodereader.retrofit.response.ProductResponse;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.room.model.User;
import com.example.barcodereader.util.AppExecutors;
import com.example.barcodereader.util.MyConnectivityManager;
import com.example.barcodereader.util.SharedPrefsManager;
import com.example.barcodereader.util.util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurationActivity extends AppCompatActivity {

    private ActivityConfigurationBinding b;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    private ApiService service;
    private static final String TAG = "Configuration";
    private MyConnectivityManager connectivityManager;
    private User currentUser;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_configuration);

        appExecutors = AppExecutors.getInstance();
        appDatabase = AppDatabase.getInstance(this);
        service = util.getApiService();
        connectivityManager = MyConnectivityManager.getInstance(this);
        sharedPrefsManager = SharedPrefsManager.getInstance(this);

        appDatabase.userDao().getCurrentUser().observe(this, user -> {
            if (user == null) {
                user = sharedPrefsManager.getCurrentUser();
            }

            currentUser = user;

            b.ipSource.setText(user.getIp_address_source());
            b.ipDestination.setText(user.getIp_address_destination());
            b.uuid.setText(user.getId());
            b.password.setText(user.getPassword());
        });


        b.download.setOnClickListener(v -> {

            if (connectivityManager.isOnline()) {
                if (currentUser == null) {
                    Toast.makeText(this, "Need to save user data", Toast.LENGTH_SHORT).show();
                    return;
                }
                downloadOnlineProducts();
            } else {
                Snackbar.make(b.appbar, "Sin conexión a Internet", BaseTransientBottomBar.LENGTH_LONG).show();
            }

        });
        b.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        b.save.setOnClickListener(v -> {
            validateInputsAndSave();
        });

    }

    private void downloadOnlineProducts() {
        util.showView(b.rlProgress, true);
        service.getAllProducts(1, currentUser.getId(), currentUser.getIp_address_destination(),
                currentUser.getIp_address_source(), currentUser.getPassword()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        Log.e(TAG, "onResponse: Products downloaded " + response.body().getProducts().toString());

                        Toast.makeText(ConfigurationActivity.this, "Productos descargados", Toast.LENGTH_SHORT).show();

                        List<Product> productList = new ArrayList<>(response.body().getProducts());

                        Log.e(TAG, "onResponse: size " + productList.size());
                        writeToRoom(productList);

                    } else {
                        util.hideView(b.rlProgress, true);
                        Toast.makeText(ConfigurationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    util.hideView(b.rlProgress, true);
                    Toast.makeText(ConfigurationActivity.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                util.hideView(b.rlProgress, true);
                Log.e(TAG, "onFailure: Fatal " + t.getLocalizedMessage());
                Toast.makeText(ConfigurationActivity.this, "Inténtalo de nuevo.!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    int progress = 0, listSize = 0;

    private void writeToRoom(List<Product> onlineProducts) {
        listSize = onlineProducts.size();
        Log.e(TAG, "writeToRoom: " + listSize);
        appExecutors.diskIO().execute(() -> {
            for (Product p : onlineProducts) {
                Log.e(TAG, "writeToRoom: add product " + p.getId());
                progress++;
                runOnUiThread(() -> {
                    b.tvProgress.setText("Guardando productos ... " + progress + "/" + listSize);
                });
                appDatabase.productDao().addProduct(p);
            }

            runOnUiThread(() -> {
                util.hideView(b.rlProgress, true);
                b.tvProgress.setText("Guardando productos ... " + 0 + "/" + 0);
                Toast.makeText(ConfigurationActivity.this, "La operación se realizó con éxito.", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void validateInputsAndSave() {
        String userID = b.uuid.getText().toString().trim();
        String ip_source = b.ipSource.getText().toString().trim();
        String ip_destination = b.ipDestination.getText().toString().trim();
        String password = b.password.getText().toString().trim();
        if (TextUtils.isEmpty(userID)) {
            b.uuid.setError("required *");
            b.uuid.requestFocus();
        } else if (TextUtils.isEmpty(ip_source)) {
            b.ipSource.setError("required *");
            b.ipSource.requestFocus();
        } else if (TextUtils.isEmpty(ip_destination)) {
            b.ipDestination.setError("required *");
            b.ipDestination.requestFocus();
        } else {
            appExecutors.diskIO().execute(() -> {
                User user = new User();
                user.setId(userID);
                user.setIp_address_source(ip_source);
                user.setIp_address_destination(ip_destination);
                user.setPassword(password);

                if (appDatabase.userDao().getCount() > 0) {
                    appDatabase.userDao().deleteAllUsers();
                }
                appDatabase.userDao().saveUser(user);

                sharedPrefsManager.saveUser(user.serialize());
                runOnUiThread(() -> Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show());

                if (getIntent().getExtras() != null && getIntent().getExtras().getString("login").equals("login")) {
                    Intent intent = new Intent(ConfigurationActivity.this, CountActivity.class);
                    intent.putExtra("message", "success");
                    setResult(101, intent);
                }
            });
        }


    }
}
