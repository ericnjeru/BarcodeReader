package com.example.barcodereader.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.barcodereader.room.model.Product;

import java.util.List;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE product LIKE :keyword")
    LiveData<List<Product>> findProduct(String keyword);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

}
