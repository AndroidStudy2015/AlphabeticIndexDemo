package com.index.www.alphabeticindexdemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.index.www.alphabeticindexbar.bean.ElementInfo;
import com.index.www.alphabeticindexbar.decoration.StickItemDecoration;
import com.index.www.alphabeticindexbar.helper.InteractHelper;
import com.index.www.alphabeticindexbar.helper.SortHelper;
import com.index.www.alphabeticindexbar.impl.drawheader.DrawHeaderStyle1Impl;
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

//        1. 得到原始数据
        String[] rawCitys = getResources().getStringArray(R.array.citys);//城市元素数组
        String[] indexs = getResources().getStringArray(R.array.index);//索引数组
        mIndexLetterList = Arrays.asList(indexs);//为了方便，将索引数组转换为集合


//        2.添加自定义词典，因为使用到拼音排序，有些你要特殊处理的文字，可以在这里定义
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

//        3.得到拼音排序后的城市元素列表
//          最后一个参数：当城市元素列表里的首字母，在indexBar里找不到时，把他归入otherFirstLetter（例如：#）
        mSortList = SortHelper.sortByLetter(rawCitys, mIndexLetterList, "#");

//        4. 在sortlist里手动增加一些特殊的元素，只要设置首字母即可，如：定位、热门
//          （注意这里定位、热门也要在indexbar的索引集合mIndexLetterList里添加，并且顺序对应）
        mSortList.add(0, new ElementInfo("定位"));//注意添加顺序
        mSortList.add(1, new ElementInfo("热门"));//注意添加顺序

        setIndexBarView();
        setRecyclerView();
    }


    private void setIndexBarView() {
        mIndexBarView = (IndexBarView) findViewById(R.id.indexbar);

//         下面这些属性，已经有了默认值，也可以自定义
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


        mIndexBarView.setLetterList(mIndexLetterList);//必须设置
        mIndexBarView.setOnTouchIndexBarListener(new IndexBarView.OnTouchIndexBarListener() {
            @Override
            public void onTouchIndexBar(int index) {
                // 当触摸indexBar时，让RecyclerView滚动到指定索引位置，并让这个位置处于RecyclerView的顶部
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

    private void setRecyclerView() {
        //1. RecyclerView常规初始化
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayout);
        recyclerView.setAdapter(new MyAdapter(this, mSortList));

        //2. 添加装饰，粘性头布局逻辑在这实现
        StickItemDecoration.Builder builder = new StickItemDecoration.Builder();
        StickItemDecoration decor = builder
                //设置分组信息，必须设置，按照自己的分组逻辑去写，GroupInfoDefalutImpl是一个写好的实现类，应该够用，
                // 有别的分组需求可以实现iGroupInfo接口在这里自定义
                .iGroupInfo(new GroupInfoDefalutImpl(mSortList))
                //设置粘性头布局样式，可以不设置，因为默认实现为DrawHeaderDefaultImpl，
                //也可以参考DrawHeaderDefaultImpl写法，写自己的样式
                .iDrawHeader(new DrawHeaderStyle1Impl())
                .dividerHeight(1)//设置分割线高度，单位px
                .headerHeight(70)//设置头布局高度，单位px
                .build();
        recyclerView.addItemDecoration(decor);

        //3.初始化让indexbar选中第0个索引
        mIndexBarView.setCurrentIndex(0);
        //4. RecyclerView滚动时候，让indexBar联动改变索引位置
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                InteractHelper.updateIndexBar(mIndexBarView, mLayout, mSortList, mIndexLetterList);


            }
        });

    }


}
