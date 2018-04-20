package com.example.berzerk.amfr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG = "LoginPage";
    EditText userEmailEditText;// = (EditText) findViewById(R.id.uEmail);
    String email;// = userEmailEditText.getText().toString();

    EditText userPassEditText;// = (EditText) findViewById(R.id.uPass);
    String password;// = userPassEditText.getText().toString();
    JSONArray user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setContentView(R.layout.activity_login);
        Button b = (Button) findViewById(R.id.login);
        userEmailEditText = (EditText) findViewById(R.id.emailId);
        userPassEditText = (EditText) findViewById(R.id.password);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = userEmailEditText.getText().toString();

                password = userPassEditText.getText().toString();


                if (email.matches("")) {
                    Toast.makeText(getApplicationContext(), "You have not entered an Email ID", Toast.LENGTH_SHORT).show();
                } else if (password.matches("")) {
                    Toast.makeText(getApplicationContext(), "You have not entered a Password", Toast.LENGTH_SHORT).show();
                } else if (email.matches(password)) {
                    Toast.makeText(getApplicationContext(), "No two fields can have matching values", Toast.LENGTH_SHORT).show();
                } else
                    login2();
            }
        });

/*
                Intent myIntent = new Intent(LoginPage.this, MainActivity.class);
           /* myIntent.putExtra("key", value);*/ //Optional parameters
      /*  LoginPage.this.startActivity(myIntent);*/


    }


    public void login2(){
        String url = "http://192.168.43.173:5001/validate";
        Map<String,String> params = new HashMap<String,String>();
        params.put("email",email);
        params.put("password",password);

        Log.e(LOG,new JSONObject(params).toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("count") == 1){
                        Log.e(LOG,"Response = " + response);
                        Intent myIntent = new Intent(LoginActivity.this, MainPageActivity.class);
                        myIntent.putExtra("email", email); //Optional parameters
                        LoginActivity.this.startActivity(myIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonRequest,"tag");
    }


























//        final Button login=  (Button)findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentlogin = new Intent(LoginActivity.this, MainPageActivity.class);
//                LoginActivity.this.startActivity(intentlogin);
//            }
//        });
    }

