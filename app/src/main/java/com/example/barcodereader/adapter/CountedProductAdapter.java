package com.example.barcodereader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodereader.R;
import com.example.barcodereader.room.model.CountedProduct;

import java.util.List;

public class CountedProductAdapter extends RecyclerView.Adapter<CountedProductAdapter.ViewHolder> {

    private List<CountedProduct> userList;
    private Context context;
    private OnClickCallBack onClickCallBack;

    public CountedProductAdapter(List<CountedProduct> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void setOnClickCallBack(OnClickCallBack onClickCallBack) {
        this.onClickCallBack = onClickCallBack;
    }

    public interface OnClickCallBack {
        void onItemClick(CountedProduct p, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_count, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountedProduct product = userList.get(position);
        holder.productName.setText(product.getProduct());
        holder.tvProductCount.setText("" + product.getCountedQuantity());

        holder.btnEditProduct.setOnClickListener(v -> {
            onClickCallBack.onItemClick(product, position);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, tvProductCount;
        AppCompatButton btnEditProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            tvProductCount = itemView.findViewById(R.id.tvProductCount);
            btnEditProduct = itemView.findViewById(R.id.btnEditProduct);

        }
    }
}
