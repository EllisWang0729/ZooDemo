package com.example.zoodemo;

import android.content.Context;

public class ZooPlantPresenter {
    private ZooPlantView zooPlantView;
    private Context mContext;


    ZooPlantPresenter(ZooPlantView view,Context context){
        this.zooPlantView=view;
        this.mContext=context;
    }

}
