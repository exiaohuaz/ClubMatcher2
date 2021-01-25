package com.example.clubmaker;

import android.util.Log;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;
import java.util.ArrayList;

public class Matcher implements Serializable {
    private ArrayList<Club> clubs;
    private Student student;
    //clubsize, time, conflict, satisfy
    private int[] hyperparams = new int[]{10,1,1,40};

    Matcher(ArrayList<Club> inClubs, Student inStudent){
        clubs = inClubs;
        student = inStudent;
    }

    public int largestInd( double[] array )
    {
        if ( array == null || array.length == 0 ) return -1;

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest;
    }

    ArrayList<Club> topClubs(int topk){
        Log.i("club", clubs.get(0).toString());

        List<List<String>> documents = new ArrayList<List<String>>();

        for (Club candidate: clubs){
            candidate.score = heuristic(candidate);
            documents.add(candidate.description);
        }

        List<String> student_des = student.comment;

        if(student_des.size()>0) {
            documents.add(student_des);

            TFIDF calc = new TFIDF();

            double[] tfscores = new double[clubs.size()];

            for (int i = 0; i < student_des.size(); i++) {
                for (int j = 0; j < clubs.size(); j++) {
                    tfscores[j] = calc.calc_TFIDF(documents.get(j), documents, student_des.get(i));
                }

                int indexoflargest = largestInd(tfscores);
                double curr_s = clubs.get(indexoflargest).score;
                clubs.get(indexoflargest).score = curr_s + Math.abs(curr_s * 0.05);
            }
        }

        Collections.sort(clubs, new ClubComparator());

        ArrayList<Club> tclubs = new ArrayList<Club>();
        for (int i =0; i<topk; i++){
            tclubs.add(clubs.get(i));
        }
        return tclubs;
    }

    double heuristic(Club candidate){
        ArrayList<int[]> schedule = student.schedule;
        int commit = student.timeCommitment;
        int size = student.clubsize;
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(student.typeOfClub));

        //size difference, the more negative the worse 0, -1
        double score1 = -1*Math.abs((double)size - candidate.clubSize)/candidate.clubSize;

        //minutes above exceed commitments, 0 or badly negative
        double score2 = candidate.commitmentCap(commit);

        //minutes conflicting large number
        double score3 = -1*candidate.timeConflict(schedule);

        //comment tags
        double score4 = candidate.tagSatisfied(tags);

        //tags * sizedifference +
        double finalScore = hyperparams[3]*score4 + (hyperparams[0]*score1)+ hyperparams[1]*score2 + hyperparams[2]*score3;

        Log.i("indiv_scores", score1 + " " + score2 + " " +  score3 + " " +  score4);

        return finalScore;
    }
}
