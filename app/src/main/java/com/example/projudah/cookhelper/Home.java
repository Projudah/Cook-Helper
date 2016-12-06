package com.example.projudah.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Trans.animatein(this,(RelativeLayout)findViewById(R.id.root));
        home();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void home(){
        final Activity thiss =this;
        Spinner add = (Spinner) findViewById(R.id.button);
        add.setSelection(0);
        add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);
                String choice = (String) (parent.getItemAtPosition(position));
                RelativeLayout bg = (RelativeLayout) findViewById(R.id.relativeLayout);
                if (choice.equals("Recipe")) {
                    Trans.outback(bg, thiss, Add.class);
                }
                if (choice == "Ingredient") {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(new Recipe("Test recipe","test category","test type"));
        /*
        try {
            FileInputStream jsonReader = openFileInput(Home.this.getFilesDir().getAbsolutePath()+"json/");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(Home.this.getFilesDir().getAbsolutePath());
        try {
            for (final File fileEntry : file.listFiles()) {
                try {
                    recipes.add(mapper.readValue(fileEntry, Recipe.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch(NullPointerException e){Toast.makeText(this, "Unable to find directory "+Home.this.getFilesDir().getAbsolutePath(),
                Toast.LENGTH_LONG).show();}

        String[] hello2 = new String[recipes.size()];
        for(int i = 0; i < recipes.size();i++){
            hello2[i] = recipes.get(i).getName();
        }
        String[] hello = new String[]{"Spaghetti","Tacos","Fried Rice","Ham Sandwich","Burger","Onion Salad","Recipe for disaster","Nachos","Greasy Nachos","Poutine"};
        Myadapter my = new Myadapter(this, R.layout.customlist, hello2);
        //Myadapter my2 = new Myadapter(this, android.R.layout.simple_list_item_1, hello);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(my);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                recipe(view);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> spin1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Category"});
        ArrayAdapter<String> spin2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Type"});
        spinner.setAdapter(spin1);
        spinner2.setAdapter(spin2);



    }

    public void search(View v){
        EditText search = (EditText) findViewById(R.id.searchtext);
        String x = search.getText().toString();
        Recipe recipeSearch = RecipeSingleton.getRecipeFromSearchMap(recipes,x, 0);
    }
    public void recipe(View v){
        TextView x =(TextView) ((RelativeLayout) v).getChildAt(0);
        setContentView(R.layout.recipe);
        TextView text;
        TextView recipe = (TextView) findViewById(R.id.recipename);
        text = (TextView) x;
        recipe.setText(text.getText());
    }

    public void add(View v){
        setContentView(R.layout.chooseadd);
        String[] hello = new String[]{"These","should","be","ingredients","and","stuff","so","full","marks","please"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.customlist, hello);
        /*GridView list = (GridView) findViewById(R.id.gridView);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getBaseContext(),"K", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    public void cat(View v){
        setContentView(R.layout.cat);
    }
    public void steps(View v){
        setContentView(R.layout.steps);
    }
    public void fin(View v){
        setContentView(R.layout.activity_home);
        home();
    }

    @Override
    protected void onResume() {
        //Trans.fadein(this, (RelativeLayout)findViewById(R.id.relativeLayout));
        home();
        super.onResume();

    }
}
