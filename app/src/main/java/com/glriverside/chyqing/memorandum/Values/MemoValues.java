package com.glriverside.chyqing.memorandum.Values;

//保存数据
public class MemoValues {
    private Integer mId;
    private String mTitle;
    private String mContent;
    private String mDate;
    private Boolean mToDo;
    private String mAlarmTime;
    private Boolean mAlarm;

    public void setId(Integer id){
        mId = id;
    }

    public Integer getId(){
        return mId;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setContent(String content){
        mContent = content;
    }

    public String getContent(){
        return mContent;
    }

    public void setDate(String date){
        mDate = date;
    }

    public String getDate(){
        return mDate;
    }

    public void setToDo(Boolean todo){
        mToDo = todo;
    }

    public Boolean getToDo(){
        return mToDo;
    }

    public void setAlarm(Boolean alarm){
        mAlarm = alarm;
    }

    public Boolean getAlarm(){
        return mAlarm;
    }

    public void setAlarmTime(String alarmTime){
        mAlarmTime = alarmTime;
    }

    public String getAlarmTime(){
        return mAlarmTime;
    }

    public String toString(){
        return "MemoValues{" +
                "id=" + mId +
                ",title='" + mTitle + "'\\" +
                ",content='" + mContent + "'\\" +
                ",time='" + mDate + "'\\" +
                ",todo='" + mToDo + "'\\" +
                ",alarm='" + mAlarm + "'\\" +
                ",alarmTime" + mAlarmTime +
                "}";
    }
}
