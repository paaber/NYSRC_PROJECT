package com.botosoft.nrstybs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.botosoft.utils.ReadDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NoRecordActivity extends AppCompatActivity {
    private String plateNumber,carColor,carModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  DocumentSnapshot document;
    private HashMap<String, String>  data;
    private  TextView plateNumberTxt,carColorTxt,carModelTxt,chasisNumberTxt,issueDateTxt,expiryDateTxt,pgMessage,name;
    private  ProgressBar pgbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_record);

        Bundle b=this.getIntent().getExtras();
        assert b != null;
        plateNumber=b.getString("plateNumber");
        carColor=b.getString("carColor");
        carModel=b.getString("carModel");


        plateNumberTxt = (TextView) findViewById(R.id.platenumber);
        carColorTxt = (TextView) findViewById(R.id.carcolor);
        carModelTxt = (TextView) findViewById(R.id.carmodel);
        chasisNumberTxt = (TextView) findViewById(R.id.chasisnumber);
        issueDateTxt = (TextView) findViewById(R.id.issuedate);
        expiryDateTxt = (TextView) findViewById(R.id.expirydate);
        name = (TextView) findViewById(R.id.name);

        pgMessage= (TextView) findViewById(R.id.pgbartext);
        pgbar = (ProgressBar)findViewById(R.id.pgbar);
        pgbar.setVisibility(View.VISIBLE);
        pgMessage.setVisibility(View.VISIBLE);




//        readDb(plateNumber);

            showDetails();


    }

    void showDetails(){
        plateNumberTxt.setText(plateNumber);
        carColorTxt.setText(carColor);
        carModelTxt.setText(carModel);
        pgbar.setVisibility(View.INVISIBLE);
        pgMessage.setVisibility(View.INVISIBLE);

    }
    private void readDb(String pltNum){
        DocumentReference docRef = db.collection("DRIVERS").document(pltNum);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    document = task.getResult();
                    Map<String,Object> dataMap = document.getData();

                    data = new HashMap<String, String>();
                    for (Map.Entry<String,Object> entry : dataMap.entrySet())
                        data.put(entry.getKey(),entry.getValue().toString());

                    Log.d(TAG, "onComplete: "+ document.getData());
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        name.setText(String.format("Name : %s", data.get("NAME")));

                        plateNumberTxt.setText(String.format("Name : %s", data.get("PLATENUMBER")));
                        carColorTxt.setText(String.format("Name : %s", data.get("CARCOLOR")));
                        carModelTxt.setText(String.format("Name : %s", data.get("CARMODE")));
                        chasisNumberTxt.setText(String.format("Name : %s", data.get("CHASISNUMBER")));
                        issueDateTxt.setText(String.format("Name : %s", data.get("ISSUEDATE")));
                        expiryDateTxt.setText(String.format("Name : %s", data.get("EXPIRYDATE")));

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                        byte[] imageBytes = baos.toByteArray();
                        //decode base64 string to image
                        imageBytes = Base64.decode(data.get("IMAGEBASE64STRING"), Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        ImageView userimg = (ImageView)findViewById(R.id.imageView);
                        userimg.setImageBitmap(decodedImage);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


}
