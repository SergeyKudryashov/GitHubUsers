package com.ss.githubusers.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ss.githubusers.model.User;

import java.util.List;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<List<User>> mUsers = new MutableLiveData<>();

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void loadUsers(List<User> list) {
        mUsers.setValue(list);
    }
}
