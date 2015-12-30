package com.intelligentmobiledesign.betterapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CreateNewJoke extends AppCompatActivity {
    private String newJoke;
    private static final String TAG=CreateNewJoke.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Test");
        Intent intent=getIntent();
        setContentView(R.layout.activity_create_new_joke);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View editTextTwo=findViewById(R.id.new_part_two);
        editTextTwo.setVisibility(View.GONE);

        View blank_who=findViewById(R.id.new_blank_who);
        blank_who.setVisibility(View.GONE);

        Button submitTwo=(Button) findViewById(R.id.part_two_button);
        submitTwo.setVisibility(View.GONE);
        submitTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitTwo(v);
            }
        });

        Button submitOne=(Button) findViewById(R.id.part_one_button);
        submitOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitOne(v);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    public void submitOne(View v) {
        EditText etOne = (EditText) findViewById(R.id.new_part_one);
        newJoke = etOne.getText()+"";
        String bW=etOne.getText()+" Who?";
        View editTextTwo = findViewById(R.id.new_part_two);
        editTextTwo.setVisibility(View.VISIBLE);
        View buttonTwo=findViewById(R.id.part_two_button);
        buttonTwo.setVisibility(View.VISIBLE);
        TextView blank_who=(TextView)findViewById(R.id.new_blank_who);
        blank_who.setVisibility(View.VISIBLE);
        blank_who.setText(bW);
    }
    public void submitTwo(View v){
        EditText etTwo=(EditText) findViewById(R.id.new_part_two);
        newJoke=newJoke+"@"+etTwo.getText()+"\n";
        Log.d(TAG,"New Joke: "+newJoke);
        int k=1;
        try {
             k = addToFile();
            Log.d(TAG,"Called Add to file in submit");
        }catch (IOException e){
            new AlertDialog.Builder(getApplicationContext()) //pops up a window
                    .setTitle("file_exception")
                    .setMessage("Error with file processing: unable to open file.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        } //just closes box
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
        if(k==0){ //return to main activity
            Intent intent=new Intent(CreateNewJoke.this, MainActivity.class);
            CreateNewJoke.this.startActivity(intent);
        }
        else{
            new AlertDialog.Builder(getApplicationContext()) //pops up a window
                    .setTitle("file_exception")
                    .setMessage("Error with file writing.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        } //just closes box
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    public int addToFile() throws IOException { //adds the joke to file
        File fOut=null;
        int i=-1;
        try{
             fOut=new File("../res/raw/jokes.txt");
            FileWriter fW=new FileWriter(fOut, true);
            Log.d(TAG,"Opened file, writing "+newJoke);
            fW.write(newJoke);
            fW.flush();
            fW.close();
            i=0;
        }finally{
            Log.d(TAG, "Complete");
        }
        return i;
    }
}
