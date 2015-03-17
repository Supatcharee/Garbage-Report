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

    private static final int TAKE_PICTURE = 100;
    private static final int TAKE_PICTURE_SAVE = 101;
    private File imageFile;

    Button takePhoto;
    ImageView imgPreview;

    EditText comments;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        takePhoto = (Button) findViewById(R.id.btnTakePhoto);
        send = (Button) findViewById(R.id.btnSend);
        comments = (EditText) findViewById(R.id.comment);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String url = "http://192.18.1.10/htdocs/LetUsKnow/insertSW.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("passcode", comments.getText().toString()));

                String resultServer;

                try {

                    resultServer = getHttpPost(url, params);

                    if (resultServer != null) {
                        Toast.makeText(Notification.this, "Insert pass", Toast.LENGTH_SHORT).show();

                        Intent intentMain = new Intent(Notification.this, Map.class);

                        Log.e("send data to", "Passcode");

                        startActivity(intentMain);


                    } else {
                        // Dialog

                        ad.setTitle("Incorrect Passcode !");
                        ad.setIcon(android.R.drawable.btn_star_big_on);
                        ad.setPositiveButton("Close", null);
                        ad.show();
                        comments.setText("");

                    }
                } catch (JsonSyntaxException e) {
                    Toast.makeText(Notification.this,
                            "Login fail!",
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










    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

