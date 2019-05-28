package com.botosoft.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ReadDatabase {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  DocumentSnapshot document;
    private HashMap<String, String>  data;

    public HashMap readDB(String pltNum){


        DocumentReference docRef = db.collection("DRIVERS").document(pltNum);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                   document = task.getResult();
                   Map <String,Object> dataMap = document.getData();

                    data = new HashMap<String, String>();
                    for (Map.Entry<String,Object> entry : dataMap.entrySet())
                        data.put(entry.getKey(),entry.getValue().toString());


                    Log.d(TAG, "onComplete: "+ document.getData());
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return data;
    }
}
