package com.savor.zhixiang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.ListItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/7/4.
 */

public class AllListAdapter extends BaseAdapter{
    private Context context;
    private List<ListItem> commonList  = new ArrayList<ListItem>();
    //为三种布局定义一个标识
    private final int TYPE_SMALL = 0;
    private final int TYPE_BIG = 1;
    public static final float SCAL = 1.829f;
    public AllListAdapter(Context mcontext){
        this.context = mcontext;
    }
    @Override
    public int getCount() {
        return commonList.size();
    }

    @Override
    public Object getItem(int position) {
        return commonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public void setData(List<ListItem> common){
        commonList.clear();
        commonList.addAll(common);
        notifyDataSetChanged();
    }

    public void clear() {
        commonList.clear();
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderSmall holderSmall;
        if (convertView == null) {
            holderSmall = new ViewHolderSmall();
            convertView = View.inflate(context, R.layout.item_collect,null);
            holderSmall.contentSmallImgIV = (ImageView) convertView.findViewById(R.id.content_small_img);
            holderSmall.contentSmallTitleTV = (TextView) convertView.findViewById(R.id.content_small_title);
            holderSmall.contentSmallSourceTV = (TextView) convertView.findViewById(R.id.content_small_source);
            holderSmall.contentSmallTimeTV = (TextView) convertView.findViewById(R.id.content_small_time);
            ViewGroup.LayoutParams layoutParams = holderSmall.contentSmallImgIV.getLayoutParams();
            float widthInPx = DensityUtil.getWidthInPx(context);
            float height = widthInPx*SCAL;
            layoutParams.height = (int) height;
            convertView.setTag(R.id.tag_holder, holderSmall);
        } else {
            holderSmall = (AllListAdapter.ViewHolderSmall) convertView.getTag(R.id.tag_holder);
        }
        final ListItem itemVo = commonList.get(position);
        Glide.with(context)
                .load(itemVo.getImgUrl())
                .placeholder(R.drawable.kong_mrjz)
                .error(R.drawable.kong_mrjz)
                .crossFade()
                .into(holderSmall.contentSmallImgIV);
        holderSmall.contentSmallTitleTV.setText(itemVo.getTitle());
        holderSmall.contentSmallSourceTV.setText(itemVo.getSourceName());
        holderSmall.contentSmallTimeTV.setText(itemVo.getBespeak_time());



        return convertView;
    }

    public class ViewHolderSmall{
        private ImageView contentSmallImgIV;
        private TextView contentSmallTitleTV;
        private TextView contentSmallSourceTV;
        private TextView contentSmallTimeTV;
    }


}
