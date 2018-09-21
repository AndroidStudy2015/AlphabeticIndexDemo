package com.index.www.alphabeticindexdemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.index.www.alphabeticindexbar.bean.ElementInfo;
import com.index.www.alphabeticindexbar.decoration.StickItemDecoration;
import com.index.www.alphabeticindexbar.helper.InteractHelper;
import com.index.www.alphabeticindexbar.helper.SortHelper;
import com.index.www.alphabeticindexbar.impl.groupinfo.GroupInfoDefalutImpl;
import com.index.www.alphabeticindexbar.view.IndexBarView;
import com.index.www.alphabeticindexdemo.MyAdapter;
import com.index.www.alphabeticindexdemo.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_bar);

//        得到原始数据
        String[] rawCitys = getResources().getStringArray(R.array.citys);
        String[] indexs = getResources().getStringArray(R.array.index);
        mIndexLetterList = Arrays.asList(indexs);


//        添加自定义词典，因为使用到拼音排序，有些你要特殊处理的文字，可以在这里定义
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("重庆", new String[]{"CHONG", "QING"});
                        map.put("乐亭", new String[]{"LAO", "TING"});
                        map.put("单县", new String[]{"SHAN", "XIAN"});

                        return map;
                    }
                }));

//        得到排序后的
        mSortList = SortHelper.sortByLetter(rawCitys, mIndexLetterList, "#");


        ElementInfo element1 = new ElementInfo();
        element1.setFirstLetter("定位");
        mSortList.add(0, element1);

        ElementInfo element = new ElementInfo();
        element.setFirstLetter("热门");
        mSortList.add(1, element);


        setIndexBarView();
        setRecyclerViewBarView();
    }


    private void setIndexBarView() {
        mIndexBarView = (IndexBarView) findViewById(R.id.indexbar);


        mIndexBarView.setLetterList(mIndexLetterList);
//        mIndexBarView.setDefaultBackgroundResource(R.drawable.default_bg);
//        mIndexBarView.setPressBackgroundResource(R.drawable.press_bg);
//        mIndexBarView.setPaddingTop(10);
//        mIndexBarView.setPaddingBottom(10);
//        mIndexBarView.setTextDefaultColor(0x85555555);
//        mIndexBarView.setTextPressColor(0xff555555);
//        mIndexBarView.setCurrentIndexColor(0xFFDE5713);
//
//        mIndexBarView.setLetterSizeScale(0.85f);
//        mIndexBarView.setLongTextSmallScale(0.7f);
//        mIndexBarView.setTypeface(Typeface.MONOSPACE);

        mIndexBarView.setOnTouchIndexBarListener(new IndexBarView.OnTouchIndexBarListener() {
            @Override
            public void onTouchIndexBar(int index) {
                Log.e("ccc0", index + "");

                boolean success = InteractHelper.letRecyclerviewScrollToIndexPostion(index, mLayout, mSortList, mIndexLetterList);
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
        mIndexBarView.setCurrentIndex(0);//初始化让indexbar选中第0个索引
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                InteractHelper.updateIndexBar(mIndexBarView, mLayout, mSortList, mIndexLetterList);


            }
        });


        StickItemDecoration.Builder builder = new StickItemDecoration.Builder();
        StickItemDecoration decor = builder
                .iGroupInfo(new GroupInfoDefalutImpl(mSortList))//必须设置
                .dividerHeight(1)
                .headerHeight(70)
                .build();

        recyclerView.addItemDecoration(decor);
    }


}
