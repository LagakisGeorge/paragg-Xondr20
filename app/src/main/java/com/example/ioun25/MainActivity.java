package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Policy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();



    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";




  //   StrictMode.setThreadPolicy(policy);
      Handler handler;
    Handler handler2;



    Button button3;
    Button button;
    String Pelatis;
    Button bb;

    ArrayList<String> pel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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








      //   */

        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // todo
                TextView h;
                h =(TextView)findViewById(R.id.hello);
                h.setText("*"+Pelatis);
                for (int i = 0; i < pel.size(); i++) {
                    System.out.println("πελατης"+pel.get(i));
                }
                return true;
            }
        });



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
                pel = new ArrayList<String>(); // Create an ArrayList object
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

        /** Called when the user taps the Send button */




        bb=(Button)findViewById(R.id.button2);
        bb.setOnClickListener(new View.OnClickListener(){
                                  @Override
                                  public void onClick(View view) {
                                      pel.clear();

                                      Runnable aRunnable = new Runnable() {
                                          public void run() {
                                              start("10");
                                              handler.sendEmptyMessage(0);
                                          }
                                      };
                                      Thread aThread = new Thread(aRunnable);
                                      aThread.start();
                                  }
        });



//--------------------------------------------------------------------
//------------------------------------------------


        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pel.clear();

                Runnable aRunnable = new Runnable() {
                    public void run() {
                        ResultSet rs = getData("select top 10 * from EID");
                        try {
                            while (rs.next()) {
                                //  System.out.println(rs.getString("EPO"));

                                pel.add(rs.getString("ONO"));
                                Pelatis=rs.getString("ONO");
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




    public void click6 (View view) {

        Intent intent = new Intent(this, trapezia.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
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



    // private String URL = "jdbc:jtds:sqlserver://192.168.1.4:52735/DHMOS;instance=SQLEXPRESS;";
    private String URL = "jdbc:jtds:sqlserver://192.168.1.7:49702/MERCURY;instance=SQLEXPRESS;";
    private String USER = "sa";
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


   // void getmovies()
  //  {
  //      movies.add("X-Men");
  //      movies.add("IRONMAN");
  //      movies.add("SPIDY");
  //      movies.add("NARNIA");
  //      movies.add("LIONKING");
  //      movies.add("AVENGERS");
  //  }























}
