package com.example.projectprogandro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PaymentAdapter extends FirestoreRecyclerAdapter<InfoPenumpang, PaymentAdapter.PaymentHolder> {


    public PaymentAdapter(@NonNull FirestoreRecyclerOptions<InfoPenumpang> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PaymentAdapter.PaymentHolder holder, int position, @NonNull InfoPenumpang model) {
//        holder.textViewAsal.setText(model.getAsal());
        holder.textViewTujuan.setText(model.getTujuan());
        holder.textViewTanggal.setText(model.getTanggal());
        holder.textViewHarga.setText(model.getHarga());
 //       holder.textViewDewasa.setText(model.getDewasa());
  //      holder.textViewAnak.setText(model.getAnak());
    }

    @NonNull
    @Override
    public PaymentAdapter.PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent, false);
        return new PaymentHolder(v);
    }


    class PaymentHolder extends RecyclerView.ViewHolder {
        TextView textViewAsal;
        TextView textViewTujuan;
        TextView textViewTanggal;
        TextView textViewHarga;
        TextView textViewDewasa;
        TextView textViewAnak;


        public PaymentHolder(@NonNull View itemView) {
            super(itemView);
            //textViewAsal = itemView.findViewById(R.id.text_asal);
            textViewTujuan = itemView.findViewById(R.id.text_tujuan);
            textViewTanggal = itemView.findViewById(R.id.text_tanggal);
            textViewHarga = itemView.findViewById(R.id.text_harga);
           // textViewDewasa = itemView.findViewById(R.id.text_dewasa);
           //siap textViewAnak = itemView.findViewById(R.id.text_anak);

        }
    }
}
