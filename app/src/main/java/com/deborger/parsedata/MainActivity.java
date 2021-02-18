package com.deborger.parsedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue mQueue;
    StringRequest stringRequest;
    String url = "http://users.telenet.be/pdbsoft/PDBSoft-WMI_Data-01-08-2007.xml";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";
    String getApiUrl = "https://jsonplaceholder.typicode.com/todos/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myText = findViewById(R.id.textView);


        // Instantiate the RequestQueue.
        // mQueue = Volley.newRequestQueue(this);

        // Get a RequestQueue (Singleton queue)
        mQueue = MyVolleyQueue.getInstance(this.getApplicationContext()).
                getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getApiUrl,
                null, response -> {
            try {
                myText.setText(response.getString("title"));
                Log.d("Main", "onCreate : " + response.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
                    Log.d("Main", "onCreate : Failed ! " + error.toString());
                });

        MyVolleyQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);

                // getJsonArray();

                // getString(mQueue);


    }

    private void getJsonArray() {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, apiUrl,
                null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Log.d("Main", "OnCreate : " + jsonObject.getString("title"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
                }, error -> {
                    Log.d("Main"," Failed ! " + error.toString() );
                });
        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mQueue.add(jsonRequest);
    }

    private void getString(RequestQueue queue) {
        // Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // display the content of our url
                    Log.d("Main","onCreate: " + response.substring(0, 700));
                }, error -> {
                    Log.d("Main", "Failed to get info " + error.toString());
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}