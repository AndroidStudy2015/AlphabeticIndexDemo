package com.index.www.alphabeticindexdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.index.www.alphabeticindexbar.bean.ElementInfo;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ElementInfo> mData;

    public MyAdapter(Context context, List<ElementInfo> data) {

        mContext = context;
        mData = data;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (position == 1) {
            return 1;

        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item0, viewGroup, false);
            return new MyVh0(view);

        } else if (itemViewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item1, viewGroup, false);
            return new MyVh1(view);

        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item2, viewGroup, false);
            return new MyVh2(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myVh, int i) {
        if (myVh instanceof MyVh0) {
            MyVh0 vh0 = (MyVh0) myVh;

        }
        if (myVh instanceof MyVh1) {
            MyVh1 vh1 = (MyVh1) myVh;

        }
        if (myVh instanceof MyVh2) {
            MyVh2 vh2 = (MyVh2) myVh;
            vh2.mTv.setText(mData.get(i).getName());

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyVh0 extends RecyclerView.ViewHolder {


        public MyVh0(@NonNull View itemView) {
            super(itemView);
        }
    }
    class MyVh1 extends RecyclerView.ViewHolder {


        public MyVh1(@NonNull View itemView) {
            super(itemView);
        }
    }


    class MyVh2 extends RecyclerView.ViewHolder {


        private final TextView mTv;

        public MyVh2(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }
}
