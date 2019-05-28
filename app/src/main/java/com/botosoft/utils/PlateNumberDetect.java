package com.botosoft.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;

import com.botosoft.nrstybs.R;

import java.net.*;
import java.io.*;
import java.nio.file.*;

public class PlateNumberDetect {
    private   String json_content = "";
    private String byteString;
    Bitmap originalImage;

    private int width;

    private int height;

    private int newWidth = 200;

    private int newHeight = 200;

    private Matrix matrix;

    private Bitmap resizedBitmap;

    private float scaleWidth ;

    private float scaleHeight;

    ByteArrayOutputStream outputStream;
    public String[] AnalyImage(Bitmap photo){
        Log.d("shi", "AnalyImage: " + "started1");
        try
        {
            String secret_key = "sk_13bc33006346bfb92f8dc0ba";

            // Read image file to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);     // Quality 0 = smallest, 100 = best quality
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            byte[] encoded = android.util.Base64.encode(byteArray, Base64.DEFAULT);
            byteString =  android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);

            // Setup the HTTPS connection to api.openalpr.com
            URL url = new URL("https://api.openalpr.com/v2/recognize_bytes?recognize_vehicle=1&country=us&secret_key=" + secret_key);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            URLConnection con = url.openConnection();
//            HttpURLConnection http = (HttpURLConnection)con;
            conn.setRequestMethod("POST"); // PUT is another valid option
            conn.setFixedLengthStreamingMode(encoded.length);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Send our Base64 content over the stream
            OutputStream os = conn.getOutputStream();
            Log.d("shi", "AnalyImage: " + "started2");
            os.write(encoded);
            Log.d("shi", "AnalyImage: " + "started3");
            int status_code = conn.getResponseCode();
            Log.d("shi", "AnalyImage: " + status_code);
            if (status_code == 200)
            {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    json_content += inputLine;
                in.close();

                Log.d("shi", "AnalyImage: " + json_content);
            }
            else
            {

            }


        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    return new String[]{json_content, byteString};
    }
//    private byte[] compree( Bitmap photo ){
//        originalImage = BitmapFactory.decodeResource(getResources(), R.id.capturedImage);
//
//        width = photo.getWidth();
//
//
//        height = photo.getHeight();
//
//
//        matrix = new Matrix();
//
//        scaleWidth = ((float) newWidth) / width;
//
//        scaleHeight = ((float) newHeight) / height;
//
//        matrix.postScale(scaleWidth, scaleHeight);
//
//
//        resizedBitmap = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
//
//        outputStream = new ByteArrayOutputStream();
//
//        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//
//        byte[] byteArray = outputStream.toByteArray();
//        return  byteArray;
//    }
}
