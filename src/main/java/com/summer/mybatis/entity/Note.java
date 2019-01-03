package com.summer.mybatis.entity;

import java.util.ArrayList;

public class Note {
    private Integer id;

    private Integer pid;

    private Integer category;

    ArrayList<Note> childNote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public ArrayList<Note> getChildNote() {
        return childNote;
    }

    public void setChildNote(ArrayList<Note> childNote) {
        this.childNote = childNote;
    }
}