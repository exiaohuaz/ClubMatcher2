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

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Survey extends AppCompatActivity {
    private static final String TAG = "Survey";


    private static final int READ_REQUEST_CODE = 42;
    private Student student = new Student ();

    private ICSReader reader = new ICSReader();

    public void pickCalendar(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        //text/calendar
        intent.setType("text/calendar");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    EditText hourText;
    EditText sizeText;

    ArrayList<String> output1 = new ArrayList<>();
    ArrayList<String> output3 = new ArrayList<>();

    String comment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        comment = (String) getIntent().getSerializableExtra("comment");

        final List<String> list = Arrays.asList("Political-Activism",
                "Religious-Spiritual",
                "GeneralInterestAndInvolvement",
                "Multicultural",
                "Student-Government",
                "Sports-Martial Arts",
                "Professional-Academic",
                "Technology",
                "GreekLife",
                "Service-Philanthropy",
                "AnnualCampusEvent",
                "Gender-LGBT",
                "Gaming",
                "MediaAndPublications",
                "Performance-Artistic");
        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        hourText = (EditText) findViewById(R.id.inputnumber);
        sizeText = (EditText) findViewById(R.id.inputnumber2);

        final List<String> list2 = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        final List<KeyPairBoolData> listArray2 = new ArrayList<>();

        for (int i = 0; i < list2.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list2.get(i));
            h.setSelected(false);
            listArray2.add(h);
        }

        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);
        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        output1.add(items.get(i).getName());
                    }
                }
            }
        });

        MultiSpinnerSearch searchMultiSpinnerUnlimited3 = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited3);
        searchMultiSpinnerUnlimited3.setItems(listArray2, -1, new SpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                ArrayList<String> output = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        output3.add(items.get(i).getName());
                    }
                }
            }
        });
    }


    private Uri uri = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                uri = resultData.getData();
//                Log.i(TAG, "Uri: " + uri.toString());
//                showImage(uri);
            }
            try{
                String output = readTextFromUri(uri);
                //displayICS.setText(output);
                student.setSchedule(reader.parseICS(output));
                //displayICS.setText(calsses.get(0)[0] + " " + calsses.get(0)[1]);
            } catch (IOException e) {}
            for(int i = 0; i < student.schedule.size(); i++){
                //displayICS.append(student.classes.get(i)[0] + " " + student.classes.get(i)[1] + "\n");
            }
        }
    }


    protected String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line+"\n");
            }
        }
        return stringBuilder.toString();
    }

    public void enterResults(View v) throws IOException{

        //attach information for clubs
        ArrayList<Club> clubs = new ArrayList<>();

        for (int i = 1; i <= 143; i++){
            String file = "clubs" + i;
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier(file,
                            "raw", getPackageName()));
            clubs.add(Club.loadClub(ins));
        }

        //attach information for student
        try{
        student.setClubsize(Integer.parseInt(sizeText.getText().toString()));
        } catch (Exception e){
            sizeText.setText("Please input a number");
            return;
        }
        try{
            student.setTimeCommitment(Integer.parseInt(hourText.getText().toString()) * 60);
        } catch (Exception e){
            sizeText.setText("Please input a number");
            return;
        }

        String[] clubPreference = new String[output1.size()];
        for(int i = 0; i < output1.size(); i++){
            clubPreference[i] = output1.get(i);
        }

        String[] days = new String[output3.size()];
        for(int i = 0; i < output3.size(); i++){
            days[i] = output3.get(i);
        }

        if(!student.hasSchedule()){
            student.setSchedule(ICSReader.generateCalendar(days));
        }

        student.setTypeOfClub(clubPreference);

        student.setComment(comment);


        Matcher matcher = new Matcher (clubs, student);

        Intent sendMatcher = new Intent(Survey.this, Results.class);
        sendMatcher.putExtra("matcher", matcher);
        startActivity(new Intent(Survey.this, Results.class));

        startActivity(sendMatcher);



        //calculate matches

        //send

    }
}