package com.index.www.alphabeticindexdemo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.index.www.alphabeticindexbar.IndexBarView;
import com.index.www.alphabeticindexdemo.MyAdapter;
import com.index.www.alphabeticindexdemo.R;
import com.index.www.alphabeticindexdemo.right.decoration.StickItemDecoration;
import com.index.www.alphabeticindexdemo.right.bean.ElementInfo;
import com.index.www.alphabeticindexdemo.right.util.GroupInfoCallbackFactory;
import com.index.www.alphabeticindexdemo.right.util.InteractHelper;
import com.index.www.alphabeticindexdemo.right.util.SortHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://blog.csdn.net/briblue/article/details/70211942
public class IndexBarActivity extends AppCompatActivity {
    private IndexBarView mIndexBarView;
    private List<String> mIndexLetterList;
    private GridLayoutManager mLayout;
    private List<ElementInfo> mSortList;
    private List<String> mFirstLetterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_bar);

        String[] rawCitys = getResources().getStringArray(R.array.citys);
        String[] codes = getResources().getStringArray(R.array.index);
        mIndexLetterList = Arrays.asList(codes);


//        添加自定义词典
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("重庆", new String[]{"CHONG", "QING"});
                        map.put("乐山", new String[]{"yue", "shan"});

                        return map;
                    }
                }));
        mSortList = SortHelper.sortByLetter(rawCitys, mIndexLetterList, "#");


        ElementInfo element1 = new ElementInfo();
        element1.setFirstLetter("定位");
        mSortList.add(0, element1);

        ElementInfo element = new ElementInfo();
        element.setFirstLetter("热门");
        mSortList.add(1, element);

        mFirstLetterList = SortHelper.getFirstLetterListBySorted(mSortList);

        setIndexBarView();
        setRecyclerViewBarView();
    }


    private void setIndexBarView() {
        mIndexBarView = (IndexBarView) findViewById(R.id.indexbar);


        mIndexBarView.setLetterList(mIndexLetterList);
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

                boolean success = InteractHelper.letRecyclerviewScrollToIndexPostion(index, mLayout, mFirstLetterList, mIndexLetterList);
                if (!success) {
                    //如果手指触摸的字母，RecyclerView里面没有包含以这个字母开头的元素，那么返回false，
                    // 让indexbarview显示当前的第一个元素的首字母的索引
                    InteractHelper.updateIndexBar(mIndexBarView, mLayout, mSortList, mIndexLetterList);

                }
            }

            @Override
            public void onTouchIndexBarAllTheTime(int index, float y) {

            }

            @Override
            public void onAnctionUp() {

            }
        });
    }

    private void setRecyclerViewBarView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayout);
        recyclerView.setAdapter(new MyAdapter(this, mSortList));
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {

                InteractHelper.updateIndexBar(mIndexBarView, mLayout, mSortList, mIndexLetterList);

            }
        }, 300);//立即执行，adapter没执行完，这里会报错

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                InteractHelper.updateIndexBar(mIndexBarView, mLayout, mSortList, mIndexLetterList);


            }
        });


        StickItemDecoration.Builder builder = new StickItemDecoration.Builder();
        StickItemDecoration decor = builder.bgColor(Color.LTGRAY)
                .callback(GroupInfoCallbackFactory.getInstance(mSortList, mIndexLetterList, mFirstLetterList))
                .textColor(Color.RED)
                .textOffsetX(30)
                .textsize(30)
                .dividerHeight(1)
                .headerHeight(70)
                .build();

        recyclerView.addItemDecoration(decor);
    }


}
