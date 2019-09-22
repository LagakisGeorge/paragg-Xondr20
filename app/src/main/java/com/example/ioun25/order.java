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
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;
import static java.util.jar.Pack200.Packer.PASS;

public class order extends AppCompatActivity {

    ArrayList<String> pel;
    ArrayList<String> EIDH_PARAGG; // ερχεται απο το trapezia class ΑΔΕΙΟς αλλα δεν το χρειαζομαι
                            // το χρησιμοποιω για τα είδη παραγγελίας τα οποία τα γεμιζω στο EpiloghEid
    ArrayList<String> KATHG;
    GridView moviesList;
    GridView kathgGrid;
    GridView Paragg;

    Handler handler2;
  public final  List<String> pelOrder_Items=new ArrayList<>();// pelOrder_Items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        String message = intent.getStringExtra("mpel2");
        pel = intent.getStringArrayListExtra("mpel");
        EIDH_PARAGG = intent.getStringArrayListExtra("mEIDH");
        KATHG = intent.getStringArrayListExtra("mKATHG");
       // pelOrder_Items = new ArrayList<String>();

      //  pel = intent.getStringArrayListExtra("mpel");
       // pel = intent.getStringArrayListExtra("mpel");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView3);
        textView.setText(message);

        ListEidh ();

// γεμισμα της λιστας ειδών
     /*   List<String> values=new ArrayList<>();
        for (int i = 0; i < EIDH.size(); i++) {
            values.add(EIDH.get(i));
        }
        moviesList=(GridView)findViewById(R.id.listmaster);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
        moviesList.setAdapter(arrayAdapter);
     */
        create_kathg();

        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // todo
                TextView h;
                h =(TextView)findViewById(R.id.hello);
            //    h.setText("*"+Pelatis);
              //  for (int i = 0; i < pel.size(); i++) {
                //    System.out.println("πελατης"+pel.get(i));
              //  }
                return true;
            }
        });


        // δειχνει τα ειδη σε αλλο intent για να διαλεξω
        kathgGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // παω να γεμισω την λιστα με την παραγγελια του τραπεζιου
                // διαλεγοντας ενα είδος το ανεβάζει στην λίστα
                // create_order( position);
                Object o = kathgGrid.getItemAtPosition(position);
               // pelOrder_Items.add(o.toString() );

                ShowDisplay(o.toString());

            }
        });


        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // παω να γεμισω την λιστα με την παραγγελια του τραπεζιου
                // διαλεγοντας ενα είδος το ανεβάζει στην λίστα
                create_order( position);

             //   ShowDisplay();

            }
        });


     //   EIDH_PARAGG.add("ΝΕΑ ΠΑΡΑΓΓΕΛΙΑ");

        Paragg=(GridView)findViewById(R.id.listdetail);
        ArrayAdapter<String> OarrayAdapter;
        OarrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EIDH_PARAGG)
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


                Resources r = order.this.getResources();
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
                tv.setText(EIDH_PARAGG.get(position));

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#d9d5dc"));

                // Return the TextView widget as GridView item
                return tv;
            }
        };
// telos αυτο το κομματι βαζει πλαισια στο gridview









        //  ArrayAdapter<String> arrayAdapter =
        //      new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Order_Items);

        Paragg.setAdapter(OarrayAdapter);




    }

// ΠΑΛΙΟ alla xreiazetai?  ΕΧΕΙ ΜΕΤΑΦΕΡΘΕΙ ΣΤΟ ΙΝΤΕΝΤ EPILOGHEID
    public void ListEidh () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.listmaster);
        List<String> values=new ArrayList<>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO,TIMH,KATHG from  EIDH", null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add( cursor2.getString(0));

                } while (cursor2.moveToNext());
            }

           // ArrayAdapter<String> arrayAdapter =
            //        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

          //  moviesList.setAdapter(arrayAdapter);


            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);








        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }






// γεμίζει το  kathgGrid απο το sqllite->KATHG
    public void  create_kathg(){

        // γεμισμα της λιστας κατηγοριών
     /*   List<String> values2 = new ArrayList<>();
        for (int i = 0; i < KATHG.size(); i++) {
            values2.add(KATHG.get(i));
        }
        kathgGrid=(GridView)findViewById(R.id.listKathg);
        ArrayAdapter<String> arrayAdapter2 =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values2);
        kathgGrid.setAdapter(arrayAdapter2);
      */

        SQLiteDatabase mydatabase=null;
        Integer n=0;
        kathgGrid=(GridView)findViewById(R.id.listKathg);
        List<String> values=new ArrayList<>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO  from  KATHG", null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add( cursor2.getString(0));

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);



                           

/*
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

                  //  IMPORTANT
                    //    Adjust the TextView widget width depending
                    //    on GridView width and number of columns.

                    //    GridView width / Number of columns = TextView width.

                    //    Also calculate the GridView padding, margins, vertical spacing
                    //    and horizontal spacing.



                            Resources r = order.this.getResources();
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
                            tv.setText(EIDH_PARAGG.get(position));

                            // Set the TextView background color
                            tv.setBackgroundColor(Color.parseColor("#d9d5dc"));

                            // Return the TextView widget as GridView item
                            return tv;
                        }
                    };
    */
// telos αυτο το κομματι βαζει πλαισια στο gridview














            kathgGrid.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }




// ανεβαζει στο Paragg apo to ton pinaka pelOrder_Items
    public void create_order (int position) {


        Paragg=(GridView)findViewById(R.id.listdetail);

        Object o = moviesList.getItemAtPosition(position);
        pelOrder_Items.add(o.toString() );

        //  textView.setText(message);
        // List<String> Ovalues=new ArrayList<>();



        //     for (int i = 0; i < Order_Items.size(); i++) {
        //         Ovalues.add(Order_Items.get(i));
        //     }



        ArrayAdapter<String> OarrayAdapter;
        OarrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pelOrder_Items);

        //  ArrayAdapter<String> arrayAdapter =
        //      new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Order_Items);

        Paragg.setAdapter(OarrayAdapter);
    }


// σώζει την παραγγελια σε sqlserver
    public void SAVE_ORDER (View view) {

        pel.clear();

        Runnable aRunnable = new Runnable() {
            public void run() {
                execData("insert into PARAGG (TRAPEZI,HME) VALUES (52,GETDATE())");
            }
        };

        Thread aThread = new Thread(aRunnable);
        aThread.start();

        //while ( bT.getText().toString()=="*"){
        android.os.SystemClock.sleep(1000);
        // };
        Toast.makeText(getApplicationContext(), "OK ENHMEROTHIKE", Toast.LENGTH_SHORT).show();

    }



    // private String URL = "jdbc:jtds:sqlserver://192.168.1.5:52735/BAR;instance=SQLEXPRESS;";
     private String URL = "jdbc:jtds:sqlserver://192.168.1.7:49702/BAR;instance=SQLEXPRESS;";
    private String USER = "sa";
  //  private String PASS = "12345678";  //"p@ssw0rd";
    private String PASS = "p@ssw0rd";

    private static ResultSet RESULT;



    public void execData(String query) {
        Connection CON = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            CON = DriverManager.getConnection(URL, USER, PASS);
            CON.createStatement().executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    };

   // δειχνει τα ειδη σε αλλο intent για να διαλεξω
    public void ShowDisplay(String cKathg) {
        Intent intent = new Intent(this, EpiloghEid.class);
     //   intent.putExtra("arrayListExtra", mArray);
     //   intent.putExtra("stringExtra", mString);
     //   intent.putExtra("intExtra", mValue);
       // String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, cKathg);
        intent.putExtra("mEIDH", EIDH_PARAGG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΗΝ ΤΡΕΧΟΥΣΑ ΠΑΡΑΓΓΕΛΙΑ
        startActivity(intent);

    };
}
