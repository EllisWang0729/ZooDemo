package com.example.zoodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zoodemo.databinding.FragmentCategoryBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;
import static com.example.zoodemo.MainActivity.TAG_PLANT_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements CategoryView, ZooPlantAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "zoo_Category_Data";
    private FragmentCategoryBinding fragmentCategoryBinding;
    private ZooData.Result.Results resultData;
    private CategoryPresenter categoryPresenter;
    private ZooPlantAdapter zooplantAdapter;
    private Handler handler = new Handler();

    //    @BindView(R.id.icon_func)
    ImageView ivIconFunc;
    //    @BindView(R.id.toolbar_title)
    TextView ivToolbarTitle;
//    @BindView(R.id.tv_info)
//    TextView tvInfo;
//    @BindView(R.id.tv_memo)
//    TextView tvMemo;
//    @BindView(R.id.tv_category)
//    TextView tvCategory;
//    @BindView(R.id.tv_webview)
//    TextView tvWebView;
//    @BindView(R.id.iv_photo)
//    ImageView ivPhoto;
//    @BindView(R.id.rv_list)
//    RecyclerView rvPlantList;
//    @BindView(R.id.progress)
//    ProgressBar pbProgress;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param data Results
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(ZooData.Result.Results data) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORY, data);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resultData = getArguments().getParcelable(ARG_CATEGORY);
        }
    }

    @SuppressLint({"CheckResult", "WrongConstant"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_category, container, false);
//        ButterKnife.bind(this, view);
        fragmentCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);

        categoryPresenter = new CategoryPresenter(this, getContext());
        ivToolbarTitle = fragmentCategoryBinding.toolbar.findViewById(R.id.toolbar_title);
        ivIconFunc = fragmentCategoryBinding.toolbar.findViewById(R.id.icon_func);
        ivIconFunc.setImageResource(R.mipmap.baseline_keyboard_arrow_left_white_24);
        ivIconFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(VERTICAL);
        fragmentCategoryBinding.rvList.setLayoutManager(linearLayoutManager);
        fragmentCategoryBinding.rvList.setHasFixedSize(true);

        zooplantAdapter = new ZooPlantAdapter(getContext(), new ArrayList<>());
        zooplantAdapter.setOnItemClickListener(this);
        fragmentCategoryBinding.rvList.setNestedScrollingEnabled(false);
        fragmentCategoryBinding.rvList.setAdapter(zooplantAdapter);

        fragmentCategoryBinding.setResultdata(resultData);
        fragmentCategoryBinding.setTvWebviewFlag(resultData.getE_URL().isEmpty());
        if (resultData != null) {
//            ivToolbarTitle.setText(resultData.getE_Name());
            ivToolbarTitle.setText(resultData.getE_Name());
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.ic_launcher_round);
            options.error(R.mipmap.ic_launcher_round);
            Glide.with(getContext())
                    .load(resultData.getE_Pic_URL())
                    .apply(options)
                    .into(fragmentCategoryBinding.ivPhoto);

//            tvInfo.setText(resultData.getE_Info());
//            tvMemo.setText(resultData.getE_Memo());
//            tvCategory.setText(resultData.getE_Category());
//            tvWebView.setVisibility(resultData.getE_URL().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    categoryPresenter.callAPI();
                }
            }, 500);
        }
//        } else {
////            tvWebView.setVisibility(View.INVISIBLE);
//        }
//        rvPlantList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Glide.with(getContext()).resumeRequests();
//                }else {
//                    Glide.with(getContext()).pauseRequests();
//                }
//            }
//        });
        return fragmentCategoryBinding.getRoot();
    }

//    @OnClick({R.id.icon_func, R.id.tv_webview})
//    void onClickListener(View view) {
//        switch (view.getId()) {
//            case R.id.icon_func:
//                getFragmentManager().popBackStack();
//                break;
//            case R.id.tv_webview:
//                //TODO Intent to WebView Fragment
//                break;
//        }
//    }

    @Override
    public void onItemClick(View view, int position, ZooData.Result.Results item) {

        ZooPlantFragment fragment = ZooPlantFragment.newInstance(item);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(getId(), fragment, TAG_PLANT_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void refreshList(List<Object> list) {
        Log.d("okok", new Gson().toJson(list));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zooplantAdapter.updateZooAdapter(list);
            }
        }, 500);
    }

    @Override
    public void showProgressDialog() {
        fragmentCategoryBinding.setProgressFlag(true);
//        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void disMissProgressDialog() {
        fragmentCategoryBinding.setProgressFlag(false);
//        pbProgress.setVisibility(View.GONE);
    }
}
