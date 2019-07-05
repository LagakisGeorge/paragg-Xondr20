package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Policy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
  //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

  //   StrictMode.setThreadPolicy(policy);
  Handler handler;
    Button button;
    String Pelatis;
    Button bb;
    ResultSet rs;
    TextView ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                TextView h;
                h=(TextView)findViewById(R.id.hello);
                h.setText(Pelatis);

                // ...
            }
        };



        bb=(Button)findViewById(R.id.button2);
        bb.setOnClickListener(new View.OnClickListener(){
                                  @Override
                                  public void onClick(View view) {

                                      Runnable aRunnable = new Runnable() {
                                          public void run() {
                                              start();
                                              handler.sendEmptyMessage(0);
                                          }
                                      };

                                      Thread aThread = new Thread(aRunnable);
                                      aThread.start();



                                  }

        });



        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Runnable aRunnable = new Runnable() {
                    public void run() {
                        start();

                        handler.sendEmptyMessage(0);
                    }
                };

                Thread aThread = new Thread(aRunnable);
                aThread.start();


            }
        });

    }
// If you cannot connect with SQL server(express), you can check here: http://stackoverflow.com/questions/10522120/connecting-to-local-ms-sql-server

    private void start() {
        System.out.println("START");

        String query = "SELECT top 100 * FROM PEL";
        rs = getData(query);
        TextView h;
        h=(TextView)findViewById(R.id.hello);

        try {
            while (rs.next()) {
                System.out.println(rs.getString("EPO"));
                Pelatis=rs.getString("EPO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("END");
    }








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
   // OK   private String URL = "jdbc:jtds:sqlserver://192.168.1.4:52735/DHMOS;instance=SQLEXPRESS;";
    private String URL = "jdbc:jtds:sqlserver://192.168.1.7:49702/MERCURY;instance=SQLEXPRESS;";
    private String USER = "sa";
    private String PASS = "p@ssw0rd"; //"12345678";
    private static ResultSet RESULT;




}
