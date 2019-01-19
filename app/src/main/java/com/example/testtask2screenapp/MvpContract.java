package com.example.testtask2screenapp;

import com.example.testtask2screenapp.pojo.RandomuserResponse;
import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

public interface MvpContract {
    interface View {
        void showLoading();

        void hideLoading();


    }

    interface MainView extends View {

        void displayUsers(final List<Result> users);

        void displayErrorDialog();


    }

    interface Presenter {

        void onDestroy();

        void requestUsers();


        void showError();

        List<Result> getUsers();

        void addData();
    }

    interface Model {


        void onDestroy();

        List<RandomuserResponse> getUsersData();

        void loadUsers(UsersPresenter usersPresenter);

        List<Result> getUsers();

        void addUsers(UsersPresenter usersPresenter);
    }

}
