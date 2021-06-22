package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapD extends RecyclerView.Adapter<AppAdapD.ViewHolder>{





    @NonNull
    public List<RdvWithId> visite;
    private RecycleViewClick r;

    public AppAdapD(List<RdvWithId> visite,RecycleViewClick r) {
        this.visite= visite;
        this.r=r;

    }

    @Override
    public AppAdapD.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rdv_d_med, parent, false);

        return new AppAdapD.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        holder.patientD.setText(visite.get(position).getR().getName());
        holder.dateD.setText(visite.get(position).getR().getJour()+"/"+visite.get(position).getR().getMoi()+"/"+visite.get(position).getR().getAnnée());
    }






    @Override
    public int getItemCount() {
        return visite.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView patientD;
        public TextView dateD;
        public Button confimer;
        public Button annuler;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            patientD=(TextView)mView.findViewById(R.id.patientD);
            dateD=(TextView)mView.findViewById(R.id.dateD);
            confimer=(Button)mView.findViewById(R.id.confirmer);
            annuler=(Button)mView.findViewById(R.id.annuler);

            confimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.onItemClickC(getAdapterPosition(), (ArrayList<RdvWithId>) visite);
                }
            });

            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.onItemClickA(getAdapterPosition(), (ArrayList<RdvWithId>) visite);
                }
            });

        }
    }
}