package com.ss.githubusers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ss.githubusers.R;
import com.ss.githubusers.model.User;

public class SearchResultActivity extends AppCompatActivity {

    public static final String USER_EXTRA_NAME = "user";

    private ImageView mUserAvatarImageView;
    private TextView mUserLoginTextView;
    private TextView mUserUrlTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();
    }

    private void init() {
        mUserAvatarImageView = findViewById(R.id.user_avatar_image_view);
        mUserLoginTextView = findViewById(R.id.user_login_text_view);
        mUserUrlTextView = findViewById(R.id.user_url_text_view);

        final User user = (User) getIntent().getSerializableExtra(USER_EXTRA_NAME);

        if (user != null) {
            Picasso.get().load(user.getAvatar_url()).into(mUserAvatarImageView);
            mUserLoginTextView.setText(user.getLogin());
            mUserUrlTextView.setText(user.getHtml_url());
            mUserUrlTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchResultActivity.this, SearchResultActivity.class);
                    intent.putExtra(SearchResultActivity.USER_EXTRA_NAME, user.getHtml_url());
                    startActivity(intent);
                }
            });
        }
    }
}
