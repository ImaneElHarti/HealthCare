package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DocListAdap extends RecyclerView.Adapter<DocListAdap.ViewHolder> {

    @NonNull
    public List<doctor> doctors;
    private  RecycleViewClick r;
    public DocListAdap(List<doctor> doctors,RecycleViewClick r){
        this.doctors=doctors;
        this.r=r;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Fullname.setText(doctors.get(position).getFullName());

    }


    @Override
    public int getItemCount() {
        return doctors.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView Fullname ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            Fullname =(TextView) mView.findViewById(R.id.textmed);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.onItemClick(getAdapterPosition(), (ArrayList<doctor>) doctors);
                }
            });
        }
    }
}
