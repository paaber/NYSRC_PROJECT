package com.botosoft.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
@Entity
public class UsersData {


        @NonNull
        @PrimaryKey
        private int userId;
        private String name;
        private String lastName;


    public UsersData(@NonNull int userId, String name, String lastName) {

        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
    }
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
