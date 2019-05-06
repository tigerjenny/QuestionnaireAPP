package com.star.project;

import android.util.Log;

public class Model {
    /*
     * This Class defines questions' any value and how can you use them.
     * You can set values only when a Model Object create, and get them by use getter.
     */

    //
    private String id;
    // Chinese (Taiwan) name
    private String name_tw;
    // English name
    private String name_eg;
    // Data's max length
    private int size;
    // Data's type
    private int type;
    // Data's identifier in database
    private int identifier;
    // Just Ps.
    private String ps;
    // If the question has checkbox, you can set this
    private String[] list;
    // If the question has text before
    private String text;

    public Model(String name_tw, String name_eg, String id, int type, String[] list, String text){
        this.name_tw = name_tw;
        this.name_eg = name_eg;
        this.id = id;
        this.type = type;
        this.list = list;
        this.text = text;
    }
    public Model(String name_tw, String name_eg, String id, int type, String text){
        this.name_tw = name_tw;
        this.name_eg = name_eg;
        this.id = id;
        this.type = type;
        this.text = text;
    }
    public Model(String name_tw, String name_eg, String id, int type, String[] list){
        this.name_tw = name_tw;
        this.name_eg = name_eg;
        this.id = id;
        this.type = type;
        this.list = list;
    }
    public Model(String name_tw, String name_eg, String id, int type){
        this.name_tw = name_tw;
        this.name_eg = name_eg;
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName_tw() {
        return name_tw;
    }

    public String getName_eg() {
        return name_eg;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getPs() {
        return ps;
    }

    public String[] getList() {
        return list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
