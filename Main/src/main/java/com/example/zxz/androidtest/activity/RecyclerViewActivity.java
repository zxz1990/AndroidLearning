package com.example.zxz.androidtest.activity;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuezhi.zxz on 2017/5/19.
 */

public class RecyclerViewActivity extends BaseActivity {
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        adapter = new MyRecyclerViewAdapter();
        adapter.setDataList();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged() called with: recyclerView = [" + recyclerView + "], newState = [" + newState + "]");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition;
                    if (layoutManager instanceof GridLayoutManager) {
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisibleItemPosition = findMax(into);
                    } else {
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    if (recyclerView.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                        Log.i(TAG, "onScrollStateChanged: load more");
                        loadMore();
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

        for (int var5 = 0; var5 < var4; ++var5) {
            int value = var3[var5];
            if (value > max) {
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

    protected void initData(List<String> list) {
        if (list == null) {
            list = new ArrayList<String>();
        }
        for (int i = 'A'; i < 'z'; i++) {
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
            if (position < mDataList.size()) {
                holder.tv.setText(mDataList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }


    void loadMore() {
        Log.d(TAG, "loadMore() called subscribe");
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                try {
                    Thread.sleep(3000);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 10; ++i) {
                        list.add(String.valueOf(Math.random()));
                    }
                    emitter.onNext(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> o) throws Exception {
                        int oldCount = adapter.getItemCount();
                        adapter.addData(o);
                        int count = adapter.getItemCount();
                        adapter.notifyItemRangeInserted(oldCount, count - oldCount);
                    }
                });
    }
}
