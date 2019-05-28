package com.botosoft.Model;

import android.arch.persistence.room.Room;
import android.content.Context;

public class PopulateLocalDB {
    private static final String DATABASE_NAME = "user_db";
    private UsetDatabase usetDatabase;
    private int userId;
    private String name;
    private String lastname;
    private  UsersData data ;

    public PopulateLocalDB(int userId,String name,String lastname,Context context){
        userId = userId;
        name = name;
        lastname = lastname;
        initDB(context);
        populate(userId,name,lastname);
    }
    private void initDB(Context context){
        usetDatabase = Room.databaseBuilder(context,UsetDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }
    private void populate(final int id, final String n, final String ln){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UsersData usersData = new UsersData(id,n,ln);
                usersData.setUserId(userId);

                usetDatabase.daoAccess().insertUserid(usersData);
            }
        }) .start();
    }


}
