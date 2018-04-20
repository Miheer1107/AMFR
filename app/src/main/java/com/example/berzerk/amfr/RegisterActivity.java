package com.example.berzerk.amfr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText userNameEditText;
    String username;
    EditText nameEditText;
    String name;
    EditText ageedittext;
    String age;
    EditText passwordedittext;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        userNameEditText = (EditText) findViewById(R.id.etUserName);
        ageedittext = (EditText) findViewById(R.id.etAge);
        nameEditText = (EditText) findViewById(R.id.etName);
        passwordedittext = (EditText) findViewById(R.id.etPassword);
        assert bRegister != null;
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = userNameEditText.getText().toString();
                age = ageedittext.getText().toString();
                name = nameEditText.getText().toString();
                password = passwordedittext.getText().toString();


                if (username.matches("")) {
                    Toast.makeText(getApplicationContext(), "You have not entered a Name", Toast.LENGTH_SHORT).show();
                } else if (age.matches("")) {
                    Toast.makeText(getApplicationContext(), "You have not entered an age", Toast.LENGTH_SHORT).show();
                } else if (password.matches("")) {
                    Toast.makeText(getApplicationContext(), "You have not entered a Password", Toast.LENGTH_SHORT).show();
                } else if (name.matches("")) {
                    Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (name.matches(username) || name.matches(password) || username.matches(password)) {
                    Toast.makeText(getApplicationContext(), "No two fields can have matching values", Toast.LENGTH_SHORT).show();
                }  else {

                    register();

                }





                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                RegisterActivity.this.startActivity(intent);

            }
        });
    }


    private void register() {

        String url = "http://192.168.43.173:5001/register";
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("age",age);


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("count") == 1){
                        Log.e("in register","Response = " + response);
                        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        myIntent.putExtra("name", name); //Optional parameters
                        startActivity(myIntent);
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


}

