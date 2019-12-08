package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class KATHGORIES extends AppCompatActivity {
    GridView moviesList;
    GridView prosueta;
    public ArrayList<String> values;

    public String fID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        // ΔΙΑΛΕΓΩ ΤΟ ΤΡΑΠΕΖΙ ΠΟΥ ΘΕΛΩ
        moviesList=(GridView)findViewById(R.id.listEidhp);
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {




                // ΟΝΟΜΑ
                int mID2 ;
                mID2=position - position%2+1;
                Object o2 = moviesList.getItemAtPosition(mID2);

                EditText t2=findViewById(R.id.t2);
                t2.setText(o2.toString());


                // ΙD
                int mID3 ;
                mID3=position - position%2;
                Object o3 = moviesList.getItemAtPosition(mID3);
                fID=o3.toString();







                // String[] separated3 = o.toString().split("€");
                // String cTable= separated3[0];  // #52

            }
        });

        Show();
    }
    public void SAVE (View view) {

        EditText t2=findViewById(R.id.t2);

        SQLiteDatabase mydatabase=null;

        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        mydatabase.execSQL("update XAR1 set ONO='"+t2.getText()+"'  where ID="+fID);

        Show();
    }

    public void NEO_EIDOS (View view) {
        EditText t1=findViewById(R.id.t1);
        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);
        SQLiteDatabase mydatabase=null;

        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        String sql;
        sql="INSERT INTO  XAR1 ( ONO) VALUES ('"+t2.getText()+"');";
        mydatabase.execSQL(sql);
        Show();
    }

    public void DELETE (View view) {
        EditText t1 = findViewById(R.id.t1);
        EditText t2 = findViewById(R.id.t2);
        EditText t4 = findViewById(R.id.t4);
        SQLiteDatabase mydatabase = null;

        mydatabase = openOrCreateDatabase("eidh", MODE_PRIVATE, null);
        mydatabase.execSQL("DELETE FROM  EIDH  where ID=" + fID);

        Show();
    }


    public void Show () {

        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.listEidhp);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        //   List<String> values=new ArrayList<>();
        values=new ArrayList<String>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ID,ONO  from  XAR1", null);
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    values.add( Integer.toString(cursor2.getInt(0) ));
                    values.add(cursor2.getString(1));

                } while (cursor2.moveToNext());
            }
            mydatabase.close();
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);








            // TrapeziaList.setAdapter(arrayAdapter);

            //final MyAdapter adapter = new MyAdapter();
            //  rv.setAdapter(adapter);
            //   GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            //  rv.setLayoutManager(mLayoutManager);

// set up the RecyclerView
            //   RecyclerView recyclerView = findViewById(R.id.rvAnimals);
            //    TrapeziaList.setLayoutManager(new LinearLayoutManager(this));
            //   adapter = new MyRecyclerViewAdapter(this, animalNames);
            //   adapter.setClickListener(this);
            //   recyclerView.setAdapter(adapter);

        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }




}
