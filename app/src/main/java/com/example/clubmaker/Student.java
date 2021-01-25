package com.example.clubmaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
* public void setTimeCommitment(int timeCommitment);
* public int getTimeCommitment();
* public void setSchedule(ArrayList<int[]> classes);
* public ArrayList<int[]> getSchedule();
* public void setClubsize (int clubsize);
* public int getClubsize (clubsize);
* public void setTypeOfClub(String[]);
* public String[] getTypeOfClub();
* public boolean hasTimeCommitment();
* public boolean hasSchedule();
* public boolean hasTypeOfClub();
* public boolean hasClubsize();
* */

public class Student implements Serializable {
    int timeCommitment = 0;
    ArrayList<int[]> schedule;
    String[] typeOfClub;
    int clubsize = 0;
    public List<String> comment;

    //call this to initiate comment;
    public void setComment(String in){

        List<String> temp = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(in);
        while(st.hasMoreTokens())
        {
            temp.add(st.nextToken());
        }
        comment = temp;
    }

    public void setTimeCommitment(int timeCommitment) {
        this.timeCommitment = timeCommitment;
    }
    public int getTimeCommitment() {
        return timeCommitment;
    }
    public void setSchedule(ArrayList<int[]> classes) {
        this.schedule = classes;
    }
    public ArrayList<int[]> getSchedule() {
        return schedule;
    }
    public void setClubsize(int clubsize) {
        this.clubsize = clubsize;
    }
    public int getClubsize() {
        return clubsize;
    }
    public boolean hasTimeCommitment(){
        return timeCommitment != 0;
    }
    public boolean hasSchedule(){
        return schedule != null;
    }
    public boolean hasTypeOfClub(){
        return typeOfClub != null;
    }
    public boolean hasClubsize(){
        return clubsize != 0;
    }

    public String[] getTypeOfClub() {
        return typeOfClub;
    }

    public void setTypeOfClub(String[] typeOfClub) {
        this.typeOfClub = typeOfClub;
    }
}
