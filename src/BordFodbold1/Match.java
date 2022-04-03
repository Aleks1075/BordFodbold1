package BordFodbold1;

import java.util.Date;

public class Match {

    private Team teamA;
    private Team teamB;
    private int teamAScore;
    private int teamBScore;
    private Date date;


    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public int getTeamAScore(){
        return teamAScore;
    }

    public int getTeamBScore(){
        return teamBScore;
    }

    public Date getDate() {
        return date;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore = teamAScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore = teamBScore;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
