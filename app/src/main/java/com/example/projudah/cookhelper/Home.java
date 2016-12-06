package com.example.projudah.cookhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


    static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    public void home(){
        recipes.clear();
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
                if (choice.equals("Ingredient")) {
                    Trans.outback(bg, thiss, Ingredients.class);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        refreshrecipe(this);

        String[] hello2 = new String[recipes.size()];
        for(int i = 0; i < recipes.size();i++){
            hello2[i] = recipes.get(i).getName();
        }

        Myadapter my = new Myadapter(this, R.layout.customlist, hello2);
        ListView list = (ListView) findViewById(R.id.listView);
        if (!searched) {
            list.setAdapter(my);
        }else{
            String[] Hello3 = new String[name.size()];
            for (int i =0; i< Hello3.length; i++)
                Hello3[i]= name.get(i).name;
            my = new Myadapter(this, R.layout.customlist, Hello3);
            list.setAdapter(my);
            searched=false;
        }
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

        if (!(x.equals(""))) {

            ArrayList<Recipe> recipeSearch = null;

            try {
                recipeSearch = RecipeSingleton.getRecipesThatSatisfyString(recipes, x, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            searched = true;
            name = recipeSearch;
            home();
        }
    }
    boolean searched = false;
    ArrayList<Recipe> name;

    public void recipe(View v){
        TextView x =(TextView) ((RelativeLayout) v).getChildAt(0);
        Recipe chosenone = findrecipe(x.getText().toString(), recipes);
        if (chosenone!= null){
            Intent next = new Intent(this, Viewrecipe.class);
            next.putExtra("recipename", chosenone.getName());
            Trans.outpassback((RelativeLayout)findViewById(R.id.relativeLayout), this, next);
        }


    }

    public static void edit(String name, ArrayList<Recipe> recipes, Activity thiss, RelativeLayout root){
        Recipe chosenone = findrecipe(name, recipes);
        if (chosenone!= null){
            Intent next = new Intent(thiss, Edit.class);
            next.putExtra("recipename", chosenone.getName());
            next.putExtra("recipecat", chosenone.category);
            next.putExtra("recipetype", chosenone.type);
            next.putExtra("recipesteps", chosenone.steps);
            next.putExtra("ing", chosenone.ingredients);
            Trans.outpassback(root, thiss, next);
        }


    }

    public static Recipe findrecipe(String name ,ArrayList<Recipe> recipes){
        Recipe chosenone=null;
        for (int i=0; i<recipes.size(); i++) {
            if (recipes.get(i).getName().equals(name)) {
                chosenone = recipes.get(i);
                return chosenone;

            }
        }
        return chosenone;
    }

    @Override
    protected void onResume() {
        home();
        super.onResume();
    }

    public static void Delete(final String recipe, final Activity thishome){
        final Context context = thishome.getBaseContext();
        AlertDialog alertDialog = new AlertDialog.Builder(thishome, R.style.dialog).create();
        alertDialog.setTitle("Delete");
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Are you sure you want to Delete "+recipe+" recipe?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(thishome,thishome.getFilesDir().getAbsolutePath()+"/"+recipe+".json", Toast.LENGTH_LONG).show();
                        File file = new File(thishome.getFilesDir().getAbsolutePath()+"/"+recipe+".json");
                        boolean worked1 = false;
                        if(file.exists()) {
                            //Toast.makeText(thishome, "File exists.... attempting to delete:",Toast.LENGTH_LONG).show();
                            worked1 = file.delete();
                        }
                        if(worked1) {
                            Toast.makeText(thishome, "Successfully deleted", Toast.LENGTH_LONG).show();
                            Trans.out((RelativeLayout)thishome.findViewById(R.id.relativeLayout),thishome,Home.class);
                        }
                        else {
                            File file2 = new File(thishome.getFilesDir().getAbsolutePath()+"/"+recipe);
                            file2.delete();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    public static void refreshrecipe(Activity thiss){
        recipes.clear();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(thiss.getFilesDir().getAbsolutePath());
        try {
            for (final File fileEntry : file.listFiles()) {
                try {
                    recipes.add(mapper.readValue(fileEntry, Recipe.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch(NullPointerException e){Toast.makeText(thiss, "Unable to find directory "+thiss.getFilesDir().getAbsolutePath(),
                Toast.LENGTH_LONG).show();}
    }
}
