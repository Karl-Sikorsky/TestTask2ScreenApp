package com.example.testtask2screenapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MvpContract.MainView {

    private MvpContract.Presenter mPresenter;
    private ProgressDialog dialog;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        mPresenter = new UsersPresenter(this);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                   mPresenter.addData();


                }
            }
        });
        mPresenter.requestUsers();
        Log.d("usersLog", "have an  request");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_linear:
                displayUsers(mPresenter.getUsers());
                return true;
            case R.id.action_grid:
                setGridForRecycler(mPresenter.getUsers(),2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(users, this);
        rv.setAdapter(adapter);

            rv.scrollToPosition(users.size()- (Constants.SIZE_OF_PORTION));



        adapter.notifyDataSetChanged();
    }

    public void setGridForRecycler(List<Result> users, int spanCount) {
        GridLayoutManager glm = new GridLayoutManager(this, spanCount);
        rv.setLayoutManager(glm);

        RVAdapter adapter = new RVAdapter(users, this);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Something went wrong");
        builder.setMessage("Check your internet connection and retry");
        builder.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.requestUsers();
                    }
                });
        builder.setCancelable(false);
        builder.show();

    }
}
