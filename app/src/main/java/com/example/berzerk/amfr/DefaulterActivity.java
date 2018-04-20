package com.example.berzerk.amfr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DefaulterActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    String selemonth;
    String[] month = { "January", "February", "March", "April", "May","June","July","August","September","October","November","December"  };
    String jsonresponse;
    String s ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the month list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,month);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Button reset = (Button)findViewById(R.id.reset);
        TextView sm = (TextView)findViewById(R.id.textView2) ;
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.setText("");
            }
        });



    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(),month[position] ,Toast.LENGTH_LONG).show();
        selemonth = (String) month[position];


        Log.d("TAG",selemonth);
        executeLoopj(selemonth);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    void executeLoopj(String month){


        String url = "http://192.168.43.173:5001/defaulter";
        Map<String,String> params = new HashMap<String,String>();
        params.put("month",month);
        params.put("subject","SPCC");

        //Log.e(LOG,new JSONObject(params).toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("TAG",response.toString());
                jsonresponse =  response.toString();
                final ArrayList<Integer> count = new ArrayList<Integer>();

                try {
                    JSONArray jsonArray = response.getJSONArray("count");
                    //JSONObject jsonObject = jsonArray.getJSONObject(0);
                    for(int i=0;i<jsonArray.length();i++){
                        int m = (int) jsonArray.get(i);
                        count.add(m);
                    }



                    for(int i=0;i<count.size();i++){
                        System.out.println(count.get(i));
                        Log.d("TAG", String.valueOf(count.get(i)));
                    }
                    Button button = (Button) findViewById(R.id.button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView smonth = (TextView)findViewById(R.id.textView2);

                            smonth.setText(count.toString());
                        }
                    });





                } catch (JSONException e) {
                    e.printStackTrace();
                }















                //                try {
//                    if(response.getInt("count") == 1){
//                        Log.e(LOG,"Response = " + response);
//                        Intent myIntent = new Intent(LoginActivity.this, MainPageActivity.class);
//                        myIntent.putExtra("email", email); //Optional parameters
//                        LoginActivity.this.startActivity(myIntent);
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();}
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonRequest,"tag");





        //        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//
//
//        String File_URL = "http://192.168.43.173:5001/defaulter";
//        RequestParams requestParams = new RequestParams();
//
//        requestParams.put("month",month);
//        requestParams.put("subject","SPCC");
//
//        asyncHttpClient.post(File_URL,requestParams,new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                super.onSuccess(statusCode,headers,response);
//                Log.d("TAG",response.toString());
//                jsonresponse =  response.toString();
//
//
//            }
//        });

    }

}




