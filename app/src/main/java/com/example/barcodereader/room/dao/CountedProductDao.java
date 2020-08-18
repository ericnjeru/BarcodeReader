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

    @Query("SELECT * FROM counted_products")
    LiveData<List<CountedProduct>> getAllCountedProducts();

    @Query("SELECT * FROM counted_products WHERE product_id=:product_id")
    CountedProduct findProduct(int product_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(CountedProduct product);

    @Delete
    void deleteProduct(CountedProduct product);

    @Update
    void updateProduct(CountedProduct product);

}
