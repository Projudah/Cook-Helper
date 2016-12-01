package com.example.projudah.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import static com.example.projudah.cookhelper.R.*;

/**
 * Created by Projudah on 2016-11-24.
 */
public class Myadapter extends ArrayAdapter<String> {
    private int layouts;
    private final Context contexts;
    private String[] objects;

    public Myadapter(Activity context, int resource, String[] objects) {
        super(context, resource, objects);
        this.contexts = context;
        this.objects = objects;
        layouts = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layouts, parent, false );
            holder = new Holder();
            holder.text = (TextView) convertView.findViewById(id.recipetext);
            holder.image = (Spinner) convertView.findViewById(id.pencil);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.text.setText(getItem(position));
        final String name =holder.text.getText().toString();
        holder.image.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setText(null);
                String choice = (String) (parent.getItemAtPosition(position));
                if (choice.equals("Edit")) {
                    parent.setSelection(0);
                }
                if (choice.equals("Delete")) {
                    parent.setSelection(0);
                    Home.Delete(name, (Activity) contexts);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return convertView;
    }

    public class Holder{
        TextView text;
        Spinner image;
    }
}
