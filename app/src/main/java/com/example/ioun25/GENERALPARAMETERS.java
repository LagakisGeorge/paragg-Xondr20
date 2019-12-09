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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GENERALPARAMETERS extends AppCompatActivity {
    GridView moviesList;
    GridView prosueta;
    public ArrayList<String> values;

    public String fID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalparameters);



        EditText t1=findViewById(R.id.t1);
        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);

        SQLiteDatabase mydatabase=null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        Cursor cursor2 = mydatabase.rawQuery("select IPPRINTER,IPSQL,ACCESSCODE  from  MEM", null);

        if (cursor2.moveToFirst()) {


            fID=cursor2.getString(2);
            t2.setText(cursor2.getString(0));  // printer
            t4.setText(cursor2.getString(1));  // sql





            /*do {
                for(int i = 0; i<5;i++)  {
                    //    int index = c.getColumnIndex("description");
                    String str = cursor2.getString(i);
                    if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                        values.add("");
                    } else {
                        values.add(str);
                    }
                }
            } while (cursor2.moveToNext());
       */
        }

        mydatabase.close();












        // ΔΙΑΛΕΓΩ ΤΟ ΤΡΑΠΕΖΙ ΠΟΥ ΘΕΛΩ

        moviesList=(GridView)findViewById(R.id.listEidhp);
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                // ΤΙΜΗ
                int mID ;
                mID=position - position%4+2;
                Object o = moviesList.getItemAtPosition(mID);
                EditText t1=findViewById(R.id.t1);
                t1.setText(o.toString());


                // ΟΝΟΜΑ
                int mID2 ;
                mID2=position - position%4+1;
                Object o2 = moviesList.getItemAtPosition(mID2);

                EditText t2=findViewById(R.id.t2);
                t2.setText(o2.toString());


                // ΙD
                int mID3 ;
                mID3=position - position%4;
                Object o3 = moviesList.getItemAtPosition(mID3);
                fID=o3.toString();

                // ΠΡΟΣΘΕΤΑ
                int mID4 ;
                mID4=position - position%4+3;
                Object o4 = moviesList.getItemAtPosition(mID4);

                EditText t4=findViewById(R.id.t4);
                t4.setText(o4.toString());
            }
        });

        // Show();
    }

   // αποθηκευση ΙΡ
    public void SAVE (View view) {
        EditText t1=findViewById(R.id.t1);
        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);


        // εαν ειναι σωστος ο κωδικος
        if (t1.getText().toString().equals(fID)) {


            SQLiteDatabase mydatabase = null;

            mydatabase = openOrCreateDatabase("eidh", MODE_PRIVATE, null);
            mydatabase.execSQL("update MEM set IPPRINTER='" + t2.getText() + "',IPSQL='" + t4.getText() + "'  where ID=1");
            mydatabase.close();
            Toast.makeText(getApplicationContext(), "OK αποθηκεύθηκε", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Λάθος κωδικός πρόσβασης", Toast.LENGTH_SHORT).show();
        }
    }


    // ενεργοποιηση αλλαγης κωδικου
    public void NEO_EIDOS (View view) {
        TextView ip1=findViewById(R.id.ip1);
        TextView ip2=findViewById(R.id.ip2);

        ip1.setText("Νέος Κωδικός");
        ip2.setText("Επιβεβαίωση Κωδικού");
        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);

        t2.setText("");
        t4.setText("");


   //     SQLiteDatabase mydatabase=null;

     //   mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
     //   String sql;
      //  sql="INSERT INTO  EIDH ( timh,ONO,CH2) VALUES ("+t1.getText()+",'"+t2.getText()+"','"+t4.getText()+"');";
      //  mydatabase.execSQL(sql);
      //  Show();
    }


    // αποθηκευση νεου κωδικου
    public void DELETE (View view) {
        EditText t1 = findViewById(R.id.t1);
        EditText t2 = findViewById(R.id.t2);
        EditText t4 = findViewById(R.id.t4);



        // εαν ειναι σωστος ο κωδικος
        if (t1.getText().toString().equals(fID)) {

            if (t2.getText().toString().equals(t4.getText().toString())) {

                SQLiteDatabase mydatabase = null;
                mydatabase = openOrCreateDatabase("eidh", MODE_PRIVATE, null);
                mydatabase.execSQL("update MEM set ACCESSCODE='" + t2.getText() + "'   where ID=1");
                mydatabase.close();
                Toast.makeText(getApplicationContext(), "OK αποθηκεύθηκε", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(), "Δεν συμφωνεί ο νέος κωδικός", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(getApplicationContext(), "Λάθος παλιός κωδικός πρόσβασης", Toast.LENGTH_SHORT).show();
        }




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
            Cursor cursor2 = mydatabase.rawQuery("select ID,ONO,TIMH,CH2  from  EIDH", null);
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    values.add( Integer.toString(cursor2.getInt(0) ));
                    values.add(cursor2.getString(1));
                    values.add( Double.toString(cursor2.getDouble(2) ));
                    values.add(cursor2.getString(3));
                } while (cursor2.moveToNext());
            }
            mydatabase.close();
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);

        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }






}
