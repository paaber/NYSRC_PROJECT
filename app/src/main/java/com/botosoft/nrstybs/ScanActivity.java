package com.botosoft.nrstybs;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.botosoft.utils.CardOwner;
import com.botosoft.utils.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScanActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    private String content;
    private boolean isToScan = false;
    ProgressDialog pd;
    private static final String LOG_TAG = ScanActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        isToScan = true;
    }


    public void get_nfc(View v) {
        isToScan = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Services.enableNfcForeground(this.nfcAdapter, this);

    }

    @Override
    protected void onPause() {

        super.onPause();
        Services.disableNfcForeground(this.nfcAdapter, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(ScanActivity.this, "started", Toast.LENGTH_LONG).show();
        //isToScan &&
        // Is the intent for a new NFC tag discovery?
        if (isToScan && intent != null && intent.getAction() == NfcAdapter.ACTION_TECH_DISCOVERED) {
            isToScan = false;
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(ScanActivity.this, "started1", Toast.LENGTH_LONG).show();
            MifareClassic mifareClassicTag = MifareClassic.get(tag);

            // Does the tag support the IsoDep interface?
            if (mifareClassicTag == null) {
                Toast.makeText(this, "NO MIFARE CLASSIC TAG FOUND", Toast.LENGTH_LONG).show();
            } else {
                new ReadMifareClassicTask(ScanActivity.this, mifareClassicTag).execute();
            }


        }
    }

    private class ReadMifareClassicTask extends AsyncTask<Void, Void, Void> {

        /*
        MIFARE Classic tags are divided into sectors, and each sector is sub-divided into blocks.
        Block size is always 16 bytes (BLOCK_SIZE). Sector size varies.
        MIFARE Classic 1k are 1024 bytes (SIZE_1K), with 16 sectors each of 4 blocks.
        MIFARE Classic 2k are 2048 bytes (SIZE_2K), with 32 sectors each of 4 blocks.
        MIFARE Classic 4k are 4096 bytes (SIZE_4K). with 40 sectors, The first 32 sectors contain 4 blocks and the last 8 sectors contain 16 blocks.

        */

        MifareClassic taskTag;
        boolean success;
        /*final int numOfSector = 32;
        final int numOfBlockInSector = 4;*/


        int numOfSector = 32;
        int numOfBlockInSector = 4;
        byte[] buffer_;
        int sod_sector = 0;
        byte[][] header = new byte[4][16];
        byte[] f_header;
        byte[][][] buffer = new byte[numOfSector][numOfBlockInSector][MifareClassic.BLOCK_SIZE];
        String text;

        int lastSector = 40;
        int numOfBlocksInLastSector = 16;

        ReadMifareClassicTask(ScanActivity scanCardActivity, MifareClassic tag) {
            pd = new ProgressDialog(scanCardActivity);
            pd.setTitle("NRSY");
            pd.setMessage("Scanning card please wait");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            taskTag = tag;
            success = false;
        }

        @Override
        protected void onPreExecute() {

            pd.show();

            //Toast.makeText(MyApplication.getAppContext(),"Reading Tag, don't remove it!", Toast.LENGTH_LONG).show();
            Log.i(LOG_TAG, "Reading Tag");
        }

        @Override
        protected Void doInBackground(Void... params) {

            //waec key
            byte[] KEY_DEFAULT =
                    {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

            try {
                taskTag.connect();

                //READ SECTOR 0 FOR SOME BASIC INFO
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(256);
                int blockIndex = 0;


                blockIndex = 0;

                //READ REMAINING SECTORS
                for (int s = 1; s < numOfSector; s++) {
                    if (taskTag.authenticateSectorWithKeyA(s, KEY_DEFAULT)) {
                        numOfBlockInSector = taskTag.getBlockCountInSector(s);
                        for (int b = 0; b < numOfBlockInSector; b++) {
                            if (b == 3) {
                                continue;
                            }
                            blockIndex = (s * numOfBlockInSector) + b;
                            byteArrayOutputStream.write(taskTag.readBlock(blockIndex));
                        }
                    }
                }
                int last_block = blockIndex + 1;
                for (int s = 32; s < lastSector; s++) {
                    if (taskTag.authenticateSectorWithKeyA(s, KEY_DEFAULT)) {
                        for (int b = 0; b < 15; b++) {
                            last_block++;
                            byteArrayOutputStream.write(taskTag.readBlock(last_block));
                        }
                        last_block++;
                    }
                }
                buffer_ = byteArrayOutputStream.toByteArray();

                success = true;
            } catch (IOException e) {
                e.printStackTrace();


            } finally {
                if (taskTag != null) {
                    try {
                        taskTag.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        public int ToUInt16(byte[] bytes, int index) {
            return (short) ((bytes[index + 1] & 0xFF) | ((bytes[index] & 0xFF) << 0));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                pd.dismiss();
                content = new String(buffer_);
                Log.i("output", content);
                Intent in = new Intent(ScanActivity.this,DataWithOffenseActivity.class);
                in.putExtra("details",content);
                startActivity(in);

            } catch (Exception ex) {
                pd.dismiss();
                ex.printStackTrace();
                isToScan = true;
            }


        }
//        private CardOwner generateStudentDetails(String[] textData, byte[] photo){
//            CardOwner student = new CardOwner();
//            student.setSurname(textData[0]);
//            student.setFirstname(textData[1]);
//            student.setMiddlename(textData[2]);
//            String dob = textData[3].substring(0,2)+"/"+textData[3].substring(2,4)+"/"+textData[3].substring(4);
//            student.setDob(dob);
//            student.setGender(textData[4]);
//            student.setExamNo(textData[5]);
//            String center_number = textData[5];
//            center_number = center_number.substring(0,7);
//            Center center = new Center();
//            center.setState(textData[6]);
//            center.setName(textData[7]);
//            center.setCode(center_number);
//
//            student.setCenter(center);
//            List<Paper> papers = new ArrayList();
//            for (int i=8; i<textData.length; i++){
//                Paper paper = new Paper();
//                paper.setDescription(textData[i]);
//                papers.add(paper);
//            }
//            student.setSubjects(papers);
//            student.setImage(photo);
//
//
//
//            return student;
//        }

    }
}
