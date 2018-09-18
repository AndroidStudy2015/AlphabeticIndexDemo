package com.index.www.alphabeticindexdemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.index.www.alphabeticindexbar.IndexBarView;
import com.index.www.alphabeticindexdemo.right.GroupInfo;
import com.index.www.alphabeticindexdemo.right.StickItemDecoration;

import java.util.ArrayList;
import java.util.List;

//https://blog.csdn.net/briblue/article/details/70211942
public class IndexBarActivity extends AppCompatActivity {

    private IndexBarView mIndexBarView;
    private ArrayList<String> mLetterList;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_bar);

        setIndexBarView();
        setRecyclerViewBarView();
    }


    private void setIndexBarView() {
        initLetterList();
        mIndexBarView = (IndexBarView) findViewById(R.id.indexbar);


        mIndexBarView.setLetterList(mLetterList);
        mIndexBarView.setDefaultBackgroundResource(R.drawable.default_bg);
        mIndexBarView.setPressBackgroundResource(R.drawable.press_bg);
        mIndexBarView.setPaddingTop(10);
        mIndexBarView.setPaddingBottom(10);
        mIndexBarView.setTextDefaultColor(0x85555555);
        mIndexBarView.setTextPressColor(0xff555555);
        mIndexBarView.setCurrentIndexColor(0xFFDE5713);

        mIndexBarView.setLetterSizeScale(0.85f);
        mIndexBarView.setLongTextSmallScale(0.7f);
        mIndexBarView.setTypeface(Typeface.MONOSPACE);

        mIndexBarView.setOnTouchIndexBarListener(new IndexBarView.OnTouchIndexBarListener() {
            @Override
            public void onTouchIndexBar(int index) {
                Log.e("ccc0", index + "");


//                https://blog.csdn.net/shanshan_1117/article/details/78780137 定位到指定位置，并且第一位显示
                mLayout.scrollToPositionWithOffset(index * 10, 0);
            }

            @Override
            public void onTouchIndexBarAllTheTime(int index, float y) {

            }

            @Override
            public void onAnctionUp() {
                Log.e("ccc2", "up");

            }
        });
    }

    private void setRecyclerViewBarView() {
        mRecyclerView = findViewById(R.id.recyclerview);

        mLayout = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayout);

        List<String> data = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            data.add(i + "item");
        }
        mRecyclerView.setAdapter(new MyAdapter(this, data));

        int firstVisibleItemPosition = mLayout.findFirstVisibleItemPosition();
        mIndexBarView.setCurrentIndex(firstVisibleItemPosition / 10);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                滚动过程中，改变indexBar的字母颜色
                int firstVisibleItemPosition = mLayout.findFirstVisibleItemPosition();
                mIndexBarView.setCurrentIndex(firstVisibleItemPosition / 10);
            }
        });

        StickItemDecoration.GroupInfoCallback callback = new StickItemDecoration.GroupInfoCallback() {
            @Override
            public GroupInfo getGroupInfo(int position) {

                /**
                 * 分组逻辑，这里为了测试每10个数据为一组。大家可以在实际开发中
                 * 替换为真正的需求逻辑
                 */
                int groupId = position / 10;
                int index = position % 10;
                GroupInfo groupInfo = new GroupInfo(groupId, mLetterList.get(groupId));
                groupInfo.setPosition(index);
                groupInfo.setGroupLength(10);
                return groupInfo;
            }
        };
        StickItemDecoration.Builder builder = new StickItemDecoration.Builder();
        StickItemDecoration decor = builder.bgColor(Color.LTGRAY)
                .callback(callback)
                .textColor(Color.RED)
                .textOffsetX(30)
                .textsize(30)
                .dividerHeight(1)
                .headerHeight(70)
                .build();

        mRecyclerView.addItemDecoration(decor);
    }


    private void initLetterList() {
        mLetterList = new ArrayList<>();
        mLetterList.add("☆");
        mLetterList.add("★");
        mLetterList.add("热门");


        for (int i = 0; i < 26; i++) {

            mLetterList.add((char) (i + 65) + "");
        }

    }
}
