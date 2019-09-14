package com.example.zoodemo;

import java.util.List;

public interface CategoryView {
    void refreshList(List<Object> list);

    void showProgressDialog();

    void disMissProgressDialog();
}
