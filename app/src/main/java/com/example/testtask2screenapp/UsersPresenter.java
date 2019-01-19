package com.example.testtask2screenapp;

import android.util.Log;

import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

public class UsersPresenter implements MvpContract.Presenter {

    private MvpContract.MainView mView;
    private MvpContract.Model mModel;

    public void setUsers(List<Result> users) {
        Log.d("usersLog", "setting users in activity");
        mView.displayUsers(users);
        mView.hideLoading();
    }

    UsersPresenter(MvpContract.MainView mView) {
        this.mView = mView;
        this.mModel = new UsersModel();
    }


    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    @Override
    public void requestUsers() {
        mView.showLoading();
        Log.d("usersLog", "request in presenter");
        mModel.loadUsers(this);

    }


    public void showError() {
        mView.displayErrorDialog();
    }

    @Override
    public List<Result> getUsers() {
        return mModel.getUsers();
    }

    @Override
    public void addData() {
        mModel.addUsers(this);
    }
}
