package com.ss.githubusers.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.ss.githubusers.R;
import com.ss.githubusers.adapter.UserItemsAdapter;
import com.ss.githubusers.client.RetrofitClient;
import com.ss.githubusers.model.User;
import com.ss.githubusers.viewmodel.UsersViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class MainActivity extends AppCompatActivity {

    private static final int USERS_LIMIT_PER_PAGE = 15;

    private UserItemsAdapter mUserItemsAdapter;

    private UsersViewModel mUsersViewModel;
    private NoPaginate mNoPaginate;
    private int mLimit = USERS_LIMIT_PER_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNoPaginate.unbind();
    }

    private void init() {
        mUserItemsAdapter = new UserItemsAdapter(this);
        mUserItemsAdapter.setOnItemClickListener(new UserItemsAdapter.OnItemClickListener() {
            @Override
            public void onClick(String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.user_list_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mUserItemsAdapter);

        mNoPaginate = NoPaginate.with(recyclerView)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        loadUsers(mLimit);
                    }
                })
                .build();

        mUsersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        mUsersViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                mUserItemsAdapter.setUserList(users);
            }
        });
    }

    private void loadUsers(int limit) {
        Call<List<User>> callAsync = RetrofitClient.getInstance().getService().getUsers(mLimit);
        callAsync.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    mUsersViewModel.loadUsers(response.body());
                    mLimit += USERS_LIMIT_PER_PAGE;
                    if (mLimit >= 100) {
                        mNoPaginate.setNoMoreItems(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchUser(final String username) {
        Call<User> callAsync = RetrofitClient.getInstance().getService().getUser(username);
        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra(SearchResultActivity.USER_EXTRA_NAME, response.body());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}