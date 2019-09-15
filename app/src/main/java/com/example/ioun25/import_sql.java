package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class import_sql extends AppCompatActivity {
    Handler handler2;
    Handler handler;
    public static ArrayList<String> pel3;
    public static ArrayList<String> pelKathg;
    ListView moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_sql);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {


                Toast.makeText(getApplicationContext(), "ok διαβαστηκε", Toast.LENGTH_SHORT).show();

                /*
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


        pel3 = new ArrayList<String>();
        pelKathg = new ArrayList<String>();





    }

    //================= sqlliteEIDH====================
    public void listTRAPEZI (View view) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;

        List<String> values=new ArrayList<>();
        values.add("TRAPEZIA---");
        moviesList=(ListView)findViewById(R.id.list1);
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("SELECT ONO,KATEILHMENO,NUM1  FROM TABLES where NUM1>0", null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add(String.valueOf(n)+";"+ cursor2.getString(0));

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

            moviesList.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "trapezia ERRORS", Toast.LENGTH_SHORT).show();
        }
    }

    //================= sqlliteEIDH====================
    public void listEidh (View view) {
        SQLiteDatabase mydatabase=null;
        Integer n=0;

        List<String> values=new ArrayList<>();
        moviesList=(ListView)findViewById(R.id.list1);
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select KOD,ONO,TIMH,KATHG from  EIDH", null);

            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    values.add(String.valueOf(n)+";"+ cursor2.getString(0)+";"+cursor2.getString(1)+";"+cursor2.getString(2)+";"+cursor2.getString(3));

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

            moviesList.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
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
















    //================  BUTTON3  ΤΟ ΙΔΙΟ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ ALLA ME EIDH  =====================
    public void loadSQLiteFromSQLSERVER(View view) {


        pel3.clear();

        Runnable aRunnable = new Runnable() {
            public void run() {
                ResultSet rs = getData("SELECT *  FROM EIDH ");
                try {
                    ExecuteSql("delete from  EIDH ");

                    while (rs.next()) {
                        //  System.out.println(rs.getString("EPO"));
                        if (rs.getInt("KATHG") > 0) {
                            pel3.add(rs.getString("ONO") + "----1------");

                            // αποθηκευση σε SQLLITE
                            String KOD, ONO, CH1, CH2;
                            int ID, KAT;
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

                                String a = "HelloBrother How are you!";
                                String r = a.replace("HelloBrother","Brother");
                                System.out.println(r);
                                This would print out "Brother How are you!"
                              */
                            KOD = rs.getString("KOD");
                            ONO = rs.getString("ONO");
                            // print_text(ONO);
                            CH1 = rs.getString("CH1");
                            CH2 = rs.getString("CH2");
                            ID = rs.getInt("ID");
                            KAT = rs.getInt("KATHG");
                            TIMH = rs.getDouble("TIMH");
                            DecimalFormat decimalFormat = new DecimalFormat("#.00");
                            String cTIMH = decimalFormat.format(TIMH);
                            cTIMH = cTIMH.replace(",", ".");


                            String Q;
                            Q = "INSERT INTO EIDH (KOD,ONO,CH1,CH2,ID,KATHG,TIMH) VALUES";
                            Q = Q + "('" + KOD + "','" + ONO + "','" + CH1 + "','" + CH2 + "'," + Integer.toString(ID) + "," + Integer.toString(KAT) + "," + cTIMH + ")";
                            ExecuteSql(Q);
                        } else {
                            pel3.add(rs.getString("ONO") + "----2------");
                        }
                        //  Pelatis=rs.getString("ONO");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "ΕΙΔΗ ΛΑΘΟΣ", Toast.LENGTH_SHORT).show();
                }
                handler2.sendEmptyMessage(0);
            }
        };
        Thread aThread = new Thread(aRunnable);
        aThread.start();
        android.os.SystemClock.sleep(1000);// };
        Toast.makeText(getApplicationContext(), "pel3 ok", Toast.LENGTH_SHORT).show();


        loadKathg();

        loadTrapezia();


    }




    public void loadTrapezia() {
        // ΦΟΡΤΩΝΕΙ TRAPEZIA ΚΑΙ ΤΙΣ ΜΕΤΑΦΕΡΕΙ ΣΤΟ sqllite

/*              "[ONO] [nvarchar](55) ,"+
                "[KATEILHMENO] [int] ,"+
                "[SYNOLO] [int] ,"+
                "[NUM1] [int] ,"+
                "[NUM2] [int] ,"+
                "[CH1] [nvarchar](55) ,"+
                "[CH2] [nvarchar](55),"+
                "[IDPARAGG] [int] ,"+
                "[ID] [int]);" );      */


        pelKathg.clear();
        Runnable cRunnable = new Runnable() {
            public void run() {

                ResultSet crs = getData("SELECT ONO,KATEILHMENO,NUM1  FROM TABLES where NUM1>0");

                try {

                    ExecuteSql("delete from TABLES");
                    while (crs.next()) {
                        //  System.out.println(rs.getString("EPO"));
                        if (crs.getInt("KOD") >= 0) {
                            pelKathg.add(crs.getString("ONO"));

                            String Q;
                            Q = "INSERT INTO TABLES (ONO,KATEILHMENO,NUM1) VALUES";
                            Q = Q + "('" + crs.getString("ONO") + "'," + Integer.toString(crs.getInt("KATEILHMENO")) + "," + Integer.toString(crs.getInt("KATEILHMENO")) + "')";
                            ExecuteSql(Q);
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
        Toast.makeText(getApplicationContext(), "trapezia ok", Toast.LENGTH_SHORT).show();
    }


    //===============================================================================================
    public void loadKathg() {
        // ΦΟΡΤΩΝΕΙ ΤΙΣ ΚΑΤΗΓΟΡΙΕΣ ΚΑΙ ΤΙΣ ΜΕΤΑΦΕΡΕΙ ΣΤΟ sqllite

/*        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS KATHG (ID [int] ,"+
                "[KOD] [int] ,"+
                "[ONO] [nvarchar](55) ,"+
                "[PICTURE] [nvarchar](55) ,"+
                "[CH1] [nvarchar](55) ,"+
                "[CH2] [nvarchar](55)  ); ");
  */
        pelKathg.clear();
        Runnable cRunnable = new Runnable() {
            public void run() {
                ResultSet crs = getData("SELECT KOD, ONO  FROM KATHG ");
                try {

                    ExecuteSql("delete from KATHG");
                    while (crs.next()) {
                        //  System.out.println(rs.getString("EPO"));
                        if (crs.getInt("KOD") >= 0) {
                            pelKathg.add(crs.getString("ONO"));

                            String Q;
                            Q = "INSERT INTO KATHG (KOD,ONO) VALUES";
                            Q = Q + "(" + Integer.toString(crs.getInt("KOD")) + ",'" + crs.getString("ONO") + "')";
                            ExecuteSql(Q);
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
        Toast.makeText(getApplicationContext(), "pelKathg ok", Toast.LENGTH_SHORT).show();
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


            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS KATHG (ID [int] ,"+
                    "[KOD] [int] ,"+
                    "[ONO] [nvarchar](55) ,"+
                    "[PICTURE] [nvarchar](55) ,"+
                    "[CH1] [nvarchar](55) ,"+
                    "[CH2] [nvarchar](55)  ); ");

            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TABLES ("+
                    "[ONO] [nvarchar](55) ,"+
                    "[KATEILHMENO] [int] ,"+
                    "[SYNOLO] [int] ,"+
                    "[NUM1] [int] ,"+
                    "[NUM2] [int] ,"+
                    "[CH1] [nvarchar](55) ,"+
                    "[CH2] [nvarchar](55),"+
                    "[IDPARAGG] [int] ,"+
                    "[ID] [int]);" );


            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS PARAGG ("+
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


            String c="CREATE TABLE IF NOT EXISTS PARAGGMASTER("+
                    "[TRAPEZI] [nvarchar](55) ,"+
                    "[IDERGAZ] [int] ,"+
                    "[HME] [datetime] ,"+
                    "[IDBARDIA] [int] ,"+
                    "[AJIA] [real] ,"+
                    "[TROPOS] [int] ,"+
                    "[NUM1] [int] ,"+
                    "[NUM2] [int] ,"+
                    "[CH1] [varchar](55) ,"+
                    "[CH2] [varchar](55) ,"+
                    "[ID] integer PRIMARY KEY )";

            mydatabase.execSQL(c);



            Cursor cursor = mydatabase.rawQuery("select count(*) from EIDH ", null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    n= Integer.parseInt(cursor.getString(0));
                    // movies.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            if (n<8){
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1,CH1,CH2,KATHG,ID,TIMH) VALUES('s4','004',1,'1','2',3,7,12.56);");
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1) VALUES('s4','004',1);");
                mydatabase.execSQL("INSERT INTO EIDH (KOD,ONO,XAR1) VALUES('s4','004',1);");
            }
            // ψαχνω να βρω τον κωδικο που εβαλε
            EditText pw;
            pw=(EditText) findViewById(R.id.editText);
            String cpw ="004"; // pw.getText().toString();
            Cursor cursor2 = mydatabase.rawQuery("select XAR1 from  EIDH", null);



        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }


    }








}
