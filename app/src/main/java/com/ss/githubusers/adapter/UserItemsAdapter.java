package com.ss.githubusers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ss.githubusers.R;
import com.ss.githubusers.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserItemsAdapter extends RecyclerView.Adapter<UserItemsAdapter.UserViewHolder> {


    private List<User> mUserList;
    private static OnItemClickListener mListener;

    public UserItemsAdapter(Context context) {

    }

    @NonNull
    @Override
    public UserItemsAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemsAdapter.UserViewHolder holder, int position) {
        holder.bind(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    public void setUserList(List<User> users) {
        mUserList = users;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserLoginTextView;
        private TextView mUserUrlTextView;

        UserViewHolder(View itemView) {
            super(itemView);
            mUserLoginTextView = itemView.findViewById(R.id.user_login_text_view);
            mUserUrlTextView = itemView.findViewById(R.id.user_url_text_view);
        }

        void bind(final User user) {
            mUserLoginTextView.setText(user.getLogin());
            mUserUrlTextView.setText(user.getHtml_url());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(user.getHtml_url());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(String url);
    }
}
