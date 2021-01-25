package com.example.clubmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button choice5;
    Matcher matcher;
    ArrayList<Club> result;


    @Override
    protected void onRestart(){
        super.onRestart();
        matcher = (Matcher) getIntent().getSerializableExtra("matcher2");
    }

    @Override
    protected void onResume() {
        super.onResume();
            matcher = (Matcher) getIntent().getSerializableExtra("matcher2");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        choice1 = (Button) findViewById(R.id.button5);
        choice2 = (Button) findViewById(R.id.button6);
        choice3 = (Button) findViewById(R.id.button7);
        choice4 = (Button) findViewById(R.id.button9);
        choice5 = (Button) findViewById(R.id.button8);

        matcher = (Matcher) getIntent().getSerializableExtra("matcher");
        //Log.i("matcher", matcher.toString());
        //ArrayList<Club> result;
        try{
            result = matcher.topClubs(5);
            choice1.setText(result.get(0).getName());
            choice2.setText(result.get(1).getName());
            choice3.setText(result.get(2).getName());
            choice4.setText(result.get(3).getName());
            choice5.setText(result.get(4).getName());
        }catch(Exception e){
            choice1.setText(e.getMessage());
        }
    }
    public void enterSurvey(View v) {
        startActivity(new Intent(Results.this, Survey.class));
    }

    public void enterMoreInformation1(View v) {
        Intent sendClub = new Intent (Results.this, MoreInformation.class);
        sendClub.putExtra("club", result.get(0));

        Intent sendMatcher = new Intent (Results.this, MoreInformation.class);
        sendMatcher.putExtra("matcher", matcher);

        startActivity(new Intent(Results.this, MoreInformation.class));
        startActivity(sendMatcher);
        startActivity(sendClub);
    }
    public void enterMoreInformation2(View v) {
        Intent sendClub = new Intent (Results.this, MoreInformation.class);
        sendClub.putExtra("club", result.get(1));

        Intent sendMatcher = new Intent (Results.this, MoreInformation.class);
        sendMatcher.putExtra("matcher", matcher);
        startActivity(new Intent(Results.this, MoreInformation.class));
        startActivity(sendMatcher);
        startActivity(sendClub);
    }
    public void enterMoreInformation3(View v) {
        Intent sendClub = new Intent (Results.this, MoreInformation.class);
        sendClub.putExtra("club", result.get(2));

        Intent sendMatcher = new Intent (Results.this, MoreInformation.class);
        sendMatcher.putExtra("matcher", matcher);
        startActivity(new Intent(Results.this, MoreInformation.class));
        startActivity(sendMatcher);
        startActivity(sendClub);
    }
    public void enterMoreInformation4(View v) {
        Intent sendClub = new Intent (Results.this, MoreInformation.class);
        sendClub.putExtra("club", result.get(3));

        Intent sendMatcher = new Intent (Results.this, MoreInformation.class);
        sendMatcher.putExtra("matcher", matcher);
        startActivity(new Intent(Results.this, MoreInformation.class));
        startActivity(sendMatcher);
        startActivity(sendClub);
    }
    public void enterMoreInformation5(View v) {
        Intent sendClub = new Intent (Results.this, MoreInformation.class);
        sendClub.putExtra("club", result.get(4));

        Intent sendMatcher = new Intent (Results.this, MoreInformation.class);
        sendMatcher.putExtra("matcher", matcher);
        startActivity(new Intent(Results.this, MoreInformation.class));
        startActivity(sendMatcher);
        startActivity(sendClub);
    }
}

