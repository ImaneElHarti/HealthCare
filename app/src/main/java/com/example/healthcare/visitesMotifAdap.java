package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class visitesMotifAdap extends RecyclerView.Adapter<visitesMotifAdap.ViewHolder> {


    @NonNull
    public List<Rdv> visite;
    private RecycleViewClick r;

    public visitesMotifAdap(List<Rdv> visite, RecycleViewClick r) {
        this.visite= visite;
        this.r = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visites_motif, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.patientB.setText(visite.get(position).getName());
        holder.motif.setText(visite.get(position).getMotif());
        holder.date.setText(visite.get(position).getJour()+"/"+visite.get(position).getMoi()+"/"+visite.get(position).getAnnée());
    }

    @Override
    public int getItemCount() {
        return visite.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView patientB,motif,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            patientB =(TextView)mView.findViewById(R.id.patientMotif);
            date =(TextView)mView.findViewById(R.id.dateMotif);
            motif=(TextView)mView.findViewById(R.id.Motifmotif);

        }
    }
}
