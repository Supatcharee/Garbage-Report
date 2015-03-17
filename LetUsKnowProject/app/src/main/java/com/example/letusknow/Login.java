package com.example.letusknow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 12) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Permission StrictMode


        final EditText txtpasscode = (EditText) findViewById(R.id.etPasscode);


        // btnLogin
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        // Perform action on click
        //เมื่อ click ปุ่ม login ให้ check ว่ามีค่าว่างหรือไม่ ถ้ามีค่าว่างให้เข้าทำ else
        // ถ้าไม่ว่างให้เข้าทำ if->ให้เรียก service login เพื่อ check ค่าที่เข้ามาว่ามีค่าที่อยู่ใน database หรือไม่
        btnLogin.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View v) {

                                            String url = "http://192.168.1.10/htdocs/LetUsKnow/LoginSW.php";
                                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                                            params.add(new BasicNameValuePair("passcode", txtpasscode.getText().toString()));

                                            String resultServer;

                                            try {

                                                resultServer = getHttpPost(url, params);

                                                if (resultServer != null) {
                                                    Toast.makeText(Login.this, "Login pass", Toast.LENGTH_SHORT).show();

                                                    Intent intentMain = new Intent(Login.this, Notification.class);

                                                    String Passcode = txtpasscode.getText().toString();

                                                    intentMain.putExtra("Passcode", Passcode);

                                                    Log.e("send data to", "Passcode");

                                                    startActivity(intentMain);


                                                } else {
                                                    // Dialog

                                                    ad.setTitle("Incorrect Passcode !");
                                                    ad.setIcon(android.R.drawable.btn_star_big_on);
                                                    ad.setPositiveButton("Close", null);
                                                    ad.show();
                                                    txtpasscode.setText("");

                                                }
                                            } catch (JsonSyntaxException e) {
                                                Toast.makeText(Login.this,
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
/*$db->close();
        echo json_encode($response);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

