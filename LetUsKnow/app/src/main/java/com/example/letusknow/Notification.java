package com.example.letusknow;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Notification extends ActionBarActivity {

    EditText comments;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        send = (Button) findViewById(R.id.btnSend);
        comments = (EditText) findViewById(R.id.comment);

        send.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {

                                        String url = "http://172.19.74.25:8081/LetUsKnow/insertSW.php";
                                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                                        params.add(new BasicNameValuePair("comment", comments.getText().toString()));
                                        Log.e("test",params.toString());


                                        String resultServer;


                                        try {

                                            resultServer = getHttpPost(url, params);

                                            Log.e("test",resultServer.toString());

                                            if (resultServer != null) {
                                                Toast.makeText(Notification.this, "Insert pass", Toast.LENGTH_SHORT).show();

                                                Intent intentMain = new Intent(Notification.this, SplashScreen.class);

                                                Log.e("send data to", "Comment");

                                                startActivity(intentMain);


                                            }
                                        } catch (JsonSyntaxException e) {
                                            Toast.makeText(Notification.this,
                                                    "Insert fail",
                                                    Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }

                                    }
                                }

        );

    }


    public String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpGet = new HttpPost(url);

        try {
            httpGet.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();

    }
}










