package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class PARAGGELIAX extends AppCompatActivity {
    ListView moviesList;


    public List<EIDOS> listOfEIDOS = new ArrayList<EIDOS>();
    public List<EIDOS> listOfEIDOS2 = new ArrayList<EIDOS>(); // gia show Eggtim

    GridView prosueta;
    public ArrayList<String> values;
    public String fID;
    public Integer fPosition=-1 ;
    public  Integer fSearchEidh=0;
    public String fArParagg="0";  // αριθμος παραγγελίας =id ParaggMaster
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraggeliax);


        ListView list = (ListView) findViewById(R.id.listEIDH);
        list.setClickable(true);
  //      Show();




      //  trapezia ttr=new trapezia();
      Integer nn=1+ReadSqlni("select ARITMISI FROM ARITMISI WHERE ID=1") ;
        TextView ARITMISI=findViewById(R.id.ARITMISI);  // timh
        ARITMISI.setText(nn.toString());













        // ***********************************ΑΝΑΖΗΤΗΣΗ ΜΕ ΟΝΟΜΑ ΠΕΛΑΤΗ
        Button ANAZ;
        ANAZ=findViewById(R.id.ANAZ);
        EditText t1=findViewById(R.id.t1);


        ANAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText t2=findViewById(R.id.t2);
                String str=t2.getText().toString();
                if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")){
                    return;
                }



                if ( fSearchEidh==1) {   // ψαχχνω είδη)

                    Show();
                }else{
                    Show();
                }
                PARAGGELIAX.EIDHadapter adapter = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS);
                ListView list = (ListView) findViewById(R.id.listEIDH);
                list.setAdapter(adapter);

            }
        });

        // ΚΛΕΙΔΩΝΩ ΤΟΝ ΠΕΛΑΤΗ
        Button tV5;
        tV5=findViewById(R.id.kataxPEL);
        tV5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fSearchEidh==1 ){
                    return;
                }

                fSearchEidh=1; //απο δω και περα ψαχχνω είδη
               // κατεβαζω τον πελάτη
                  EditText t5=findViewById(R.id.t5);    // getProsu => ο κωδικος του πελατη
                  t5.setText(listOfEIDOS.get(fPosition).getName()+";"+fID+";"+listOfEIDOS.get(fPosition).getProsu()); //kathg

                // μηδενιζω τα υπολοιπα κουτάκια
                EditText t2=findViewById(R.id.t2);  // onoma
                t2.setText("");

                EditText t1=findViewById(R.id.t1);  // timh
                t1.setText("");
                EditText tp=findViewById(R.id.Timhp);
                tp.setText(""); //kathg

                TextView ip1;
                ip1=findViewById(R.id.ip1);
                ip1.setText("ΠΕΡΙΓΡΑΦΗ ΕΙΔΟΥΣ");



                SQLiteDatabase mydatabase=null;
                mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
                String s = "0";  // αριθμος παργγελιας
                String Q;

                    Q = "INSERT INTO PARAGGMASTER (NUM1,AJIA,TRAPEZI,IDBARDIA,CH1) VALUES (0,0,'" + fID + "',"+fID+",datetime('now','localtime'))";

                    mydatabase.execSQL(Q);

                    Cursor cursor5 = mydatabase.rawQuery("select max(ID) from PARAGGMASTER ", null);
                    // long n=0;
                    // looping through all rows and adding to list

                    if (cursor5.moveToFirst()) {
                        do {
                            s = cursor5.getString(0);
                            // n= Integer.parseInt(cursor5.
                            // movies.add(cursor.getString(0));
                        } while (cursor5.moveToNext());
                    }
              //  }else{
              //      s=fIDPARAGG;
              //  }

                fArParagg=s;


               Button ANAZ=findViewById(R.id.ANAZ);
                ANAZ.setText("ΑΝΑΖ.ΕΙΔΟΥΣ");
             //   t2=findViewById(R.id.t2);
               // t2.setFocusable(false); //setText("ΔΩΣΕ ΚΩΔ.ΕΙΔΟΥΣ")  ;

                Button  kataxEID=findViewById(R.id.kataxEID);
                kataxEID.setVisibility(View.VISIBLE);


                Button  kataxPEL=findViewById(R.id.kataxPEL);
                kataxPEL.setVisibility(View.INVISIBLE);

              //  EditText t2=findViewById(R.id.Timhp);  // onoma
                t2.setFocusableInTouchMode(true);
                t2.setBackgroundColor(YELLOW);
                t2.requestFocus();





            }
        });



        // ΚΛΕΙΔΩΝΩ ΤΟ ειδος
        Button kataxEID;
        kataxEID=findViewById(R.id.kataxEID);
        kataxEID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fSearchEidh==0 ){
                    return;
                }

                SQLiteDatabase mydatabase=null;
                mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);



                EditText t1=findViewById(R.id.t1);  // timh
                String ct1=t1.getText().toString();

                EditText tp=findViewById(R.id.Timhp);
                String cPos=tp.getText().toString();




                cPos=cPos.replace(",",".");
                ct1=ct1.replace(",",".");
                String cName=listOfEIDOS.get(fPosition).getName().substring(0,24);

                String Q;
                Q="INSERT INTO PARAGG (IDPARAGG,TRAPEZI,ONO,POSO,TIMH) VALUES ("+fArParagg+",'','"+ cName+"',";
                Q=Q+ cPos+","+ ct1+");";
                mydatabase.execSQL(Q);

                mydatabase.close();

                // μηδενιζω τα υπολοιπα κουτάκια
                EditText t2=findViewById(R.id.t2);  // onoma
                t2.setText("");

               // EditText t1=findViewById(R.id.t1);  // timh
                t1.setText("  ");
              //  EditText tp=findViewById(R.id.Timhp);
                tp.setText(""); //kathg
                tp.setBackgroundColor(WHITE);



               show_EGGTIM();

                Button tV5;
                tV5=findViewById(R.id.kataxPEL);
                tV5.setVisibility(View.VISIBLE);
                tV5.setText("ok "+listOfEIDOS.get(fPosition).getName().substring(0,10));


                //  EditText t2=findViewById(R.id.Timhp);  // onoma
                t2.setFocusableInTouchMode(true);
                t2.setBackgroundColor(YELLOW);
                t2.requestFocus();

              //  t2.setFocusable(false);
              //  t2.setBackgroundColor(YELLOW);


            }
        });




// ========================================  ΔΙΑΛΕΓΩ ΤΟ ΕΙΔΟΣ ==========================================================

        PARAGGELIAX.EIDHadapter adapter = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS);
       // SHMADEYV TO EIDOS  // PELATH
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                fPosition=position;
                String a=listOfEIDOS.get(position).getName();


                EditText t1=findViewById(R.id.t1);  // timh
                t1.setText(listOfEIDOS.get(position).getTimh().toString());


               // EditText tp=findViewById(R.id.Timhp);
               // tp.setText(listOfEIDOS.get(position).getTimhp().toString()); //kathg


                EditText tp=findViewById(R.id.Timhp);
                tp.setText(""); //kathg

                EditText t2=findViewById(R.id.t2);  // onoma
                t2.setText(listOfEIDOS.get(position).getName());
                fID=Integer.toString(listOfEIDOS.get(position).getID());  //id

                EditText t4=findViewById(R.id.t4);//prosu
                t4.setText(listOfEIDOS.get(position).getProsu());

                EditText TimhP=findViewById(R.id.Timhp );//prosu
               //  TimhP.setFocusable(false);
               // TimhP.setBackgroundColor(YELLOW);

                EditText Timhp=findViewById(R.id.Timhp);  // onoma
                Timhp.setFocusableInTouchMode(true);
                Timhp.setBackgroundColor(YELLOW);
                Timhp.requestFocus();
                t2.setBackgroundColor(WHITE);

            }
        });

        list.setAdapter(adapter);


        EditText t2=findViewById(R.id.t2);  // onoma
       t2.setFocusableInTouchMode(true);
       t2.requestFocus();
       // t1.setFocusable(false);




    }



    public void SAVE (View view) {
        EditText t1=findViewById(R.id.t1);
        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);
        EditText t5=findViewById(R.id.t5);
        EditText tp=findViewById(R.id.Timhp);
        listOfEIDOS2 = new ArrayList<EIDOS>();
        listOfEIDOS = new ArrayList<EIDOS>();

        PARAGGELIAX.EIDHadapter adapter = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS2);
        ListView list = (ListView) findViewById(R.id.listEGGTIM);
        list.setAdapter(adapter);


        PARAGGELIAX.EIDHadapter adapter2 = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS);
        ListView list2 = (ListView) findViewById(R.id.listEIDH);
        list2.setAdapter(adapter2);
        fSearchEidh=0;



    //    SQLiteDatabase mydatabase=null;

    //    mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
    //    mydatabase.execSQL("update EIDH set  num1="+tp.getText()+", timh="+t1.getText()+",ONO='"+t2.getText()+"',CH2='"+t4.getText()+"',CH1='"+t5.getText()+"' where ID="+fID);

    //    Show();
    }
    public void NEO_EIDOS (View view) {

      /*
        EditText t1=findViewById(R.id.t1);

        EditText t2=findViewById(R.id.t2);
        EditText t4=findViewById(R.id.t4);
        EditText t5=findViewById(R.id.t5);
        SQLiteDatabase mydatabase=null;

        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        String sql;
        sql="INSERT INTO  EIDH ( timh,ONO,CH2,ch1) VALUES ("+t1.getText()+",'"+t2.getText()+"','"+t4.getText()+"','"+t5.getText()+"');";
        mydatabase.execSQL(sql);
        Show();
        */

    }
    public void DELETE (View view) {
       /* EditText t1 = findViewById(R.id.t1);
        EditText t2 = findViewById(R.id.t2);
        EditText t4 = findViewById(R.id.t4);
        SQLiteDatabase mydatabase = null;

        mydatabase = openOrCreateDatabase("eidh", MODE_PRIVATE, null);
        mydatabase.execSQL("DELETE FROM  EIDH  where ID=" + fID);

        Show();
        */


    }
    public void Show () {

        SQLiteDatabase mydatabase=null;
        Integer n=0;
       // moviesList2=(ListView)findViewById(R.id.listEidhp);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        //   List<String> values=new ArrayList<>();
     //   values=new ArrayList<String>();

        try{
            /*mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            Cursor cursor2 = mydatabase.rawQuery("select ID,ONO,TIMH,CH2,CH1  from  EIDH", null);
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    values.add( Integer.toString(cursor2.getInt(0) ));
                    values.add(cursor2.getString(1));
                    values.add( Double.toString(cursor2.getDouble(2) ));
                    values.add(cursor2.getString(3));
                    values.add(cursor2.getString(4));
                } while (cursor2.moveToNext());
            }
            mydatabase.close();
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
            moviesList.setAdapter(arrayAdapter);
*/

            listOfEIDOS = new ArrayList<EIDOS>();



            String mName;
            EditText t2=findViewById(R.id.t2);  // onoma
            mName=t2.getText().toString();
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
            String SQL;
            if ( fSearchEidh==0){
                SQL="select  ID,ONO,IFNULL(AFM,'') AS CCH2,KOD from  PEL   WHERE ONO LIKE '%"+mName+"%' order by ONO limit 300; ";
                Button ANAZ;




            }else{
                SQL="select  ID,ONO,TIMH,IFNULL(CH2,'') AS CCH2,IFNULL(CH1,'') AS CCH1 ,IFNULL(num1,0) AS TIMHP  from  EIDH   WHERE ONO LIKE '%"+mName+"%' order by ONO desc limit 300 ";
            }
            Cursor cursor2 = mydatabase.rawQuery(SQL, null);  // WHERE ONO LIKE '%"+mName+"%'
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    if ( fSearchEidh==0) {
                        listOfEIDOS.add(new EIDOS(cursor2.getInt(0) , cursor2.getString(1),0.0,cursor2.getString(2),cursor2.getString(3),0.0,0));
                    } else {
                        listOfEIDOS.add(new EIDOS(cursor2.getInt(0) , cursor2.getString(1),cursor2.getDouble(2),cursor2.getString(3),cursor2.getString(4),cursor2.getDouble(5),0));
                    }


                } while (cursor2.moveToNext());
            }
            mydatabase.close();
        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }


    public void show_EGGTIM () {

        SQLiteDatabase mydatabase=null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        String SQL="";
        // if ( fSearchEidh==0){
        // SQL="select  ID,ONO,IFNULL(AFM,'') AS CCH2,KOD from  PEL   WHERE ONO LIKE '%"+mName+"%' order by ONO limit 300; ";
        // }else{
        SQL="select  ONO,POSO,TIMH  from  PARAGG   WHERE  IDPARAGG="+fArParagg +" order by id ";
        //  }
        Cursor cursor2 = mydatabase.rawQuery(SQL, null);  // WHERE ONO LIKE '%"+mName+"%'
        listOfEIDOS2 = new ArrayList<EIDOS>();

        String kat="";
        String syn="";
        if (cursor2.moveToFirst()) {
            do {
                // Integer ID,String name, Double timh,String prosu,String kathg,Double timhp,Integer status
              //  if ( fSearchEidh==0) {
                listOfEIDOS2.add(new EIDOS(0 , cursor2.getString(0),cursor2.getDouble(2),"","",cursor2.getDouble(1),0));
               // } else {
                 //   listOfEIDOS.add(new EIDOS(cursor2.getInt(0) , cursor2.getString(1),cursor2.getDouble(2),cursor2.getString(3),cursor2.getString(4),cursor2.getDouble(5),0));
              //  }


                // values.add( Integer.toString(cursor2.getInt(1) ));
                // values.add(cursor2.getString(0));

            } while (cursor2.moveToNext());
        }

        PARAGGELIAX.EIDHadapter adapter = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS2);
        ListView list = (ListView) findViewById(R.id.listEGGTIM);
        list.setAdapter(adapter);











mydatabase.close();




    }

   /*
        Integer n=0;
       // moviesList=findViewById(R.id.listKathgp);
        //recyclerView=(RecyclerView) findViewById(R.id.grid2);
        //   List<String> values=new ArrayList<>();
        //values=new ArrayList<String>();

        try{

            SQLiteDatabase mydatabase=null;
            mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
           String SQL="";
           // if ( fSearchEidh==0){
               // SQL="select  ID,ONO,IFNULL(AFM,'') AS CCH2,KOD from  PEL   WHERE ONO LIKE '%"+mName+"%' order by ONO limit 300; ";
           // }else{
                SQL="select  ID,ONO,TIMH,IFNULL(CH2,'') AS CCH2,IFNULL(CH1,'') AS CCH1 ,IFNULL(num1,0) AS TIMHP  from  EIDH   WHERE  IDPARAGG="+fArParagg +" order by id ";
          //  }
            Cursor cursor2 = mydatabase.rawQuery(SQL, null);  // WHERE ONO LIKE '%"+mName+"%'


            listOfEIDOS = new ArrayList<EIDOS>();

         //   Cursor cursor2 = mydatabase.rawQuery("SELECT ID,ONO,POSO,TIMH FROM PARAGG WHERE IDPARAGG="+fArParagg, null);
            String kat="";
            String syn="";
            if (cursor2.moveToFirst()) {
                do {
                    if ( fSearchEidh==0) {
                        listOfEIDOS.add(new EIDOS(cursor2.getInt(0) , cursor2.getString(1),0.0,cursor2.getString(2),cursor2.getString(3),0.0,0));
                    } else {
                        listOfEIDOS.add(new EIDOS(cursor2.getInt(0) , cursor2.getString(1),cursor2.getDouble(2),cursor2.getString(3),cursor2.getString(4),cursor2.getDouble(5),0));
                    }


                   // values.add( Integer.toString(cursor2.getInt(1) ));
                   // values.add(cursor2.getString(0));

                } while (cursor2.moveToNext());
            }
            mydatabase.close();




            PARAGGELIAX.EIDHadapter adapter = new PARAGGELIAX.EIDHadapter(PARAGGELIAX.this, listOfEIDOS);
            ListView list = (ListView) findViewById(R.id.listEGGTIM);
            list.setAdapter(adapter);
        //    moviesList.setAdapter(adapter);

     //       ArrayAdapter<String> arrayAdapter =
      //              new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
      //      moviesList.setAdapter(arrayAdapter);


        } catch (SQLiteAccessPermException e) {
            e.printStackTrace();
        }
    }
*/



    public class EIDHadapter extends BaseAdapter {  // implements OnClickListener
        private Context context;

        private List<EIDOS> listOfEIDOS;
        private Integer nPointer;

        public EIDHadapter(Context context, List<EIDOS> listOfEIDOS) {
            this.context = context;
            this.listOfEIDOS = listOfEIDOS;
        }

        public int getCount() {
            return listOfEIDOS.size();
        }



        public Object getItem(int position) {

            return listOfEIDOS.get(position);
            // return listPhonebook.get(getCount() - position - 1);
        }

        public long getItemId(int position) {

            return position;  // κανονικη : το τελευταιο κατω
            //  return  ( getCount() - position - 1);
        }

        //  @Override
        //  public Object getItem(int position) {
        //      return getCount() - position - 1;
        //  }




        public View getView(int position, View convertView, ViewGroup viewGroup) {
            EIDOS entry = listOfEIDOS.get(position);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.paragg_line, null);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvName.setText(entry.getName());



            TextView tvPhone = (TextView) convertView.findViewById(R.id.tvID);
            tvPhone.setText(entry.getID().toString());


            TextView tvMail = (TextView) convertView.findViewById(R.id.tvTimh);
            tvMail.setText(entry.getTimh().toString());

         //   TextView tvProsu = (TextView) convertView.findViewById(R.id.tvProsu);
         //   tvProsu.setText(entry.getProsu());

            TextView tvTimhp = (TextView) convertView.findViewById(R.id.tvTimhp);
            tvTimhp.setText(entry.getTimhp().toString());

          //  TextView tvKathg = (TextView) convertView.findViewById(R.id.tvSxolia);
          //  tvKathg.setText(entry.getKathg());
          //  tvKathg.setTextColor(Color.GREEN);

            //  nPointer=entry.getPointer();

            // Set the onClick Listener on this button
            Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
            if (entry.getStatus()>0){  // μολις παραγγειλε
                tvName.setTextColor(Color.GREEN);
                //  tvName.setEnabled(true);
                //  tvName.setText(entry.getPointer()+entry.getName());

            } else{ //παλια
                tvName.setTextColor(Color.BLACK );
                //  tvName.setEnabled(false);
                btnRemove.setWidth(70);
                //debug  tvName.setText(entry.getStatus()+entry.getName());
            }

            if (entry.getName().substring(0,1).equals("*")){
                tvName.setTextColor(Color.GREEN);
            }






            btnRemove.setFocusableInTouchMode(false);
            btnRemove.setFocusable(false);




            //To lazy to implement interface
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Phonebook entry = (Phonebook) v.getTag();
                    // TextView tvMail = (TextView) order.findViewById(R.id.tvTimh);

                    //              Button mer=super.findViewById(R.id.merikiB);

                    Toast.makeText(context,nPointer.toString(),Toast.LENGTH_SHORT).show();
                    //  listPhonebook.remove(entry);
                    // listPhonebook.remove(view.getId());

                    //  notifyDataSetChanged();

                }
            });





            //debug   btnRemove.setOnClickListener(this);


            // Set the entry, so that you can capture which item was clicked and
            // then remove it
            // As an alternative, you can use the id/position of the item to capture
            // the item
            // that was clicked.
            btnRemove.setTag(entry);

            // btnRemove.setId(position);


            return convertView;
        }

        //  @Override   // θα χρειαστεί στον ορισμό της κλάσης ... implements OnClickListener {...
        // και στο getView()       btnRemove.setOnClickListener(this);
        //public void onClick(View view) {
        //   Phonebook entry = (Phonebook) view.getTag();
        //   listPhonebook.remove(entry);
        //    // listPhonebook.remove(view.getId());
        //    notifyDataSetChanged();
        // }

        private void showDialog(Phonebook entry) {
            // Create and show your dialog
            // Depending on the Dialogs button clicks delete it or do nothing
        }

    }



    public Integer ReadSqlni (String query ){
        Integer x;
        x=0;
        SQLiteDatabase mydatabase = null;
        mydatabase = openOrCreateDatabase("eidh",MODE_PRIVATE,null);
        Cursor cursor5 = mydatabase.rawQuery(query, null);
        if (cursor5.moveToFirst()) {
            //do {
            x = cursor5.getInt(0);
            //   } while (cursor5.moveToNext());
        }
        else{
        }
        return x;
    }

}
