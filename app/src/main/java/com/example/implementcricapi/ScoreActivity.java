package com.example.implementcricapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {
    TextView score, over, rr, wicket, team;
    TextView score2, over2, rr2, wicket2, team2;
    String Url = "https://cricket.sportmonks.com/api/v2.0/fixtures/14985?api_token=9ivr14EK7LFO5sg5SSQLg7Fby8NHWL8vJ8MkzsHgm0Y5WvFblbMubiYRlVZf&include=scoreboards";
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = findViewById(R.id.txtScore);
        over = findViewById(R.id.txtOver);
        wicket = findViewById(R.id.txtWicket);
        rr = findViewById(R.id.txtRR);
        team = findViewById(R.id.txtTeam);
        score2 = findViewById(R.id.txtScore2);
        over2 = findViewById(R.id.txtOver2);
        rr2 = findViewById(R.id.txtRR2);
        wicket2 = findViewById(R.id.txtWicket2);
        team2 = findViewById(R.id.txtTeam2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //JSONArray jsonArray = new JSONObject(response).getJSONArray("response");
                    JSONObject jObject = new JSONObject(response);
                    JSONObject responseObject = jObject.getJSONObject("data");

                    Integer tossWinTeam = responseObject.getInt("toss_won_team_id");
                    String elected = responseObject.getString("elected");
                    dref.child("LiveScore").child("tosswin").setValue(tossWinTeam);
                    dref.child("LiveScore").child("elected").setValue(elected);
                    // JSONObject rankObject = responseObject.getJSONObject("scoreboards");
                    // JSONObject batObject = responseObject.getJSONObject("scoreboards");
                    JSONArray odisObject = new JSONObject(String.valueOf(responseObject)).getJSONArray("scoreboards");
//                    Toast.makeText(getApplicationContext() , String.valueOf(odisObject.length()), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < odisObject.length(); i++) {
                        try {
                            String scoret = odisObject.getJSONObject(i).getString("total");
                            String overt = odisObject.getJSONObject(i).getString("overs");
                            String wickett = odisObject.getJSONObject(i).getString("wickets");
                            String teamid = odisObject.getJSONObject(i).getString("team_id");
                            Float s = Float.parseFloat(odisObject.getJSONObject(i).getString("total"));
                            Float o = Float.parseFloat(odisObject.getJSONObject(i).getString("overs"));
                            if (i == 3) {

                                score.setText(scoret);
                                over.setText(overt);
                                wicket.setText(wickett);
                                if (!String.valueOf(s / o).equals("NaN"))
                                    rr.setText(String.valueOf(s / o));
                                else
                                    rr.setText("0");
                                if (teamid.equals("11")) {
                                    team.setText("Islamabad United");
                                    dref.child("LiveScore").child("Islamabad United").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Islamabad United").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Islamabad United").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Islamabad United").child("team").setValue("Islamabad United");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Islamabad United").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Islamabad United").child("rr").setValue("0");
                                }
                                if (teamid.equals("12")) {
                                    team.setText("Karachi Kings");
                                    dref.child("LiveScore").child("Karachi Kings").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Karachi Kings").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Karachi Kings").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Karachi Kings").child("team").setValue("Karachi Kings");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Karachi Kings").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Karachi Kings").child("rr").setValue("0");

                                }
                                if (teamid.equals("13")) {
                                    team.setText("Lahore Qalandars");
                                    dref.child("LiveScore").child("Lahore Qalandars").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("team").setValue("Lahore Qalandars");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Lahore Qalandars").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Lahore Qalandars").child("rr").setValue("0");

                                }
                                if (teamid.equals("14")) {
                                    team.setText("Multan Sultan");
                                    dref.child("LiveScore").child("Multan Sultan").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Multan Sultan").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Multan Sultan").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Multan Sultan").child("team").setValue("Multan Sultan");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Multan Sultan").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Multan Sultan").child("rr").setValue("0");

                                }
                                if (teamid.equals("15")) {
                                    team.setText("Peshawar Zalmi");
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("team").setValue("Peshawar Zalmi");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Peshawar Zalmi").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Peshawar Zalmi").child("rr").setValue(String.valueOf(s / o));

                                }
                                if (teamid.equals("53")) {
                                    team.setText("Quetta Gladiators");
                                    dref.child("LiveScore").child("Quetta Gladiators").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("team").setValue("Quetta Gladiators");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Quetta Gladiators").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Quetta Gladiators").child("rr").setValue("0");

                                }


                            }
                            if (i == 1) {
                                score2.setText(scoret);
                                over2.setText(overt);
                                wicket2.setText(wickett);
                                if (!String.valueOf(s / o).equals("NaN"))
                                    rr2.setText(String.valueOf(s / o));
                                else
                                    rr2.setText("0");
                                if (teamid.equals("51")) {
                                    team2.setText("Islamabad United");
                                    dref.child("LiveScore").child("Islamabad United").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Islamabad United").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Islamabad United").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Islamabad United").child("team").setValue("Islamabad United");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Islamabad United").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Islamabad United").child("rr").setValue("0");
                                }
                                if (teamid.equals("12")) {
                                    team2.setText("Karachi Kings");
                                    dref.child("LiveScore").child("Karachi Kings").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Karachi Kings").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Karachi Kings").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Karachi Kings").child("team").setValue("Karachi Kings");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Karachi Kings").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Karachi Kings").child("rr").setValue("0");

                                }
                                if (teamid.equals("13")) {
                                    team2.setText("Lahore Qalandars");
                                    dref.child("LiveScore").child("Lahore Qalandars").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Lahore Qalandars").child("team").setValue("Lahore Qalandars");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Lahore Qalandars").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Lahore Qalandars").child("rr").setValue("0");

                                }
                                if (teamid.equals("14")) {
                                    team2.setText("Multan Sultan");
                                    dref.child("LiveScore").child("Multan Sultan").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Multan Sultan").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Multan Sultan").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Multan Sultan").child("team").setValue("Multan Sultan");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Multan Sultan").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Multan Sultan").child("rr").setValue("0");

                                }
                                if (teamid.equals("15")) {
                                    team2.setText("Peshawar Zalmi");
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Peshawar Zalmi").child("team").setValue("Peshawar Zalmi");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Peshawar Zalmi").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Peshawar Zalmi").child("rr").setValue(String.valueOf(s / o));

                                }
                                if (teamid.equals("16")) {
                                    team2.setText("Quetta Gladiators");
                                    dref.child("LiveScore").child("Quetta Gladiators").child("score").setValue(scoret);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("wicket").setValue(wickett);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("over").setValue(overt);
                                    dref.child("LiveScore").child("Quetta Gladiators").child("team").setValue("Quetta Gladiators");
                                    if (!String.valueOf(s / o).equals("NaN"))
                                        dref.child("LiveScore").child("Quetta Gladiators").child("rr").setValue(String.valueOf(s / o));
                                    else
                                        dref.child("LiveScore").child("Quetta Gladiators").child("rr").setValue("0");

                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {

                    Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        loadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        }, 5000);


    }

    private void loadData(){
        String dataUrl="https://cricket.sportmonks.com/api/v2.0/fixtures/14985?api_token=9ivr14EK7LFO5sg5SSQLg7Fby8NHWL8vJ8MkzsHgm0Y5WvFblbMubiYRlVZf&include=scoreboards,localTeam,visitorTeam,batting,bowling,balls,lineup";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONObject dataObject = responseObject.getJSONObject("data");
                    //JSONObject ballsObject = dataObject.getJSONObject("ball");
                    JSONArray ballsObject = new JSONObject(String.valueOf(dataObject)).getJSONArray("balls");

                    String batsman1=ballsObject.getJSONObject(ballsObject.length()-1).getString("batsman_one_on_creeze_id");
                    String batsman2=ballsObject.getJSONObject(ballsObject.length()-1).getString("batsman_two_on_creeze_id");
                    //JSONObject battingObject=dataObject.getJSONObject("batting");
                    JSONArray playerArray=new JSONObject(String.valueOf(dataObject)).getJSONArray("batting");
                    for(int j=0;j<playerArray.length();j++){
                        String playerId=playerArray.getJSONObject(j).getString("player_id");
                        if(playerId.equals(batsman1)){
                            String score=playerArray.getJSONObject(j).getString("score");
                            String balls=playerArray.getJSONObject(j).getString("ball");
                            String fours=playerArray.getJSONObject(j).getString("four_x");
                            String sixs=playerArray.getJSONObject(j).getString("six_x");
                            String rr=playerArray.getJSONObject(j).getString("rate");
                            dref.child("LiveScore").child("batsmanScore").child("1").child("score").setValue(score);
                            dref.child("LiveScore").child("batsmanScore").child("1").child("balls").setValue(balls);
                            dref.child("LiveScore").child("batsmanScore").child("1").child("fours").setValue(fours);
                            dref.child("LiveScore").child("batsmanScore").child("1").child("sixs").setValue(sixs);
                            dref.child("LiveScore").child("batsmanScore").child("1").child("rr").setValue(rr);
                            getPlayerData(batsman1,1);
                        }
                        if(playerId.equals(batsman2)){
                            String score=playerArray.getJSONObject(j).getString("score");
                            String balls=playerArray.getJSONObject(j).getString("ball");
                            String fours=playerArray.getJSONObject(j).getString("four_x");
                            String sixs=playerArray.getJSONObject(j).getString("six_x");
                            String rr=playerArray.getJSONObject(j).getString("rate");
                            dref.child("LiveScore").child("batsmanScore").child("2").child("score").setValue(score);
                            dref.child("LiveScore").child("batsmanScore").child("2").child("balls").setValue(balls);
                            dref.child("LiveScore").child("batsmanScore").child("2").child("fours").setValue(fours);
                            dref.child("LiveScore").child("batsmanScore").child("2").child("sixs").setValue(sixs);
                            dref.child("LiveScore").child("batsmanScore").child("2").child("rr").setValue(rr);
                            getPlayerData(batsman2,2);
                        }
                    }
                    JSONObject bowlerObject= ballsObject.getJSONObject(ballsObject.length()-1);
                    JSONObject nameObject=bowlerObject.getJSONObject("bowler");
                    String bowlerName = nameObject.getString("fullname");
                    dref.child("LiveScore").child("bowler").setValue(bowlerName);
                    JSONObject batsmanObject=bowlerObject.getJSONObject("batsman");
                    String batsmanName = batsmanObject.getString("fullname");
                    dref.child("LiveScore").child("currentBatting").setValue(batsmanName);
                    int count=6;
                    for(int i=ballsObject.length()-1;i>=ballsObject.length()-6;i--){
                        try {
                            JSONObject recentObject= ballsObject.getJSONObject(i);
                            JSONObject scoreObject= recentObject.getJSONObject("score");
                            String name=scoreObject.getString("name");
                            if(name.contains("Out") || name.contains("Bowled")|| name.contains("OUT")){
                                name="W ";
                            }
                            else if(name.contains("No")){
                                name="0 ";
                            }
                            else if(name.contains("Runs")){
                                name=name.replace("Runs","");
                            }
                            else if(name.contains("Run")){
                                name=name.replace("Run","");
                            }
                            else if(name.contains("Wide")){
                                name=name.replace(" Wide","wd ");
                            }
                            else if(name.contains("FOUR")){
                                name="4 ";
                            }
                            else if(name.contains("SIX")){
                                name="6 ";
                            }

                            dref.child("LiveScore").child("recent").child(String.valueOf(count)).setValue(name);
                            count--;

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

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

    private  void getPlayerData(String id, final int x){
        String playerUrl="https://cricket.sportmonks.com/api/v2.0/players/"+id+"?api_token=9ivr14EK7LFO5sg5SSQLg7Fby8NHWL8vJ8MkzsHgm0Y5WvFblbMubiYRlVZf";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, playerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response);
                    JSONObject dataObject = responseObject.getJSONObject("data");
                    String playerName=dataObject.getString("fullname");
                    dref.child("LiveScore").child("batsmanScore").child(String.valueOf(x)).child("name").setValue(playerName);

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
