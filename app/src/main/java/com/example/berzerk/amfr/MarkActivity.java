package com.example.berzerk.amfr;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MarkActivity extends AppCompatActivity {
    ImageView imgview;
    String path;
    private static final int SELECT_PICTURE = 100;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    String [] permissionRequired= new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
    String jsonresponse;
    final ArrayList<String> count = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);








        imgview = (ImageView) findViewById(R.id.imageView2);
//        if(ContextCompat.checkSelfPermission(MarkActivity.this,permissionRequired[0])
//                != PackageManager.PERMISSION_GRANTED||
//                ContextCompat.checkSelfPermission(MarkActivity.this,permissionRequired[1])!=PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(MarkActivity.this,permissionRequired,PERMISSION_CALLBACK_CONSTANT);
//        }
        //else {
            openImageChooser();
        //}
    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    ((ImageView) findViewById(R.id.imageView2)).setImageURI(selectedImageUri);
                    executeLoopj(path);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri uri) {
        Log.d("TAG",uri.toString());
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        String fileId = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = fileId.split(":")[1];
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] column = {MediaStore.Images.Media.DATA};
        String selector = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = MarkActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, selector, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            String filePath = cursor.getString(columnIndex);
            return filePath;
        }
        // this is our fallback here
        return uri.getPath();
    }

    void executeLoopj(String path){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        File myfile = new File(imageEncoded);
         // "/mnt/sdcard/FileName.mp3"
        File file = new File(path);
        String File_URL = "http://192.168.43.173:5001/upload";
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("file",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        asyncHttpClient.post(File_URL,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                super.onSuccess(statusCode,headers,response);
                Log.d("TAG",response.toString());
                jsonresponse =  response.toString();
                try {
                    JSONArray jsonArray = response.getJSONArray("count");
                    //JSONObject jsonObject = jsonArray.getJSONObject(0);
                    for(int i=0;i<jsonArray.length();i++){
                        String m = jsonArray.get(i).toString();
                        count.add(m);
                    }



                    for(int i=0;i<count.size();i++){
                        System.out.println(count.get(i));
                        Log.d("TAG", String.valueOf(count.get(i)));
                    }
                    TextView smonth = (TextView)findViewById(R.id.textView3);

                    smonth.setText(count.toString());



                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        });

    }
}
