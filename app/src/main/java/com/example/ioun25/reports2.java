package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class reports2 extends AppCompatActivity {
    GridView moviesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports2);
    }


    public void show_ParaggMaster(View view){
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.grid);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        final  List<String> values=new ArrayList<>();

     /*   "[IDERGAZ] [int] ,"+
                "[HME] [datetime] ,"+
                "[IDBARDIA] [int] ,"+
                "[AJIA] [real] ,"+
                "[TROPOS] [int] ,"+
                "[NUM1] [int] ,"+
                "[NUM2] [int] ,"+
                "[CH1] [nvarchar](255) ,"+
                "[CH2] [nvarchar](255) )");

                System.out.print("ID: "+rs.getInt("ID")+", ");
      System.out.print("Name: "+rs.getString("First_Name")+", ");
      System.out.print("Age: "+rs.getString("Last_Name")+", ");
      System.out.print("Salary: "+rs.getDate("Date_Of_Birth")+", ");



     */
        try{


            values.add("τραπεζι");
            values.add("ωρα");
            values.add("πληρωμη");
            values.add("Αία");
            values.add("ID ΠΑΡΑΓΓ");


            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select TRAPEZI,SUBSTR(ch2,11,6),tropos,ajia,id  from  PARAGGMASTER", null);

            if (cursor2.moveToFirst()) {
                do {

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


                            Resources r = reports2.this.getResources();
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


    public void show_Paragg(View view){
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.grid);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        final  List<String> values=new ArrayList<>();

     /*   "[IDERGAZ] [int] ,"+
                "[HME] [datetime] ,"+
                "[IDBARDIA] [int] ,"+
                "[AJIA] [real] ,"+
                "[TROPOS] [int] ,"+
                "[NUM1] [int] ,"+
                "[NUM2] [int] ,"+
                "[CH1] [nvarchar](255) ,"+
                "[CH2] [nvarchar](255) )");

                System.out.print("ID: "+rs.getInt("ID")+", ");
      System.out.print("Name: "+rs.getString("First_Name")+", ");
      System.out.print("Age: "+rs.getString("Last_Name")+", ");
      System.out.print("Salary: "+rs.getDate("Date_Of_Birth")+", ");



     */
        try{
            values.add("ΕΙΔΟΣ");
            values.add("ΠΡΟΣΘ");
            values.add("ΠΟΣΟΤΗΤΑ");
            values.add("ΤΙΜΗ");
            values.add("ID ΠΑΡΑΓΓ");
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ono,prosueta,poso,timh,idparagg from  PARAGG order by idparagg desc", null);

            if (cursor2.moveToFirst()) {
                do {

                    for(int i = 0; i<5;i++)  {

                        //    int index = c.getColumnIndex("description");
                        String str = cursor2.getString(i);
                        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                            values.add("");
                        } else {
                            values.add(str);
                        }




                        // values.add(cursor2.getString(i));
                        // values.add(cursor2.getString(1));
                        // values.add(cursor2.getString(2));
                        // values.add(cursor2.getString(3));
                        // values.add(cursor2.getString(4));



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


                            Resources r = reports2.this.getResources();
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




}
