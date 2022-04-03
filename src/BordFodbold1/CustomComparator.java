package BordFodbold1;

import java.util.Comparator;


public class CustomComparator implements Comparator<Team> {

    @Override
    public int compare(Team t, Team t1) {

        if(t.getPoints() > t1.getPoints())
            return -1;
        else
        if (t.getPoints() < t1.getPoints())
            return 1;
        else {
            int goalDif = t.getScoredGoalsCount()-t.getReceivedGoalsCount();
            int goalDif1 = t1.getScoredGoalsCount()-t1.getReceivedGoalsCount();
            if(goalDif > goalDif1)
                return -1;
            else
            if(goalDif < goalDif1)
                return 1;
            else return 0;
        }

    }
}
