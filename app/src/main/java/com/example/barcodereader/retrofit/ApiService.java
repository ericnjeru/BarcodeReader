package com.example.barcodereader.retrofit;

import com.example.barcodereader.retrofit.response.CountedProductResponse;
import com.example.barcodereader.retrofit.response.ProductResponse;
import com.example.barcodereader.retrofit.response.SingleCountedProductResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    /*Endpoint call*/
    @GET("counted/product")
    Call<CountedProductResponse> getCountedProducts();

    /*Get all products*/
    @POST("accesso_app3.php")
    @FormUrlEncoded
    Call<ProductResponse> getAllProducts(
            @Field("get_products") int p,
            @Field("id_usario") String user_id,
            @Field("ip_destination") String ip_destination,
            @Field("ip_source") String ip_source,
            @Field("password") String password
    );

    @POST("accesso_app3.php")
    @FormUrlEncoded
    Call<SingleCountedProductResponse> saveSingleProduct(
            @Field("save_counted_products") int c,
            @Field("id_usario") String user_id,
            @Field("ip_destination") String ip_destination,
            @Field("ip_source") String ip_source,
            @Field("password") String password,
            @Field("producto") String product,
            @Field("cantidad_contada") int counted,
            @Field("cedula") String id_card,
            @Field("empresa") int business,
            @Field("sucursal") int branch_office
    );
}
