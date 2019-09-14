package com.example.zoodemo;

import android.content.Context;
import android.util.Log;

import com.example.zoodemo.Client.APIServiceClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {

    private Context mContext;
    private CategoryView categoryView;

    CategoryPresenter(CategoryView view, Context context) {
        this.categoryView = view;
        this.mContext = context;
    }

    void callAPI() {
        categoryView.showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                APIServiceClient.getInstance()
                        .zoo("resourceAquire", "f18de02f-b6c9-47c0-8cda-50efad621c14").enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        categoryView.disMissProgressDialog();
                        if (response.body() != null) {
                            Log.d("sssss", new Gson().toJson(response.body()));
                            ZooData zooData = new Gson().fromJson(new Gson().toJson(response.body()), ZooData.class);
                            Log.d("sssss", new Gson().toJson(zooData));
                            List<Object> tmpList = new ArrayList();

                            if (zooData.getResult().getResults() != null && !zooData.getResult().getResults().isEmpty()) {
                                tmpList.add(0);
                                for (ZooData.Result.Results result : zooData.getResult().getResults()) {
                                    tmpList.add(result);
                                }
                            } else {
                                tmpList.add("查詢不到資料,請再刷新一次");
                            }
                            categoryView.refreshList(tmpList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        categoryView.disMissProgressDialog();
                        List<Object> tmpList = new ArrayList();
                        tmpList.add("查詢不到資料,請再刷新一次");
                        categoryView.refreshList(tmpList);
                    }
                });
            }
        }).start();
    }
}
