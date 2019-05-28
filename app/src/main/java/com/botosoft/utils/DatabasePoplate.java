package com.botosoft.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.botosoft.Model.PopulateLocalDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DatabasePoplate {
   private  FirebaseFirestore db = FirebaseFirestore.getInstance();
   private  CollectionReference collectionReference = db.collection("Drivers");
    public DatabasePoplate(){

    }
    public void PopulateDB(Bitmap photo,String name,String plateNumber,String carColor,String cartype){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);     // Quality 0 = smallest, 100 = best quality
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String image = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);

        Map<String, Object> user = new HashMap<>();
        user.put("FullName", name);
        user.put("PlateNumber", plateNumber);
        user.put("CarColor", carColor);
        user.put("CarType", cartype);
        user.put("Image", image);


        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
    public void PopulateDB(String offence,String offencePoint){

    }
}
