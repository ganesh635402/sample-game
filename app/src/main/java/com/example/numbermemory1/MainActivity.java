package com.example.numbermemory1;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button des;
    int time=0;
    TableLayout tb;
    int winner=0;
    Chronometer mChronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = findViewById(R.id.tb);
        mChronometer= findViewById(R.id.chr);
        mChronometer.start();
        Integer[] num = new Integer[32];
        for (int i = 0; i < 32; i++) {
            if (i <= 15)
                num[i] = i + 1;
            else
                num[i] = i - 16 + 1;
        }
        List<Integer> numl = Arrays.asList(num);
        Collections.shuffle(numl);
        numl.toArray(num);
        int k = 0;
        for (int i = 0; i < 8; i++) {
            TableRow row = (TableRow) tb.getChildAt(i);
            for (int j = 0; j < 4; j++) {
                Button but = (Button) row.getChildAt(j);
                but.setOnClickListener(this);
                but.setText(String.valueOf(num[k]));
                but.setTextSize(0);
                k++;
            }
        }
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                time++;
            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v){
        Button b=(Button)v;
        delete(b);
        if(des==null){
            des=b;
        }
        else{
            if(des.getText()==b.getText()){
                /*makeTost("Matched","short");*/
                winner++;
                des=null;
                isWinner();
            }
            else{
                restore(des);
                /*makeTost("Not Matched","short");*/
                des=b;
            }
        }
    }
    public void delete(Button B){
        B.setTextSize(10);
        B.setClickable(false);
        B.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        B.setTextColor(getResources().getColor(R.color.White));
    }
    public void restore(Button B){
        B.setClickable(true);
        B.setTextSize(0);
        B.setBackgroundResource(android.R.drawable.btn_default);
    }
    public void isWinner() {
        if(winner==16) {
            mChronometer.stop();
            time--;
            String str=(time/60)+"Minutes"+(time%60)+"Seconds";
            writeToFile(""+time);
            makeTost("Congratulations....\nYou have completed the challenge in :" + str, "long");
        }

    }
    public void makeTost(String s,String len){
        if(len.equals("short"))
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }
    public void writeToFile(String data) {
        try {
            FileOutputStream fou = openFileOutput("data.txt", MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fou);
            outputStreamWriter.write(data);
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
        }
        catch (Exception e) { }
    }
}