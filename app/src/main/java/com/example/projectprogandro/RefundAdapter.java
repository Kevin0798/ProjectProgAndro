package com.example.projectprogandro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RefundAdapter extends FirestoreRecyclerAdapter<InfoPenumpang, RefundAdapter.RefundHolder> {
    private DeleteListener mDeleteListener;

    public RefundAdapter(@NonNull FirestoreRecyclerOptions<InfoPenumpang> options, DeleteListener mDeleteListener) {
        super(options);
        this.mDeleteListener = mDeleteListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull RefundAdapter.RefundHolder holder, int position, @NonNull InfoPenumpang model) {
        holder.textViewTujuan.setText(model.getTujuan());
        holder.textViewTanggal.setText(model.getTanggal());
        holder.textViewHarga.setText(model.getHarga());
    }

    @NonNull
    @Override
    public RefundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent, false);
        return new RefundAdapter.RefundHolder(v , mDeleteListener);
    }

    class RefundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTujuan;
        TextView textViewTanggal;
        TextView textViewHarga;
        DeleteListener deleteListener;
        Button button;

        public RefundHolder(@NonNull final View itemView, DeleteListener deleteListener) {
            super(itemView);
            textViewTujuan = itemView.findViewById(R.id.refund_tujuan);
            textViewTanggal = itemView.findViewById(R.id.refund_tanggal);
            textViewHarga = itemView.findViewById(R.id.refund_harga);
            button = (Button) itemView.findViewById(R.id.refundButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Permintaan anda akan kami proses",Toast.LENGTH_LONG).show();
                    //AlertDialog dialog = new AlertDialog.Builder()
                }
            });


            //this.deleteListener = deleteListener;
            //itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            deleteListener.deleteClick(getAdapterPosition());
        }
    }

    public interface DeleteListener{
        void deleteClick(int position);
    }
}
