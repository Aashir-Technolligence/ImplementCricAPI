package com.example.implementcricapi;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {
    TextView score, over, rr, wicket, team;
    TextView score2, over2, rr2, wicket2, team2;
    String Url = "https://cricket.sportmonks.com/api/v2.0/fixtures/15102?api_token=9ivr14EK7LFO5sg5SSQLg7Fby8NHWL8vJ8MkzsHgm0Y5WvFblbMubiYRlVZf&include=scoreboards,localTeam,visitorTeam,batting,bowling,balls,lineup";
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
                    try {
                        final String matchnn = responseObject.getString("note");
                        dref.child("Schedule").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                                            String id = dataSnapshot1.child("id").getValue().toString();
                                            String sid = dataSnapshot1.child("sid").getValue().toString();
                                            if (!id.equals(null)) {
                                                dref.child("Schedule").child(id).child("note").setValue(matchnn);
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    catch (Exception e){}
                    Integer tossWinTeam = responseObject.getInt("toss_won_team_id");
                    String elected = responseObject.getString("elected");
                    try {
                        String matchStatus = responseObject.getString("status");
                        if (matchStatus.equals("Finished")) {
                            final String matchNote = responseObject.getString("note");
                            dref.child("Schedule").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                                                String id = dataSnapshot1.child("id").getValue().toString();
                                                String sid = dataSnapshot1.child("sid").getValue().toString();
                                                if (!id.equals(null)) {
                                                    dref.child("FinishMatches").child(sid).child("note").setValue(matchNote);
                                                    dref.child("FinishMatches").child(sid).child("id").setValue(sid);
                                                    dref.child("Schedule").child(id).child("note").setValue(matchNote);
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    catch (Exception e){}
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
                            if (i == 1) {
                                score.setText(scoret);
                                over.setText(overt);
                                wicket.setText(wickett);
                                if (!String.valueOf(s / o).equals("NaN"))
                                    rr.setText(String.valueOf(s / o));
                                else
                                    rr2.setText("0");
                                if (teamid.equals("51")) {
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
                            if (i == 3) {
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
                                if (teamid.equals("53")) {
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
                    loadData(response);
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
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        }, 2000);


    }

    private void loadData(String response) {

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONObject dataObject = responseObject.getJSONObject("data");
            //JSONObject ballsObject = dataObject.getJSONObject("ball");
            JSONArray ballsObject = new JSONObject(String.valueOf(dataObject)).getJSONArray("balls");
            JSONArray bowlerDataArray = new JSONObject(String.valueOf(dataObject)).getJSONArray("bowling");


            String batsman1 = ballsObject.getJSONObject(ballsObject.length() - 1).getString("batsman_one_on_creeze_id");
            String batsman2 = ballsObject.getJSONObject(ballsObject.length() - 1).getString("batsman_two_on_creeze_id");
            String teamId = ballsObject.getJSONObject(ballsObject.length() - 1).getString("team_id");
            String teamname = "";
            if (teamId.equals("51")) {
                teamname = "Islamabad United";
            } else if (teamId.equals("12")) {
                teamname = "Karachi Kings";
            } else if (teamId.equals("13")) {
                teamname = "Lahore Qalandars";
            } else if (teamId.equals("14")) {
                teamname = "Multan Sultan";
            } else if (teamId.equals("15")) {
                teamname = "Peshawar Zalmi";
            } else if (teamId.equals("53")) {
                teamname = "Quetta Gladiators";
            }
            //JSONObject battingObject=dataObject.getJSONObject("batting");
            JSONArray playerArray = new JSONObject(String.valueOf(dataObject)).getJSONArray("batting");
            for (int j = 0; j < playerArray.length(); j++) {
                String activePlayerId = "";
                String playerId = playerArray.getJSONObject(j).getString("player_id");
                boolean playerStatus = playerArray.getJSONObject(j).getBoolean("active");
                if (playerStatus == true) {
                    activePlayerId = playerArray.getJSONObject(j).getString("player_id");
                    getActivePlayerData(activePlayerId);
                }
                if (playerId.equals(batsman1)) {
                    String score = playerArray.getJSONObject(j).getString("score");
                    String balls = playerArray.getJSONObject(j).getString("ball");
                    String fours = playerArray.getJSONObject(j).getString("four_x");
                    String sixs = playerArray.getJSONObject(j).getString("six_x");
                    String rr = playerArray.getJSONObject(j).getString("rate");

                    dref.child("LiveScore").child("batsmanScore").child("1").child("score").setValue(score);
                    dref.child("LiveScore").child("batsmanScore").child("1").child("balls").setValue(balls);
                    dref.child("LiveScore").child("batsmanScore").child("1").child("fours").setValue(fours);
                    dref.child("LiveScore").child("batsmanScore").child("1").child("sixs").setValue(sixs);
                    dref.child("LiveScore").child("batsmanScore").child("1").child("rr").setValue(rr);
                    getPlayerData(batsman1, 1, teamname, score, balls, fours, sixs, rr);
                }
                if (playerId.equals(batsman2)) {
                    String score = playerArray.getJSONObject(j).getString("score");
                    String balls = playerArray.getJSONObject(j).getString("ball");
                    String fours = playerArray.getJSONObject(j).getString("four_x");
                    String sixs = playerArray.getJSONObject(j).getString("six_x");
                    String rr = playerArray.getJSONObject(j).getString("rate");
                    dref.child("LiveScore").child("batsmanScore").child("2").child("score").setValue(score);
                    dref.child("LiveScore").child("batsmanScore").child("2").child("balls").setValue(balls);
                    dref.child("LiveScore").child("batsmanScore").child("2").child("fours").setValue(fours);
                    dref.child("LiveScore").child("batsmanScore").child("2").child("sixs").setValue(sixs);
                    dref.child("LiveScore").child("batsmanScore").child("2").child("rr").setValue(rr);

                    getPlayerData(batsman2, 2, teamname, score, balls, fours, sixs, rr);
                }
            }
            JSONObject bowlerObject = ballsObject.getJSONObject(ballsObject.length() - 1);
            JSONObject nameObject = bowlerObject.getJSONObject("bowler");
            String bowlerName = nameObject.getString("fullname");
            String bowlerId = nameObject.getString("id");
            for (int i = 0; i < bowlerDataArray.length(); i++) {
                try {
                    String bowler_player_id = bowlerDataArray.getJSONObject(i).getString("player_id");
                    String bowler_team_id = bowlerDataArray.getJSONObject(i).getString("team_id");
                    String bowler_score = bowlerDataArray.getJSONObject(i).getString("runs");
                    String bowler_overs = bowlerDataArray.getJSONObject(i).getString("overs");
                    String bowler_medians = bowlerDataArray.getJSONObject(i).getString("medians");
                    String bowler_wickets = bowlerDataArray.getJSONObject(i).getString("wickets");
                    String bowler_rr = bowlerDataArray.getJSONObject(i).getString("rate");

                    String bowlerTeamName = "";
                    if (bowler_team_id.equals("51")) {
                        bowlerTeamName = "Islamabad United";
                    } else if (bowler_team_id.equals("12")) {
                        bowlerTeamName = "Karachi Kings";
                    } else if (bowler_team_id.equals("13")) {
                        bowlerTeamName = "Lahore Qalandars";
                    } else if (bowler_team_id.equals("14")) {
                        bowlerTeamName = "Multan Sultan";
                    } else if (bowler_team_id.equals("15")) {
                        bowlerTeamName = "Peshawar Zalmi";
                    } else if (bowler_team_id.equals("53")) {
                        bowlerTeamName = "Quetta Gladiators";
                    }
                    if (bowler_player_id.equals(bowlerId)) {
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("name").setValue(bowlerName);
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("score").setValue(bowler_score);
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("overs").setValue(bowler_overs);
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("medians").setValue(bowler_medians);
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("econ").setValue(bowler_rr);
                        dref.child("LiveScore").child("summary").child(bowlerTeamName).child("bowling").child(bowlerName).child("wickets").setValue(bowler_wickets);

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }


            dref.child("LiveScore").child("bowler").setValue(bowlerName);
            JSONObject batsmanObject = bowlerObject.getJSONObject("batsman");
            String batsmanName = batsmanObject.getString("fullname");
            dref.child("LiveScore").child("currentBatting").setValue(batsmanName);
            int count = 6;
            for (int i = ballsObject.length() - 1; i >= ballsObject.length() - 6; i--) {
                try {
                    JSONObject recentObject = ballsObject.getJSONObject(i);
                    JSONObject scoreObject = recentObject.getJSONObject("score");
                    String name = scoreObject.getString("name");
                    if (name.contains("Out") || name.contains("Bowled") || name.contains("OUT")) {
                        name = "W ";
                    } else if (name.contains("No Run")) {
                        name = "0 ";
                    } else if (name.contains("Runs")) {
                        name = name.replace("Runs", "");
                    } else if (name.contains("Run")) {
                        name = name.replace("Run", "");
                    } else if (name.contains("Wide")) {
                        name = name.replace(" Wide", "wd ");
                    } else if (name.contains("FOUR")) {
                        name = "4 ";
                    } else if (name.contains("SIX")) {
                        name = "6 ";
                    } else if (name.contains("Leg Byes")) {
                        name = name.replace(" Leg Byes", "L ");
                    } else if (name.contains("Leg Bye")) {
                        name = name.replace(" Leg Bye", "L ");
                    } else if (name.contains("Byes")) {
                        name = name.replace(" Byes", "B ");
                    } else if (name.contains("Bye")) {
                        name = name.replace(" Bye", "B ");
                    } else if (name.contains("No Ball")) {
                        name = "nb ";
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

    private void getPlayerData(final String id, final int x, final String team, final String score, final String balls, final String fours, final String sixs, final String rr) {
        try {
            dref.child("ApiPlayersData").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String playerName = dataSnapshot.child("fullname").getValue().toString();
                        dref.child("LiveScore").child("batsmanScore").child(String.valueOf(x)).child("name").setValue(playerName);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("name").setValue(playerName);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("score").setValue(score);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("balls").setValue(balls);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("fours").setValue(fours);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("sixs").setValue(sixs);
                        dref.child("LiveScore").child("summary").child(team).child("batting").child(playerName).child("rr").setValue(rr);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void getActivePlayerData(String id) {
        try {
            dref.child("ApiPlayersData").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String playerName = dataSnapshot.child("fullname").getValue().toString();
                        dref.child("LiveScore").child("currentBatting").setValue(playerName);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


}
