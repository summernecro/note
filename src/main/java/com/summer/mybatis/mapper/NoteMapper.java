package com.summer.mybatis.mapper;

import com.summer.mybatis.entity.Note;

import java.util.List;

public interface NoteMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    Note selectByPrimaryKey(Integer id);
//
    List<Note> selectAll();

    List<Note> selectByPId(Integer pid);

    int updateByPrimaryKey(Note record);
}