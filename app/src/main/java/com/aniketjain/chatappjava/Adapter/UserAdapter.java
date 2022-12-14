package com.aniketjain.chatappjava.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniketjain.chatappjava.Activity.ChatActivity;
import com.aniketjain.chatappjava.Model.Users;
import com.aniketjain.chatappjava.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserItemViewHolder> {

    private final Context context;
    private final ArrayList<Users> usersArrayList;

    public UserAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_layout, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {

        Users users = usersArrayList.get(position);

        if(!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()
                .equals(users.getUid())){
            // set data to the item layout
            Picasso.get().load(users.getImage_uri()).into(holder.profileIv);
            holder.nameTv.setText(users.getName());
            holder.statusTv.setText(users.getStatus());

            // when user click on the user profile
            holder.userRl.setOnClickListener(view -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("receiver_uid", users.getUid());
                intent.putExtra("receiver_name", users.getName());
                intent.putExtra("receiver_image", users.getImage_uri());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size()-1;
    }

    static class UserItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout userRl;
        private CircleImageView profileIv;
        private TextView nameTv, statusTv;

        public UserItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userRl = itemView.findViewById(R.id.user_rl);
            profileIv = itemView.findViewById(R.id.user_profile_iv);
            nameTv = itemView.findViewById(R.id.user_name_tv);
            statusTv = itemView.findViewById(R.id.user_status_tv);
        }
    }
}
