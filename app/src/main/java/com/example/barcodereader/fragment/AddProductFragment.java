package com.example.barcodereader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.barcodereader.R;
import com.example.barcodereader.activity.ConsultActivity;
import com.example.barcodereader.adapter.ProductAdapter;
import com.example.barcodereader.databinding.FragmentAddProductBinding;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.util.AppExecutors;
import com.example.barcodereader.util.util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends BottomSheetDialogFragment {

    private FragmentAddProductBinding b;
    private AppExecutors appExecutors;
    private AppDatabase appDatabase;
    private List<Product> searchResults = new ArrayList<>();
    private ProductAdapter adapter;
    private ProductAdapter.OnClickCallBack onClickCallBack;
    private static final String TAG = "AddProductFragment";


    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                // androidx should use: com.google.android.material.R.id.design_bottom_sheet
                FrameLayout bottomSheet = (FrameLayout)
                        dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false);

        appDatabase = AppDatabase.getInstance(getContext());
        appExecutors = AppExecutors.getInstance();

        initUI();

        b.btBack.setOnClickListener(v -> dismiss());

        b.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = b.etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    Toast.makeText(getContext(), "Please enter a search keyword", Toast.LENGTH_SHORT).show();
                } else {
                    performSearch(keyword);
                }
                return true;
            }
            return false;
        });

        return b.getRoot();
    }

    private void initUI() {
        b.searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(searchResults, getContext());
        b.searchRecyclerView.setAdapter(adapter);

        onClickCallBack = (p, position) -> {

            findOrFail(p, position);
        };

        adapter.setOnClickCallBack(onClickCallBack);
    }

    private void findOrFail(Product p, int position) {
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

            Log.e(TAG, "onItemClick: " + position);
            Intent intent = new Intent(getContext(), ConsultActivity.class);
            intent.putExtra("isNew", true);
            intent.putExtra("counted_product", countedProduct);
            startActivity(intent);
        });
    }

    private void performSearch(String keyword) {
        util.showView(b.progressBar, true);

        appExecutors.diskIO().execute(() -> {

            getActivity().runOnUiThread(() -> {
                appDatabase.productDao().findProduct(keyword).observe(getActivity(), products -> {
                    searchResults.clear();
                    searchResults.addAll(products);
                    adapter.notifyDataSetChanged();
                    util.hideView(b.progressBar, true);

                    if (searchResults.size() == 0) {
                        util.showView(b.lytNoResult, true);
                    } else {
                        util.hideView(b.lytNoResult, true);
                    }

                });

            });
        });

    }
}
