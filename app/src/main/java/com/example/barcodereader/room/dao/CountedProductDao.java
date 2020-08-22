package com.example.barcodereader.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.barcodereader.room.model.CountedProduct;

import java.util.List;


@Dao
public interface CountedProductDao {

    @Query("SELECT * FROM counted_products WHERE uploaded=0")
    LiveData<List<CountedProduct>> getAllCountedProducts();

    @Query("SELECT * FROM counted_products WHERE product_id=:product_id")
    CountedProduct findProduct(String product_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(CountedProduct product);

    @Delete
    void deleteProduct(CountedProduct product);

    @Update
    void updateProduct(CountedProduct product);

    @Query("UPDATE counted_products SET countedQuantity=:total WHERE product_id=:product_id")
    void updateSingleProduct(int total, String product_id);


    @Query("UPDATE counted_products SET uploaded=:is_uploaded WHERE product_id=:product_id")
    void setProductIsUploaded(int is_uploaded, String product_id);

}
