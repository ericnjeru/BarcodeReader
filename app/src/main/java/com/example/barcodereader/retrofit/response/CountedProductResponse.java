package com.example.barcodereader.retrofit.response;

import com.example.barcodereader.room.model.CountedProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountedProductResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("counted_products")
    @Expose
    private List<CountedProduct> countendProducts = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CountedProduct> getCountendProducts() {
        return countendProducts;
    }

    public void setCountendProducts(List<CountedProduct> countendProducts) {
        this.countendProducts = countendProducts;
    }
}
