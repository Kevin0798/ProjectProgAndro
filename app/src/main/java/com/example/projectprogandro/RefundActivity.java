package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RefundActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        setTitle("REFUND");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        databaseReference = FirebaseDatabase.getInstance().getReference("UserPayment");
        ListView lv = (ListView) findViewById(R.id.listRefund);
        lv.setAdapter(new MyListAdapter(this, R.layout.cardview, arrayList));
        Button button = (Button) findViewById(R.id.button3);

       databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String myChildValues = dataSnapshot.getValue(String.class);
                arrayList.add(myChildValues);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog dialog = new AlertDialog.Builder(RefundActivity.this)
                       .setTitle("Apakah anda yakin ingin refund?")
                       .setPositiveButton("Iya", new DialogInterface.OnClickListener(){
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               DatabaseReference refund = FirebaseDatabase.getInstance().getReference("UserPayment");
                               refund.removeValue();
                               startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                           }
                       })
                       .setNegativeButton("Tidak", null)
                       .create();
               dialog.show();
           }
       });
    }

    private class MyListAdapter extends ArrayAdapter<String>{

        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent) {

            ViewHolder mainViewHolder = null;
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView  = inflater.inflate(layout,parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.anakKecil = (TextView) convertView.findViewById(R.id.listanakKecil);
                viewHolder.orangDewasa = (TextView) convertView.findViewById(R.id.listorangDewasa);
                viewHolder.Asal = (TextView) convertView.findViewById(R.id.listAsal);
                viewHolder.Tujuan = (TextView) convertView.findViewById(R.id.listTujuan);
                viewHolder.Harga = (TextView) convertView.findViewById(R.id.listHarga);
                viewHolder.Tanggal = (TextView) convertView.findViewById(R.id.listTanngal);
                viewHolder.button = (Button) convertView.findViewById(R.id.button3);
                convertView.setTag(viewHolder);
            }
            else {
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.Asal.setText(getItem(position));
                mainViewHolder.Tujuan.setText(getItem(position));
                mainViewHolder.Tanggal.setText(getItem(position));
                mainViewHolder.anakKecil.setText(getItem(position));
                mainViewHolder.orangDewasa.setText(getItem(position));
                mainViewHolder.Harga.setText(getItem(position));
            }
                return convertView;
        }
    }

    public class ViewHolder{
        TextView Asal;
        TextView Tujuan;
        TextView Tanggal;
        TextView anakKecil;
        TextView orangDewasa;
        TextView Harga;
        Button button;
    }
}