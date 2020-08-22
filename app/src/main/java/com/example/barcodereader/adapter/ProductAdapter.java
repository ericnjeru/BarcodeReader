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
import com.example.barcodereader.room.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> userList;
    private Context context;
    private OnClickCallBack onClickCallBack;

    public ProductAdapter(List<Product> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void setOnClickCallBack(OnClickCallBack onClickCallBack) {
        this.onClickCallBack = onClickCallBack;
    }

    public interface OnClickCallBack {
        void onItemClick(Product p, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_count, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = userList.get(position);
        holder.productName.setText(product.getProducto());
        holder.tvProductCount.setText("0");

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
