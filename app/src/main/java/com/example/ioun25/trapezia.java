package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;

//  import static com.example.ioun25.MainActivity.pel;

public class trapezia extends AppCompatActivity {
    ArrayList<String> pel;
    ArrayList<String> EIDH;
    GridView moviesList;

    ArrayList<String> KATHG;
    public String[] arrIdParagg=new String[100];


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> planetList=new ArrayList();
//Other Stuff

    private final String[] mPlanets = new String[] {
            "Mercury",
            "Venus",
            "Earth",
            "Mars",
            "Jupiter",
            "Saturn",
            "Uranus",
            "Neptune"
    };



    private class PlanetViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public PlanetViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),
                    "You have clicked " + ((TextView) v).getText(),
                    Toast.LENGTH_LONG).show();
        }
    }






  //  MyRecyclerViewAdapter adapter;
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


        mRecyclerView = (RecyclerView) findViewById(R.id.grid2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<PlanetViewHolder>() {

            @Override
            public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false);
                PlanetViewHolder vh = new PlanetViewHolder(v);
                return vh;
            }

            @Override
            public void onBindViewHolder(PlanetViewHolder vh, int position) {
                TextView tv = (TextView) vh.itemView;
                tv.setText(mPlanets[position]);
                tv.setCompoundDrawablePadding(24);
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stars_black_24dp, 0, 0, 0);
            }

            @Override
            public int getItemCount() {
                return mPlanets.length;
            }
        });






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
                intent.putExtra("mpel2", o.toString()+";"+arrIdParagg[position]);  // αριθμος τραπεζιου
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
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        final List<String> values=new ArrayList<>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO,KATEILHMENO,idparagg  from  TABLES", null);
            String kat="";
            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    kat="";
                    if (cursor2.getShort(1)==1){ kat="#";
                    }
                    values.add(kat + cursor2.getString(0));
                    arrIdParagg[n-1]=Long.toString(cursor2.getLong(2));

                } while (cursor2.moveToNext());
            }
            mydatabase.close();
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);

           // TrapeziaList.setAdapter(arrayAdapter);

            //final MyAdapter adapter = new MyAdapter();
          //  rv.setAdapter(adapter);
         //   GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
          //  rv.setLayoutManager(mLayoutManager);

// set up the RecyclerView
         //   RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        //    TrapeziaList.setLayoutManager(new LinearLayoutManager(this));
         //   adapter = new MyRecyclerViewAdapter(this, animalNames);
         //   adapter.setClickListener(this);
         //   recyclerView.setAdapter(adapter);












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