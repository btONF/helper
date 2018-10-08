package com.bt.helper.helper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bt.helper.helper.Model.HomeGridItem;
import com.bt.helper.helper.R;
import com.bt.helper.helper.base.BaseRecyclerViewAdapter;
import com.bt.helper.helper.base.CommonViewHolder;
import com.bt.helper.helper.util.PageJumpUtils;

import java.util.List;

/**
 * Created by 18030693 on 2018/8/13.
 */

public class HomeGridAdapter extends BaseRecyclerViewAdapter{
    public HomeGridAdapter(Context context, List dataList) {
        super(context, R.layout.home_grid_item, dataList);
    }

    @Override
    public void convert(CommonViewHolder holder,final Object data) {
        holder.setText(R.id.home_grid_title,((HomeGridItem)data).getTitle())
                .setBackgroundResource(R.id.home_grid_icon,((HomeGridItem)data).getPicRes());
        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PageJumpUtils.getInstance().JumpToPage(context,((HomeGridItem)data).getType(),null);
            }
        });
    }

}
