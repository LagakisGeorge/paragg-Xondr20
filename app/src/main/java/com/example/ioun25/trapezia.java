package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;

//  import static com.example.ioun25.MainActivity.pel;

public class trapezia extends AppCompatActivity {
    ArrayList<String> pel;
    ArrayList<String> EIDH;
    GridView moviesList;
    ArrayList<String> KATHG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezia);


        EIDH=new ArrayList<String>();
        pel=new ArrayList<String>();

         Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
         pel = intent.getStringArrayListExtra("mpel");
        EIDH = intent.getStringArrayListExtra("mEIDH");
        KATHG = intent.getStringArrayListExtra("mKathg");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);



     /*
        List<String> values=new ArrayList<>();

        for (int i = 0; i < pel.size(); i++) {
            values.add(pel.get(i));
        }

        for (int i = 0; i < EIDH.size(); i++) {
            values.add(EIDH.get(i));
        }

        for (int i = 0; i < KATHG.size(); i++) {
            values.add(KATHG.get(i));
        }



         moviesList=(GridView)findViewById(R.id.grid);
       // GridView myGrid=(GridView)findViewById(R.id.grid);
      //  myGrid.setAdapter(new ArrayAdapter<String>(this,R.layout.cell,values));

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

        moviesList.setAdapter(arrayAdapter);
*/

        moviesList=(GridView)findViewById(R.id.grid);
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Object o = moviesList.getItemAtPosition(position);


                Intent intent = new Intent(view.getContext(), order.class);
               // intent.putExtra(EXTRA_MESSAGE, o.toString());
                intent.putExtra("mpel2", o.toString());
                intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                intent.putExtra("mEIDH", EIDH); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
                intent.putExtra("mKATHG", KATHG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
                // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                trapezia.this.startActivity(intent);
            }
        });

        listTRAPEZIA();

    }




    //================= sqlliteEIDH====================
    public void listTRAPEZIA () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.grid);
        List<String> values=new ArrayList<>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO,KATEILHMENO  from  TABLES", null);
            String kat="";
            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    kat="";
                    if (cursor2.getShort(1)==1){ kat="*";
                    }
                    values.add(kat + cursor2.getString(0));

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

            moviesList.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }



}
/*

 =============  split ============================
String str = "geekss@for@geekss";
        String[] arrOfStr = str.split("@", 5);

        for (String a : arrOfStr)
            System.out.println(a);
    }
Output:

geekss
for
geekss


 */