package com.example.berzerk.amfr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DefaulterDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter_display);

        Intent i = getIntent();

        Bundle bundle = getIntent().getExtras();
        //ArrayList<Integer> count = bundle.getParcelableArrayList("count");
    }
}
