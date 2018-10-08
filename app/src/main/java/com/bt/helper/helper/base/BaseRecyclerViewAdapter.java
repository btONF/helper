package com.bt.helper.helper.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.helper.helper.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18030693 on 2018/8/13.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>{
    public int rootLayoutResId;
    public Context context;
    public List<T> dataList = new ArrayList<>();
    public BaseRecyclerViewAdapter(Context context, int rootLayoutResId, List<T> dataList){
        super();
        this.context = context;
        this.rootLayoutResId = rootLayoutResId;
        this.dataList.addAll(dataList);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(rootLayoutResId,parent,false);
        return new CommonViewHolder(context,convertView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder,dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 处理视图数据绑定
     * @param holder
     * @param data
     */

    public abstract void convert(CommonViewHolder holder, T data);

    protected List<T> getDataList(){
        return dataList;
    }

    protected void setDataList(List<T> dataList){
        if (dataList !=null && dataList.size() != 0){
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
}
