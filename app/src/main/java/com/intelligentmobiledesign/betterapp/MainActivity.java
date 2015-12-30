package com.intelligentmobiledesign.betterapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    private ArrayList<String[]> jokes=new ArrayList<String[]>();
    private String[] chosenJoke;
    private int numJoke=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            jokes = createJokeList();
        }catch(IOException e){
            new AlertDialog.Builder(getApplicationContext()) //pops up a window
                    .setTitle("file_exception")
                    .setMessage("Error:File Not Found.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        } //just closes box
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        chooseJoke();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button who=(Button) findViewById(R.id.whos_there);
        who.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       whosThere(v);
                                   }
                               }
        );

        Button blank_who=(Button) findViewById(R.id.blank_who);
        blank_who.setVisibility(View.GONE); // make button invisible
        blank_who.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       blankWho(v);
                                   }
                               }
        );
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CreateNewJoke.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
    public void blankWho(View v){
        String joke=chosenJoke[1];
        TextView secondHalf=(TextView) findViewById(R.id.secondHalf);
        secondHalf.setText(joke);
    }

    public void whosThere(View v){
        String joke=chosenJoke[0];

        TextView firstHalf=(TextView) findViewById(R.id.firstHalf);
        firstHalf.setText(joke);
        joke=joke+" Who?";
        Button blank_who=(Button) findViewById(R.id.blank_who);
        blank_who.setText(joke);
        blank_who.setVisibility(View.VISIBLE);

    }
    public void refreshButton(View v){
        chooseJoke();
        Button blank_who=(Button) findViewById(R.id.blank_who);
        blank_who.setVisibility(View.INVISIBLE);

        TextView firstHalf=(TextView) findViewById(R.id.firstHalf);
        TextView secondHalf=(TextView) findViewById(R.id.secondHalf);

        firstHalf.setText("");
        secondHalf.setText("");

    }
    public void chooseJoke(){ //UNDO THIS >:(
       // int rand=(int)(Math.random()*(jokes.size()-1)); //pick a joke at random
        if(numJoke==jokes.size()) {
            numJoke=0;
        }
        chosenJoke = jokes.get(numJoke);
        numJoke++;
    }
    public ArrayList<String[]> createJokeList() throws IOException {
        ArrayList<String[]> jokes=new ArrayList<String[]>();
        InputStream in = null;
        in = this.getResources().openRawResource(R.raw.jokes);
        if (in != null) {

            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    String[] l=line.split("@");
                    if(l.length!=2){
                        new AlertDialog.Builder(getApplicationContext()) //pops up a window
                                .setTitle("file_exception")
                                .setMessage("Error with file processing: Too many punchlines.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    } //just closes box
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    else{

                        String[] fullJoke={l[0], l[1]};
                        jokes.add(fullJoke);

                    }
                }
            } finally {
                in.close();
            }

        } else {
            new AlertDialog.Builder(getApplicationContext()) //pops up a window
                    .setTitle("file_exception")
                    .setMessage("Error:File Not Found.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        } //just closes box
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return jokes;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
