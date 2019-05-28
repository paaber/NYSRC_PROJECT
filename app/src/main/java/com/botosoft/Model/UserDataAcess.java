package com.botosoft.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDataAcess {
    @Insert
    void insertUserid (UsersData usersData);
    @Insert
    void insertAlluserData (List<UsersData> usersDataList);
    @Query("SELECT * FROM UsersData WHERE userId = :userId")
    UsersData getUserData (int userId);
    @Update
    void updateUserDate (UsersData usersData);
    @Delete
    void deleteData (UsersData usersData);
}
