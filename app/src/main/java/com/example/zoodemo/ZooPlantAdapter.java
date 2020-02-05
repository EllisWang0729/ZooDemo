package com.example.zoodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.zoodemo.databinding.ItemTittleBinding;
import com.example.zoodemo.databinding.ItemWarnBinding;
import com.example.zoodemo.databinding.ItemZooBinding;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import android.os.Handler;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZooPlantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int ITEM_VIEW_TYPE_WARM = 0;
    public static final int ITEM_VIEW_TYPE_CONTENT = 1;
    public static final int ITEM_VIEW_TYPE_TITTLE = 2;
    private Context mContext;
    private List<Object> dataList;
    private OnItemClickListener onItemClickListener;
    private ItemWarnBinding itemWarnBinding;
    private ItemZooBinding itemContentBinding;
    private ItemTittleBinding itemTittleBinding;

    ZooPlantAdapter(Context context, List<Object> list) {
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
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_warn, parent, false);
            itemWarnBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_warn, parent, false);
            return new WarnViewHolder(itemWarnBinding);
        } else if (viewType == ITEM_VIEW_TYPE_CONTENT) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_zoo, parent, false);
            itemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_zoo, parent, false);
            itemContentBinding.getRoot().setOnClickListener(this);
//            view.setOnClickListener(this);
            return new ContentViewHolder(itemContentBinding);
        } else {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tittle, parent, false);
            itemTittleBinding=DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_tittle, parent, false);
            return new TittleViewHolder(itemTittleBinding);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof ContentViewHolder) {
            Glide.with(((ContentViewHolder) holder).itemView.getContext()).pauseRequests();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof ContentViewHolder) {
            Glide.with(((ContentViewHolder) holder).itemView.getContext()).resumeRequests();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_VIEW_TYPE_CONTENT) {
            holder.itemView.setTag(position);
            itemContentBinding.tvTittle.setText(((ZooData.Result.Results) dataList.get(position)).getF_Name_Ch() == null ?
                    "" : ((ZooData.Result.Results) dataList.get(position)).getF_Name_Ch());
            itemContentBinding.tvInfo.setText(((ZooData.Result.Results) dataList.get(position)).getF_AlsoKnown() == null ?
                    "" : ((ZooData.Result.Results) dataList.get(position)).getF_AlsoKnown());
//            ((ContentViewHolder) holder).tvTittle.setText(((ZooData.Result.Results) dataList.get(position)).getF_Name_Ch() == null ?
//                    "" : ((ZooData.Result.Results) dataList.get(position)).getF_Name_Ch());
//            ((ContentViewHolder) holder).tvInfo.setText(((ZooData.Result.Results) dataList.get(position)).getF_AlsoKnown() == null ?
//                    "" : ((ZooData.Result.Results) dataList.get(position)).getF_AlsoKnown());

            RequestOptions options = new RequestOptions();
            options.centerInside();
            options.dontTransform();
            options.placeholder(R.mipmap.ic_launcher_round);
            options.error(R.mipmap.ic_launcher_round);
            options.fallback(R.mipmap.ic_launcher_round);

            if (paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))) != null &&
                    isHttpUrl(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))))) {

//                Glide.with(((ContentViewHolder) holder).itemView.getContext())
//                        .load(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))))
//                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
////                    .load((((ZooData.Result.Results) dataList.get(position)).getF_Pic01_URL()))
//                        .apply(options)
//                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                        .into(((ContentViewHolder) holder).ivPhoto);
                Glide.with(mContext)
                        .load(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .apply(options)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(itemContentBinding.ivPhoto);
//            Uri uri = Uri.parse(((ZooData.Result.Result
//                Glide.with(((ContentViewHolder) holder).itemView.getContext())
//                        .load(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .thumbnail(0.1f)
//                        .transition(new DrawableTransitionOptions().crossFade())
////                    .load((((ZooData.Result.Results) dataList.get(position)).getF_Pic01_URL()))
//                        .apply(options)
//                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                        .into(new SimpleTarget<Drawable>() {
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                                itemContentBinding.ivPhoto.setImageDrawable(resource);
//                            }
//                        });
//                Uri uri = Uri.parse(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))));
//                if(uri!=null) {
//                    ((ContentViewHolder) holder).ivPhoto.setImageURI(uri);
//                }

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            bitmap = Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(paserPhotoUrl((((ZooData.Result.Results) dataList.get(position)))))
//                                    //                    .load((((ZooData.Result.Results) dataList.get(position)).getF_Pic01_URL()))
//                                    .apply(options)
//                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                                    .into(140, 140)
//                                    .get();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//                ((ContentViewHolder) holder).ivPhoto.setImageBitmap(bitmap);

            }
        } else if (holder.getItemViewType() == ITEM_VIEW_TYPE_WARM) {
            itemWarnBinding.tvWarn.setText((String) dataList.get(position));
//            ((WarnViewHolder) holder).tvWarn.setText((String) dataList.get(position));
        } else if (holder.getItemViewType() == ITEM_VIEW_TYPE_TITTLE) {
//            ((TittleViewHolder) holder).tvTittle.setText("植物資料");
            itemTittleBinding.tvTittle.setText("植物資料");

        }
    }

    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
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
//        @BindView(R.id.tv_warn)
//        TextView tvWarn;
//
//        public WarnViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }

        private WarnViewHolder(ItemWarnBinding warnBinding) {
            super(warnBinding.getRoot());
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
        private ContentViewHolder(ItemZooBinding zooBinding) {
            super(zooBinding.getRoot());
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
        private TittleViewHolder(ItemTittleBinding tittleBinding){
            super(tittleBinding.getRoot());
        }
    }
}

