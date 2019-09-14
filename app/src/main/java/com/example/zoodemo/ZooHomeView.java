package com.example.zoodemo;

import java.util.List;

public interface ZooHomeView {

    void refreshList(List<Object> list);

    void showProgressDialog();

    void disMissProgressDialog();
}
