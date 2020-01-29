package com.example.implementcricapi;

public class RankingAttributes {
    String Rank;
    String Player;
    String Team;
    String Rating;

    public RankingAttributes() {
    }

    public RankingAttributes(String rank, String player, String team, String rating) {
        Rank = rank;
        Player = player;
        Team = team;
        Rating = rating;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
