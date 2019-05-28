package com.botosoft.nrstybs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.botosoft.Model.AppDatabase;
import com.botosoft.Model.User;
import com.botosoft.utils.DatabaseInitializer;
import com.botosoft.utils.Offfences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ImmutableList;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataWithOffenseActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private AppDatabase mDb;
    private  TextView textUser,loadText;
    private String strUserDet;
    private  String[] detailsWithoutImg;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private HashMap<String,String>data;
    private  StringBuilder textBuilder = new StringBuilder();
    private ProgressBar pgbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_with_offense);

         textUser = (TextView)findViewById(R.id.userData);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        StringBuilder sb = new StringBuilder();
        StringBuilder textBuilder = new StringBuilder();



        populateDb();

        Bundle b=this.getIntent().getExtras();
        assert b != null;

        strUserDet = b.getString("details");
        String strUserDetnew = new String(strUserDet);
        String[] strUserDetList = strUserDet.split("#");
        detailsWithoutImg = strUserDetList[0].split("\\|");

        Log.d("map", "onCreate: " + strUserDetList[0]);
        Log.d("map1", "onCreate: " + strUserDet);
        textBuilder.append("FullName : " + detailsWithoutImg[0]);
        textBuilder.append(" \n ");
        textBuilder.append("Address Street: " + detailsWithoutImg[1]);
        textBuilder.append("\n");
        textBuilder.append("Gender: " + detailsWithoutImg[2]);
        textBuilder.append("\n");
        textBuilder.append("Height: " + detailsWithoutImg[3]);
        textBuilder.append("\n");
        textBuilder.append("1ST|ISS ST: " + detailsWithoutImg[4]);
        textBuilder.append("\n");
        textBuilder.append("Blood Group: " + detailsWithoutImg[5]);
        textBuilder.append("\n");
        textBuilder.append("Remarks: " + detailsWithoutImg[6] + "\t" + "GL :" + detailsWithoutImg[7] +
                "\t" + "REP :" + detailsWithoutImg[8] + "\t" + "REN :" + detailsWithoutImg[9]+ "\t" + "END:" + detailsWithoutImg[10]  );
        textBuilder.append("\n");
        textBuilder.append("NOK : " + detailsWithoutImg[11]);
        textBuilder.append("\n");
        textBuilder.append("Date of issue : " + detailsWithoutImg[12]);
        textBuilder.append("\n");
        textBuilder.append("Expiring Date : " + detailsWithoutImg[13]);
        textBuilder.append("\n");
        pgbar = (ProgressBar)findViewById(R.id.pgbar);
        loadText = (TextView)findViewById(R.id.pgbartext);

        textUser.setText(textBuilder.toString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        byte[] imageBytes = baos.toByteArray();
        //decode base64 string to image
        imageBytes = Base64.decode(strUserDetList[1], Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ImageView userimg = (ImageView)findViewById(R.id.userImage);
        userimg.setImageBitmap(decodedImage);

        Button viewOffence = (Button)findViewById(R.id.viewOffences);
        pgbar.setVisibility(View.INVISIBLE);
        loadText.setVisibility(View.INVISIBLE);
        viewOffence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readOffences(detailsWithoutImg[0]);
                pgbar.setVisibility(View.VISIBLE);
                loadText.setVisibility(View.VISIBLE);
            }
        });


    }

    public void showoffencepopup(View view) {
        PopupMenu offencePopup = new PopupMenu(this,view);
        offencePopup.setOnMenuItemClickListener(this);
        offencePopup.inflate(R.menu.offece_menu);
        offencePopup.show();
    }

    private void firepopulateDB(String offAbv){

        Offfences offences = new Offfences();
        String[] offenceArr = offences.offencesWithPoints.get(offAbv);
        String offenceActVal = offences.offencesWithAbv.get(offenceArr[1]);
        assert offenceArr != null;
        recordOffense(offenceActVal,offenceArr[0],detailsWithoutImg[0],offenceArr[1]);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.LSV:
                firepopulateDB("LSV");
                return true;
            case R.id.ROB:
                firepopulateDB("ROB");
                return true;
            case R.id.RTV:
                firepopulateDB("RTV");
                return true;
            case R.id.SLV:
                firepopulateDB("SLV");
                return true;
            case R.id.VLV:
                firepopulateDB("VLV");
                return true;
            case R.id.NPV:
                firepopulateDB("NPV");
                return true;
            case R.id.DLV:
                firepopulateDB("DLV");
                return true;
            case R.id.WOV:
                firepopulateDB("WOV");
                return true;
            case R.id.RMV:
                firepopulateDB("RMV");
                return true;
            case R.id.CSV:
                firepopulateDB("CSV");
                return true;
            case R.id.DGD:
                firepopulateDB("DGD");
                return true;
            case R.id.DUI:
                firepopulateDB("DUI");
                return true;
            default:
                return super.onContextItemSelected(menuItem);

        }

    }
//    protected void onDestroy() {
//        AppDatabase.destroyInstance();
//        super.onDestroy();
//    }

    private void populateDb() {
        DatabaseInitializer.populateSync(mDb);
    }

    private User fetchData() {
        // This activity is executing a query on the main thread, making the UI perform badly.
        User user = mDb.userModel().loadUserById(1);
      return user;
    }

    public void onRefreshBtClicked(View view) {
        textUser.setText("");
        fetchData();
    }

    public void readOffences(String nameQuery){

        DocumentReference docRef = db.collection("OFFENCES").document(nameQuery);
        final ArrayList<String> offencesList = new ArrayList<String>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String,Object> dataMap = document.getData();

                    data = new HashMap<String, String>();
                    int sizeList = 0;
                    String[] offenceList_Str = new String[dataMap.size()];
                    StringBuilder textbuild=new StringBuilder();
                    for (Map.Entry<String,Object> entry : dataMap.entrySet()) {
                        data.put(entry.getKey(), entry.getValue().toString());
                        offenceList_Str[0] = entry.getValue().toString();
                        sizeList+=1;

                        offencesList.add(entry.getValue().toString());
                        textbuild.append(entry.getKey());
                        textbuild.append(" : ");
                        textbuild.append(entry.getValue().toString());
                        textbuild.append("\n");
                    }

                    Log.d("offences", "onComplete: " + offencesList.toString());
//                    ArrayAdapter adapter = new ArrayAdapter<String>(DataWithOffenseActivity.this,R.layout.progressfetch,offencesList);
//
//                    ListView listView = (ListView) findViewById(R.id.offence_list);
//                    listView.setAdapter(adapter);





                    if (document.exists()) {
                        Log.d("Exist", "DocumentSnapshot data: " + document.getData());
                        Intent intnt = new Intent(DataWithOffenseActivity.this,offeceViewActivity.class);
                        intnt.putExtra("userOffence", textbuild.toString());
                        intnt.putExtra("datalist",data);
                        pgbar.setVisibility(View.INVISIBLE);
                        loadText.setVisibility(View.INVISIBLE);
                        startActivity(intnt);
                    } else {
                        Log.d("NonExist", "No such document");
                    }
                } else {
                    Log.d("Failure", "get failed with ", task.getException());
                }
            }
        });

    }
    private void recordOffense(String offeceAbr,String offencePoint,String name,String offenceAct){


        // Create a new user with a first and last name
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        Map<String, List<String>> user = new HashMap<>();
        user.put(offeceAbr, Arrays.asList(new String[]{offencePoint, format,offenceAct}));

// Add a new document with a generated ID
        db.collection("OFFENCES").document(name)
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Sucess", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Faliure", "Error writing document", e);
                    }
                });
    }

}
