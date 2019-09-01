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

    Integer kodikos_ok=0;


  //   StrictMode.setThreadPolicy(policy);
      Handler handler;
    Handler handler2;

    ListView moviesList;

    Button button3;
    Button button;
    String Pelatis;
    Button bb,load,save;
    EditText arxeio,db;
    public static ArrayList<String> pel;

    public static ArrayList<String> pel3;
    public static ArrayList<String> pelKathg;
    public String arr[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  pel.add("aa");
    //*  δεν βγαζει warning kai doyleyei αλλα σε βαθος λειτουργιας πρεπει να μπουκωνει
         handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // todo
                TextView h;
                h =(TextView)findViewById(R.id.hello);
                h.setText(Pelatis);
                for (int i = 0; i < pel.size(); i++) {
                    System.out.println("πελατης"+pel.get(i));
                }
                return true;
            }
         });


        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {


                Toast.makeText(getApplicationContext(),"ok διαβαστηκε",Toast.LENGTH_SHORT).show();

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




        bb=(Button)findViewById(R.id.button2);
        bb.setOnClickListener(new View.OnClickListener(){
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
        button=(Button)findViewById(R.id.button);
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
                           if (1==rs.getInt("KATEILHMENO")   )
                            {
                                    pel.add(rs.getString("ONO")+"----------");
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
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();

            }
        });

    }  // -------------- onCreate
// If you cannot connect with SQL server(express), you can check here: http://stackoverflow.com/questions/10522120/connecting-to-local-ms-sql-server


//================  BUTTON3  ΤΟ ΙΔΙΟ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ ALLA ME EIDH  =====================
    public void TRAP2 (View view) {


        pel3.clear();

        Runnable aRunnable = new Runnable() {
            public void run() {
                ResultSet rs = getData("SELECT *  FROM EIDH ");
                try {
                    ExecuteSql("delete from  EIDH ");

                    while (rs.next()) {
                        //  System.out.println(rs.getString("EPO"));
                        if (1==rs.getInt("KATHG")   )
                        {
                            pel3.add(rs.getString("ONO")+"----1------");

                           // αποθηκευση σε SQLLITE
                            String KOD,ONO,CH1,CH2;
                            int ID,KAT;
                            double TIMH;
                           /*  int i = 5;     MATATROPH SE STRING
                            String strI = String.valueOf(i);
                            Or int aInt = 1;
                            String aString = Integer.toString(aInt);
                              String numberAsString = "153.25";

                              double number = Double.parseDouble(numberAsString);
                              OR  double number = new Double("153.25").doubleValue();

                               double number = 456.7891d;
                               DecimalFormat decimalFormat = new DecimalFormat("#.00");
                                String numberAsString = decimalFormat.format(number);
                              */
                                KOD=rs.getString("KOD");
                                ONO=rs.getString("ONO");
                                CH1=rs.getString("CH1");
                                CH2=rs.getString("CH2");
                                ID=rs.getInt("ID");
                                KAT=rs.getInt("KATHG");
                                TIMH=rs.getDouble("TIMH");
                                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                                String Q;
                                Q="INSERT INTO EIDH (KOD,ONO,CH1,CH2,ID,KATHG,TIMH) VALUES";
                                Q=Q+"('"+KOD+"','"+ONO+"','"+CH1+"','"+CH2+"',"+Integer.toString(ID)+","+Integer.toString(KAT)+","+decimalFormat.format(TIMH)+")";
                                ExecuteSql(Q);
                        } else {
                            pel3.add(rs.getString("ONO")+"----2------");
                        }
                        //  Pelatis=rs.getString("ONO");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"ΕΙΔΗ ΛΑΘΟΣ",Toast.LENGTH_SHORT).show();
                }
                handler2.sendEmptyMessage(0);
            }
        };
        Thread aThread = new Thread(aRunnable);
        aThread.start();
        android.os.SystemClock.sleep(1000);// };
        Toast.makeText(getApplicationContext(),"pel3 ok",Toast.LENGTH_SHORT).show();

        loadKathg();




    }


    public void loadKathg () {

        pelKathg.clear();

        Runnable cRunnable = new Runnable() {
            public void run() {
                ResultSet crs = getData("SELECT KOD, ONO  FROM KATHG ");
                try {
                    while (crs.next()) {
                        //  System.out.println(rs.getString("EPO"));
                        if (crs.getInt("KOD")>=0   )
                        {
                            pelKathg.add(crs.getString("ONO"));
                        } else {
                            // pelKathg.add(rs.getString("ONO")+"----2------");
                        }
                        //  Pelatis=rs.getString("ONO");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                handler2.sendEmptyMessage(0);
            }
        };
        Thread cThread = new Thread(cRunnable);
        cThread.start();
        android.os.SystemClock.sleep(1000);// };
        Toast.makeText(getApplicationContext(),"pelKathg ok",Toast.LENGTH_SHORT).show();
//*/





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

            if (n<4){
                mydatabase.execSQL("INSERT INTO users VALUES('s4','004');");
                mydatabase.execSQL("INSERT INTO users VALUES('s5','005');");
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

    public void listEidh (View view) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;

        List<String> values=new ArrayList<>();
        moviesList=(ListView)findViewById(R.id.list1);
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select KOD,ONO from  EIDH", null);

            if (cursor2.moveToFirst()) {
                do {
                     n++;
                    values.add(String.valueOf(n)+";"+ cursor2.getString(0)+";"+cursor2.getString(1));

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

            moviesList.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }

    public void loginEidh (View view) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS EIDH( [KOD] [int],\n" +
                    "\t[ONO] [nvarchar](255) ,\n" +
                    "\t[XAR1] [int] ,\n" +
                    "\t[XAR2] [int] ,\n" +
                    "\t[CH1] [nvarchar](255) ,\n" +
                    "\t[CH2] [nvarchar](255) ,\n" +
                    "\t[NUM1] [int] ,\n" +
                    "\t[NUM2] [int] ,\n" +
                    "\t[TIMH] [real] ,\n" +
                    "\t[KATHG] [int] ,\n" +
                    "\t[PICTURE] [nvarchar](255) ,\n" +
                    "\t[ID] [int]  );");

            Cursor cursor = mydatabase.rawQuery("select count(*) from EIDH ", null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    n= Integer.parseInt(cursor.getString(0));
                    // movies.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            if (n<4){
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1) VALUES('s4','004',1);");
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1) VALUES('s4','004',1);");
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1) VALUES('s4','004',1);");
            }
            // ψαχνω να βρω τον κωδικο που εβαλε
            EditText pw;
            pw=(EditText) findViewById(R.id.editText);
            String cpw ="004"; // pw.getText().toString();
            Cursor cursor2 = mydatabase.rawQuery("select XAR1 from  EIDH", null);

            //   Integer kodikos_ok=0;
            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {

                    if(Integer.parseInt(cpw)==Integer.parseInt(cursor2.getString(0)))
                    {
                        kodikos_ok=1;
                        Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                    }
                } while (cursor2.moveToNext());
            }


            if(kodikos_ok==0) {

                Toast.makeText(getApplicationContext(),"λαθος",Toast.LENGTH_SHORT).show();
            }


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }


    }























    public void save (View view) {



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


   /*  εβγαζε προβλημα οτι πρεπει να ειναι static
         handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                TextView h;
                h=(TextView)findViewById(R.id.hello);
                h.setText(Pelatis);
                 for (int i = 0; i < pel.size(); i++) {
                     System.out.println("πελατης"+pel.get(i));
                 }

                TextView bT;
                bT=(TextView)findViewById(R.id.hello);
                bT.setText("*");



                // ...
            }
        };
    */




// ArrayList<String>
                // pel = new ArrayList<String>(); // Create an ArrayList object
// modify             movies.set(0, "Opel");
// get         cars.get(0);
//   cars.remove(0);
// cars.clear();
// cars.size();
// ArrayList<String> cars = new ArrayList<String>();
// cars.add("Volvo");
// cars.add("BMW");
// for (int i = 0; i < cars.size(); i++) {
//     System.out.println(cars.get(i));
// }




// void getmovies()
//  {
//      movies.add("X-Men");
//      movies.add("IRONMAN");
//      movies.add("SPIDY");
//      movies.add("NARNIA");
//      movies.add("LIONKING");
//      movies.add("AVENGERS");
//  }





    /*
private boolean isExternalStorageWriteable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState() )) return true;
        else {
            return false;
        }
}

    private boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }


    public void save2 (View view) {

        FileWriter fw;
        String name = "sad";
        String age = "sad--";
        String temp = "s77ad";

        try {
            fw = new FileWriter(new File(Environment.getDownloadCacheDirectory()+"/mytextfile.txt"));

            fw.write(String.format("My name is %s.", name));
            // fw.write(System.lineSeparator()); //new line
            fw.write(String.format("I am %d years old.", age));
            // fw.write(System.lineSeparator()); //new line
            fw.write(String.format("Today's temperature is %.2f.", temp));
            //  fw.write(System.lineSeparator()); //new line
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

*/
  /*  save
       /*
        FileWriter fw;
        String name="sad";
        String age="sad--";
        String temp="s77ad";

        try {
            fw = new FileWriter(new File(Environment.getDownloadCacheDirectory()+"/mytextfile.txt"));

            fw.write(String.format("My name is %s.",name));
           // fw.write(System.lineSeparator()); //new line
            fw.write(String.format("I am %d years old.",age));
           // fw.write(System.lineSeparator()); //new line
            fw.write(String.format("Today's temperature is %.2f.",temp));
          //  fw.write(System.lineSeparator()); //new line
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }





        /*
        EditText db;
        String dum;
        db =(EditText) findViewById(R.id.db);
        if ( checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            dum="dsad";
        }

  if (isExternalStorageWriteable() ) {
            try {
                File myFile = new File(Environment.getDownloadCacheDirectory() , "/mysdfile.txt");
               // myFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(myFile);
                fos.write(db.getText().toString().getBytes());
                fos.close();
                Toast.makeText(getBaseContext(),
                        "Done writing SD 'mysdfile.txt'",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
       /*
        File myDir = new File(getCacheDir(), "folder");

        myDir.mkdir();
        // Please try this may be helps to you.

        //       Ok If you want to create the TextFile in Specif Folder then You can try to beow code.

        String rootPath="/sdcard/";
        try {
             rootPath = "/sdcard/" + "Lagakis2019/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




            load =(Button)findViewById(R.id.load);
        save =(Button)findViewById(R.id.save);

        arxeio =(EditText) findViewById(R.id.arxeio);
        db =(EditText) findViewById(R.id.db);


        String marxeio=arxeio.getText().toString();
        String mdb=db.getText().toString();
        FileOutputStream fos;
        marxeio= Environment.getDownloadCacheDirectory()+"/Lagakis.txt";
        marxeio= "Lagakis.txt";

        mdb="sdfασδασδασ";
        try {
            fos=openFileOutput(marxeio, Context.MODE_PRIVATE);
            fos.write(mdb.getBytes());
            //fos.write("assddfλαγακισ");
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////
        File myDir = new File(getCacheDir(), "folder");
        myDir.mkdir();
       // Please try this may be helps to you.

         //       Ok If you want to create the TextFile in Specif Folder then You can try to beow code.

        try {
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Lagakis2019/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(rootPath + "mttext.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream out = new FileOutputStream(f);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

*/



