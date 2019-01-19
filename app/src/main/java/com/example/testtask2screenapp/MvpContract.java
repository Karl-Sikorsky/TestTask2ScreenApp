package com.example.testtask2screenapp;

import com.example.testtask2screenapp.pojo.RandomuserResponse;
import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

public interface MvpContract {
    interface View {
        void showLoading();

        void hideLoading();

        void displayBaseContent();
    }

    interface MainView extends View {

        void displayUsers(final List<Result> users);

        void displayErrorDialog();


    }

    interface Presenter {

        void onDestroy();

        void requestUsers();



        void showError();

    }

    interface Model {
        void onDestroy();

        List<RandomuserResponse> getUsersData();

        void loadUsers(UsersPresenter usersPresenter);
    }

}