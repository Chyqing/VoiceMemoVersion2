package com.glriverside.chyqing.memorandum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glriverside.chyqing.memorandum.Values.MemoValues;
import com.glriverside.chyqing.memorandum.R;

import java.util.List;

public class MemoAdapter extends BaseAdapter {
    private List<MemoValues> memoValuesList;
    private Context context;
    private int mId;

    public MemoAdapter(List<MemoValues> list, Context context, int layoutId){
        this.context = context;
        this.mId = layoutId;
        this.memoValuesList = list;
    }

    @Override
    public int getCount() {
        if (memoValuesList != null && memoValuesList.size() > 0){
            return memoValuesList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        if (memoValuesList != null && memoValuesList.size() > 0){
            return memoValuesList.get(i);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.memo_list_item, viewGroup, false);

            vh = new ViewHolder();
            vh.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
            vh.tvContent = (TextView)convertView.findViewById(R.id.tv_content);
            vh.tvTime = (TextView)convertView.findViewById(R.id.tv_time);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        String title = memoValuesList.get(i).getTitle();
        String content = memoValuesList.get(i).getContent();
        String time = memoValuesList.get(i).getDate();

        vh.tvTitle.setText(title);
        vh.tvContent.setText(content);
        vh.tvTime.setText(time);

        return convertView;
    }

    public void removeItem(int i){
        this.memoValuesList.remove(i);
    }

    public class ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
    }
}
