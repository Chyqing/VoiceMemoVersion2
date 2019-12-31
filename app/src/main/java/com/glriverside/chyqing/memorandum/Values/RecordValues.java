package com.glriverside.chyqing.memorandum.Values;

//保存数据
public class RecordValues {
    private Integer mId;
    private String mTitle;
    private String mContent;

    RecordValues(Integer mId,String mTitle,String mContent){
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

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
}
