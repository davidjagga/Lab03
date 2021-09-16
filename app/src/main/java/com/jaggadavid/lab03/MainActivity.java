package com.jaggadavid.lab03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    Button reset;
    TextView name, hobbies, school, age;
    EditText editText;
    SeekBar seekBar;
    SharedPreferences sh;
    String TAG = "com.jaggadavid.lab03.shared";
    SharedPreferences.Editor edit;
    static int x1, x2, x3, x4, prog;
    String nameStr, hobbiesStr, schoolStr, ageStr;
    ArrayList<TextView> array;
    ConstraintLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create views
//        b1=findViewById(R.id.button);
//        b2=findViewById(R.id.button2);
//        b3=findViewById(R.id.button3);
//        b4=findViewById(R.id.button4);
        name = findViewById(R.id.nameValue);
        hobbies = findViewById(R.id.hobbiesValue);
        school = findViewById(R.id.schoolValue);
        age = findViewById(R.id.ageValue);
        editText = findViewById(R.id.enterText);

        array=new ArrayList<TextView>();
        // add views to list
        array.add(name);
        array.add(hobbies);
        array.add(school);
        array.add(age);
        //create seekbar
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);

        reset=findViewById(R.id.reset);
        //shared prefernces
        sh = getSharedPreferences(TAG, MODE_PRIVATE);
        edit = sh.edit();
//        x1=0;
//        x2=0;
//        x3=0;
//        x4=0;
        nameStr="";
        hobbiesStr="";
        ageStr="";
        schoolStr="";
        prog=30;
        setBasePreferences();
//        b1.setText(""+x1);
//        b2.setText(""+x2);
//        b3.setText(""+x3);
//        b4.setText(""+x4);
        seekBar.setProgress(prog);
        ageStr=String.valueOf(seekBar.getProgress());
        age.setText(ageStr);
        activity_main = findViewById(R.id.activity_main);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                for (TextView view: array) {
//                    view.setTextSize(seekBar.getProgress());
//                }
                ageStr=String.valueOf(seekBar.getProgress());
                age.setText(ageStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar s = Snackbar.make(findViewById(R.id.activity_main),
                        "Age set to "+ seekBar.getProgress(),
                        Snackbar.LENGTH_LONG);
                s.setAction("Undo",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                seekBar.setProgress(progress);
                                ageStr=String.valueOf(seekBar.getProgress());
                                age.setText(Integer.valueOf(ageStr));
                                Snackbar.make(findViewById(R.id.activity_main),
                                        "Age set to "+ seekBar.getProgress(),
                                        Snackbar.LENGTH_LONG);
                            }
                        });
                s.setActionTextColor(Color.BLUE);

                s.show();
            }
        });
        View layout = findViewById(R.id.activity_main);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edit.clear().apply();
                setBasePreferences();
                return false;
            }
        });

//        activity_main.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.i("reset", "Reseting Values");
//                edit.putInt("B1", 0);
//                edit.putInt("B2", 0);
//                edit.putInt("B3", 0);
//                edit.putInt("B4", 0);
//                edit.putInt("Progress", prog);
//
//
//                setBasePreferences();
//
//                b1.setText(""+x1);
//                b2.setText(""+x2);
//                b3.setText(""+x3);
//                b4.setText(""+x4);
//                seekBar.setProgress(prog);
//
//                for (TextView view1: array) {
//                    view1.setTextSize(seekBar.getProgress()/2);
//                }
//                return false;
//            }
//        });


    }
    public void reset(View view) {
        edit.putString("school", "");
        edit.putString("hobbies", "");
        edit.putString("age", "");
        edit.putString("name", "");
        edit.putInt("Progress", 30);

        setBasePreferences();

        name.setText("");
        hobbies.setText("");
        school.setText("");
        age.setText("");
        seekBar.setProgress(30);
    }


    public void setTextValues(View view) {
        TextView v = (TextView) (view);

        v.setText(editText.getEditableText());


    }
    public void setBasePreferences(){
        nameStr="";
        hobbiesStr="";
        ageStr="";
        schoolStr="";
        prog=30;

        nameStr = sh.getString("name", "");
        hobbiesStr = sh.getString("hobbies", "");
        ageStr = sh.getString("age", "");
        schoolStr = sh.getString("school", "");
        prog = sh.getInt("Progress", 30);
    }

    @Override
    protected void onPause() {
        super.onPause();
        edit.putString("name", (String) name.getText());
        edit.putString("hobbies", (String) hobbies.getText());
        edit.putString("age", (String) age.getText());
        edit.putString("school", (String) school.getText());
        edit.putInt("Progress", Integer.valueOf(prog));
        edit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBasePreferences();
    }


}