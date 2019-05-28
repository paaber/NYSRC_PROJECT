package com.botosoft.nrstybs;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button cardScanBtn;
    private Button fingerPrintScanBtn;
    private Button queryPlateNumberBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardScanBtn = (Button)findViewById(R.id.dlscanbtn);
        fingerPrintScanBtn = (Button)findViewById(R.id.fpscanbtn);
        queryPlateNumberBtn = (Button)findViewById(R.id.qrypltnum);

        cardScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intn = new Intent(MainActivity.this,ScanActivity.class);
                        startActivity(intn);
                    }
                },1000);
            }
        });
        queryPlateNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intn = new Intent(MainActivity.this,CameraActivity.class);
                        startActivity(intn);
                    }
                },1000);
            }
        });
        fingerPrintScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intn = new Intent(MainActivity.this,DataWithOffenseActivity.class);
                        startActivity(intn);
                    }
                },1000);
            }
        });
    }
}
