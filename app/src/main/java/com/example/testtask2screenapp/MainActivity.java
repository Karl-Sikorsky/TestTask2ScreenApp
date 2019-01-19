package com.example.testtask2screenapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MvpContract.MainView {

    private MvpContract.Presenter mPresenter;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        mPresenter = new UsersPresenter(this);


        mPresenter.requestUsers();
        Log.d("usersLog", "have an  request");
    }

    @Override
    public void showLoading() {
        dialog.setMessage("Loading data, please wait.");
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void displayBaseContent() {

    }

    @Override
    public void displayUsers(List<Result> users) {
Log.d("usersLog", "have an "+users.size()+" users");
    }

    @Override
    public void displayErrorDialog() {

    }
}
