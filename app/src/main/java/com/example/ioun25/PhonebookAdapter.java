package com.example.ioun25;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public class PhonebookAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<Phonebook> listPhonebook;

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

        // Set the onClick Listener on this button
        Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        if (entry.getStatus()>0){  // μολις παραγγειλε
            tvName.setTextColor(RED);
          //  tvName.setEnabled(true);
            tvName.setText(entry.getStatus()+entry.getName());

        } else{ //παλια
            tvName.setTextColor(GRAY );
          //  tvName.setEnabled(false);
            btnRemove.setWidth(70);
          //debug  tvName.setText(entry.getStatus()+entry.getName());


        }

        btnRemove.setFocusableInTouchMode(false);
        btnRemove.setFocusable(false);
        btnRemove.setOnClickListener(this);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        btnRemove.setTag(entry);

        // btnRemove.setId(position);


        return convertView;
    }

    @Override
    public void onClick(View view) {
        Phonebook entry = (Phonebook) view.getTag();
        listPhonebook.remove(entry);
        // listPhonebook.remove(view.getId());
        notifyDataSetChanged();

    }

    private void showDialog(Phonebook entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }

}
