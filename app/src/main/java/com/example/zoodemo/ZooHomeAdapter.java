package com.example.zoodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.zoodemo.databinding.ItemWarnBinding;
import com.example.zoodemo.databinding.ItemZooBinding;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZooHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int ITEM_VIEW_TYPE_WARM = 0;
    public static final int ITEM_VIEW_TYPE_CONTENT = 1;
    public static final int ITEM_VIEW_TYPE_TITTLE = 2;
    private ItemZooBinding itemContentBinding;
    private ItemWarnBinding itemWarnBinding;
    private Context mContext;
    private List<Object> dataList;
    private OnItemClickListener onItemClickListener;
    private int lastPosition = -1;

    ZooHomeAdapter(Context context, List<Object> list) {
        this.mContext = context;
        this.dataList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = dataList.get(position);
        if (obj instanceof String) {
            return ITEM_VIEW_TYPE_WARM;
        } else if (obj instanceof ZooData.Result.Results) {
            return ITEM_VIEW_TYPE_CONTENT;
        } else {
            return ITEM_VIEW_TYPE_TITTLE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_WARM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_warn, parent, false);
            itemWarnBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_warn, parent, false);
            return new TittleViewHolder(itemWarnBinding);
        } else if (viewType == ITEM_VIEW_TYPE_CONTENT) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_zoo, parent, false);
//            view.setOnClickListener(this);
            itemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_zoo, parent, false);
            itemContentBinding.getRoot().setOnClickListener(this);
            return new ContentViewHolder(itemContentBinding);
        } else {
            itemWarnBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_warn, parent, false);
            return new TittleViewHolder(itemWarnBinding);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_VIEW_TYPE_CONTENT) {
            holder.itemView.setTag(position);
//            itemContentBinding.setResults(((ZooData.Result.Results) dataList.get(position)));
//            ((ContentViewHolder) holder).tvTittle.setText(((ZooData.Result.Results) dataList.get(position)).getE_Name() == null ?
//                    "" : ((ZooData.Result.Results) dataList.get(position)).getE_Name());
//            ((ContentViewHolder) holder).tvInfo.setText(((ZooData.Result.Results) dataList.get(position)).getE_Info() == null ?
//                    "" : ((ZooData.Result.Results) dataList.get(position)).getE_Info());
            itemContentBinding.tvTittle.setText(((ZooData.Result.Results) dataList.get(position)).getE_Name() == null ?
                    "" : ((ZooData.Result.Results) dataList.get(position)).getE_Name());
            itemContentBinding.tvInfo.setText(((ZooData.Result.Results) dataList.get(position)).getE_Info() == null ?
                    "" : ((ZooData.Result.Results) dataList.get(position)).getE_Info());
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.ic_launcher_round);
            options.error(R.mipmap.ic_launcher_round);
//            Glide.with(mContext)
//                    .load(((ZooData.Result.Results) dataList.get(position)).getE_Pic_URL())
//                    .transition(new DrawableTransitionOptions().crossFade())
//                    .apply(options)
//                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                    .into(((ContentViewHolder) holder).ivPhoto);
            Glide.with(mContext)
                    .load(((ZooData.Result.Results) dataList.get(position)).getE_Pic_URL())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(options)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(itemContentBinding.ivPhoto);
//            Uri uri = Uri.parse(((ZooData.Result.Results) dataList.get(position)).getE_Pic_URL());
//            ((ContentViewHolder) holder).ivPhoto.setImageURI(uri);

            if (position > lastPosition) {
                // Scrolled Down
                setAnimationDown(((ContentViewHolder) holder).itemView, position);
//                viewHolder.itemView.animate().setStartDelay(800).start();
            } else {
                // Scrolled Up
                setAnimationUp(((ContentViewHolder) holder).itemView, position);
//                viewHolder.itemView.animate().setStartDelay(800).start();
            }
            lastPosition = position;
        } else if (holder.getItemViewType() == ITEM_VIEW_TYPE_WARM) {
//            ((WarnViewHolder) holder).tvWarn.setText((String) dataList.get(position));
            itemWarnBinding.tvWarn.setText((String) dataList.get(position));
        }
    }

    private void setAnimationDown(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_fall_up);
//        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_fall_up);
        animation.setDuration(500);
        viewToAnimate.startAnimation(animation);

    }

    private void setAnimationUp(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_fall_down);
//        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_fall_up);
        animation.setDuration(500);
        viewToAnimate.startAnimation(animation);

//            lastPosition = position;

//        viewToAnimate.setTranslationY(viewToAnimate.getHeight());
//        viewToAnimate.setAlpha(0f);
//        viewToAnimate.animate()
//                .translationY(0)
//                .setDuration(2000)
//                .alpha(1f)
//                .setInterpolator(new AccelerateDecelerateInterpolator())
//                .start();
//        }
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    void updateZooAdapter(List<Object> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemClickListener.onItemClick(v, (int) v.getTag(), (ZooData.Result.Results) dataList.get((int) v.getTag()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ZooData.Result.Results item);
    }

    class WarnViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_warn)
        TextView tvWarn;

        public WarnViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_tittle)
//        TextView tvTittle;
//        @BindView(R.id.tv_info)
//        TextView tvInfo;
//        @BindView(R.id.iv_photo)
//        ImageView ivPhoto;
//
//        public ContentViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }

        private ContentViewHolder(ItemZooBinding itemBinding) {
            super(itemBinding.getRoot());

        }
    }

    class TittleViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_tittle)
//        TextView tvTittle;
//
//        public TittleViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }

        private TittleViewHolder(ItemWarnBinding itemBinding) {
            super(itemBinding.getRoot());

        }
    }
}
