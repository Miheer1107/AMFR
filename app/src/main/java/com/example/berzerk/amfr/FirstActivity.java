package com.example.berzerk.amfr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegisterHere = (Button) findViewById(R.id.bRegisterHere);

        bRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(FirstActivity.this, RegisterActivity.class);
                FirstActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginintent = new Intent(FirstActivity.this, LoginActivity.class);
                FirstActivity.this.startActivity(loginintent);
            }
        });

    }
}
