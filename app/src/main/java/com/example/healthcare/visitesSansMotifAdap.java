package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class visitesSansMotifAdap extends RecyclerView.Adapter<visitesSansMotifAdap.ViewHolder> {



    @NonNull
    public List<RdvWithId> visite;
    private RecycleViewClick r;

    public visitesSansMotifAdap(List<RdvWithId> visite, RecycleViewClick r) {
        this.visite= visite;
        this.r = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visites_sansmotif, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.patient.setText(visite.get(position).getR().getName());
        holder.date.setText(visite.get(position).getR().getJour()+"/"+visite.get(position).getR().getMoi()+"/"+visite.get(position).getR().getAnnée());
    }



    @Override
    public int getItemCount() {
        return visite.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public Button visiteB;
        public TextView patient;
        public TextView date;
        public EditText motif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            patient = (TextView) mView.findViewById(R.id.patientSansmotif);
            date =(TextView)mView.findViewById(R.id.dateSansMotif);
            motif =(EditText) mView.findViewById(R.id.motifSansMotif);
            motif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(motif.getText().toString().equals("Motif ...")){
                        motif.setText("");
                    }
                }
            });
            visiteB = (Button) mView.findViewById(R.id.saveSansMotif);
            visiteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    r.onItemClickV(getAdapterPosition(), (ArrayList<RdvWithId>) visite, motif.getText().toString());
                }
            });
        }
    }
}
