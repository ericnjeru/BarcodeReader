package com.example.barcodereader.retrofit;

import com.example.barcodereader.retrofit.response.CountedProductResponse;
import com.example.barcodereader.retrofit.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    /*Endpoint call*/
    @GET("counted/product")
    Call<CountedProductResponse> getCountedProducts();

    /*Get all products*/
    @GET("product/stock")
    Call<ProductResponse> getAllProducts();
}
