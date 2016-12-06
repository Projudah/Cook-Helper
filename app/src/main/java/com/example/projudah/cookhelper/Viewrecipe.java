package com.example.projudah.cookhelper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    String name;
    public void populate(){
        Intent home = getIntent();
        name = home.getStringExtra("recipename");
        Home.refreshrecipe(this);
        Recipe recipe = Home.findrecipe(name, Home.recipes);
        if (recipe == null) {
            recipe = Home.findrecipe(newname, Home.recipes);
            name = recipe.name;
        }
        String type = recipe.type;
        String cat = recipe.category;
        String steps = recipe.steps;
        ArrayList<String> ing = recipe.ingredients;

        TextView recipename = (TextView) findViewById(R.id.name);
        TextView Category = (TextView) findViewById(R.id.category);
        TextView Type = (TextView) findViewById(R.id.type);
        LinearLayout ingredients =(LinearLayout) findViewById(R.id.ingslay);
        LinearLayout step = (LinearLayout) findViewById(R.id.steps);
        refresh(ingredients);
        refresh(step);

        recipename.setText(name);
        Category.setText(cat);
        Type.setText(type);
        for (int i = 0 ; i<ing.size();i++){
            TextView oneing = new TextView(this);
            oneing.setText("- "+ing.get(i));
            oneing.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
            ingredients.addView(oneing);
        }

        String[] sepsteps = separate(steps);
        for (int i= 0 ;i < sepsteps.length; i++){
            TextView oneing = new TextView(this);
            oneing.setText(Integer.toString(i+1)+". "+ sepsteps[i]);
            oneing.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
            step.addView(oneing);
        }


    }

    static String newname;
    public static void setname(String nname){
        newname = nname;
    }

    public void onBackPressed() {
        Trans.back(this,(RelativeLayout)findViewById(R.id.root));
    }

    public String[] separate(String steps){
        return (steps.split(" ;; "));
    }

    public void delete(View v){
        Home.Delete(name,this);
    }

    public void edit(View v){
        Home.edit(name, Home.recipes,this, (RelativeLayout)findViewById(R.id.relativeLayout));
    }

    @Override
    protected void onResume() {
        populate();
        super.onResume();

    }

    @Override
    protected void onRestart() {
        populate();
        super.onRestart();
    }



    public void refresh(ViewGroup x){
        x.removeAllViews();
    }
}
