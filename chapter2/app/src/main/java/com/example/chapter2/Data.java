package com.example.chapter2;

public class Data {
    private String name;
    private int index;
    private int hotnum;
    public Data(int index, String n, int h){
        this.index = index;
        name = n;
        hotnum = h;
    }

    public String getName(){
        return name;
    }
    public int getHotnum()
    {
        return  hotnum;
    }
    public int getIndex()
    {
        return  index;
    }
    public void setName(String n){
        name = n;
    }
    public void setHotnum( int h)
    {
        hotnum = h;
    }
    public void setIndex( int index)
    {
        this.index = index;
    }
}
