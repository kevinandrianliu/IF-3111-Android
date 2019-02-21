package com.example.tugasbesarandroid;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class SendToken extends AsyncTask<String,Void,String> {
    private final String SERVER_URL = "https://tugas-besar-if3111-2018-2019.herokuapp.com/";
    private final String TAG = this.getClass().getSimpleName();

    private TextView mResponseText;

    SendToken (TextView mResponseText){
        this.mResponseText = mResponseText;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG,"Done!");

        mResponseText.setText(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG,"Doing in background...");

        StringBuffer stringBuffer = null;
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;

        try{
            URL requestURL = new URL(SERVER_URL);
            conn = (HttpsURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write("token+id=" + strings[0]);
            bufferedWriter.flush();

            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = conn.getInputStream();

            if (inputStream == null){
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line + '\n');
            }

            if (stringBuffer.length() == 0){
                return null;
            }

            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.disconnect();
            }

            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}