package com.example.barcodereader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.barcodereader.R;
import com.example.barcodereader.adapter.CountedProductAdapter;
import com.example.barcodereader.databinding.ActivityCountBinding;
import com.example.barcodereader.fragment.AddProductFragment;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {

    ActivityCountBinding b;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    private List<CountedProduct> productList = new ArrayList<>();
    private CountedProductAdapter adapter;
    private CountedProductAdapter.OnClickCallBack onClickCallBack;
    private static final String TAG = "CountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_count);

        appDatabase = AppDatabase.getInstance(this);
        appExecutors = AppExecutors.getInstance();

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

    }


    private void loadCountedProducts() {
        appDatabase.countedProductDao().getAllCountedProducts().observe(this, countedProducts -> {
            productList.clear();
            productList.addAll(countedProducts);

            adapter.notifyDataSetChanged();
        });
    }

    private void showAddProductDialog() {
        AddProductFragment addProductFragment = AddProductFragment.newInstance();
        addProductFragment.show(getSupportFragmentManager(), "Add_product_fragment");
    }
}
