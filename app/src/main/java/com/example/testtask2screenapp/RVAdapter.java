package com.example.testtask2screenapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testtask2screenapp.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.UserViewHolder> {

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userPhone;
        TextView userId;
        ImageView userPhoto;
        ConstraintLayout user_card;


        UserViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            user_card = (ConstraintLayout) itemView.findViewById(R.id.user_card);
            userName = (TextView) itemView.findViewById(R.id.textViewName);
            userPhone = (TextView) itemView.findViewById(R.id.textViewPhone);
            userPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            userId = (TextView) itemView.findViewById(R.id.textViewId);

        }
    }

    List<Result> users;
    Context context;

    RVAdapter(List<Result> users, Context context) {
        this.users = users;
        this.context = context;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card, viewGroup, false);
        UserViewHolder uvh = new UserViewHolder(v);

        return uvh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder userViewHolder, final int position) {

        userViewHolder.userName.setText(String.format("%s %s", users.get(position).getName().getLast(), users.get(position).getName().getFirst()));
        userViewHolder.userPhone.setText(users.get(position).getPhone());
        userViewHolder.userId.setText(String.valueOf(position));
        Picasso.get()
                .load(users.get(position).getPicture().getMedium())
                .placeholder(context.getResources().getDrawable(R.drawable.ic_launcher_background))
                .error(context.getResources().getDrawable(R.drawable.ic_launcher_background))
                .fit()
                .into(userViewHolder.userPhoto);
        userViewHolder.user_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserActivity.class);

                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }
}