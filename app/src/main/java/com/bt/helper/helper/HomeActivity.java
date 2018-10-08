package com.bt.helper.helper;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bt.helper.helper.Model.HomeGridItem;
import com.bt.helper.helper.adapter.HomeGridAdapter;
import com.bt.helper.helper.util.DensityUtil;
import com.bt.helper.helper.util.PageJumpUtils;
import com.bt.helper.helper.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    private DrawerLayout mHomeLayoutMain;

    private ConstraintLayout mHeadLayoutMain;
    private Button mHeadLeftBtn;
    private TextView mHeadTitle;

    private RecyclerView mHelperGridRv;
    private List<HomeGridItem> mHomeGridList;
    private HomeGridAdapter mHomeGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        mHomeLayoutMain = findViewById(R.id.home_layout_main);
        mHeadLayoutMain = findViewById(R.id.head_layout_main);
        mHeadLeftBtn = findViewById(R.id.head_left_btn);
        mHeadTitle = findViewById(R.id.head_title);
        mHelperGridRv = findViewById(R.id.helper_grid_rv);
        StatusBarUtils.setTransparent(this);
        StatusBarUtils.setTopView(this,mHeadLayoutMain);

    }

    private void initData(){
        mHeadLeftBtn.setBackgroundResource(R.drawable.menu);
        mHeadTitle.setText("工具");

        mHomeGridList = new ArrayList<>();
        mHomeGridList.add(new HomeGridItem("打卡",R.drawable.punch, PageJumpUtils.PUNCH_CARD));
        mHomeGridList.add(new HomeGridItem("打卡数据",R.drawable.calendar, PageJumpUtils.PUNCH_CARD_DATA));
        mHomeGridList.add(new HomeGridItem("打卡统计",R.drawable.statistics, PageJumpUtils.STATISTICS));

        mHomeGridAdapter = new HomeGridAdapter(this,mHomeGridList);
        mHelperGridRv.setLayoutManager(new GridLayoutManager(this,4));
        mHelperGridRv.setAdapter(mHomeGridAdapter);
        mHelperGridRv.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener(){
        mHomeLayoutMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
                mHomeLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mHomeLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        mHeadLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeLayoutMain.openDrawer(Gravity.LEFT);
            }
        });

    }
}
