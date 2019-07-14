package com.example.ioun25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    ListView moviesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezia);
         Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
         pel = intent.getStringArrayListExtra("mpel");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        List<String> values=new ArrayList<>();

        for (int i = 0; i < pel.size(); i++) {
            values.add(pel.get(i));
        }

         moviesList=(ListView)findViewById(R.id.listview);
       // GridView myGrid=(GridView)findViewById(R.id.grid);
      //  myGrid.setAdapter(new ArrayAdapter<String>(this,R.layout.cell,values));

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

        moviesList.setAdapter(arrayAdapter);
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Object o = moviesList.getItemAtPosition(position);
    /* write you handling code like...
    String st = "sdcard/";
    File f = new File(st+o.toString());
    */

                Intent intent = new Intent(view.getContext(), order.class);
               // intent.putExtra(EXTRA_MESSAGE, o.toString());
                intent.putExtra("mpel2", o.toString());
               // intent.putExtra("mpel", pel); // ΣΤΕΛΝΩ ΤΟΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΤΡΑΠΕΖΙΑ
                trapezia.this.startActivity(intent);


              //  Intent intent = new Intent(CurrentActivity.this, OtherActivity.class);
              //  CurrentActivity.this.startActivity(intent);


               // Intent intent =  new Intent(trapezia.this, SendMessage.class);
               // String message = "abc";
               // intent.putExtra(EXTRA_MESSAGE, message);
               // startActivity(intent);
            }
        });



/*
        private ArrayList<String> mArray;
        private String mString;
        private int mValue;

        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("arrayListExtra", mArray);
        intent.putExtra("stringExtra", mString);
        intent.putExtra("intExtra", mValue);
        startActivity(intent);
        All of these data types (and more) can be seamlessly passed in an Intent. Then, you can access them on the other side as follows:

        Intent intent = getIntent();
        ArrayList<String> array = intent.getStringArrayListExtra("arrayListExtra");
        String string = intent.getStringExtra("stringExtra");
        int value = intent.getIntExtra("intExtra", 0);

*/


    }
}
