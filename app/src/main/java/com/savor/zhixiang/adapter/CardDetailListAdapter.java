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
import com.savor.zhixiang.bean.CardDetailListItem;

import java.util.List;

/**
 * Created by hezd on 2017/9/22.
 */

public class CardDetailListAdapter extends BaseAdapter {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_COUNT = 2;
    private final Context mContext;
    private List<CardDetailListItem> mData;

    public CardDetailListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CardDetailListItem> datas) {
        this.mData = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        CardDetailListItem cardDetailListItem = mData.get(position);
        String dailytype = cardDetailListItem.getDailytype();
        int type = -1;
        if("1".equals(dailytype)) {// 文字
            type = TYPE_TEXT;
        }else if("3".equals(dailytype)) {// 图片
            type = TYPE_IMAGE;
        }
        return type;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextHolder textHolder = null;
        ImageHolder imageHolder = null;
        int itemViewType = getItemViewType(position);
        if(convertView == null) {
            switch (itemViewType) {
                case TYPE_TEXT:
                    textHolder = new TextHolder();
                    convertView = View.inflate(mContext, R.layout.item_card_detail_list_text,null);
                    textHolder.textView = (TextView) convertView.findViewById(R.id.tv_text);
                    textHolder.divider = convertView.findViewById(R.id.divider);
                    convertView.setTag(textHolder);
                    break;
                case TYPE_IMAGE:
                    imageHolder = new ImageHolder();
                    convertView = View.inflate(mContext,R.layout.item_card_detail_list_image,null);
                    imageHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
                    imageHolder.divider = convertView.findViewById(R.id.divider);
                    convertView.setTag(imageHolder);
                    break;
            }
        }else {
            switch (itemViewType) {
                case TYPE_TEXT:
                    textHolder = (TextHolder) convertView.getTag();
                    break;
                case TYPE_IMAGE:
                    imageHolder = (ImageHolder) convertView.getTag();
                    break;
            }
        }

        CardDetailListItem item = (CardDetailListItem) getItem(position);
        if(item!=null) {
            switch (itemViewType) {
                case TYPE_TEXT:
                    String stext = item.getStext();
                    textHolder.textView.setText(stext);
                    ViewGroup.LayoutParams layoutParams = textHolder.divider.getLayoutParams();
                    handleDividerHeight(position, itemViewType, layoutParams);
                    break;
                case TYPE_IMAGE:
                    String imageUrl = item.getSpicture();
                    ViewGroup.LayoutParams imageDividerLayout = imageHolder.divider.getLayoutParams();
                    Glide.with(mContext).load(imageUrl).placeholder(R.mipmap.ico_default).centerCrop().into(imageHolder.imageView);
                    handleDividerHeight(position,itemViewType,imageDividerLayout);
                    break;
            }
        }

        return convertView;
    }

    private void handleDividerHeight(int position, int itemViewType, ViewGroup.LayoutParams layoutParams) {
        int count = getCount();
        if(count>1&&position<count-1) {
            int currentType = itemViewType;
            int nextType = 0;
            CardDetailListItem nextItem = (CardDetailListItem) getItem(position + 1);
            String dailytype = nextItem.getDailytype();
            if("1".equals(dailytype)) {
                nextType = TYPE_TEXT;
            }else if("3".equals(dailytype)) {
                nextType = TYPE_IMAGE;
            }
            switch (currentType) {
                case TYPE_TEXT:
                    if(nextType == TYPE_TEXT) {
                        layoutParams.height = DensityUtil.dip2px(mContext,40);
                    }else if(nextType == TYPE_IMAGE) {
                        layoutParams.height = DensityUtil.dip2px(mContext,30);
                    }
                    break;
                case TYPE_IMAGE:
                    if(nextType == TYPE_TEXT) {
                        layoutParams.height = DensityUtil.dip2px(mContext,30);
                    }else if(nextType == TYPE_IMAGE) {
                        layoutParams.height = DensityUtil.dip2px(mContext,15);
                    }
                    break;
            }
        }else if(position==count-1) {
            layoutParams.height = DensityUtil.dip2px(mContext,25);
        }

    }

    public class TextHolder {
        public TextView textView;
        public View divider;
    }

    public class ImageHolder {
        public ImageView imageView;
        public View divider;
    }
}
