package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AppAdapC extends  RecyclerView.Adapter<AppAdapC.ViewHolder>{





        @NonNull
        public List<Rdv> visite;
        private RecycleViewClick r;
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        int i;
        String nom;
        public AppAdapC(List<Rdv> visite) {
            this.visite= visite;

        }

        @Override
        public AppAdapC.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rdv_c_med, parent, false);

            return new AppAdapC.ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        String n =visite.get(position).getName();
        holder.patientC.setText(n);
        holder.dateC.setText(visite.get(position).getJour()+"/"+visite.get(position).getMoi()+"/"+visite.get(position).getAnnée());
    }




        @Override
        public int getItemCount() {
            return visite.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View mView;

            public TextView patientC;
            public TextView dateC;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                mView = itemView;
               patientC=(TextView)mView.findViewById(R.id.patientC);
               dateC=(TextView)mView.findViewById(R.id.dateC);
            }
        }
    }

