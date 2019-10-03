package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;

public class EpiloghEid extends AppCompatActivity {
    GridView moviesList;
    TextView TextProsu;  // π.χ. μετριος,καστανη
    EditText Keim; // π.χ. θελει ψηλο ποτηρι
    String message;
    GridView prosuGrid;
    TextView TextEidos;
    public static ArrayList<String> prosu;
    public static  ArrayList<String> EIDH_PARAGG;
    public int Eidos_position=-1;

    public String[] arr=new String[100];
    public String[] arr_ono=new String[100];
    public String[] arr_timh=new String[100];
    public String[] arr_prosu=new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilogh_eid);
        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);
        String message = intent.getStringExtra("mpel2");  // αριθμος τραπεζιού

        TextView textView = findViewById(R.id.trapezi);
        textView.setText(message); // αριθμος τραπεζιού;idparagg


        EIDH_PARAGG = intent.getStringArrayListExtra("mEIDH");

        ListEidh ();

     //   prosu = new ArrayList<String>();
        prosuGrid=(GridView)findViewById(R.id.prosu);
        TextProsu=(TextView)findViewById(R.id.textView4);  // π.χ. μετριος,καστανη
        TextEidos=(TextView)findViewById(R.id.TextEidos);
         Keim=(EditText)findViewById(R.id.editText2); // π.χ. θελει ψηλο ποτηρι

        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // exo tsimpisei to eidos kai pao na dixo to prosueta
                // arr{position] exei ta prostheta toy eidoys 1;2;4

                Eidos_position=position;  // κραταω την θέση του είδους γιατι μπορει να παρει άλλο είδος
                TextEidos.setText(arr_ono[Eidos_position].toString());
                ShowProsu(arr[position]);

            }
        });


        prosuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //EIDH_PARAGG.add("AKOMH ENA");

                TextProsu.setText(TextProsu.getText().toString()+","+arr_prosu[position]);




            }
        });




    }

    // σβηνω τις επιλογές πρόσθετων
    public void Akyro_Prosu (View view){
        TextProsu=(TextView)findViewById(R.id.textView4);  // π.χ. μετριος,καστανη

        TextProsu.setText("");

    }



    // στελνει το ειδος στο intent order
    public void SendEidos (View view){

        EIDH_PARAGG.add(arr_ono[Eidos_position]);
        EIDH_PARAGG.add("1");
        EIDH_PARAGG.add(arr_timh[Eidos_position]);




        String Q=TextProsu.getText().toString();
        EIDH_PARAGG.add(Q);
        Q=Keim.getText().toString();
        EIDH_PARAGG.add(Q);
        EIDH_PARAGG.add("---");
        Intent intent = new Intent(view.getContext(), order.class);
        // intent.putExtra(EXTRA_MESSAGE, o.toString());
        // intent.putExtra("mpel2", o.toString());
        //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
        intent.putExtra("mEIDH", EIDH_PARAGG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        //   intent.putExtra("mKATHG", KATHG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ

        TextView textView = findViewById(R.id.trapezi);
        String message=textView.getText().toString(); // αριθμος τραπεζιού
        intent.putExtra("mpel2", message); // ΣΤΕΛΝΩ ΤΟ  ΤΡΑΠΕΖΙ


        EpiloghEid.this.startActivity(intent);

    }


    public void ShowProsu (String c){

        SQLiteDatabase mydatabase=null;
        Integer n=0;
        prosuGrid=(GridView)findViewById(R.id.prosu);
       final List<String> values=new ArrayList<>();


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
                    arr_prosu[n-1]=cursor2.getString(0);
                  //  arr[n]=cursor2.getString(3);
                } while (cursor2.moveToNext());
            }
            // ArrayAdapter<String> arrayAdapter =
            //        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            //  moviesList.setAdapter(arrayAdapter);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values)

// arxh  αυτο το κομματι βαζει πλαισια στο gridview
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


                            Resources r = EpiloghEid.this.getResources();
                            int  px = (int) (TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 168, r.getDisplayMetrics()));



                            params.width = px;  // getPixelsFromDPs(EpiloghEid.this,168);

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
// telos αυτο το κομματι βαζει πλαισια στο gridview


























            ;
            prosuGrid.setAdapter(arrayAdapter);
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }


    public void ListEidh () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.listmaster);

      //  moviesList.setBackgroundColor();
      //  moviesList.setVerticalSpacing(1);
      //  moviesList.setHorizontalSpacing(1);




       final List<String> values=new ArrayList<>();


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
                       arr_ono[n-1]=cursor2.getString(0);
                       arr_timh[n-1]=Float.toString(cursor2.getFloat(2));


                } while (cursor2.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values)


                            // arxh  αυτο το κομματι βαζει πλαισια στο gridview
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


                            Resources r = EpiloghEid.this.getResources();
                            int  px = (int) (TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 168, r.getDisplayMetrics()));



                            params.width = px;  // getPixelsFromDPs(EpiloghEid.this,168);

                            // Set the TextView layout parameters
                            tv.setLayoutParams(params);

                            // Display TextView text in center position
                            tv.setGravity(Gravity.CENTER);

                            // Set the TextView text font family and text size
                            tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

                            // Set the TextView text (GridView item text)
                            tv.setText(values.get(position));

                            // Set the TextView background color
                            tv.setBackgroundColor(Color.parseColor("#CDDC39"));

                            // Return the TextView widget as GridView item
                            return tv;
                        }
                    };
// telos  αυτο το κομματι βαζει πλαισια στο gridview




    //More android examples
    //How to use GridView OnItemClickLi
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

