package com.example.projudah.cookhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Ingredients extends ActionBarActivity {
    IngredientList ing;
    PopupWindow window;
    PopupWindow window2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
        Trans.animatein(this,(RelativeLayout)findViewById(R.id.root));
        ing = new IngredientList();
        start();
    }

    public void start(){
        ArrayList<String> ingslist = new ArrayList<>();
        try {
            ingslist = ing.readRecipe(this);
        } catch (IOException e){}


        ArrayAdapter my = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingslist);

        ListView list = (ListView) findViewById(R.id.ings);
        list.setAdapter(my);

        final EditText name = new EditText(this);
        final EditText name2 = new EditText(this);
        final EditText amount = new EditText(this);
        final EditText unit  = new EditText(this);
        final EditText amount2 = new EditText(this);
        final EditText unit2  = new EditText(this);
        name.setHint("Ingredient Name");
        name2.setHint("Ingredient Name");
        amount.setHint("Ingredient Amount");
        unit.setHint("Amount unit");
        amount2.setHint("Ingredient Amount");
        unit2.setHint("Amount unit");

        window = new PopupWindow(this);
        window.setOutsideTouchable(false);
        window.setHeight(800);
        window.setWidth(800);
        window.setFocusable(true);
        window2 = new PopupWindow(this);
        window2.setOutsideTouchable(false);
        window2.setHeight(800);
        window2.setWidth(800);
        window2.setFocusable(true);
        final String[] curname = {"","",""};

        TextView Ok = new TextView(this), cancel = new TextView(this) , Delete = new TextView(this), Ok2 = new TextView(this), cancel2 = new TextView(this);
        Ok.setClickable(true);
        cancel.setClickable(true);
        Ok2.setClickable(true);
        cancel2.setClickable(true);
        Delete.setClickable(true);
        final Activity thishome = this;


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window2.dismiss();
            }
        });

        Ok.setText("Ok");
        Ok.setTextSize(18);
        cancel.setText("Cancel");
        cancel.setTextSize(18);
        Ok2.setText("Ok");
        Ok2.setTextSize(18);
        cancel2.setText("Cancel");
        cancel2.setTextSize(18);
        Delete.setText("Delete");
        Delete.setTextSize(18);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout popup = new LinearLayout(this);

        popup.setOrientation(LinearLayout.VERTICAL);
        popup.addView(name, params2);
        popup.addView(amount, params2);
        popup.addView(unit, params2);
        popup.addView(Ok,params);
        popup.addView(cancel,params);
        popup.setBackgroundColor(getResources().getColor(R.color.themecolor));

        LinearLayout popup2 = new LinearLayout(this);
        popup2.setOrientation(LinearLayout.VERTICAL);
        popup2.addView(name2, params2);
        popup2.addView(amount2, params2);
        popup2.addView(unit2, params2);
        popup2.addView(Ok2,params);
        popup2.addView(cancel2,params);
        popup2.addView(Delete, params);
        popup2.setBackgroundColor(getResources().getColor(R.color.themecolor));
        window.setContentView(popup);
        window2.setContentView(popup2);


        ImageView add = (ImageView) findViewById(R.id.adding);
        add.setClickable(true);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curname[0]="";
                name.setText("");
                amount.setText("");
                unit.setText("");
                window.showAtLocation(findViewById(R.id.relativeLayout), 0, 500, 500);

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                window2.showAtLocation(findViewById(R.id.relativeLayout), 0,
                        500, 500);
                String ingname = ((TextView) view).getText().toString();
                String n = ingname.split(" ;; ")[0];
                String a = ingname.split(" ;; ")[1];
                String u = ingname.split(" ;; ")[2];
                name2.setText(n);
                amount2.setText(a);
                unit2.setText(u);
                curname[0] = ingname;
            }
        });


        final Activity thiss =this;
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(curname[0].equals(""))) {
                    ing.delete(curname[0]);
                    curname[0] = "";
                }
                if (!(name.getText().toString().equals(""))) {
                    ing.store(name.getText().toString()+" ;; "+ amount.getText().toString()+" ;; "+ unit.getText().toString());
                    try {
                        ing.writeRecipe(thiss);
                    } catch (IOException e) {}
                    window.dismiss();
                }else {
                    Toast.makeText(thishome, "Blank name field", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing.delete(curname[0]);
                try {
                    ing.writeRecipe(thiss);
                } catch (IOException e) {}
                window.dismiss();
                Trans.out((RelativeLayout)findViewById(R.id.relativeLayout), thiss, Ingredients.class);
            }
        });

        Ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(curname[0].equals(""))) {
                    ing.delete(curname[0]);
                    curname[0] = "";
                }
                if (!(name.getText().toString().equals(""))) {
                    ing.store(name2.getText().toString()+" ;; "+ amount2.getText().toString()+" ;; "+ unit2.getText().toString());
                    try {
                        ing.writeRecipe(thiss);
                    } catch (IOException e) {}
                    window.dismiss();
                }else {
                    Toast.makeText(thishome, "Blank name field", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Trans.back(this,(RelativeLayout)findViewById(R.id.root));
    }

}
