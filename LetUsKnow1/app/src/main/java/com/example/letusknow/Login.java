package com.example.letusknow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
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
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class Login extends ActionBarActivity {
    private String url="http://192.168.56.1:8081/LetUsKnow";
    private List<Passcode> listpasscode;
    String edtPassCode;
    List<String> strings;
    EditText txtpasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        /*** Rows 1 ***/
        map = new HashMap<String, String>();
        map.put("Date", "20/12/2558");
        map.put("Name", "The villa is necessary to suspend the water service during 3AM -3PM");

        MyArrList.add(map);

        /*** Rows 2 ***/
        map = new HashMap<String, String>();
        map.put("Date", "30/12/2558");
        map.put("Name", "The villa is necessary to suspend the water service during 3AM -3PM");

        MyArrList.add(map);

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(Login.this, MyArrList, R.layout.column,
                new String[] {"Date", "Name", }, new int[] {R.id.ColDate, R.id.ColName});
        lisView1.setAdapter(sAdap);


        // Permission StrictMode

        txtpasscode = (EditText) findViewById(R.id.etPasscode);


        // btnLogin
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);

        GsonBuilder builder = new GsonBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setConverter(new GsonConverter(builder.create()))
                .build();

        ApiService retrofit = restAdapter.create(ApiService.class);
        retrofit.getPasscodeWithCallback(new Callback<List<Passcode>>() {
            @Override
            public void success(List<Passcode> passcodes, Response response) {
                listpasscode = passcodes;
                strings = new ArrayList<String>();
                for (Passcode object : listpasscode) {
                    strings.add(object.getPasscode());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassCode = txtpasscode.getText().toString();
                if (txtpasscode.getText().toString().trim().length() > 0) {
                    if (strings.contains(edtPassCode)) {
                        Log.d("Passcode", "EditText=" + edtPassCode + " Check=" + strings.toString());
                        Toast.makeText(Login.this, "Login pass", Toast.LENGTH_SHORT).show();
                        Intent intentMain = new Intent(Login.this, Notification.class);

                        startActivity(intentMain);
                    } else {
                        Toast.makeText(Login.this,
                                "Passcord Incorect", Toast.LENGTH_LONG).show();
                        Log.d("Passcode", "" + edtPassCode + "" + strings.toString());
                        Log.d("Passcode", "EditText=" + edtPassCode + " Check=" + strings.toString());
                    }
                } else {
                    Toast.makeText(Login.this,
                            "Please enter Passcode", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
