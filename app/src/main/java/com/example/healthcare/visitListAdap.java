package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class visitListAdap extends RecyclerView.Adapter<visitListAdap.ViewHolder> {

    @NonNull
    public List<Rdv> visites;
    public visitListAdap(List<Rdv> visites){
        this.visites=visites;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visites_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medcin.setText(visites.get(position).getMedcin());
        holder.motif.setText(visites.get(position).getMotif());
        holder.date.setText(visites.get(position).getJour()+"/"+visites.get(position).getMoi()+"/"+visites.get(position).getAnnée());
    }

    


    @Override
    public int getItemCount() {
        return visites.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView medcin ;
        public TextView motif ;
        public TextView date ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            medcin =(TextView) mView.findViewById(R.id.MedcinV);
            motif =(TextView) mView.findViewById(R.id.MotifV);
            date=(TextView) mView.findViewById(R.id.DateV);

        }
    }
}
