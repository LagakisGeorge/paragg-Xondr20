package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;

public class EpiloghEid extends AppCompatActivity {
    GridView moviesList;
    String message;
    GridView prosuGrid;
    public static ArrayList<String> prosu;
    public static  ArrayList<String> EIDH_PARAGG;

    public String[] arr=new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilogh_eid);
        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);
        EIDH_PARAGG = intent.getStringArrayListExtra("mEIDH");

        ListEidh ();

     //   prosu = new ArrayList<String>();
        prosuGrid=(GridView)findViewById(R.id.prosu);


        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // παω να γεμισω την λιστα με την παραγγελια του τραπεζιου
                // διαλεγοντας ενα είδος το ανεβάζει στην λίστα
                // create_order( position);
               // Object o = moviesList.getItemAtPosition(position);
                // pelOrder_Items.add(o.toString() );

                ShowProsu(arr[position]);
            }
        });


        prosuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                EIDH_PARAGG.add("AKOMH ENA");
                Intent intent = new Intent(view.getContext(), order.class);
                // intent.putExtra(EXTRA_MESSAGE, o.toString());
               // intent.putExtra("mpel2", o.toString());
              //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                intent.putExtra("mEIDH", EIDH_PARAGG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
             //   intent.putExtra("mKATHG", KATHG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
                // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ

                EpiloghEid.this.startActivity(intent);




            }
        });




    }





    public void ShowProsu (String c){

        SQLiteDatabase mydatabase=null;
        Integer n=0;
        prosuGrid=(GridView)findViewById(R.id.prosu);
        List<String> values=new ArrayList<>();


      //  prosu.clear();
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            String cc="SELECT ONO,ID FROM XAR1 where ID IN (" + c + "9999)";
            Cursor cursor2 = mydatabase.rawQuery(cc, null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add( cursor2.getString(0));
                  //  prosu.add(cursor2.getString(3));
                  //  arr[n]=cursor2.getString(3);
                } while (cursor2.moveToNext());
            }
            // ArrayAdapter<String> arrayAdapter =
            //        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            //  moviesList.setAdapter(arrayAdapter);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            prosuGrid.setAdapter(arrayAdapter);
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }


    public void ListEidh () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.listmaster);
        List<String> values=new ArrayList<>();


       // prosu.clear();
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            String c="select ONO, ID,TIMH,CH2,PICTURE FROM EIDH WHERE CH1 like '"+message +"%'";
     Cursor cursor2 = mydatabase.rawQuery(c, null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add( cursor2.getString(0));
                   //  prosu.add(cursor2.getString(3));
                       arr[n-1]=cursor2.getString(3);
                } while (cursor2.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }
}



/*               HELP ******************************
    String[] plants = new String[]{
            "Striped alder",
            "Amy root",
            "Arizona sycamore",
            "Green ash",
            "Cherry birch",
            "Gray birch",
            "Mahogany birch",
            "Spice birch",
            "Yellow birch"
    };

    List<String> plantsList = new ArrayList<String>(Arrays.asList(plants));

        /*
            setAdapter (ListAdapter adapter)
                Sets the data behind this GridView.

                Parameters
                adapter : the adapter providing the grid's data
         */
// Data bind GridView with ArrayAdapter (String Array elements)
//        gv.setAdapter(new ArrayAdapter<String>(
//        this, android.R.layout.simple_list_item_1,plantsList));

