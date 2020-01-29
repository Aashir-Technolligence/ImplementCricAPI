package com.example.implementcricapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    ArrayList<RankingAttributes> pacakgeAttrs;
    RankingAdapter adapter;
    RecyclerView recyclerView;

    String Url = "https://rest.entitysport.com/v2/iccranks?token=ec471071441bb2ac538a0ff901abd249";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.List);
        pacakgeAttrs = new ArrayList<RankingAttributes>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadUrlData();
    }

    private void LoadUrlData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    //JSONArray jsonArray = new JSONObject(response).getJSONArray("response");
                    JSONObject jObject = new JSONObject(response);
                    JSONObject responseObject = jObject.getJSONObject("response");
                    JSONObject rankObject = responseObject.getJSONObject("ranks");
                    JSONObject batObject = rankObject.getJSONObject("batsmen");
                    JSONArray odisObject = new JSONObject(String.valueOf(batObject)).getJSONArray("odis");
//                    Toast.makeText(getApplicationContext() , String.valueOf(odisObject.length()), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < odisObject.length(); i++) {
                        try {
                            String rank = odisObject.getJSONObject(i).getString("rank");
                            String player = odisObject.getJSONObject(i).getString("player");
                            String team = odisObject.getJSONObject(i).getString("team");
                            String rating = odisObject.getJSONObject(i).getString("rating");

                            RankingAttributes attributes = new RankingAttributes(rank , player , team , rating);
                            pacakgeAttrs.add(attributes);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        recyclerView.setAdapter(new RankingAdapter(pacakgeAttrs , MainActivity.this));
                    }
                } catch (Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Error " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
