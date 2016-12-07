package com.example.projudah.cookhelper;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Add extends ActionBarActivity {
    LinearLayout Choose;
    LinearLayout steps ;
    LinearLayout classi;
    LinearLayout name;
    ScrollView scroll;
    boolean choosedone =false;
    boolean stepsdone =false;
    boolean classdone = false;
    boolean namedone = false;
    boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseadd);
        Choose = (LinearLayout) findViewById(R.id.ing);
        steps = (LinearLayout) findViewById(R.id.step);
        classi = (LinearLayout) findViewById(R.id.Class);
        name = (LinearLayout) findViewById(R.id.name);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        Choose.setVisibility(View.GONE);
        steps.setVisibility(View.GONE);
        classi.setVisibility(View.GONE);
        choosedone =false;
        stepsdone =false;
        classdone = false;
        namedone = false;
        Trans.animatein(this,(RelativeLayout)findViewById(R.id.root));
        start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
    int[] checkid;
    public void start(){
        LinearLayout inglayout = (LinearLayout) findViewById(R.id.ing);
        //getting variables
        IngredientList ing = new IngredientList();
        ArrayList<String> ingedientsa = new ArrayList<>();
        ArrayList<String> ingedients = new ArrayList<>();
        try {
            ingedientsa = ing.readRecipe(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String x: ingedientsa){
            String[] split = x.split(" ;; ");
            ingedients.add(split[0]+" amount: "+split[1]+" "+split[2]);
        }
        checkid = new int[ingedients.size()];

        //stepid
        stepid = checkid.length+1;

        //

        for  (int i=0; i< ingedients.size(); i++){
            CheckBox oneing = new CheckBox(this);
            inglayout.addView(oneing);
            oneing.setText(ingedients.get(i));
            oneing.setId(i);
            checkid[i] = oneing.getId();

        }
    }

    @Override
    public void onBackPressed() {
        Trans.back(this,(RelativeLayout)findViewById(R.id.root));
    }
    boolean done = false;
    public void check (View v){
        if (!namedone){

            if (!(((EditText) findViewById(R.id.nametext)).getText().toString().equals(""))) {
                namedone = true;
                Trans.fadein(Choose);
            }else{
                Toast.makeText(getBaseContext(),"incomplete name field", Toast.LENGTH_SHORT).show();
                complete=false;
            }
        }else if(!choosedone){
            boolean flag = false;
            for (int i=0; i< checkid.length ; i++ ){
                if (((CheckBox) findViewById(checkid[i])).isChecked())
                    flag =true;
            }
            if (flag) {
                choosedone = true;
                Trans.fadein(steps);
            }else {
                Toast.makeText(getBaseContext(), "No Ingredient Selected", Toast.LENGTH_SHORT).show();
                complete = false;
            }
        }else if (!stepsdone){
            if (emptystep()) {
                Toast.makeText(getBaseContext(), "You have an Empty Step", Toast.LENGTH_SHORT).show();
                complete = false;
            }else if (!(((EditText) findViewById(R.id.stepone)).getText().toString().equals(""))) {
                stepsdone = true;
                Trans.fadein(classi);

            }else {
                Toast.makeText(getBaseContext(), "at least one step", Toast.LENGTH_SHORT).show();
                complete = false;
            }
        }else if (!classdone){
            if (!((((AutoCompleteTextView) findViewById(R.id.type)).getText().toString().equals("")) || ((((AutoCompleteTextView) findViewById(R.id.category)).getText().toString().equals(""))))){
                classdone =true;
                ImageView done = (ImageView) findViewById(R.id.next);
                done.setImageDrawable(getResources().getDrawable(R.drawable.done));
                this.done=true;
            }else {
                Toast.makeText(getBaseContext(), "No Category or Type", Toast.LENGTH_SHORT).show();
                complete = false;
            }
        }else{
            if (!complete) {
                choosedone = false;
                stepsdone = false;
                classdone = false;
                namedone = false;
                complete = true;
                for (int i = 0; i<5 ; i++){
                    Log.i("loop", "loop "+ Integer.toString(i));
                    if (!complete) {
                        Log.i("loop", "incomplete");
                        choosedone =stepsdone = classdone = namedone =true;
                        break;
                    }
                    check(v);
                }
            }else {

                String RecipeName = ((EditText) findViewById(R.id.nametext)).getText().toString();
                ArrayList<String> Ingredients = new ArrayList<>();
                for (int i=0; i< checkid.length ; i++ ) {
                    if (((CheckBox) findViewById(checkid[i])).isChecked())
                        Ingredients.add(((CheckBox) findViewById(checkid[i])).getText().toString());
                }
                String Steps;
                Steps = ((EditText) findViewById(R.id.stepone)).getText().toString();
                for (int i = 0; i < stepidlist.size(); i++) {
                    Steps = Steps+" ;; "+((EditText) findViewById(stepidlist.get(i))).getText().toString();
                }
                String Type = ((AutoCompleteTextView) findViewById(R.id.type)).getText().toString();
                String Category = ((AutoCompleteTextView) findViewById(R.id.category)).getText().toString();

                Recipe rec = new Recipe(RecipeName,Category,Type,Steps);
                for (int i = 0; i < Ingredients.size(); i++)
                    rec.addIngredient(Ingredients.get(i));

                String filename = rec.getName()+".json";
                String string = "";
                try {
                    string = rec.writeAsString();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename,0);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!done) {
            android.os.Handler h = new android.os.Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scroll.smoothScrollTo(0, (findViewById(R.id.scroll).getBottom()));
                }
            }, 500);
        }


    }
    int stepid;
    int count=2;
    ArrayList<Integer> stepidlist = new ArrayList<Integer>();

    public void addstep(View v) {
        if (emptystep()){
            Toast.makeText(getBaseContext(), "You have an Empty Step", Toast.LENGTH_SHORT).show();

        }else if (!(((EditText) findViewById(R.id.stepone)).getText().toString().equals(""))) {
            if (stepid == checkid.length+1) {
                EditText more = new EditText(this);
                ImageView delete = new ImageView(this);
                delete.setImageDrawable(getResources().getDrawable(R.drawable.delete));
                ImageView add = (ImageView) findViewById(R.id.add);
                ViewGroup.LayoutParams params = add.getLayoutParams();
                ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                delete.setLayoutParams(params);
                delete.setClickable(true);
                delete.setScaleX((float) 0.7);
                delete.setScaleY((float) 0.7);
                //delete.setY(-50);
                Log.i("location", Float.toString(delete.getX()) + " " + Float.toString(delete.getY()));
                delete.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletestep(v);
                    }
                });
                steps.addView(more, count);
                steps.addView(delete,count+1);
                more.setHint("Enter Step " + Integer.toString((count/2)+1));
                count+=2;
                more.setId(stepid);
                delete.setId(stepid+1);
                stepidlist.add(stepid);
                stepid+=2;

            }else if (!(((EditText) findViewById(stepidlist.get(stepidlist.size()-1))).getText().toString().equals(""))) {
                EditText more = new EditText(this);
                ImageView delete = new ImageView(this);
                delete.setImageDrawable(getResources().getDrawable(R.drawable.delete));
                ImageView add = (ImageView) findViewById(R.id.add);
                ViewGroup.LayoutParams params = add.getLayoutParams();
                ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                delete.setLayoutParams(params);
                delete.setClickable(true);
                delete.setScaleX((float) 0.7);
                delete.setScaleY((float) 0.7);
                //delete.getResources().getDimension(-50);
                Log.i("location", Float.toString(delete.getX()) + " " + Float.toString(delete.getY()));
                delete.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletestep(v);
                    }
                });
                steps.addView(more, count);
                steps.addView(delete,count+1);
                more.setHint("Enter Step " + Integer.toString((count/2)+1));
                count+=2;
                more.setId(stepid);
                delete.setId(stepid+1);
                stepidlist.add(stepid);
                stepid+=2;
            }else
                Toast.makeText(getBaseContext(),"You have an Empty Step", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getBaseContext(),"at least one step", Toast.LENGTH_SHORT).show();
    }

    public void deletestep(View v){
        ((LinearLayout)v.getParent()).removeView(findViewById(v.getId() - 1));
        ((LinearLayout)v.getParent()).removeView(findViewById(v.getId()));
        //stepidlist.add(stepidlist.indexOf(v.getId()-1),-1);

        stepidlist.remove(stepidlist.indexOf(v.getId()-1));
        stepid-=2;
        count-=2;

    }

    public boolean emptystep(){
        boolean flag = false;
        for (int i = 0; i < stepidlist.size(); i++) {
            if (((EditText) findViewById(stepidlist.get(i))).getText().toString().equals(""))
                flag = true;
        }
        return flag;
    }

 }
