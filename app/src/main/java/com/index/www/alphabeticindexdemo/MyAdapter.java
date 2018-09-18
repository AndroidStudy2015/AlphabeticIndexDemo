package com.index.www.alphabeticindexdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVh> {

    Context mContext;
    List<String> mData;

    public MyAdapter(Context context, List<String> data) {

        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVh myVh, int i) {
        myVh.mTv.setText(mData.get(i));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyVh extends RecyclerView.ViewHolder {


        private final TextView mTv;

        public MyVh(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }
}
