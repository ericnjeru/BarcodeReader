package com.example.barcodereader.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.barcodereader.R;
import com.example.barcodereader.databinding.ActivityConsultBinding;
import com.example.barcodereader.room.AppDatabase;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.util.AppExecutors;

public class ConsultActivity extends AppCompatActivity {

    private ActivityConsultBinding b;
    private AppDatabase appDatabase;
    private AppExecutors appExecutors;
    private CountedProduct product = null;
    private static final String TAG = "ConsultActivity";
    int original_count = 0, added = 0, subtracted = 0, total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_consult);

        appDatabase = AppDatabase.getInstance(this);
        appExecutors = AppExecutors.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = (CountedProduct) extras.getSerializable("counted_product");
            populateFields();
        } else {
            finish();
        }

        b.edtSubtract.addTextChangedListener(myTextWatcher);
        b.edtAdd.addTextChangedListener(myTextWatcher);

        b.btnSave.setOnClickListener(v -> {
            if (total < 0) {
                Toast.makeText(this, "Total is less than 0", Toast.LENGTH_SHORT).show();
            } else {

                product.setCountedQuantity(total);
                appExecutors.diskIO().execute(() -> {
                    appDatabase.countedProductDao().updateSingleProducs(total, product.getProduct_id());
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    });

                });
            }

        });

        b.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void updateTotal() {
        original_count = product.getCountedQuantity();
        added = Integer.parseInt(b.edtAdd.getText().toString().trim());
        subtracted = Integer.parseInt(b.edtSubtract.getText().toString().trim());

        total = (original_count - subtracted) + added;

        b.tvTotal.setText("" + total);
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 0)
                updateTotal();
        }
    };

    private void populateFields() {
        b.tvProductTitle.setText(product.getProduct());
        b.tvContado.setText("" + product.getCountedQuantity());
        b.edtAdd.setText("0");
        b.edtSubtract.setText("0");
    }
}
