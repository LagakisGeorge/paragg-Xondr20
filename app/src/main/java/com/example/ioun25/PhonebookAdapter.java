package com.example.ioun25;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;


import static com.example.ioun25.order.EIDH_PARAGG;


public class PhonebookAdapter extends BaseAdapter  {  // implements OnClickListener
    private Context context;

    private List<Phonebook> listPhonebook;
    private Integer nPointer;

    public PhonebookAdapter(Context context, List<Phonebook> listPhonebook) {
        this.context = context;
        this.listPhonebook = listPhonebook;
    }

    public int getCount() {
        return listPhonebook.size();
    }



    public Object getItem(int position) {

        return listPhonebook.get(position);
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
        Phonebook entry = listPhonebook.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.phone_row, null);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(entry.getName());


     //   tvName.setTextColor(RED);

        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPosothta);
        tvPhone.setText(entry.getPosothta());


        TextView tvMail = (TextView) convertView.findViewById(R.id.tvTimh);
        tvMail.setText(entry.getTimh());

        TextView tvProsu = (TextView) convertView.findViewById(R.id.tvProsu);
        tvProsu.setText(entry.getProsu());

        TextView tvSxolia = (TextView) convertView.findViewById(R.id.tvSxolia);
        tvSxolia.setText(entry.getSxolia());

        nPointer=entry.getPointer();

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
