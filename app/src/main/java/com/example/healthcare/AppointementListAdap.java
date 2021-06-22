package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointementListAdap  extends  RecyclerView.Adapter<AppointementListAdap.ViewHolder>{
    @NonNull
    public List<Rdv> Rdv;

    public AppointementListAdap(List<Rdv> Rdv){
        this.Rdv=Rdv;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rdv_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medcin.setText(Rdv.get(position).getMedcin());
        holder.date.setText((Rdv.get(position).getJour()+"/"+Rdv.get(position).getMoi()+"/"+Rdv.get(position).getAnnée()));
    }

    @Override
    public int getItemCount() {
        return Rdv.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView date;
        public TextView medcin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            medcin =(TextView) mView.findViewById(R.id.medcin);
            date =(TextView) mView.findViewById(R.id.dateMed);

        }
    }
}
