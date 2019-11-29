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
import android.print.PrintManager;
import android.util.Log;
import android.util.Xml;
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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
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


    public static final String[] mPliromes = new String[] {"00",
            "ΜΕΤΡΗΤΟΙΣ",
            "ΚΑΡΤΑ 1",
            "ΚΑΡΤΑ 2",
            "ΚEΡAΣMENA"};







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




       // printhtml();

    }  // -------------- onCreate
// If you cannot connect with SQL server(express), you can check here: http://stackoverflow.com/questions/10522120/connecting-to-local-ms-sql-server

 /*   public static final byte[] INIT = {27, 64};
    public static final byte[] FEED_LINE = {10};

    public static final byte[] SELECT_FONT_A = {27, 33, 0};

    public static final byte[] FONT_B = {0x1B, 0x4D, 0x01};
    public static final byte[] ALLINEA_SX = {0x1B, 0x61, 0x00};
    public static final byte[] ALLINEA_CT = {0x1B, 0x61, 0x01};
*/



// ΤΥΠΩΝΕΙ ΕΝΑ ΚΕΙΜΕΝΟ
    public void print_text(View view) {













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
            String s = "ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ,test from Android Device";// text returned by web service taking it as static for testing
            //  1. not working:
           // Char c = (char) n;
            //System.out.print(c)//char c will store the converted value.
            // shareimprove this answer
            // edited Dec 17 '14 at 14:59

            //  Uri Agassi
            // 33.5k1010 gold badges6363 silver badges8383 bronze badges
            //  answered Dec 17 '14 at 14:56

            //  Entriple Aardee
            //   1111 bronze badge
            //  add a comment

            //  0
            oStream.println("geia χαρα");
            for (int i = 128; i < 256; i=i+5) {
                //  Char c=(char)n;
                //  System.out.print(c)//

                oStream.println((char) i);
        }


        //    String str = new String(s.getBytes("ISO-8859-7"), "utf-8");
            //  String str = new String(s.getBytes(), "ISO-8859-7");
         //   oStream.println(str);
            oStream.println("αβγδεζηθικλμνξοπρστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ HI,test from Android Device");
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



//debug
    public static void sendData(byte[] buffer, OutputStream os)
    {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            os.write(byteBuffer.array());
            os.flush();
            // tell the user data were sent

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    //debug
    public void print (String text, String codePage, OutputStream os) throws UnsupportedEncodingException {
        /*Your codetable initialization here.
         *You can refactor this more efficiently.
         *Hardcoded just so you can understand.
         */

        ByteBuffer init = ByteBuffer.allocate(3);
        init.put((byte) 0x1B);
        init.put((byte) 0x74);
        init.put((byte) 2);
        sendData(init.array(), os);

      //   εναλλακτικα
      //  ByteBuffer closeMultibyte = ByteBuffer.allocate(2);
      //  closeMultibyte.put((byte) 0x1C);
      //  closeMultibyte.put((byte) 0x2E);
      //  sendData(closeMultibyte.array(), os);


        ByteBuffer dataToPrint = ByteBuffer.allocate(text.length());
        dataToPrint.put(text.getBytes(codePage));
        sendData(dataToPrint.array(), os);
    }

   // debug
    public void IntentPrint(String txtvalue)
    {
        byte[] buffer = txtvalue.getBytes();
        byte[] PrintHeader = { (byte) 0xAA, 0x55,2,0 };
        PrintHeader[3]=(byte) buffer.length;
       // InitPrinter();
        if(PrintHeader.length>128)
        {
          //  value+="\nValue is more than 128 size\n";
            //txtLogin.setText(value);
        }
        else
        {
            try
            {
                for(int i=0;i<=PrintHeader.length-1;i++)
                {
              //      mmOutputStream.write(PrintHeader[i]);
                }
                for(int i=0;i<=buffer.length-1;i++)
                {
                //    mmOutputStream.write(buffer[i]);
                }
              //  mmOutputStream.close();
              //  mmSocket.close();
            }
            catch(Exception ex)
            {
                //value+=ex.toString()+ "\n" +"Excep IntentPrint \n";
                //txtLogin.setText(value);
            }
        }
    }



    // convert internal Java String format to UTF-8
    public static String convertStringToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-7");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public String toGreek(String lexh){
        int i;
        String cc="";
        String c;
        for (i=0;i<lexh.length();i++){
             c=lexh.substring(i,i+1);
            switch (c) {
                case "Α":
                    c="A";
                    break;
                case "Γ":
                    c="G";
                    break;
                case "Β":
                    c="B";
                    break;
                case "Δ":
                    c="D";
                    break;
                case "Ε":
                    c="E";
                    break;
                case "Ζ":
                    c="Z";
                    break;
                case "Η":
                    c="H";
                    break;
                case "Θ":
                    c="8";
                    break;
                case "Ι":
                    c="I";
                    break;
                case "Κ":
                    c="K";
                    break;
                case "Λ":
                    c="L";
                    break;
                case "Μ":
                    c="M";
                    break;
                case "Ν":
                    c="N";
                    break;
                case "Ξ":
                    c="3";
                    break;
                case "Ο":
                    c="O";
                    break;
                case "Π":
                    c="P";
                    break;
                case "Ρ":
                    c="R";
                    break;
                case "Σ":
                    c="S";
                    break;
                case "Τ":
                    c="T";
                    break;
                case "Υ":
                    c="Y";
                    break;
                case "Φ":
                    c="F";
                    break;
                case "Χ":
                    c="X";
                    break;
                case "Ψ":
                    c="PS";
                    break;
                case "Ω":
                    c="O";
                    break;
                case "α":
                    c="A";
                    break;
                case "γ":
                    c="G";
                    break;
                case "β":
                    c="B";
                    break;
                case "δ":
                    c="D";
                    break;
                case "ε":
                    c="E";
                    break;
                case "ζ":
                    c="Z";
                    break;
                case "η":
                    c="H";
                    break;
                case "θ":
                    c="8";
                    break;
                case "ι":
                    c="I";
                    break;
                case "κ":
                    c="K";
                    break;
                case "λ":
                    c="L";
                    break;
                case "μ":
                    c="M";
                    break;
                case "ν":
                    c="N";
                    break;
                case "ξ":
                    c="3";
                    break;
                case "ο":
                    c="O";
                    break;
                case "π":
                    c="P";
                    break;
                case "ρ":
                    c="R";
                    break;
                case "σ":
                    c="S";
                    break;
                case "τ":
                    c="T";
                    break;
                case "υ":
                    c="Y";
                    break;
                case "φ":
                    c="F";
                    break;
                case "χ":
                    c="X";
                    break;
                case "ψ":
                    c="PS";
                    break;
                case "ω":
                    c="O";
                    break;
                case "ς":
                    c="S";
                    break;




                case "ά":
                    c="A";
                    break;
                case "έ":
                    c="E";
                    break;
                case "ί":
                    c="I";
                    break;
                case "ό":
                    c="O";
                    break;
                case "ή":
                    c="H";
                    break;
                case "ύ":
                    c="Y";
                    break;
                case "ώ":
                    c="O";
                    break;

            }
            cc=cc+c;
        }


     return cc;
    }



    public void print_order (View view)
    {





        try
        {
            Socket sock = new Socket("192.168.1.202", 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            byte[] printformat = {27, 116, 7};
// java.io.OutputStream stream  = new java.io.OutputStream()

            String Str2 = new String( "LΛGΓ".getBytes( "737" ));
            oStream.println(Str2);
            Str2 = new String ("LΛGΓ".getBytes( "cp737" ));
            oStream.println(Str2);
            Str2 = new String ("LΛGΓ".getBytes( "cp850" ));
            oStream.println(Str2);

            String s6 = "λαγακης";
            String out = new String(s6.getBytes("850"), "CP737");
            oStream.println(out);

            CharsetEncoder encoder = Charset.forName("CP737").newEncoder();
            oStream.println("ENCODER-START");
            String response = "λαγακης γεωργιος ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ";
            oStream.println(encoder.encode(CharBuffer.wrap(response)));
            oStream.println("ENCODER-END");
            // String ASCII2 = new String("λαγακης γεωργιος ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ", "737");








              ByteBuffer closeMultibyte = ByteBuffer.allocate(2);
              closeMultibyte.put((byte) 0x1C);
              closeMultibyte.put((byte) 0x2E);
            oStream.println(closeMultibyte.array());

            oStream.println("λgΦG");
            //  sendData(closeMultibyte.array(), os);


            byte[] buffer = "lagaκης Γεωργιος γεοργιοσ georgios".getBytes();
            byte[] PrintHeader = { (byte) 0xAA, 0x55,2,0 };
            PrintHeader[3]=(byte) buffer.length;
           // InitPrinter();
            if(PrintHeader.length>128)
            {
               // value+="\nValue is more than 128 size\n";
               // txtLogin.setText(value);
            }
            else
            {
                try
                {
                    for(int i=0;i<=PrintHeader.length-1;i++)
                    {
                        oStream.print(closeMultibyte.array());
                        oStream.write(PrintHeader[i]);
                    }
                    for(int i=0;i<=buffer.length-1;i++)
                    {
                        oStream.write(buffer[i]);
                    }
                   // mmOutputStream.close();
                   // mmSocket.close();
                }
                catch(Exception ex)
                {
                    // value+=ex.toString()+ "\n" +"Excep IntentPrint \n";
                   // txtLogin.setText(value);
                }
            }























            SQLiteDatabase mydatabase=null;
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO, ID,TIMH,CH2,PICTURE FROM EIDH", null);
            if (cursor2.moveToFirst()) {
                do {
         //           oStream.println( toGreek(cursor2.getString(0)));
                } while (cursor2.moveToNext());
            }
            mydatabase.close();






/*
            byte[] buffer = new byte[128];
            for (int i = 0; i < 128; i=i+1) {
                buffer[i] = (byte)(128 + i);
                oStream.print("=pr-buf=");
                oStream.print(buffer[i]);
                oStream.print("/i/");
                oStream.print(i);
                oStream.print("-wbuf-");
                oStream.write(buffer[i]);
            }



            oStream.println(printformat);
            oStream.print("buffer");
           oStream.print(buffer);
            oStream.print("end-buffer");
*/
            oStream.print("αδασδαδαδΑΒΓΔΕΖΗΘΙΚΛ");

           // print("αδασδαδαδΑΒΓΔΕΖΗΘΙΚΛ","737",oStream );

            oStream.print("αδασδαδαδΑΒΓΔΕΖΗΘΙΚΛ".getBytes("737")) ;

            oStream.write("αδασδαδαδασδδ");


            oStream.println(convertStringToUTF8("λαγακησ γεωργιος HI,test from Android Device"));
            oStream.println("HI,test from Android Device");
            String s = convertStringToUTF8("ΛΑΓΑΚΗΣ ΓΕΩΡΓΙΟΣ,test from Android Device");// text returned by web service taking it as static for testing
          //  1. not working:

            String Str1="---ΛtΑeΓsΑt ,test ";
            String Str3 = new String( Str1.getBytes( "UTF-8" ));
            oStream.println("Returned Value " + Str3 );
            Str2 = new String (Str1.getBytes( "ISO-8859-7" ));
            oStream.println("Returned Value " + Str2 );



            String str = new String(s.getBytes("850"), "utf-8");
           String str2 = new String(s.getBytes(), "850");
            oStream.println(str);
            oStream.println(str2);
            oStream.println("HI,test from Android Device");
            oStream.println("HI,test from Android Device");
            oStream.println("geia χαρα");

            for (int i = 128; i < 256; i=i+15) {
                  Character c=(char) i;
                oStream.print(c);  //
                oStream.write(i);
                oStream.println((char) i);
            }

s="αβγδεζηθικλμνξοπρστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
            String str22 = new String(s.getBytes("ISO-8859-7"), "737");
            //  String str = new String(s.getBytes(), "ISO-8859-7");
            oStream.println(str22);
            oStream.println("αβγδεζηθικλμνξοπρστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ HIsssss,test from Android Device");
            oStream.println("HI,test from Android Device");

            oStream.println("\n\n\n");

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


