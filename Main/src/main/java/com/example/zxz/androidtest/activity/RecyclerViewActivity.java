package com.example.zxz.androidtest.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuezhi.zxz on 2017/5/19.
 */

public class RecyclerViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview_activity);
        init();
    }

    void init() {
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(RecyclerViewActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecyclerViewActivity.this, "done", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter();
        adapter.setDataList();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged() called with: recyclerView = [" + recyclerView + "], newState = [" + newState + "]");
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition;
                    if(layoutManager instanceof GridLayoutManager) {
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if(layoutManager instanceof StaggeredGridLayoutManager) {
                        int [] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisibleItemPosition = findMax(into);
                    } else {
                        lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    }

                    if(recyclerView.getChildCount()>0 && lastVisibleItemPosition>=layoutManager.getItemCount()-1) {
                        Log.i(TAG, "onScrollStateChanged: load more");
                        adapter.addData(String.valueOf(Math.random()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled() called with: recyclerView = [" + recyclerView + "], dx = [" + dx + "], dy = [" + dy + "]");
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        int[] var3 = lastPositions;
        int var4 = lastPositions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int value = var3[var5];
            if(value > max) {
                max = value;
            }
        }

        return max;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) (itemView.findViewById(R.id.textview));
        }
    }

    protected void initData(List<String> list)
    {
        if(list == null) {
            list = new ArrayList<String>();
        }
        for (int i = 'A'; i < 'z'; i++)
        {
            list.add("" + (char) i);
        }
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<String> mDataList = new ArrayList<>();

        public void setDataList() {
            initData(mDataList);
        }

        public void addData(List<String> list) {
            mDataList.addAll(list);
        }

        public void addData(String str) {
            mDataList.add(str);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.layout_recyclerview_item, parent, false);
            final MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataList.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(position< mDataList.size()) {
                holder.tv.setText(mDataList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }
}
