package com.example.clubmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

public class MainActivity extends AppCompatActivity {

    private EditText getComment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getComment = (EditText) findViewById (R.id.editText3);
        //displayICS.setText("");
        //Button pickCalendar = (Button) findViewById(R.id.button);

    }
    public void enterSurvey(View v){
        String s = getComment.getText().toString();
        Intent sendComment = new Intent(MainActivity.this, Survey.class);
        sendComment.putExtra("comment", s);
        startActivity(new Intent(MainActivity.this, Survey.class));
        startActivity(sendComment);
    }

}
