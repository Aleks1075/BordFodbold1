package BordFodbold1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;


public class TextUI implements FileIO {

    private final int numberOfClubs;

    private final ArrayList<Team> league;
    private final Scanner scanner;
    private final ArrayList<Match> matches;

    public TextUI(int numberOfClubs) {

        this.numberOfClubs = numberOfClubs;
        league = new ArrayList<>();
        matches = new ArrayList<>();
        scanner = new Scanner(System.in);
        displayMenu();
    }


    private void displayMenu() {

        while(true) {
            System.out.println("Bordfodboldturnering menu: ");
            System.out.println("Tilmeld ny hold og tilføj til turnering (tast 1)");
            System.out.println("Slet eksisterende hold fra turnering (tast 2)");
            System.out.println("Vis statistik for hold (tast 3)");
            System.out.println("Vis fodboldturneringstabel (tast 4)");
            System.out.println("Tilføj en spillet kamp (tast 5)");
            System.out.println("Vis kalender og find kamp (tast 6)");
            String line = scanner.nextLine();
            int command = 0;
            try {
                command = Integer.parseInt(line);
            } catch (Exception e) {
            }

            switch(command) {
                case 1 :
                    addTeam();
                    break;
                case 2 :
                    deleteTeam();
                    break;
                case 3 :
                    displayStatistics();
                    break;
                case 4 :
                    displayLeagueTable();
                    break;
                case 5:
                    addPlayedMatch();
                    break;
                case 6:
                    displayCalendar();
                    break;
                default:
                    System.out.println("Forkert kommando");
            }

        }
    }

    private void addTeam(){
        if(league.size() == numberOfClubs) {
            System.out.println("Kan ikke tilføje flere holder til ligaen");
            return;
        }

        Team club = new Team();
        System.out.println("Indsæt holdnavn: ");
        String line = scanner.nextLine();
        club.setName(line);

        if(league.contains(club)){
            System.out.println("Dette hold er allerede i ligaen");
            return;
        }
        System.out.println("Indsæt holdets lokation: ");
        line = scanner.nextLine();
        club.setLocation(line);
        league.add(club);
    }

    private void deleteTeam() {
        System.out.println("Indsæt holdnavn: ");
        String line = scanner.nextLine();
        for(Team club : league) {
            if(club.getName().equals(line)){
                league.remove(club);
                System.out.println("Hold "+ club.getName()+" fjernet");
                return;
            }
        }
        System.out.println("Ingen hold med dette navn i ligaen");
    }

    private void displayStatistics() {

        System.out.println("Indsæt holdnavn: ");
        String line = scanner.nextLine();
        for (Team club : league) {
            if(club.getName().equals(line)){
                System.out.println("Hold " + club.getName()+ " kampe vundet: " + club.getWinCount());
                System.out.println("Hold " + club.getName()+ " kampe tabt: " + club.getDefeatCount());
                System.out.println("Hold " + club.getName()+ " kampe uafgjort: " + club.getDrawCount());
                System.out.println("Hold " + club.getName()+ " mål scoret: " + club.getScoredGoalsCount());
                System.out.println("Hold " + club.getName()+ " mål imod: " + club.getReceivedGoalsCount());
                System.out.println("Hold " + club.getName()+ " points: " + club.getPoints());
                System.out.println("Hold " + club.getName()+ " kampe spillet: " + club.getMatchesPlayed());
                return;
            }
        }
        System.out.println("Ingen hold med dette navn i ligaen");
    }

    private void displayLeagueTable() {

        Collections.sort(league, new CustomComparator());
        for(Team club : league) {
            System.out.println("Hold: " + club.getName()+" Points: "+ club.getPoints()+" mål forskel: "+ (club.getScoredGoalsCount()-club.getReceivedGoalsCount()));
        }
    }

    private void addPlayedMatch(){
        System.out.println("Indtast dato (format mm-dd-yyyy): ");
        String line = scanner.nextLine();
        Date date;
        try {
            date = new SimpleDateFormat("MM-dd-yyyy").parse(line);
        } catch (ParseException ex) {
            System.out.println("Du skal indtaste dato i format mm-dd-yyyy");
            return;
        }
        System.out.println("Indtast hjemmeholdet: ");
        line = scanner.nextLine();
        Team home = null;
        for(Team club : league){
            if(club.getName().equals(line))
                home = club;
        }
        if (home == null) {
            System.out.println("Ingen hold med dette navn i ligaen");
            return;
        }
        System.out.println("Indtast udehold: ");
        line = scanner.nextLine();
        Team away = null;
        for(Team club : league){
            if(club.getName().equals(line))
                away = club;
        }
        if (away == null) {
            System.out.println("Ingen hold med dette navn i ligaen");
            return;
        }

        System.out.println("Indtast hjemmeholdets antal mål: ");
        line = scanner.nextLine();
        int homeGoals = -1;
        try {
            homeGoals = Integer.parseInt(line);
        } catch (Exception e) {
        }
        if (homeGoals == -1) {
            System.out.println("Du skal indtaste antal mål");
            return;
        }

        System.out.println("Indtast udeholdets antal mål: ");
        line = scanner.nextLine();
        int awayGoals = -1;
        try {
            awayGoals = Integer.parseInt(line);
        } catch (Exception e) {
        }
        if (awayGoals == -1) {
            System.out.println("Du skal indtaste antal mål");
            return;
        }


        Match match = new Match();
        match.setDate(date);
        match.setTeamA(home);
        match.setTeamB(away);
        match.setTeamAScore(awayGoals);
        match.setTeamBScore(homeGoals);
        matches.add(match);
        home.setScoredGoalsCount(home.getScoredGoalsCount()+homeGoals);
        away.setScoredGoalsCount(away.getScoredGoalsCount()+awayGoals);
        home.setRecievedGoalsCount(home.getReceivedGoalsCount()+awayGoals);
        away.setRecievedGoalsCount(away.getReceivedGoalsCount()+homeGoals);
        home.setMatchesPlayed(home.getMatchesPlayed()+1);
        away.setMatchesPlayed(away.getMatchesPlayed()+1);

        if (homeGoals > awayGoals) {
            home.setPoints(home.getPoints()+2);
            home.setWinCount(home.getWinCount()+1);
            away.setDefeatCount(away.getDefeatCount()+1);
        }

        else if (homeGoals < awayGoals) {
            away.setPoints(away.getPoints()+2);
            away.setWinCount(away.getWinCount()+1);
            home.setDefeatCount(home.getDefeatCount()+1);
        }
        else {
            home.setPoints(home.getPoints()+1);
            away.setPoints(away.getPoints()+1);
            home.setDrawCount(home.getDrawCount()+1);
            away.setDrawCount(away.getDrawCount()+1);
        }
    }

    private void displayCalendar() {

        System.out.println("Indtast år: ");
        String line = scanner.nextLine();
        int Y = -7777;
        try {
            Y = Integer.parseInt(line);
        } catch (Exception e) {
        }
        if (Y == -7777) {
            System.out.println("Du skal indtaste et år");
            return;
        }

        System.out.println("Indtast måned: ");
        line = scanner.nextLine();
        int M = 0;
        try {
            M = Integer.parseInt(line);
        } catch (Exception e) {
        }
        if (M == 0) {
            System.out.println("Du skal indtaste en måned");
            return;
        }

        String[] months = {
                "",
                "Januar", "Februar", "Marts",
                "April", "Mai", "Juni",
                "Juli", "August", "September",
                "October", "November", "December"
        };

        int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (M == 2 && isLeapYear(Y)) days[M] = 29;

        System.out.println("    " + months[M] + " " + Y);
        System.out.println("S  M  Ti  O  To  F  L");

        int d = day(M, 1, Y);
        String space = "";

        for (int i = 0; i < d; i++)
            System.out.print("   ");
        for (int i = 1; i <= days[M]; i++) {
            if (i<10)
                System.out.print(i +"  ");
            else
                System.out.print(i+" ");
            if (((i + d) % 7 == 0) || (i == days[M])) System.out.println();
        }

        System.out.println("Indtast dag: ");
        line = scanner.nextLine();
        int D = 0;
        try {
            D= Integer.parseInt(line);
        }  catch (Exception e) {
        }
        if (D == 0 || days[M] < D) {
            System.out.println("Du skal indtaste dag i måned");
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Y, M-1, D);
        for (Match m : matches) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(m.getDate());
            if (cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) || cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                System.out.println(m.getTeamA().getName()+ " "+m.getTeamAScore() + " : "+ m.getTeamBScore()+ " "+ m.getTeamB().getName());
            }
        }
    }

    public int day(int M, int D, int Y) {
        int y = Y - (14 - M) / 12;
        int x = y + y/4 - y/100 + y/400;
        int m = M + 12 * ((14-M) / 12) - 2;
        int d = (D + x + (31*m)/12) % 7;
        return d;
    }

    public boolean isLeapYear(int year) {

        if ((year % 4 ==0) && (year % 100 !=0 )) return true;
        if (year % 400 == 0) return true;
        return false;
    }
}