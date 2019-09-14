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


public class ZooHomePresenter {
    private Context mContext;
    private ZooHomeView zooHomeView;

    ZooHomePresenter(ZooHomeView view, Context context) {
        this.zooHomeView = view;
        this.mContext = context;
    }

    void callAPI() {
        zooHomeView.showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                APIServiceClient.getInstance()
                        .zoo("resourceAquire", "5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a").enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        zooHomeView.disMissProgressDialog();
                        if (response.body() != null) {
                            Log.d("sssss", new Gson().toJson(response.body()));
                            ZooData zooData = new Gson().fromJson(new Gson().toJson(response.body()), ZooData.class);
                            Log.d("sssss", new Gson().toJson(zooData));
                            List<Object> tmpList = new ArrayList();
                            if (zooData.getResult().getResults() != null && !zooData.getResult().getResults().isEmpty()) {
                                for (ZooData.Result.Results result : zooData.getResult().getResults()) {
                                    tmpList.add(result);
                                }
                            } else {
                                tmpList.add("查詢不到資料,請再刷新一次");
                            }
                            zooHomeView.refreshList(tmpList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        zooHomeView.disMissProgressDialog();
                        List<Object> tmpList = new ArrayList();
                        tmpList.add("查詢不到資料,請再刷新一次");
                        zooHomeView.refreshList(tmpList);
                    }
                });
            }
        }).start();
    }
}
