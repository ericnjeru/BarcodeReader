package com.example.barcodereader.retrofit.response;

import com.example.barcodereader.room.model.CountedProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleCountedProductResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("counted_products")
    @Expose
    private CountedProduct countendProducts = null;

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

    public CountedProduct getCountendProducts() {
        return countendProducts;
    }

    public void setCountendProducts(CountedProduct countendProducts) {
        this.countendProducts = countendProducts;
    }
}
