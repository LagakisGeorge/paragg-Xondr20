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
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.view.Menu;
import android.view.MenuItem;

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
    // private String URL = "jdbc:jtds:sqlserver://192.168.1.5:52735/BAR;instance=SQLEXPRESS;";
    private String URL = "jdbc:jtds:sqlserver://192.168.1.7:49702/BAR;instance=SQLEXPRESS;";
    private String USER = "sa";
    //  private String PASS = "12345678";  //"p@ssw0rd";
    private String PASS = "p@ssw0rd";

    private static ResultSet RESULT;
    private static String fIDPARAGG;  // ιδ παραγγελιασ
    private static int  fYparxSeires;  // ποσες σειρες υπαρχουν στην ηδη υπάρχουσα παραγγελια

    Handler handler2;
  public final  List<String> pelOrder_Items=new ArrayList<>();// pelOrder_Items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        String Who;
        Intent intent = getIntent();
        String message = intent.getStringExtra("mpel2");  // αριθμος τραπεζιού
        Who = intent.getStringExtra("WhoCall");

        pel = intent.getStringArrayListExtra("mpel");
        EIDH_PARAGG = intent.getStringArrayListExtra("mEIDH");
        KATHG = intent.getStringArrayListExtra("mKATHG");




if (null==EIDH_PARAGG) {
    fYparxSeires=0;
}else{
    fYparxSeires= EIDH_PARAGG.size();
}



        // κραταω αυτο που ερχεται απο το trapazia μονο που κόβω το αστερακι για να μην  ξαναδιαβαζεται οταν ερχεται απο το epilogheid  δηλαδη ανα ερχεται *62;1200  => 62;1200
        String message2;
        message2=message;
        if (message2.substring(0, 1).equals("#")) {
            String[] separated3 = message2.split("#");
            message2 = separated3[1];

        }
        TextView Trapezi_idparagg = findViewById(R.id.textView32); //   *51;1256   trapezi;idparagg
        Trapezi_idparagg.setText(message2);




        // pelOrder_Items = new ArrayList<String>();

      //  pel = intent.getStringArrayListExtra("mpel");
       // pel = intent.getStringArrayListExtra("mpel");
        // Capture the layout's TextView and set the string as its text


        /*=============  split ============================
String currentString = "Fruit: they taste good";
String[] separated = currentString.split(":");
separated[0]; // this will contain "Fruit"
separated[1]; // this will contain " they taste good"
    */


        String[] separated = message.split(";");
        message=separated[0];
        fIDPARAGG=separated[1];


        TextView textView = findViewById(R.id.textView3);
        textView.setText(message); // αριθμος τραπεζιού
     //   String hallostring = "hallo";
     //   String asubstring = hallostring.substring(0, 1);


     //   boolean resultOfComparison=stringA.equals(stringB);
        fYparxSeires=0;
       if (message.substring(0, 1).equals("#")){

           String[] separated2 = message.split("#");
           message=separated[1];
              LoadYparxoysa(message,fIDPARAGG);
       } else{

           if (Who.equals("trapezia")){
               EIDH_PARAGG=new ArrayList<String>();


           }
       }
        moviesList=(GridView)findViewById(R.id.listmaster);

       // γεμισμα της λιστας ειδώ
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

                ShowDisplay(o.toString());  // ονομα κατηγοριας

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



       // if(fYparxSeires==0){
        //    EIDH_PARAGG=new ArrayList<String>();
       //     return ;
       // };

     //   EIDH_PARAGG.add("ΝΕΑ ΠΑΡΑΓΓΕΛΙΑ");

        Paragg=(GridView)findViewById(R.id.listdetail);
        ArrayAdapter<String> OarrayAdapter;





        OarrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EIDH_PARAGG)
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


                Resources r = order.this.getResources();
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


/*
   // @Override
    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
       // return true;
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.search_item:
                // do your code
                return true;
            case R.id.upload_item:
                // do your code
                return true;
            case R.id.copy_item:
                // do your code
                return true;
            case R.id.print_item:
                // do your code
                return true;
            case R.id.share_item:
                // do your code
                return true;
            case R.id.bookmark_item:
                // do your code
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
       final List<String> values=new ArrayList<>();

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
                            tv.setText(values.get(position));

                            // Set the TextView background color
                            tv.setBackgroundColor(Color.parseColor("#CDDC39"));

                            // Return the TextView widget as GridView item
                            return tv;
                        }
                    };

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


// σώζει την παραγγελια σε sqlιτε   //server
    public void SAVE_ORDER (View view) {


        // --------------------- ok sql server save ----------------------------------------------

      /*  pel.clear();
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
      */




  //   apo vbnet :   ExecuteSQLQuery("INSERT INTO PARAGGMASTER (TRAPEZI,HME,IDBARDIA,CH1) VALUES ('" + p_Trapezi + "'," + MDATE + "," //+ Str(gBardia) + ",'" + Format(Now(), "hh:mm") + "' )", DT)
   //     'ExecuteSQLQuery("UPDATE PARAGGMASTER SET CH2='" + Format(Now(), "hh:mm") + "',AJIA=" + SS + ",TROPOS=" + F.LoginName + " WHERE ID=" + p_IDPARAGG, DT)

  //      ExecuteSQLQuery("SELECT MAX(ID) FROM PARAGGMASTER", DT)
    //    Dim N As Integer = DT(0)(0)
      //  Dim pr As String = ""






        //-----------------------------------  sql lite save order --------------------------
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);

        TextView textView = findViewById(R.id.textView3);
        String tr=textView.getText().toString(); // αριθμος τραπεζιού


        String s = "0";  // αριθμος παργγελιας
        String Q;
        // αν ειναι νέα παραγγελία
        if (fYparxSeires ==0) {
            Q = "INSERT INTO PARAGGMASTER (TRAPEZI,IDBARDIA,CH1) VALUES ('" + tr + "',1,datetime('now','localtime'))";

            mydatabase.execSQL(Q);

            Cursor cursor5 = mydatabase.rawQuery("select max(ID) from PARAGGMASTER ", null);
            // long n=0;
            // looping through all rows and adding to list

            if (cursor5.moveToFirst()) {
                do {
                    s = cursor5.getString(0);
                    // n= Integer.parseInt(cursor5.
                    // movies.add(cursor.getString(0));
                } while (cursor5.moveToNext());
            }
        }else{
            s=fIDPARAGG;
        }




        for(int i = fYparxSeires; i<EIDH_PARAGG.size();i=i+5)//
        {
            //String Q;
            Q="INSERT INTO PARAGG (IDPARAGG,TRAPEZI,ONO,POSO,TIMH,PROSUETA,CH2) VALUES ("+s+",'"+tr+"','"+ EIDH_PARAGG.get(i)+"',";
    Q=Q+ EIDH_PARAGG.get(i+1)+","+ EIDH_PARAGG.get(i+2)+",'"+ EIDH_PARAGG.get(i+3)+"','"+EIDH_PARAGG.get(i+4)+"');";

            mydatabase.execSQL(Q);

        }


        mydatabase.execSQL("UPDATE TABLES SET KATEILHMENO=1,IDPARAGG=" + s + " WHERE ONO='" + tr + "'"   );


        mydatabase.close();
        //-----------------------------------


        Intent intent = new Intent(this, trapezia.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
     //   String message2 ="---" ;// EditText.GetText().toString();
      //  intent.putExtra(EXTRA_MESSAGE, message2);
      //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
      //  intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
      //  intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);



      /*  mydatabase.execSQL("CREATE TABLE IF NOT EXISTS PARAGG ("+
                "[TRAPEZI] [varchar](55),"+
                "[HME] [datetime] ,"+
                "[IDPARAGG] [int] ,"+
                "[KOD] [nvarchar](55) ,"+
                "[POSO] [real] ,"+
                "[TIMH] [real] ,"+
                "[ONO] [varchar](55) ,"+
                "[PROSUETA] [varchar](55) ,"+
                "[CH1] [varchar](55) ,"+
                "[CH2] [varchar](55) ,"+
                "[NUM1] [int] ,"+
                "[NUM2] [int] ,"+
                "[ENERGOS] [int] ,"+
                "[KERASMENOAPO] [varchar](55) ,"+
                "[KERASMENOSE] [varchar](55) ,"+
                "[ID]  INTEGER PRIMARY KEY )");
      */

    }


// ΦΟΡΤΩΝΩ ΤΗΝ ΗΔΗ ΥΠΑΡΧΟΥΣΑ ΠΑΡΑΓΓΕΛΙΑ
    public void LoadYparxoysa (String tr,String IDPARAGG) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.listmaster);
      //  final List<String> values=new ArrayList<>();
        Paragg=(GridView)findViewById(R.id.listdetail);
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);



            // βρισκω τον αριθμο παραγγελιας idparagg
            TextView textView = findViewById(R.id.textView3);
         //   String tr=textView.getText().toString(); // αριθμος τραπεζιού

         /*   String idparagg="00";
            Cursor cursor1 = mydatabase.rawQuery("select IDPARAGG from  TABLES WHERE ONO='"+tr+"'", null);
            if (cursor1.moveToFirst()) {
                do {
                    n++;
                    idparagg= cursor1.getString(0);
                } while (cursor1.moveToNext());
            }
            mydatabase.close();

            int in = Integer.valueOf(idparagg);
            if (in==0) {
                return;
            }


            if (idparagg==null) {
                idparagg = "00";
            }else
            {
                double number = Double.parseDouble(idparagg);
                if (number==0) {
                    idparagg = "00";
                }
            }
         */



















            EIDH_PARAGG=new ArrayList<String>();

          //  EIDH_PARAGG.clear();

            Cursor cursor2 = mydatabase.rawQuery("select ONO,POSO,TIMH,PROSUETA,CH2 from  PARAGG where idparagg="+IDPARAGG, null);

            if (cursor2.moveToFirst()) {
                while (!cursor2.isAfterLast()) {
                    n++;
                    EIDH_PARAGG.add( cursor2.getString(0));
                    EIDH_PARAGG.add( cursor2.getString(1));
                    EIDH_PARAGG.add( cursor2.getString(2));
                    EIDH_PARAGG.add( cursor2.getString(3));
                    EIDH_PARAGG.add( cursor2.getString(4));
                   // EIDH_PARAGG.add( "");
                  //  fIDPARAGG=fIDPARAGG+5;  // υπαρχουσες εγγραφες
                    fYparxSeires=fYparxSeires+5;

                    cursor2.moveToNext();
                }
            }




           /* if (cursor2.moveToFirst()) {
                do {
                    n++;
                    EIDH_PARAGG.add( cursor2.getString(0));
                    EIDH_PARAGG.add( cursor2.getString(1));
                    EIDH_PARAGG.add( cursor2.getString(2));
                    EIDH_PARAGG.add( cursor2.getString(3));
                    EIDH_PARAGG.add( cursor2.getString(4));
                    EIDH_PARAGG.add( "");
                    fIDPARAGG=fIDPARAGG+5;  // υπαρχουσες εγγραφες

                } while (cursor2.moveToNext());
            }  */


            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EIDH_PARAGG);
            Paragg.setAdapter(arrayAdapter);
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }




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
        // cKathg : ονομα κατηγοριας

        Intent intent = new Intent(this, EpiloghEid.class);
     //   intent.putExtra("arrayListExtra", mArray);
     //   intent.putExtra("stringExtra", mString);
     //   intent.putExtra("intExtra", mValue);
       // String message2 ="---" ;// EditText.GetText().toString();

        TextView Trapezi_idparagg = findViewById(R.id.textView32);
         String message=Trapezi_idparagg.getText().toString(); // αριθμος τραπεζιού;idparagg


        intent.putExtra("mpel2",message);  // αριθμος τραπεζιού

       //String message = intent.getStringExtra("mpel2");  // αριθμος τραπεζιού

        intent.putExtra(EXTRA_MESSAGE, cKathg);
        intent.putExtra("mEIDH", EIDH_PARAGG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΗΝ ΤΡΕΧΟΥΣΑ ΠΑΡΑΓΓΕΛΙΑ
        startActivity(intent);

    };
}

/*            help  dates
//Building the table includes:
StringBuilder query= new StringBuilder();
query.append("CREATE TABLE "+TABLE_NAME+ " (");
query.append(COLUMN_ID+"int primary key autoincrement,");
query.append(COLUMN_CREATION_DATE+" DATE)");

//Inserting the data includes this:
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
values.put(COLUMN_CREATION_DATE,dateFormat.format(reactionGame.getCreationDate()));

// Fetching the data includes this:
try {
   java.util.Date creationDate = dateFormat.parse(cursor.getString(0);
   YourObject.setCreationDate(creationDate));
} catch (Exception e) {
   YourObject.setCreationDate(null);
}
 */

