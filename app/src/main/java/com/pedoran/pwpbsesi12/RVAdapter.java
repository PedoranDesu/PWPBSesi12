package com.pedoran.pwpbsesi12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.UserViewHolder> {
    Context context;
    OnUserClickListener listener;

    public interface  OnUserClickListener{
        void onUserClick(Person person,String action);
    }

    List<Person> listPersonInfo;

    public RVAdapter(Context context,List<Person> personList,OnUserClickListener listener){
        this.context = context;
        this.listPersonInfo = personList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RVAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item,parent,false);
        UserViewHolder uvh = new UserViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.UserViewHolder holder, int position) {
        final Person jalmi = listPersonInfo.get(position);
        holder.ctxtName.setText(jalmi.getName());
        holder.ctxtAge.setText(jalmi.getAge()+"");
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(jalmi,"Edit");
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(jalmi,"Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPersonInfo.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView ctxtAge,ctxtName;
        ImageView imgDelete,imgEdit;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ctxtName = itemView.findViewById(R.id.ctxtName);
            ctxtAge = itemView.findViewById(R.id.ctxtAge);
            imgDelete = itemView.findViewById(R.id.imgdelete);
            imgEdit = itemView.findViewById(R.id.imgedit);
        }
    }
}
