package com.example.testtask2screenapp;

import android.util.Log;

import com.example.testtask2screenapp.network.RetrofitHelper;
import com.example.testtask2screenapp.network.UsersService;
import com.example.testtask2screenapp.pojo.RandomuserResponse;
import com.example.testtask2screenapp.pojo.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UsersModel implements MvpContract.Model {

    private List<Result> users;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @NonNull
    private UsersService mUsersService;

    UsersModel() {
        mUsersService = new RetrofitHelper().getUsersFromApi();
    }

    private void setUsers(List<Result> users) {
        this.users = users;
    }

    @NonNull
    public List<Result> getUsers() {
            return users;
    }

    @Override
    public void addUsers(final UsersPresenter usersPresenter) {
        mCompositeDisposable.add(mUsersService.queryUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<RandomuserResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(
                            @io.reactivex.annotations.NonNull final RandomuserResponse usersResponse) throws Exception {
                        Log.d("usersLog", "have an apply in model and can change response");
                        return usersResponse.getResults();
                    }
                })
                .subscribe(new Consumer<List<Result>>() {

                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Result> results)
                            throws Exception {
                        Log.d("usersLog", "have an  list of ready Users in model");
                        addUsersToField(results, usersPresenter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        usersPresenter.showError();
                        Log.d("usersLog", "have adding trouble: "+throwable.getMessage());
                    }
                })
        );
    }


    public void addUsersToField(List<Result> resultsToAdd, UsersPresenter mUsersPresenter) {
        if(users==null){
            users = new ArrayList<>();
        }
        users.addAll(resultsToAdd);
        mUsersPresenter.setUsers(users);

    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadUsers (final UsersPresenter mUsersPresenter) {
        mCompositeDisposable.add(mUsersService.queryUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<RandomuserResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(
                            @io.reactivex.annotations.NonNull final RandomuserResponse usersResponse) throws Exception {
                        Log.d("usersLog", "have an apply in model and can change response");
                        return usersResponse.getResults();
                    }
                })
                .subscribe(new Consumer<List<Result>>() {

                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Result> results)
                            throws Exception {
                        Log.d("usersLog", "have an  list of ready Users in model");
                        setUsers(results);
                        mUsersPresenter.setUsers(results);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mUsersPresenter.showError();
                        Log.d("usersLog", "have trouble: "+throwable.getMessage());
                    }
                })
        );
    }



    @Override
    public List<RandomuserResponse> getUsersData() {
        return null;
    }


}
