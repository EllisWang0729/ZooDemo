package com.example.zoodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ZooPlantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZooPlantFragment extends Fragment implements ZooPlantView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_PLANT = "zoo_Plant_Data";
    private Handler handler = new Handler();
    private ZooData.Result.Results resultData;
    private ZooPlantPresenter zooPlantPresenter;

    @BindView(R.id.icon_func)
    ImageView ivIconFunc;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.toolbar_title)
    TextView ivToolbarTitle;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_identify)
    TextView tvIdentify;
    @BindView(R.id.tv_features)
    TextView tvFeatures;
    @BindView(R.id.tv_last_update)
    TextView tvLastUpdate;

    public ZooPlantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param data Results
     * @return A new instance of fragment ZooPlantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZooPlantFragment newInstance(ZooData.Result.Results data) {
        ZooPlantFragment fragment = new ZooPlantFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLANT, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            resultData = getArguments().getParcelable(ARG_PLANT);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoo_plant, container, false);
        ButterKnife.bind(this, view);
        ivIconFunc.setImageResource(R.mipmap.baseline_keyboard_arrow_left_white_24);
        zooPlantPresenter = new ZooPlantPresenter(this, getContext());
        Fresco.initialize(getContext());
        if (resultData != null) {
            ivToolbarTitle.setText(resultData.getF_Name_Ch());
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.ic_launcher_round);
            options.error(R.mipmap.ic_launcher_round);
            Glide.with(getContext())
                    .load(paserPhotoUrl(resultData))
                    .apply(options)
                    .into(ivPhoto);
            tvTittle.setText(String.format("%s%n%s", resultData.getF_Name_Ch(), resultData.getF_Name_En()));
            tvCategory.setText(String.format("%s%n%s", "別名", resultData.getF_AlsoKnown()));
            tvIntroduction.setText(String.format("%s%n%s", "簡介", resultData.getF_Brief()));
            tvIdentify.setText(String.format("%s%n%s", "辨認方式", resultData.getF_Feature()));
            tvFeatures.setText(String.format("%s%n%s", "功能性", resultData.getF_Function()));
            tvLastUpdate.setText(String.format("%s %s", "最後更新", resultData.getF_Update() == null ? "" : resultData.getF_Update()));
        }

        return view;
    }

    private String paserPhotoUrl(ZooData.Result.Results results) {
        if (results.getF_Pic01_URL() != null && !results.getF_Pic01_URL().isEmpty()) {
            return results.getF_Pic01_URL();
        } else if (results.getF_AlsoKnown() != null && !results.getF_AlsoKnown().isEmpty()) {
            return results.getF_AlsoKnown();
        } else {
            return results.getF_Pic01_URL();
        }
    }

    @OnClick({R.id.icon_func})
    void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.icon_func:
                getFragmentManager().popBackStack();
                break;
        }
    }

}
