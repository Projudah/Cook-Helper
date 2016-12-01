package com.example.projudah.cookhelper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Viewrecipe extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecipe);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewrecipe, menu);
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

    public void populate(){
        Intent home = getIntent();
        String name = home.getStringExtra("recipe");
        ArrayList<Recipe> recipes = home.getParcelableArrayListExtra("recipe list");
        TextView recipename = (TextView) findViewById(R.id.name);
        TextView Category = (TextView) findViewById(R.id.category);
        TextView Type = (TextView) findViewById(R.id.type);
        LinearLayout ingredients =(LinearLayout) findViewById(R.id.ingslay);
        LinearLayout steps = (LinearLayout) findViewById(R.id.steps);




    }
}