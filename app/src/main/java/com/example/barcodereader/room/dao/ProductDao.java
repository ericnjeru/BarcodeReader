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

    @Query("SELECT * FROM products WHERE producto LIKE :keyword ORDER BY producto ASC")
    LiveData<List<Product>> findProduct(String keyword);

    @Query("SELECT * FROM products WHERE codBarra=:barcode")
    Product getProductByBarCode(String barcode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

}
