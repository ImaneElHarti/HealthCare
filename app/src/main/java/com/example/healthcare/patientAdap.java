package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class patientAdap extends RecyclerView.Adapter<patientAdap.ViewHolder> {


    @NonNull
    public List<PatientWithId> PatientWithId;
    private RecycleViewClick r;

    public patientAdap(List<PatientWithId> PatientWithId, RecycleViewClick r) {
        this.PatientWithId= PatientWithId;
        this.r = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.patientB.setText(PatientWithId.get(position).getP().getFullName());
    }

    @Override
    public int getItemCount() {
        return PatientWithId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public Button patientB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            patientB = (Button) mView.findViewById(R.id.p12);
            patientB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.onItemClickP(getAdapterPosition(), (ArrayList<PatientWithId>) PatientWithId);
                }
            });
        }
    }
}
