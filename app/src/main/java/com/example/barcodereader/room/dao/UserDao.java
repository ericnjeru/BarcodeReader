package com.example.barcodereader.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.barcodereader.room.model.User;


@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE id=:user_id")
    LiveData<User> getUserById(int user_id);

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    LiveData<User> getCurrentUser();

    @Query("SELECT count(*) FROM users as count")
    int getCount();

    @Query("DELETE FROM users WHERE 1")
    void deleteAllUsers();


    @Insert
    void saveUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    /*Custom update query*/
    @Query("UPDATE users SET ip_address_source=:ip_address WHERE id=:user_id")
    void updateUsername(String ip_address, int user_id);

}
