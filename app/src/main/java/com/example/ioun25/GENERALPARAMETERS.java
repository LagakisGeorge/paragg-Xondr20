package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GENERALPARAMETERS extends AppCompatActivity {
    GridView moviesList;
    GridView prosueta;
    public ArrayList<String> values;
     public Button tropos;
    public String fID;
    public Double[] sumes=new Double[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalparameters);

     //   MainActivity.idBardia=trapezia.ReadSql("select MAX(id) AS CID  from BARDIA");

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
        }

        mydatabase.close();



   tropos=findViewById(R.id.list_tropos);
   tropos.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     list_tropos_pliromis();
                                 }
                             });


           // ΔΙΑΛΕΓΩ ΤΟ ΤΡΑΠΕΖΙ ΠΟΥ ΘΕΛΩ

           moviesList = (GridView) findViewById(R.id.listEidhp);
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


    public void list_tropos_pliromis (){

        moviesList=(GridView)findViewById(R.id.listEidhp);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        final List<String> values=new ArrayList<>();




        try{
            SQLiteDatabase mydatabase=null;

            values.add("τραπεζι");
            values.add("ωρα");
            values.add("πληρωμη");
            values.add("Αξία");


            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);

            if (MainActivity.idBardia.equals("0")){
                Cursor cursor = mydatabase.rawQuery("select MAX(id) AS CID  from BARDIA",null);
                if (cursor.moveToFirst()) {
                    MainActivity.idBardia=Integer.toString(cursor.getInt(0));
                }
            }


            Double Gen=0.0;
            Cursor cursor3 = mydatabase.rawQuery("select sum(ajia) as syn,tropos   from  PARAGGMASTER  where IDBARDIA="+MainActivity.idBardia+"  group by tropos", null);

            if (cursor3.moveToFirst()) {
                do {



                            values.add("");
                            values.add("");

                        values.add(Integer.toString(cursor3.getInt(1)));
                        values.add(Double.toString(cursor3.getDouble(0)));
                        Gen=Gen+cursor3.getDouble(0);


                } while (cursor3.moveToNext());
            }

            values.add("-----");
            values.add("-----");
            values.add("Συνολο");
            values.add(Double.toString(Gen));




            Cursor cursor2 = mydatabase.rawQuery("select TRAPEZI,SUBSTR(ch2,11,6),tropos,ajia,id  from  PARAGGMASTER where IDBARDIA="+MainActivity.idBardia+" order by TROPOS", null);

            if (cursor2.moveToFirst()) {
                do {

                    for(int i = 0; i<4;i++)  {

                        //    int index = c.getColumnIndex("description");
                        String str = cursor2.getString(i);
                        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                            values.add("");
                        } else {
                            values.add(str);
                        }


                    }

                } while (cursor2.moveToNext());
            }
            mydatabase.close();
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values)
                            //-------------------- arxh  αυτο το κομματι βαζει πλαισια στο gridview
                    {
                        public View getView(int position, View convertView, ViewGroup parent) {

                            // Return the GridView current item as a View
                            View view = super.getView(position,convertView,parent);

                            // Convert the view as a TextView widget
                            TextView tv = (TextView) view;

                            //tv.setTextColor(Color.DKGRAY);

                            // Set the layout parameters for TextView widget
                            RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                            );
                            tv.setLayoutParams(lp);

                            // Get the TextView LayoutParams
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                            // Set the width of TextView widget (item of GridView)
                /*
                    IMPORTANT
                        Adjust the TextView widget width depending
                        on GridView width and number of columns.

                        GridView width / Number of columns = TextView width.

                        Also calculate the GridView padding, margins, vertical spacing
                        and horizontal spacing.
                 */


                            Resources r = GENERALPARAMETERS.this.getResources();
                            int  px = (int) (TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
                            // tv.setLayoutParams(new GridView.LayoutParams((width/10)*6, 50));

                            // if (position==0 || position==5) {
                            // params.width = px/2;  // getPixelsFromDPs(EpiloghEid.this,168);
                            //   tv.setLayoutParams(new GridView.LayoutParams((px*6), 100));
                            // }else{
                            params.width = px;  // getPixelsFromDPs(EpiloghEid.this,168);
                            // }


                            // Set the TextView layout parameters
                            tv.setLayoutParams(params);

                            // Display TextView text in center position
                            tv.setGravity(Gravity.CENTER);

                            // Set the TextView text font family and text size
                            tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

                            // Set the TextView text (GridView item text)
                            tv.setText(values.get(position));

                            // Set the TextView background color
                            tv.setBackgroundColor(Color.parseColor("#d9d5dc"));

                            // Return the TextView widget as GridView item
                            return tv;
                        }
                    };
            ;
            moviesList.setAdapter(arrayAdapter);

        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
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
