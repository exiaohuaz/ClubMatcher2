package com.example.clubmaker;
import android.content.Intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.*;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ICSReader extends AppCompatActivity {


    private static final int READ_REQUEST_CODE = 42;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat match = new SimpleDateFormat("yyyy/M/dd");



    protected ArrayList<int[]> parseICS(String input){
        Reader inputString = new StringReader(input);
        BufferedReader reader = new BufferedReader(inputString);
        ArrayList<int[]> output = new ArrayList<>();
        try{
            String line = "";
            String year = "2000";
            String month = "11";
            String day = "15";
            int hour = 0;
            int minute = 0;
            int day_of_week = 0;
            int fminFromMon = 0;
            int sminFromMon = 0;
            while((line = reader.readLine()) != null){
                int split = line.indexOf(':');
                if(split != -1){
                    switch(line.substring(0,split)){
                        case "DTSTART":
                            year = line.substring(split+1, split+5);
                            month = line.substring(split+5, split+7);
                            day = line.substring(split+7, split+9);
                            c.setTime(match.parse(year + "/" + month + "/" + day));

                            hour = Integer.parseInt(line.substring(split+10, split+12));
                            minute = Integer.parseInt(line.substring(split+12, split+14));
                            day_of_week = (c.get(Calendar.DAY_OF_WEEK) -2 + 7) % 7;
                            fminFromMon = day_of_week * 1440 + hour * 60 + minute;
                            //displayICS.append(fminFromMon + "\n");
                            //displayICS.append(day_of_week + " " + hour + " " + minute + "\n");
                            //displayICS.setText(c.toString());
                            //displayICS.setText(year + " " + month + " " + day + " " + day_of_week.toString());
                            break;
                        case "DTEND":
                            year = line.substring(split+1, split+5);
                            month = line.substring(split+5, split+7);
                            day = line.substring(split+7, split+9);
                            c.setTime(match.parse(year + "/" + month + "/" + day));

                            hour = Integer.parseInt(line.substring(split+10, split+12));
                            minute = Integer.parseInt(line.substring(split+12, split+14));
                            day_of_week = (c.get(Calendar.DAY_OF_WEEK) -2 + 7) % 7;
                            sminFromMon = day_of_week * 1440 + hour * 60 + minute;
                            //displayICS.append(day_of_week + " " + hour + " " + minute + "\n");
                            //displayICS.append(fminFromMon + " " + sminFromMon + "\n");
                            int[] o = {fminFromMon, sminFromMon};
                            output.add(o);
                            break;
                        default: break;
                    }
                }
            }
        } catch(IOException e1){

        } catch(ParseException e2){}
        return output;
    }

    public static ArrayList<int[]> generateCalendar(String[] days){
        //list of names in days will be mon - sun where you could work, set all others to false.
        ArrayList<int[]> output = new ArrayList<>();
        if(!contains(days, "Monday")){
            int[] a = {0, 1439};
            output.add(a);
        }
        if(!contains(days, "Tuesday")){
            int[] a = {1440, 2879};
            output.add(a);
        }
        if(!contains(days, "Wednesday")){
            int[] a = {2880, 4319};
            output.add(a);
        }
        if(!contains(days, "Thursday")){
            int[] a = {4320, 5759};
            output.add(a);
        }
        if(!contains(days, "Friday")){
            int[] a = {5760, 7199};
            output.add(a);
        }
        if(!contains(days, "Saturday")){
            int[] a = {7200, 8639};
            output.add(a);
        }
        if(!contains(days, "Sunday")){
            int[] a = {8640, 10800};
            output.add(a);
        }
        return output;
    }

    private static boolean contains(String[] x, String y){
        for(int i = 0; i < x.length; i++){
            if(x[i].equals(y)){
                return true;
            }
        }
        return false;
    }
}
