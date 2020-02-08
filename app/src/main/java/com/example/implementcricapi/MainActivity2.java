package com.example.implementcricapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView showName;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showName=(TextView) findViewById(R.id.playerName);
        String playerUrl = "https://cricket.sportmonks.com/api/v2.0/players?api_token=9ivr14EK7LFO5sg5SSQLg7Fby8NHWL8vJ8MkzsHgm0Y5WvFblbMubiYRlVZf";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, playerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response);
                    JSONArray dataArray = new JSONObject(String.valueOf(responseObject)).getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {

                            String id = dataArray.getJSONObject(i).getString("id");
                            String name = dataArray.getJSONObject(i).getString("fullname");
                            dref.child("ApiPlayersData").child(id).child("fullname").setValue(name);
                            showName.setText(name);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
