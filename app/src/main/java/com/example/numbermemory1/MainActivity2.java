package com.example.numbermemory1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {
    TextView tv;
    int HighScore;
    String str;
    String str2;
    String[] str1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=findViewById(R.id.tv);
        findHigh();
    }
    public void plyClk(View view) {
        startActivity(new Intent(MainActivity2.this,MainActivity.class));
    }
    public String readFromFile() {
        String ret = "";
        try {
            InputStream inputStream = openFileInput("data.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    stringBuilder.append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

        }
        catch (Exception e) {}
        return ret;
    }
    public void findHigh(){
        str=readFromFile();
        str1=str.split("\n");
        try {
            HighScore = Integer.parseInt(str1[0]);
        }catch(Exception e){}
        for(int i=0;i<str1.length;i++)
        {
            if ( str1[i] != null ) {
                try {
                    if (HighScore > Integer.parseInt(str1[i]))
                        HighScore = Integer.parseInt(str1[i]);
                }
                catch (Exception e){}
            }
        }
        if(HighScore!=0)
            str2 = (HighScore / 60) + "Minute" + (HighScore % 60) + "Seconds";
        else
            str="!You Haven't Played Yet!";
        tv.setText("The Highest Score Until Now is:\n"+str2);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        findHigh();
    }
}