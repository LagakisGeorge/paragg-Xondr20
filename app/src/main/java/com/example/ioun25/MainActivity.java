package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Policy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    Integer kodikos_ok = 0;


    //   StrictMode.setThreadPolicy(policy);
    Handler handler;
    Handler handler2;

    ListView moviesList;

    Button button3;
    Button button;
    String Pelatis;
    Button bb, load, save;
    EditText arxeio, db;
    public static ArrayList<String> pel;

    public static ArrayList<String> pel3;
    public static ArrayList<String> pelKathg;
    public String arr[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //  pel.add("aa");
        //*  δεν βγαζει warning kai doyleyei αλλα σε βαθος λειτουργιας πρεπει να μπουκωνει
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // todo
                TextView h;
                h = (TextView) findViewById(R.id.hello);
                h.setText(Pelatis);
                for (int i = 0; i < pel.size(); i++) {
                    System.out.println("πελατης" + pel.get(i));
                }
                return true;
            }
        });


        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {


                Toast.makeText(getApplicationContext(), "ok διαβαστηκε", Toast.LENGTH_SHORT).show();

                /*    / todo
                TextView h;
                h =(TextView)findViewById(R.id.hello);
                h.setText("*"+Pelatis);
                for (int i = 0; i < pel.size(); i++) {
                    System.out.println("πελατης"+pel.get(i));
                }
                */

                return true;
            }
        });

// ArrayList<String>
        pel = new ArrayList<String>(); // Create an ArrayList object
        pel3 = new ArrayList<String>();
        pelKathg = new ArrayList<String>();

        /** Called when the user taps the Send button */


        bb = (Button) findViewById(R.id.button2);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pel.clear();

                Runnable aRunnable = new Runnable() {
                    public void run() {
                        start("100");
                        handler.sendEmptyMessage(0);
                    }
                };
                Thread aThread = new Thread(aRunnable);
                aThread.start();
            }
        });


//==============  BUTTON  ΤΡΑΠΕΖΙΩΝ ========================================
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pel.clear();

                Runnable aRunnable = new Runnable() {
                    public void run() {
                        ResultSet rs = getData("SELECT ONO,KATEILHMENO,NUM1  FROM TABLES where NUM1=2");
                        try {
                            while (rs.next()) {
                                //  System.out.println(rs.getString("EPO"));
                                if (1 == rs.getInt("KATEILHMENO")) {
                                    pel.add(rs.getString("ONO") + "----------");
                                } else {
                                    pel.add(rs.getString("ONO"));
                                }
                                //  Pelatis=rs.getString("ONO");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        handler2.sendEmptyMessage(0);
                    }
                };

                Thread aThread = new Thread(aRunnable);
                aThread.start();

                //while ( bT.getText().toString()=="*"){
                android.os.SystemClock.sleep(1000);
                // };
                Toast.makeText(getApplicationContext(), "Hello Javatpoint", Toast.LENGTH_SHORT).show();

            }
        });

    }  // -------------- onCreate
// If you cannot connect with SQL server(express), you can check here: http://stackoverflow.com/questions/10522120/connecting-to-local-ms-sql-server



// ΤΥΠΩΝΕΙ ΕΝΑ ΚΕΙΜΕΝΟ
    public void print_text(String ss) {


        try {
            Socket sock = new Socket("192.168.1.202", 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            oStream.println("HI,test from Android Device");
            oStream.println(ss);
            String s = "ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ,test from Android Device";// text returned by web service taking it as static for testing
            //  1. not working:


            String str = new String(s.getBytes("ISO-8859-7"), "utf-8");
            //  String str = new String(s.getBytes(), "ISO-8859-7");
            oStream.println(str);
            oStream.println("HI,test from Android Device");
            oStream.println("HI,test from Android Device");

            oStream.println("\n\n\n");
            oStream.close();
            sock.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void report (View view){



        Intent intent = new Intent(this, reports2.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
      //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
      //  intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
      //  intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);


    }




    public void print_order (View view)
    {







        try
        {
            Socket sock = new Socket("192.168.1.202", 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            oStream.println("HI,test from Android Device");
            oStream.println("HI,test from Android Device");
            String s = "ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ,test from Android Device";// text returned by web service taking it as static for testing
          //  1. not working:
            String str = new String(s.getBytes("850"), "utf-8");
           String str2 = new String(s.getBytes(), "850");
            oStream.println(str);
            oStream.println(str2);
            oStream.println("HI,test from Android Device");
            oStream.println("HI,test from Android Device");

            oStream.println("\n\n\n");
            oStream.close();
            sock.close();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }







    //=====================================  READ  TXT  FILE ===============================
    public void load (View view) {
        String marxeio = "Lagakis.txt"; // arxeio.getText().toString();
      //  File f = new File(marxeio);
        //  if (f.exists()) {

        try {


            FileInputStream fin = openFileInput(marxeio);
            InputStreamReader inputStreamReader = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            fin.close();
            inputStreamReader.close();
          // '' TextView tcarxeio = (TextView) findViewById(R.id.carxeio);
          //  TextView tcdata = (TextView) findViewById(R.id.cdata);
        //    tcarxeio.setText("FileName" + marxeio);
         //   tcdata.setText("data" + stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//******************************   SQLLITE DATABASE   ********************************
    public void login (View view) {

        SQLiteDatabase mydatabase=null;
        Integer n=0;

        try{
            mydatabase = openOrCreateDatabase("logins",MODE_PRIVATE,null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS users(Username VARCHAR,Password VARCHAR);");

            Cursor cursor = mydatabase.rawQuery("select count(*) from  users", null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                   n= Integer.parseInt(cursor.getString(0));
                   // movies.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            if (n<12){
                mydatabase.execSQL("INSERT INTO users VALUES('s4','004');");
                mydatabase.execSQL("INSERT INTO users VALUES('s5','005');");
                mydatabase.execSQL("INSERT INTO users VALUES('s6','006');");
                mydatabase.execSQL("INSERT INTO users VALUES('s6','006');");

            }
            // ψαχνω να βρω τον κωδικο που εβαλε
             EditText pw;
            pw=(EditText) findViewById(R.id.editText);
            String cpw = pw.getText().toString();
            Cursor cursor2 = mydatabase.rawQuery("select * from  users", null);

          //   Integer kodikos_ok=0;
            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {

                    if(Integer.parseInt(cpw)==Integer.parseInt(cursor2.getString(1))) {
                        kodikos_ok=1;
                        Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                    }
                } while (cursor2.moveToNext());
            }


            if(kodikos_ok==0) {

                Toast.makeText(getApplicationContext(),"λαθος",Toast.LENGTH_SHORT).show();
            }

            //  mydb = new DBHelper(this);
            //   int eggrafes=mydb.numberOfRows();
            //  Toast.makeText(getApplicationContext(),eggrafes,Toast.LENGTH_SHORT).show();
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }

/*
        Cursor cursor = mydatabase.rawQuery("select * from  TutorialsPoint", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                movies.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, movies);
        moviesList.setAdapter(arrayAdapter);
*/
    }





    public void formaSQL (View view) {

        Intent intent = new Intent(this,  import_sql.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
       // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
       /// intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
       //   intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);



    }


    public void save (View view) {



    }


    // @Override
    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
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





    //  ======================   SHOW TRAPEZIA  =========================
    public void click6 (View view) {
//================= TRAPEZIA ===========================================
/*
        private ArrayList<String> mArray;
        private String mString;
        private int mValue;

        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("arrayListExtra", mArray);
        intent.putExtra("stringExtra", mString);
        intent.putExtra("intExtra", mValue);
        startActivity(intent);
  */
        Intent intent = new Intent(this, trapezia.class);
       // EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
        intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
        intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);
        // Do something in response to button
    };



    public void click7 (View view) {

        Intent intent = new Intent(this, trapezia.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
        startActivity(intent);
        // Do something in response to button
    };


    private void start( String k ) {
       // System.out.println("START");
        String query = "SELECT top "+k+" * FROM PEL";
        ResultSet rs = getData(query);
       // TextView h;
      //  h=(TextView)findViewById(R.id.hello);

        try {
            while (rs.next()) {
              //  System.out.println(rs.getString("EPO"));
                pel.add(rs.getString("EPO"));
                Pelatis=rs.getString("EPO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  System.out.println("END");
    }



   // private String URL = "jdbc:jtds:sqlserver://192.168.1.5:52735/BAR;instance=SQLEXPRESS;";
    private String URL = "jdbc:jtds:sqlserver://192.168.1.7:49702/BAR;instance=SQLEXPRESS;";
    private String USER = "sa";
   // private String PASS = "12345678";  //"p@ssw0rd";
    private String PASS = "p@ssw0rd";
    private static ResultSet RESULT;
    public ResultSet getData(String query) {
        Connection CON = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            CON = DriverManager.getConnection(URL, USER, PASS);
            RESULT = CON.createStatement().executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RESULT;
    }


    public void ExecuteSql (String SQL) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        try{    mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
                mydatabase.execSQL(SQL);
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }

} // end class ===============================================================================================


