package com.example.barcodereader.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.barcodereader.room.dao.CountedProductDao;
import com.example.barcodereader.room.dao.ProductDao;
import com.example.barcodereader.room.dao.UserDao;
import com.example.barcodereader.room.model.CountedProduct;
import com.example.barcodereader.room.model.Product;
import com.example.barcodereader.room.model.User;

/*Add all entities here*/
@Database(entities = {User.class, Product.class, CountedProduct.class}, exportSchema = false, version = 10)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "app_database";

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    /*Define all dao classes here*/
    public abstract UserDao userDao();

    public abstract ProductDao productDao();

    public abstract CountedProductDao countedProductDao();

}
