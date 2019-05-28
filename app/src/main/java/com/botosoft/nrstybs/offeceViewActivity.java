package com.botosoft.nrstybs;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class offeceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offece_view);

        Bundle b=this.getIntent().getExtras();
        assert b != null;

        String strUserOff = b.getString("userOffence");
        HashMap<String,String> data = new HashMap<>();
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("datalist");
//        data = b.getSerializable("datalist");
        TextView offenceTxt = (TextView)findViewById(R.id.textView);
//        offenceTxt.setText("");
        offenceTxt.setText(strUserOff);
        Log.d("offenceMap", "onCreate: " + hashMap);

    }

}
