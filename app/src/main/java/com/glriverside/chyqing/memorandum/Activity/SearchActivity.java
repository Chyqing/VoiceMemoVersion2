package com.glriverside.chyqing.memorandum.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.glriverside.chyqing.memorandum.Adapter.MemoAdapter;
import com.glriverside.chyqing.memorandum.Contract.MemoContract;
import com.glriverside.chyqing.memorandum.Manager.MemoOpenHelper;
import com.glriverside.chyqing.memorandum.R;
import com.glriverside.chyqing.memorandum.Values.MemoValues;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity{
    private ListView lvSearch;
    private EditText etSearch;
    private Button btSearch;
    private String title;
    private MemoOpenHelper memoOpenHelper;
    private SQLiteDatabase database;
    private BottomNavigationView bottomNavigationView;
    private MemoAdapter memoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        init();
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResult();
            }
        });

        lvSearch.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, MemoEditActivity.class);
                MemoValues memoValues = (MemoValues) lvSearch.getItemAtPosition(i);
                intent.putExtra(MemoContract.MemoEntry._ID, memoValues.getId().toString());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoValues.getTitle());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_DATE, memoValues.getDate());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_CONTENT_PATH, memoValues.getContent());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_ALARM, memoValues.getAlarm());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_ALARM_TIME, memoValues.getAlarmTime());
                // intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TODO, memoValues.getToDo().toString());
                intent.putExtra(MemoActivity.MODEL, "true");
                startActivity(intent);
            }
        });
    }

    public void init(){
        lvSearch = findViewById(R.id.lv_search);
        etSearch = findViewById(R.id.et_search_title);
        btSearch = findViewById(R.id.bt_search_result);
        bottomNavigationView = findViewById(R.id.bottom_nav);
    }

    public void searchResult(){
        title = etSearch.getText().toString();
        List<MemoValues> memoValuesList = new ArrayList<>();
        List<MemoValues> searchResultList = new ArrayList<>();
        memoOpenHelper = new MemoOpenHelper(SearchActivity.this);
        database = memoOpenHelper.getReadableDatabase();

        Cursor cursor = database.query(MemoContract.MemoEntry.TABLE_NAME,
                null,
                null/*MemoContract.MemoEntry.COLUMN_NAME_TITLE + " LIKE ? "*/,
                null/*new String[]{"%"+title+"%"}*/,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            MemoValues values;
            while (!cursor.isAfterLast()) {

                //实例化一个MemoValues
                values = new MemoValues();

                //将数据库中的数据赋给values
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry._ID))));
                values.setTitle(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
                values.setDate(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_DATE)));
                values.setContent(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_CONTENT_PATH)));

                //是否提醒

                //是否为待办事件

                //存入memoValuesList中
                memoValuesList.add(values);
                cursor.moveToNext();
            }
        }

        for (MemoValues i:memoValuesList){
            if (i.getTitle().contains(title)){
                searchResultList.add(i);
            }
        }
        cursor.close();
        database.close();
        memoAdapter = new MemoAdapter(searchResultList, SearchActivity.this, R.layout.memo_list_item);
        lvSearch.setAdapter(memoAdapter);
    }
}
