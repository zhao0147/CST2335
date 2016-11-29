package com.example.sulin.lab1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    protected String message = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Version 1.0, by Sulin Zhao", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch (id) {
            case R.id.action_one:
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Log.d("Toolbar", "Option 1 selected");
                break;

            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to go back?");

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
                break;

            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("New Message");
                final View view = getLayoutInflater().inflate(R.layout.custome_dialog, null);
                builder.setView(view);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        EditText et = (EditText)view.findViewById(R.id.newMessage);
                        message = et.getText().toString();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.create().show();
            }
                break;

            case R.id.about:
                Toast toast = Toast.makeText(this, "Version 1.0, by Sulin Zhao", Toast.LENGTH_LONG);
                toast.show(); //display your message box
                Log.d("Toolbar", "Option about selected");
                break;
        }
        return true;
    }

}
