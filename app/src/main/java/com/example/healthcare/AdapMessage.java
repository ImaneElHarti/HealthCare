package com.example.healthcare;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdapMessage extends RecyclerView.Adapter<AdapMessage.ViewHolder> {



    @NonNull
    public List<Message> msg;
    FirebaseFirestore fStore;

    public AdapMessage(List<Message> msg) {
        this.msg= msg;
        fStore= FirebaseFirestore.getInstance();
    }

    @Override
    public AdapMessage.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list, parent, false);
        return new AdapMessage.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.message.setText(msg.get(position).getMessage());
        fStore.collection("doctor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("Appointement","Error : "+e.getMessage());
                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getDocument().getId().equals(msg.get(position).getMedcin())){
                        holder.emeteur.setText("de :" +doc.getDocument().get("fullName"));
                    }

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return msg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView message;
        public TextView emeteur;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            message=(TextView)mView.findViewById(R.id.message);
            emeteur=(TextView)mView.findViewById(R.id.emeteur);
        }
    }
}
