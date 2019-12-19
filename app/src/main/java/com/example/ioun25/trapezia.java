package com.example.ioun25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;





import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static java.lang.Double.parseDouble;


//  import static com.example.ioun25.MainActivity.pel;

public  class trapezia extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener{
    ArrayList<String> pel;
    ArrayList<String> EIDH;
    GridView moviesList;
    public   List<String> values=new ArrayList<>();
    TextView trapezi;

    public String TrapeziPlirisPerigrafi="";
    public String TrapeziFull;
    ArrayList<String> KATHG;
    public String[] arrIdParagg=new String[100];
    private String m_Text = "";

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


            TrapeziPlirisPerigrafi=((TextView) v).getText().toString();
            String[] separated3 = ((TextView) v).getText().toString().split("€");
            String cTable= separated3[0];  // #52


      //   String cTable=((TextView) v).getText().toString(); //#52  ή  52

            //  String[] separated2 = message.split("#");
            //  message=separated[1];
            trapezi = (TextView)findViewById(R.id.textView);
            trapezi.setText(cTable); // o.toString());  //#52  ή  52
            int position=this.getPosition()  ;  //position=((TextView) v).getId();

            TrapeziFull=cTable+";"+arrIdParagg[position];  // #52 ; 234

           // Toast.makeText(getApplicationContext(),
            //        "You have clicked " +position+ ((TextView) v).getText(),
              //      Toast.LENGTH_LONG).show();
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
        // String message = intent.getStringExtra(EXTRA_MESSAGE);
         pel = intent.getStringArrayListExtra("mpel");
        EIDH = intent.getStringArrayListExtra("mEIDH");
        KATHG = intent.getStringArrayListExtra("mKathg");

       // SQLiteDatabase mydatabase=null;
      //  mydatabase = openOrCreateDatabase("eidh", MODE_PRIVATE, null);
     //   MainActivity.idBardia=ReadSql("select MAX(id) AS CID  from BARDIA");







        bb=(Button)findViewById(R.id.payment);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] cTrapeziFull = TrapeziFull.split(";");
                String idpar=cTrapeziFull[1];

                Double nn=ReadSqln("select AJIA-NUM1 FROM PARAGGMASTER WHERE ID="+idpar) ;
                TextView ypoloipo=findViewById(R.id.ypoloipo);
                ypoloipo.setText(Double.toString(nn));







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


    runRecycler();

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

/*
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
/*
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


*/



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


public void meriki(View view) {
    trapezi = (TextView)findViewById(R.id.textView);

    String skTrapezi=trapezi.getText().toString();
    if (skTrapezi.length()==0){
        Toast.makeText(trapezia.this,"διαλεξτε τραπεζι", Toast.LENGTH_LONG).show();
        return ;
    }


    show_meriki();
}
public void show_meriki(){

    trapezi = (TextView)findViewById(R.id.textView);
    String skTrapezi2=trapezi.getText().toString();
    if (skTrapezi2.substring(0, 1).equals("#")){
        //ok
    } else {
        Toast.makeText(trapezia.this,"το τραπέζι είναι κλειστό", Toast.LENGTH_LONG).show();
        return;
    }




    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Μετρητά");

// Set up the input
    final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
    builder.setView(input);

// Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            m_Text = input.getText().toString();
            Toast.makeText(trapezia.this,m_Text, Toast.LENGTH_LONG).show();
            m_Text = m_Text.replace(",", ".");

            trapezi = (TextView)findViewById(R.id.textView);
            String skTrapezi=trapezi.getText().toString();

            String cPlir,enanti,arxiko,Pliromes;
            cPlir="";
            Double SynPlir=0.0;
            if (skTrapezi.substring(0, 1).equals("#")){
                String[] separated3 = skTrapezi.split("#");
                String[] separated4 = TrapeziPlirisPerigrafi.split("€");
                arxiko=separated4[1];
                Pliromes=separated4[2];

                if (Pliromes == null || Pliromes.isEmpty() || Pliromes.equalsIgnoreCase("null")) {
                    SynPlir =  parseDouble(m_Text);
                } else{
                    SynPlir = parseDouble(Pliromes) + parseDouble(m_Text);
                }



                //if (parseDouble(Pliromes)==0) {
                //    SynPlir = parseDouble(arxiko) - parseDouble(m_Text);
                //} else {

                //}


                cPlir=Double.toString(SynPlir);

                skTrapezi=separated3[1];
            }

            SQLiteDatabase mydatabase = null;
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            mydatabase.execSQL("UPDATE TABLES SET CH2='"+cPlir+"' WHERE ONO='" + skTrapezi + "'");

            String[] cTrapeziFull = TrapeziFull.split(";");
            String idpar=cTrapeziFull[1];


            Double cc =SynPlir;

            // κραταω την πληρωμη σε μορφη πληρ*100 (γιατι num1 integer) για να βλεπω το υπολοιπο
            mydatabase.execSQL("UPDATE PARAGGMASTER SET num1="+cc.toString()+"   WHERE ID=" + idpar);



            mydatabase.close();
            listTRAPEZIA();
            runRecycler();




        }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    builder.show();
}




    // διαλεγει τροπο πληρωμης
    @Override
    public boolean onMenuItemClick(MenuItem item) { // click popup menu






        trapezi = (TextView)findViewById(R.id.textView);

        String skTrapezi=trapezi.getText().toString();
        if (skTrapezi.length()==0){
            Toast.makeText(trapezia.this,"διαλεξτε τραπεζι", Toast.LENGTH_LONG).show();
            return false;
        }





        String PLIROMI="";
        Integer mp=1;
        switch (item.getItemId()) {
            case R.id.one: {
                PLIROMI=MainActivity.mPliromes[1];
               // Toast.makeText(trapezia.this,"Clicked 1on the action", Toast.LENGTH_LONG).show();
        //        return true;
                mp=1;
                 break;
            }
            case R.id.two: {
                PLIROMI=MainActivity.mPliromes[2];
                mp=2;
                      break;
          //      return true;
            }
            case R.id.three: {
                PLIROMI=MainActivity.mPliromes[3];
                mp=3;
                break;
            //    return true;
            }
            case R.id.four: {
                PLIROMI=MainActivity.mPliromes[4];
                mp=4;
                break;
              //  return true;
            }

            case R.id.EXODOS: {
                return true;
            }


        }

Payment(Long.toString(mp) ) ;
        Toast.makeText(trapezia.this,PLIROMI, Toast.LENGTH_LONG).show();


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
        // intent.putExtra(EXTRA_MESSAGE, message2);
        //  intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
        //  intent.putExtra("mEIDH", pel3); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        //  intent.putExtra("mKathg", pelKathg); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ EIDH
        startActivity(intent);


    }

    // call order intent
  public void Paraggelia(View view) {

      trapezi = (TextView)findViewById(R.id.textView);

      String skTrapezi=trapezi.getText().toString();
      if (skTrapezi.length()==0){
          Toast.makeText(trapezia.this,"διαλεξτε τραπεζι", Toast.LENGTH_LONG).show();
          return ;
      }



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

  public void Payment(String tropos) {




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
        mydatabase.execSQL("UPDATE PARAGGMASTER SET CH2= datetime('now','localtime'),TROPOS="+tropos+"   WHERE ID=" + idpar);
  mydatabase.close();



        listTRAPEZIA();
      runRecycler();


    }

    //================= sqlliteEIDH====================
    public void listTRAPEZIA () {
        SQLiteDatabase mydatabase=null;
        Integer n=0;
        moviesList=(GridView)findViewById(R.id.grid);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
      //   List<String> values=new ArrayList<>();
        values=new ArrayList<String>();
        arrIdParagg=new String[100];
        try{
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ONO,KATEILHMENO,idparagg,CH1,ch2  from  TABLES", null);
            String kat="";
            String syn="";
            String syn2="";
            if (cursor2.moveToFirst()) {
                do {
                    n++;
                    kat="";
                    syn="";
                    syn2="";
                    if (cursor2.getShort(1)==1){
                        kat="#";
                        syn="€   "+cursor2.getString(3);
                        syn2=" €"+cursor2.getString(4);
                    }
                    values.add(kat + cursor2.getString(0)+syn+syn2);
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

    public void runRecycler(){

//==============================================================================================
        mRecyclerView = (RecyclerView) findViewById(R.id.grid2);
        //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));


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






    }



    public String ReadSql (String query ){
        String x;
        x="";
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);

        Cursor cursor5 = mydatabase.rawQuery(query, null);


        if (cursor5.moveToFirst()) {
            //do {
                x = Integer.toString(cursor5.getInt(0));
         //   } while (cursor5.moveToNext());
        }
    else{
    }
      return x;
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