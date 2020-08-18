package com.example.barcodereader.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.room.model.User;

import java.util.List;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<User>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

}
