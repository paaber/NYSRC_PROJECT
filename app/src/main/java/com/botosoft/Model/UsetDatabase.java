package com.botosoft.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {UsersData.class}, version = 1, exportSchema = false)
public abstract class UsetDatabase extends RoomDatabase {
    public abstract UserDataAcess daoAccess() ;

}
