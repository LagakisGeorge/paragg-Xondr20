package com.example.ioun25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;
import static com.example.ioun25.MainActivity.gYparxoyses;
import static com.example.ioun25.MainActivity.idBardia;
import static com.example.ioun25.MainActivity.toGreek;
import static java.lang.Double.parseDouble;
import static java.util.jar.Pack200.Packer.PASS;

public class order extends AppCompatActivity {


    TextView total_price; // μερικο συνολο
    ArrayList<String> pel;
  //  ArrayList<String> EIDH_PARAGG; // ερχεται απο το trapezia class ΑΔΕΙΟς αλλα δεν το χρειαζομαι
    public static  ArrayList<String> EIDH_PARAGG;
    public Long[] idArr=new Long[100];    //  // ΤΟ ΣΗΜΑΔΕΥΩ ΓΙΑ ΤΗΝ ΜΕΡΙΚΗ ΠΛΗΡΩΜΗ

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


    private   Double f_sum=0.0;
    private static ResultSet RESULT;
    private static String fIDPARAGG;  // ιδ παραγγελιασ
    private static int  fYparxSeires;  // ποσες σειρες υπαρχουν στην  παραγγελια(μαζι με αυτες που δεν τυπωθηκαν)

    Handler handler2;
  public final  List<String> pelOrder_Items=new ArrayList<>();// pelOrder_Items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        total_price = (TextView) findViewById(R.id.meriki);


        String Who;
        Intent intent = getIntent();
        String message = intent.getStringExtra("mpel2");  // αριθμος τραπεζιού
        Who = intent.getStringExtra("WhoCall");

        pel = intent.getStringArrayListExtra("mpel");
        EIDH_PARAGG = intent.getStringArrayListExtra("mEIDH");
        KATHG = intent.getStringArrayListExtra("mKATHG");

        for (int kn = 0; kn < 99; kn++) {
            idArr[kn] = 0L;
        }


        if (null == EIDH_PARAGG) {
            fYparxSeires = 0;
            gYparxoyses = 0;
        } else {
            fYparxSeires = EIDH_PARAGG.size();
        }


        // κραταω αυτο που ερχεται απο το trapazia μονο που κόβω το αστερακι για να μην  ξαναδιαβαζεται οταν ερχεται απο το epilogheid  δηλαδη ανα ερχεται *62;1200  => 62;1200
        String message2;
        message2 = message;
        if (message2.substring(0, 1).equals("#")) {
            String[] separated3 = message2.split("#");
            message2 = separated3[1];

        }
        TextView Trapezi_idparagg = findViewById(R.id.textView32); //   *51;1256   trapezi;idparagg
        Trapezi_idparagg.setText(message2);
        Trapezi_idparagg.setVisibility(View.INVISIBLE );

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
        message = separated[0];
        fIDPARAGG = separated[1];


        TextView textView = findViewById(R.id.textView3);
        textView.setText(message); // αριθμος τραπεζιού
        //   String hallostring = "hallo";
        //   String asubstring = hallostring.substring(0, 1);


        //   boolean resultOfComparison=stringA.equals(stringB);
        fYparxSeires = 0;
        if (message.substring(0, 1).equals("#")) {

            String[] separated2 = message.split("#");
            message = separated[1];
            LoadYparxoysa(message, fIDPARAGG);
        } else {

            if (Who.equals("trapezia")) {
                EIDH_PARAGG = new ArrayList<String>();


            }
        }


        ListView list = (ListView) findViewById(R.id.listview01);
        list.setClickable(true);

        final List<Phonebook> listOfPhonebook = new ArrayList<Phonebook>();
         Integer status2;
            // for (int j = 0; j < EIDH_PARAGG.size(); j = j + 5){
            for (int j = EIDH_PARAGG.size()-5;j>=0; j = j - 5){
            if (j<gYparxoyses){status2=0;}else{status2=1;}

            listOfPhonebook.add(new Phonebook( EIDH_PARAGG.get(j), EIDH_PARAGG.get(j+1), EIDH_PARAGG.get(j+2),EIDH_PARAGG.get(j+3),EIDH_PARAGG.get(j+4),status2,j));
        }

        //  listOfPhonebook.add(new Phonebook("FREDDO ESSPRESSO", "2", "12"));
       //  listOfPhonebook.add(new Phonebook("FREDDO CAPPUCCINO", "1", "1.5"));

        Phonebook2Adapter adapter = new Phonebook2Adapter(this, listOfPhonebook);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
              //  System.out.println("sadsfsf");

               Toast.makeText(order.this, listOfPhonebook.get(position).getName(), Toast.LENGTH_LONG).show();
               ListalertDialog(listOfPhonebook.get(position).getPointer());






            }
        });

        list.setAdapter(adapter);



       // δεν χρειαζεται
       // moviesList=findViewById(R.id.listmaster);  //kathgories eidon


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

/*
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

*/



        Button merikiB;
        merikiB=(Button)findViewById(R.id.merikiB);
        merikiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_meriki();
            }
        });




        CheckBox    chkIos = (CheckBox) findViewById(R.id.checkBox);

        chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(order.this,
                            "Πιέστε αυτό που πληρώθηκε", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(order.this,
                            "Πιέστε αυτό που θα σβηστεί", Toast.LENGTH_LONG).show();
                }

            }
        });



        Paragg=(GridView)findViewById(R.id.listdetail);


        //Set Long-Clickable
        Paragg.setLongClickable(true);
        Paragg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int position , long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
                /*
                 *You Can use parameters like position,view or id to
                 *Customize your action
                 */

                Object o = Paragg.getItemAtPosition(position);
                // pelOrder_Items.add(o.toString() );

                //ShowDisplay(o.toString());  // ονομα κατηγοριας




                alertDialog(position);
                return false;
            }
        });











        ArrayAdapter<String> OarrayAdapter;





        OarrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EIDH_PARAGG)
                //-------------------- arxh  αυτο το κομματι βαζει πλαισια στο gridview
        {







            public View getView(int position, View convertView, ViewGroup parent) {

                // Return the GridView current item as a View
                View view = super.getView(position,convertView,parent);

                // Convert the view as a TextView widget
                TextView tv = (TextView) view;
               //  if (position%5==0) {
                     tv.setTextColor(Color.DKGRAY);
               //  }else{
               //      tv.setTextColor(Color.GREEN);
               //  }


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
                //    params.width = px/2;  // getPixelsFromDPs(EpiloghEid.this,168);
                 //   tv.setLayoutParams(new GridView.LayoutParams((px*6), 100));
               // }else{
                    params.width = px;  // getPixelsFromDPs(EpiloghEid.this,168);
               // }








                // Set the TextView layout parameters
                tv.setLayoutParams(params);

                // Display TextView text in center position
                  if (position%5==0 ||position%5==3 ||position%5==4)
                  {
                tv.setGravity(Gravity.LEFT);
                  } else{
                      tv.setGravity(Gravity.CENTER);  //center
                  }

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


 /*  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                alertDialog();
                break;
        }
    }
   */
// πληρωνω ενα είδος ή το διαγράφω apo LISTVIEW
 public void ListalertDialog(int position) {
     CheckBox ch1;
     ch1=findViewById(R.id.checkBox);

     // μερικη πληρωμή
     if (ch1.isChecked() ){
         if (position>=gYparxoyses){
             Toast.makeText(getApplicationContext(),"ΔΕΝ ΕΧΕΙ ΚΑΤΑΧΩΡΗΘΕΙ ΑΚΟΜΑ",Toast.LENGTH_LONG).show();
             return;
         }
         int j=position-position%5; // ειναι το ιδιο με το position
         if( EIDH_PARAGG.get(j).substring(0,2).equals("**")){
             return;
         }

         // Modify the element at a given index
         //  topCompanies.set(4, "Walmart");
         EIDH_PARAGG.set(j,"**"+EIDH_PARAGG.get(j));  //   +EIDH_PARAGG.get(j+4));
         f_sum=f_sum+parseDouble(EIDH_PARAGG.get(j+2));
         TextView meriki;
         meriki=findViewById(R.id.meriki);
         meriki.setText(f_sum.toString());
         show_paraggelia();
         show_Listparaggelia();
         return;



     }




     // διαγραφη

     if (position<gYparxoyses){
         Toast.makeText(getApplicationContext(),"ΕΧΕΙ ΗΔΗ ΚΑΤΑΧΩΡΗΘΕΙ",Toast.LENGTH_LONG).show();
         return;
     }
     AlertDialog.Builder dialog=new AlertDialog.Builder(this);
     dialog.setMessage("Να διαγραφεί;");
     dialog.setTitle("Dialog Box");
     final int n;
     n=position-position%5;
     dialog.setPositiveButton("Ναι",
             new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog,
                                     int which) {
                     Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                     EIDH_PARAGG.remove(n);
                     EIDH_PARAGG.remove(n);
                     EIDH_PARAGG.remove(n);
                     EIDH_PARAGG.remove(n);
                     EIDH_PARAGG.remove(n);
                     show_paraggelia(); //grid
                     show_Listparaggelia();  //Listbox
                 }

             });
     dialog.setNegativeButton("Οχι",new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             Toast.makeText(getApplicationContext(),"ΑΚΥΡΩΣΗ",Toast.LENGTH_LONG).show();
         }
     });
     AlertDialog alertDialog=dialog.create();
     alertDialog.show();

 }


    public void show_Listparaggelia(){

        ListView list = (ListView) findViewById(R.id.listview01);
        list.setClickable(true);
        final List<Phonebook> listOfPhonebook = new ArrayList<Phonebook>();
        Integer status2;
        // for (int j = 0; j < EIDH_PARAGG.size(); j = j + 5){
        for (int j = EIDH_PARAGG.size()-5;j>=0; j = j - 5){
            if (j<gYparxoyses){status2=0;}else{status2=1;}

            listOfPhonebook.add(new Phonebook( EIDH_PARAGG.get(j), EIDH_PARAGG.get(j+1), EIDH_PARAGG.get(j+2),EIDH_PARAGG.get(j+3),EIDH_PARAGG.get(j+4),status2,j));
        }

        //  listOfPhonebook.add(new Phonebook("FREDDO ESSPRESSO", "2", "12"));
        //  listOfPhonebook.add(new Phonebook("FREDDO CAPPUCCINO", "1", "1.5"));

        PhonebookAdapter adapter = new PhonebookAdapter(this, listOfPhonebook);
        list.setAdapter(adapter);
        //notifyDataSetChanged();

    }




    public void show_paraggelia(){

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

public void save_meriki(){

     if (f_sum==0){
         Toast.makeText(getApplicationContext(),"ΔΕΝ ΕΧΕΤΕ ΔΙΑΛΕΞΕΙ ΕΙΔΗ ΑΚΟΜΑ",Toast.LENGTH_LONG).show();
         return;
     }




    SQLiteDatabase mydatabase = null;
    mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);

int kn=0;
    for(int i =0;i< gYparxoyses;i=i+5)//
    {

        if(EIDH_PARAGG.get(i).substring(0,2).equals("**")){

            if(idArr[kn]>0){
                mydatabase.execSQL("update PARAGG SET ono='"+EIDH_PARAGG.get(i)+"' WHERE ID="+idArr[kn].toString());
            }


            // κραταω την πληρωμη σε μορφη πληρ*100 (γιατι num1 integer) για να βλεπω το υπολοιπο



        } //if

        kn++;

    } //for

    Double synplir=0.0;

       mydatabase.execSQL("UPDATE PARAGGMASTER SET num1=num1+"+f_sum.toString()+"   WHERE ID=" + fIDPARAGG);

    Cursor cur1=mydatabase.rawQuery("select num1 from PARAGGMASTER  WHERE ID=" + fIDPARAGG,null);
            if (cur1.moveToFirst()) {
                synplir = cur1.getDouble(0);
            }
        TextView textView = findViewById(R.id.textView3);
        String tr = textView.getText().toString(); // αριθμος τραπεζιού
        String[] separated3 = tr.split("#");
        tr = separated3[1];
        mydatabase.execSQL("UPDATE TABLES SET CH2='" + synplir.toString() + "' WHERE ONO='" + tr + "'");
        mydatabase.close();


        Intent intent = new Intent(this, trapezia.class);
        startActivity(intent);
    } // save meriki



// πληρωνω ενα είδος ή το διαγράφω
    private void alertDialog(int position) {
        CheckBox ch1;
        ch1=findViewById(R.id.checkBox);

        // μερικη πληρωμή
        if (ch1.isChecked() ){
            if (position>=gYparxoyses){
                Toast.makeText(getApplicationContext(),"ΔΕΝ ΕΧΕΙ ΚΑΤΑΧΩΡΗΘΕΙ ΑΚΟΜΑ",Toast.LENGTH_LONG).show();
                return;
            }
            int j=position-position%5;
            if( EIDH_PARAGG.get(j).substring(0,2).equals("**")){
                return;
            }

                // Modify the element at a given index
                //  topCompanies.set(4, "Walmart");
                EIDH_PARAGG.set(j,"**"+EIDH_PARAGG.get(j));  //   +EIDH_PARAGG.get(j+4));
                f_sum=f_sum+parseDouble(EIDH_PARAGG.get(j+2));
                TextView meriki;
                meriki=findViewById(R.id.meriki);
                meriki.setText(f_sum.toString());
                show_paraggelia();
            return;



        }




        // διαγραφη

        if (position<gYparxoyses){
            Toast.makeText(getApplicationContext(),"ΕΧΕΙ ΗΔΗ ΚΑΤΑΧΩΡΗΘΕΙ",Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Να διαγραφεί;");
        dialog.setTitle("Dialog Box");
        final int n;
        n=position-position%5;
        dialog.setPositiveButton("Ναι",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        EIDH_PARAGG.remove(n);
                        EIDH_PARAGG.remove(n);
                        EIDH_PARAGG.remove(n);
                        EIDH_PARAGG.remove(n);
                        EIDH_PARAGG.remove(n);
                        show_paraggelia();
                    }

                });
        dialog.setNegativeButton("Οχι",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"ΑΚΥΡΩΣΗ",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
*/
  /*  @Override
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

*/




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
        SAVE_ORDER2();
    }

    public void SAVE_ORDER2() {
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


        //-----------------------------------  sql lite save order --------------------------
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);

        TextView textView = findViewById(R.id.textView3);
        String tr=textView.getText().toString(); // αριθμος τραπεζιού


        String s = "0";  // αριθμος παργγελιας
        String Q;
        // αν ειναι νέα παραγγελία
        // "INSERT INTO PARAGGMASTER (TRAPEZI,HME,IDBARDIA,CH1) VALUES ('" + p_Trapezi + "'," + MDATE + "," + Str(gBardia) + ",'" + Format(Now(), "hh:mm") + "' )"
        if (gYparxoyses ==0) {    //"+idBardia+"
            Q = "INSERT INTO PARAGGMASTER (NUM1,AJIA,TRAPEZI,IDBARDIA,CH1) VALUES (0,0,'" + tr + "',"+idBardia+",datetime('now','localtime'))";

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


Double sum=0.0;

        for(int i = gYparxoyses; i<EIDH_PARAGG.size();i=i+5)//
        {
            //String Q;
            Q="INSERT INTO PARAGG (IDPARAGG,TRAPEZI,ONO,POSO,TIMH,PROSUETA,CH2,NUM1) VALUES ("+s+",'"+tr+"','"+ EIDH_PARAGG.get(i)+"',";
    Q=Q+ EIDH_PARAGG.get(i+1)+","+ EIDH_PARAGG.get(i+2)+",'"+ EIDH_PARAGG.get(i+3)+"','"+EIDH_PARAGG.get(i+4)+"',0);";
            mydatabase.execSQL(Q);
            sum=sum+ parseDouble(EIDH_PARAGG.get(i+1))*parseDouble(EIDH_PARAGG.get(i+2));
        }

        try {

            Socket sock = new Socket("192.168.1.202", 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            int typose=0;

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());  //"yyyyMMdd_HHmmss"
            String cur = sdf.format(new Date());

        for(int i = gYparxoyses; i<EIDH_PARAGG.size();i=i+5)//
            {

                if (i == gYparxoyses){


                    oStream.println(toGreek("ΤΡΑΠΕΖΙ No ")+tr+"  "+cur);
                    oStream.println(toGreek("=================="));
                    typose=1;
                }


                oStream.println(toGreek(EIDH_PARAGG.get(i))+" X "+EIDH_PARAGG.get(i+1));
                oStream.println("  "+ toGreek(EIDH_PARAGG.get(i+3)));
                oStream.println("  "+ toGreek(EIDH_PARAGG.get(i+4)));
                // oStream.println("λσαδσξδΞΣΔΞΑΔΞΕΙΞΔΣΞΦΔΨΝΔΞΦ");
            }

        if (typose==1){

            oStream.println("\n\n\n");
            oStream.println("\n\n\n");
            oStream.println("\n\n\n");

        }


            oStream.close();
            sock.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


       // DecimalFormat df = new DecimalFormat("#.##");
       // sum = Double.valueOf(df.format(sum));
        String csum = sum.toString();    // df.format(sum);

       // String csum = df.format(sum);

        csum = csum.replace(",", ".");

        mydatabase.execSQL("UPDATE PARAGGMASTER SET AJIA=AJIA+"+csum+" WHERE ID=" + s  );

        if (gYparxoyses ==0) {    //"+idBardia+"

            mydatabase.execSQL("UPDATE TABLES SET ch2='0',CH1='" + csum + "',KATEILHMENO=1,IDPARAGG=" + s + " WHERE ONO='" + tr + "'");
        }else{
            Double dd=0.0;
            dd=ReadSqln("select AJIA FROM PARAGGMASTER WHERE ID=" + s );

            mydatabase.execSQL("UPDATE TABLES SET CH1='" + dd.toString() + "',KATEILHMENO=1,IDPARAGG=" + s + " WHERE ONO='" + tr + "'");
        }



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

    /*
     try {
        Socket sock = new Socket("192.168.1.202", 9100);
        PrintWriter oStream = new PrintWriter(sock.getOutputStream());
        String s6 = "λαγακης";
        String out = new String(s6.getBytes("UTF-8"), "ISO-8859-7");
        oStream.println(out);

        Character c=(char) 27;
        String d=""+c+"t15";
        //  oStream.println((char) 27);
        oStream.println("HI,test from Android Device");
        oStream.println("λσαδσξδΞΣΔΞΑΔΞΕΙΞΔΣΞΦΔΨΝΔΞΦ");

            oStream.println("\n\n\n");
            oStream.close();
            sock.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    */

    public void kentriko (View view){
        Intent intent = new Intent(this, trapezia.class);
        String message2 ="---" ;// EditText.GetText().toString();
        startActivity(intent);
    }

public void print_logar(View view){
    SAVE_ORDER2();
Double sum=0.0;
    TextView textView = findViewById(R.id.textView3);
    String tr=textView.getText().toString(); // αριθμος τραπεζιού
    try {

        Socket sock = new Socket("192.168.1.202", 9100);
        PrintWriter oStream = new PrintWriter(sock.getOutputStream());
        int typose=0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());  //"yyyyMMdd_HHmmss"
        String cur = sdf.format(new Date());

        for(int i = 0; i<EIDH_PARAGG.size();i=i+5)//
        {

            if (i == 0){


                oStream.println(toGreek("ΤΡΑΠΕΖΙ No ")+tr+"   "+cur);
                oStream.println(toGreek("==========================================="));
                typose=1;
            }
            String c=toGreek(EIDH_PARAGG.get(i))+"                                   ";
            String timh=Double.toString(parseDouble(EIDH_PARAGG.get(i+2)))+"                                   ";
             double mer=  parseDouble(EIDH_PARAGG.get(i+1))*parseDouble(EIDH_PARAGG.get(i+2));

            oStream.println(c.substring(0,30)+"  "+EIDH_PARAGG.get(i+1)+"X"+timh.substring(0,5)+"="+Double.toString(mer));



            sum=sum+ mer;
        }

        if (typose==1){

            oStream.println("===========================================");
            oStream.println("                                       "+Double.toString(sum));
            oStream.println("\n\n\n");
            oStream.println("\n\n\n");

        }


        oStream.close();
        sock.close();
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }



}

    public Double ReadSqln (String query ){
        Double x;
        x=0.0;
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        Cursor cursor5 = mydatabase.rawQuery(query, null);
        if (cursor5.moveToFirst()) {
            //do {
            x = cursor5.getDouble(0);
            //   } while (cursor5.moveToNext());
        }
        else{
        }
        return x;
    }

// ΦΟΡΤΩΝΩ ΤΗΝ ΗΔΗ ΥΠΑΡΧΟΥΣΑ ΠΑΡΑΓΓΕΛΙΑ
    public void LoadYparxoysa (String tr,String IDPARAGG) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
      //  moviesList=(GridView)findViewById(R.id.listmaster);
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

            Cursor cursor2 = mydatabase.rawQuery("select ONO,POSO,TIMH,PROSUETA,CH2,ID from  PARAGG where idparagg="+IDPARAGG, null);

            if (cursor2.moveToFirst()) {
                while (!cursor2.isAfterLast()) {
                    idArr[n]=cursor2.getLong(5);
                    n++;
                    // ΤΟ ΣΗΜΑΔΕΥΩ ΓΙΑ ΤΗΝ ΜΕΡΙΚΗ ΠΛΗΡΩΜΗ

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

            gYparxoyses=fYparxSeires;


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




    public class Phonebook2Adapter extends BaseAdapter {  // implements OnClickListener
        private Context context;

        private List<Phonebook> listPhonebook;
        private Integer nPointer;

        public Phonebook2Adapter(Context context, List<Phonebook> listPhonebook) {
            this.context = context;
            this.listPhonebook = listPhonebook;
        }

        public int getCount() {
            return listPhonebook.size();
        }



        public Object getItem(int position) {

            return listPhonebook.get(position);
            // return listPhonebook.get(getCount() - position - 1);
        }

        public long getItemId(int position) {

            return position;  // κανονικη : το τελευταιο κατω
            //  return  ( getCount() - position - 1);
        }

        //  @Override
        //  public Object getItem(int position) {
        //      return getCount() - position - 1;
        //  }




        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Phonebook entry = listPhonebook.get(position);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.phone_row, null);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvName.setText(entry.getName());


            //   tvName.setTextColor(RED);

            TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPosothta);
            tvPhone.setText(entry.getPosothta());


            TextView tvMail = (TextView) convertView.findViewById(R.id.tvTimh);
            tvMail.setText(entry.getTimh());

            TextView tvProsu = (TextView) convertView.findViewById(R.id.tvProsu);
            tvProsu.setText(entry.getProsu());

            TextView tvSxolia = (TextView) convertView.findViewById(R.id.tvSxolia);
            tvSxolia.setText(entry.getSxolia());

            nPointer=entry.getPointer();

            // Set the onClick Listener on this button
            Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
            if (entry.getStatus()>0){  // μολις παραγγειλε
                tvName.setTextColor(RED);
                //  tvName.setEnabled(true);
                //  tvName.setText(entry.getPointer()+entry.getName());

            } else{ //παλια
                tvName.setTextColor(BLACK );
                //  tvName.setEnabled(false);
                btnRemove.setWidth(70);
                //debug  tvName.setText(entry.getStatus()+entry.getName());
            }

            if (entry.getName().substring(0,1).equals("*")){
                tvName.setTextColor(GREEN);
            }






            btnRemove.setFocusableInTouchMode(false);
            btnRemove.setFocusable(false);




            //To lazy to implement interface
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Phonebook entry = (Phonebook) v.getTag();
                    nPointer=entry.getPointer();
                   // EIDH_PARAGG.remove(nPointer);
                   // EIDH_PARAGG.remove(nPointer);
                   // EIDH_PARAGG.remove(nPointer);
                   // EIDH_PARAGG.remove(nPointer);
                   // EIDH_PARAGG.remove(nPointer);


                    Toast.makeText(context,"koympi"+nPointer.toString(),Toast.LENGTH_SHORT).show();
                 //   listPhonebook.remove(entry);

                    ListalertDialog(nPointer);




                //ok δουλεύει    f_sum=f_sum+parseDouble(entry.getTimh());
                // ok δουλεύει      total_price.setText(f_sum.toString()) ;


                    // TextView tvMail = (TextView) order.findViewById(R.id.tvTimh);

                    //              Button mer=super.findViewById(R.id.merikiB);






                    // listPhonebook.remove(view.getId());

                    //  notifyDataSetChanged();

                }
            });





            //debug   btnRemove.setOnClickListener(this);


            // Set the entry, so that you can capture which item was clicked and
            // then remove it
            // As an alternative, you can use the id/position of the item to capture
            // the item
            // that was clicked.
            btnRemove.setTag(entry);

            // btnRemove.setId(position);


            return convertView;
        }

        //  @Override   // θα χρειαστεί στον ορισμό της κλάσης ... implements OnClickListener {...
        // και στο getView()       btnRemove.setOnClickListener(this);
        //public void onClick(View view) {
        //   Phonebook entry = (Phonebook) view.getTag();
        //   listPhonebook.remove(entry);
        //    // listPhonebook.remove(view.getId());
        //    notifyDataSetChanged();
        // }

        private void showDialog(Phonebook entry) {
            // Create and show your dialog
            // Depending on the Dialogs button clicks delete it or do nothing
        }

    }




}
