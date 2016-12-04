package com.example.projudah.cookhelper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Viewrecipe extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecipe);
        Trans.animatein(this, (RelativeLayout)findViewById(R.id.root));
        populate();
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
        String name = home.getStringExtra("recipename");
        String type = home.getStringExtra("recipetype");
        String cat = home.getStringExtra("recipecat");
        String steps = home.getStringExtra("recipesteps");
        ArrayList<String> ing = home.getStringArrayListExtra("ing");

        TextView recipename = (TextView) findViewById(R.id.name);
        TextView Category = (TextView) findViewById(R.id.category);
        TextView Type = (TextView) findViewById(R.id.type);
        LinearLayout ingredients =(LinearLayout) findViewById(R.id.ingslay);
        LinearLayout step = (LinearLayout) findViewById(R.id.steps);

        recipename.setText(name);
        Category.setText(cat);
        Type.setText(type);
        Log.i("ingredients", ing.size()+"");
        for (int i = 0 ; i<ing.size();i++){
            Log.i("ing", ing.get(i));
            TextView oneing = new TextView(this);
            oneing.setText("- "+ing.get(i));
            oneing.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
            ingredients.addView(oneing);
        }

        String[] sepsteps = separate(steps);
        Log.i("steps", steps);
        for (int i= 0 ;i < sepsteps.length; i++){
            Log.i("seperate", sepsteps[i]);
            TextView oneing = new TextView(this);
            oneing.setText(Integer.toString(i+1)+". "+ sepsteps[i]);
            oneing.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
            step.addView(oneing);
        }


    }

    public String[] separate(String steps){
        return (steps.split(" ;; "));
    }
}
