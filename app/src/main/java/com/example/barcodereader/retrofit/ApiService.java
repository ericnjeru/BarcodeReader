package com.example.barcodereader.retrofit;

import com.example.barcodereader.retrofit.response.CountedProductResponse;
import com.example.barcodereader.retrofit.response.ProductResponse;
import com.example.barcodereader.retrofit.response.SingleCountedProductResponse;
import com.example.barcodereader.room.model.CountedProduct;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    /*Endpoint call*/
    @GET("counted/product")
    Call<CountedProductResponse> getCountedProducts();

    /*Get all products*/
    @GET("product/stock")
    Call<ProductResponse> getAllProducts();

    @POST("counted/product/store")
    Call<SingleCountedProductResponse> saveSingleProduct(@Body CountedProduct countedProduct);
}
