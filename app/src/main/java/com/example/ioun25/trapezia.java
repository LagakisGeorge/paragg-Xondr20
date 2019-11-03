package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;





import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

// import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridTopOffsetItemDecoration;

import java.util.ArrayList;
import java.util.List;


import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static com.example.ioun25.MainActivity.EXTRA_MESSAGE;

//  import static com.example.ioun25.MainActivity.pel;

public  class trapezia extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener{
    ArrayList<String> pel;
    ArrayList<String> EIDH;
    GridView moviesList;
    public   List<String> values=new ArrayList<>();
    TextView trapezi;

    public String TrapeziFull;
    ArrayList<String> KATHG;
    public String[] arrIdParagg=new String[100];


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> planetList=new ArrayList();
//Other Stuff

   /*  private final String[] mPlanets = new String[] {
            "Mercury aaaa",
            "Venus dfd",
            "Earth fgf",
            "Mars fgfg",
            "Jupiter fgf",
            "Saturn fgf",
            "Uranus gf",
            "Neptune gfg fgf"
    };
 */



  /*
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            //define span size for this position
            //for example, if you have 2 column per row, you can implement something like that:
            if(position == youRule) {
                return 2; //item will take 2 column (full row size)
            } else {
                return 1; //you will have 2 rolumn per row
            }
        }
    });
*/

Button bb;
   // διαλεγω τραπεζι για παραγγελια ή πληρωμή
    private class PlanetViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public PlanetViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {



            String[] separated3 = ((TextView) v).getText().toString().split("€");
            String cTable= separated3[0];  // #52


      //   String cTable=((TextView) v).getText().toString(); //#52  ή  52

            //  String[] separated2 = message.split("#");
            //  message=separated[1];
            trapezi = (TextView)findViewById(R.id.textView);
            trapezi.setText(cTable); // o.toString());  //#52  ή  52
            int position=this.getPosition()  ;  //position=((TextView) v).getId();

            TrapeziFull=cTable+";"+arrIdParagg[position];  // #52 ; 234

            Toast.makeText(getApplicationContext(),
                    "You have clicked " +position+ ((TextView) v).getText(),
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



        bb=(Button)findViewById(R.id.payment);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(getApplicationContext(),bb);
                popup.setOnMenuItemClickListener(trapezia.this);

                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });










        // Capture the layout's TextView and set the string as its text
     //   TextView textView = findViewById(R.id.textView);
      //  textView.setText(message);

        listTRAPEZIA();




        mRecyclerView = (RecyclerView) findViewById(R.id.grid2);
      //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));



/*  κανει τα ιδια με την βιβλιοθηκη 'com.bignerdranch.android:simple-item-decoration:1.0.0'
// αλλα μπορει να γινει null και με βγαζει προειδοποιησεις το Android Stud
        DividerItemDecoration Hdivider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration Vdivider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Hdivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
        Vdivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
        mRecyclerView.addItemDecoration(Hdivider);
        mRecyclerView.addItemDecoration(Vdivider);
*/









    //    private GridLayoutManager mGridLayoutManager;
// ...
    //    mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

      //  int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
      //  mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));


        /* δουλευει με οριζοντιεσ γραμμεσ
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),VERTICAL); // HORIZONTAL or VERTICAL.
        mRecyclerView.addItemDecoration(dividerItemDecoration);
         */



        // με την βιβλιοθηκη 'com.bignerdranch.android:simple-item-decoration:1.0.0'
        int numColumns=3;
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider);

        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(horizontalDivider, verticalDivider, numColumns));
        int offsetPx = 10;
        mRecyclerView.addItemDecoration(new GridTopOffsetItemDecoration(offsetPx, numColumns));








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
                tv.setText(values.get(position)); // mPlanets[position]);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f);
               
                // αν θελω εικονα

                if (values.get(position).indexOf("€")>0){
                    tv.setCompoundDrawablePadding(24);
                    tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stars_white_24dp , 0, 0, 0);

                }else {
                    // αν θελω εικονα
                    tv.setCompoundDrawablePadding(24);
                    tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stars_black_24dp, 0, 0, 0);
                }
                // αν θελω εικονα




            }

            @Override
            public int getItemCount() {
                return values.size();
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


       // ΔΙΑΛΕΓΩ ΤΟ ΤΡΑΠΕΖΙ ΠΟΥ ΘΕΛΩ
        moviesList=(GridView)findViewById(R.id.grid);
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Object o = moviesList.getItemAtPosition(position);
                trapezi = (TextView)findViewById(R.id.textView);   // #52 €45.70
                //  textView.setText(message);
                String[] separated3 = o.toString().split("€");
                String cTable= separated3[0];  // #52
              TrapeziFull=cTable+";"+arrIdParagg[position];  // #52 ; 234
              //  String[] separated2 = message.split("#");
              //  message=separated[1];
                trapezi.setText(cTable); // o.toString());  //#52  ή  52
            }
        });

    //    listTRAPEZIA();
    }




    // διαλεγει τροπο πληρωμης
    @Override
    public boolean onMenuItemClick(MenuItem item) { // click popup menu

        String PLIROMI;

        switch (item.getItemId()) {
            case R.id.one: {
                PLIROMI=MainActivity.mPliromes[1];
               // Toast.makeText(trapezia.this,"Clicked 1on the action", Toast.LENGTH_LONG).show();
        //        return true;
            }
            case R.id.two: {
                PLIROMI=MainActivity.mPliromes[2];

          //      return true;
            }
            case R.id.three: {
                PLIROMI=MainActivity.mPliromes[3];

            //    return true;
            }
            case R.id.four: {
                PLIROMI=MainActivity.mPliromes[4];

              //  return true;
            }

            Toast.makeText(trapezia.this,PLIROMI, Toast.LENGTH_LONG).show();


        }





        return true;
    }


/*    @Override
  //  public abstract boolean onMenuItemClick (MenuItem item)
    public  boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.one:
                Toast.makeText(this,"item1 clicked",Toast.LENGTH_SHORT);
                return true;
            case R.id.two:
                Toast.makeText(this,"item1 clicked",Toast.LENGTH_SHORT);
                return true;
            case R.id.three:
                Toast.makeText(this,"item1 clicked",Toast.LENGTH_SHORT);
                return true;

            default:
                return false;
        }
  }

*/
// call main intent
    public void kentriko (View view){
        Intent intent = new Intent(this, MainActivity.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
        String message2 ="---" ;// EditText.GetText().toString();
        intent.putExtra(EXTRA_MESSAGE, message2);
        //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
        //  intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        //  intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);


    }

    // call order intent
  public void Paraggelia(View view) {
      Intent intent = new Intent(view.getContext(), order.class);
      // intent.putExtra(EXTRA_MESSAGE, o.toString());
     // String ctrapezi;
     // trapezi = (TextView)findViewById(R.id.textView);
     // ctrapezi=trapezi.getText().toString();

      intent.putExtra("mpel2", TrapeziFull);  // αριθμος τραπεζιου
      intent.putExtra("WhoCall", "trapezia");  // ποια φορμα καλει
      intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
      intent.putExtra("mEIDH", EIDH); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
      intent.putExtra("mKATHG", KATHG); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
      // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
      trapezia.this.startActivity(intent);


  }

  public void Payment(View view) {
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        trapezi = (TextView)findViewById(R.id.textView);

        String skTrapezi=trapezi.getText().toString();

        if (skTrapezi.substring(0, 1).equals("#")){
            String[] separated3 = skTrapezi.split("#");
            skTrapezi=separated3[1];
        }

        String[] cTrapeziFull = TrapeziFull.split(";");
        String idpar=cTrapeziFull[1];



        mydatabase.execSQL("UPDATE TABLES SET KATEILHMENO=0,IDPARAGG=0 WHERE ONO='" + skTrapezi + "'");
        mydatabase.execSQL("UPDATE PARAGGMASTER SET CH2= datetime('now','localtime'),AJIA=0 ,TROPOS=2  WHERE ID=" + idpar);
  mydatabase.close();



        listTRAPEZIA();



    }

    //================= sqlliteEIDH====================
    public void listTRAPEZIA () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.grid);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
      //   List<String> values=new ArrayList<>();

        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO,KATEILHMENO,idparagg,CH1  from  TABLES", null);
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    kat="";
                    syn="";
                    if (cursor2.getShort(1)==1){
                        kat="#";
                        syn="€"+cursor2.getString(3);
                    }
                    values.add(kat + cursor2.getString(0)+syn);
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