package com.example.fantasyfootball;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare DBHandler
    DBHandler dbHandler;

    // declare EditText
    EditText nameEditText;

    // declare Spinners
    Spinner positionSpinner;
    Spinner teamSpinner;

    // declare Strings to store year and major selected in Spinners

    String position;
    String team;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        position = "";
        team = "";

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditText
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        // initialize Spinners
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);

        // initialize ArrayAdapters with values in position and team string arrays
        // and stylize them with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this,
                R.array.postion, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(this,
                R.array.team, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapters with style defined by simple_spinner_dropdown_item
        positionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapters on Spinners
        positionSpinner.setAdapter(positionAdapter);
        teamSpinner.setAdapter(teamAdapter);

        // register On Item Selected Listener to Spinners
        positionSpinner.setOnItemSelectedListener(this);
        teamSpinner.setOnItemSelectedListener(this);

    }

    /**
     * This method further initializes the Action Bar of the activity.
     * It gets the code (XML) in the menu resource file and incorporates it
     * into the Action Bar.
     *
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * This method gets called when a menu-item in the overflow menu is selected.
     *
     * @param menuItem selected team
     * @return String that contains count of the number of students who have the selected major.
     */
    public void getMessage(MenuItem menuItem) {
        int count = dbHandler.getCount("Eagles");
        int count2 = dbHandler.getCount("Steelers");
        int count3 = dbHandler.getCount("Cowboys");

        Toast.makeText(this, "Eagles: " + count + "\nSteelers: " + count2 + "\nCowboys: " + count3, Toast.LENGTH_LONG).show();

    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     *
     * @param menuItem add student menu item
     */
    public void add(MenuItem menuItem) {
        // get data input in EditText and store it in String
        String name = nameEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || position.trim().equals("") || team.trim().equals("")) {
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a position, name, and team!", Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.add(name, position, team);

            // display "Student added!" Toast of none of the Strings are empty
            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
        // get the id of the Spinner that called method
        switch (parent.getId()) {
            case R.id.teamSpinner:
                // get the item selected in the Spinner and store it in String
                team = parent.getItemAtPosition(position1).toString();
                break;
            case R.id.positionSpinner:
                // get the item selected in the Spinner and store it in String
                position = parent.getItemAtPosition(position1).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}