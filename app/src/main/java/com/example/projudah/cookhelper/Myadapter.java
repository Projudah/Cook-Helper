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
        Holder holder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layouts, parent, false );
            holder = new Holder();
            holder.text = (TextView) convertView.findViewById(id.recipetext);
            holder.image = (Spinner) convertView.findViewById(id.pencil);
            holder.image.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView)view).setText(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.text.setText(getItem(position));
        return convertView;
    }

    public class Holder{
        TextView text;
        Spinner image;
    }
}
