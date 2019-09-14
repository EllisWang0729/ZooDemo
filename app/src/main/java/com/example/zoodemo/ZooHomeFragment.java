package com.example.zoodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;
import static com.example.zoodemo.MainActivity.TAG_CATEGORY_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ZooHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZooHomeFragment extends Fragment implements ZooHomeView, SwipeRefreshLayout.OnRefreshListener, ZooHomeAdapter.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ZooHomePresenter zooHomePresenter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.rv_list)
    RecyclerView rvMenuList;
    @BindView(R.id.icon_func)
    ImageView ivIconFunc;
    @BindView(R.id.sl_layout)
    SwipeRefreshLayout slLayout;
    @BindView(R.id.progress)
    ProgressBar pbProgress;

    private ZooHomeAdapter zooHomeAdapter;

    public ZooHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZooHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZooHomeFragment newInstance(String param1, String param2) {
        ZooHomeFragment fragment = new ZooHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoo_home, container, false);
        ButterKnife.bind(this, view);
        ivIconFunc.setImageResource(R.mipmap.baseline_menu_white_24);
        zooHomePresenter = new ZooHomePresenter(this, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(VERTICAL);
        rvMenuList.setLayoutManager(linearLayoutManager);
        rvMenuList.setHasFixedSize(true);

        zooHomeAdapter = new ZooHomeAdapter(getContext(), new ArrayList<>());
        zooHomeAdapter.setOnItemClickListener(this);
        rvMenuList.setAdapter(zooHomeAdapter);
        zooHomePresenter.callAPI();
        slLayout.setOnRefreshListener(this);
        return view;
    }


    @OnClick({R.id.icon_func})
    void onClickListener(View view) {
//        switch (view.getId()) {
//            case R.id.icon_func:
////                zooHomePresenter.callAPI();
//                break;
//        }
    }


    @Override
    public void refreshList(List<Object> list) {
        if (slLayout.isRefreshing()) {
            slLayout.setRefreshing(false);
        }
        zooHomeAdapter.updateZooAdapter(list);
    }

    @Override
    public void showProgressDialog() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void disMissProgressDialog() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        zooHomePresenter.callAPI();
    }

    @Override
    public void onItemClick(View view, int position, ZooData.Result.Results item) {
        Log.d("onItemClick", new Gson().toJson(item));
        CategoryFragment fragment = CategoryFragment.newInstance(item);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_left,R.anim.slide_out_right);
        transaction.replace(getId(), fragment, TAG_CATEGORY_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
