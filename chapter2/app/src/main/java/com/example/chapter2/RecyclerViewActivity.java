package com.example.chapter2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Data> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        mAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        setList();
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();

    }

    public void setList(){
        mList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        nameList.add("敬礼我的超级英雄");
        nameList.add("锦觅旭凤误会解开");
        nameList.add("90斤的脸 130斤的腿");
        nameList.add("易烊千玺 亚运会");
        nameList.add("开学季");
        nameList.add("高考状元");
        nameList.add("华为5G");
        nameList.add("北京冬奥会");
        nameList.add("新能源汽车");
        nameList.add("特斯拉上海建厂");
        int hotValue = 105432;
        for(int i=1;i<=30;i++)
        {
            Data data;
            if (i<=10){
                 data= new Data(i,nameList.get(i-1),hotValue);
            }
            else {
                 data = new Data(i,i+"Hello World",hotValue);
            }
            hotValue -= 1987;
            mList.add(data);
        }
    }

}
