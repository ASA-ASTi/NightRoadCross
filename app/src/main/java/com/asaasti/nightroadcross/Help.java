package com.asaasti.nightroadcross;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Help extends AppCompatActivity {
    Button swi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        swi=(Button) findViewById(R.id.theme);

        switch (loadSwitch()){
            case 1:
                swi.setText("Black Theme");
                break;
            case 2:
                swi.setText("Yellow Theme");
                break;
            case 3:
                swi.setText("Teal Theme");
                break;
            case 4:
                swi.setText("Red Theme");
                break;
        }

        swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int k;
                if(loadSwitch()==4){
                   k=1 ;
                }
                else {
                    k=loadSwitch()+1;
                }
                switch (k){
                    case 1:
                        swi.setText("Black Theme");
                        save(k);
                        break;
                    case 2:
                        swi.setText("Yellow Theme");
                        save(k);
                        break;
                    case 3:
                        swi.setText("Teal Theme");
                        save(k);
                        break;
                    case 4:
                        swi.setText("Red Theme");
                        save(k);
                        break;
                }
            }
        });

    }
    private int loadSwitch(){
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences("NightTrafficCross", MODE_PRIVATE);
        return  sharedPreferences.getInt("theme",1);
    }
    private void save(int i){
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences("NightTrafficCross", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        editor.putInt("theme",i);
        editor.commit();

    }
    @Override
    public void onBackPressed() {

        Intent r=new Intent(Help.this,MainActivity.class);
        startActivity(r);
        finish();
    }
}

