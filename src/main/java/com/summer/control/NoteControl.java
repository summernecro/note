package com.summer.control;


import com.summer.base.OnFinishI;
import com.summer.base.bean.BaseResBean;
import com.summer.base.bean.Tools;
import com.summer.mybatis.DBTools;
import com.summer.mybatis.entity.Note;
import com.summer.mybatis.mapper.NoteMapper;
import com.summer.util.GsonUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping("/note")
public class NoteControl {

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req, res);
        SqlSession sqlSession = DBTools.getSession();
        NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
        String gson = req.getParameter("data");
        Note note = GsonUtil.getInstance().fromJson(gson,Note.class);
        noteMapper.insert(note);
        sqlSession.commit();
        sqlSession.close();
        BaseResBean baseResBean = new BaseResBean();
        baseResBean.setData(GsonUtil.getInstance().toJson(note));
        Tools.printOut(res,baseResBean);

    }


    @RequestMapping(value = "/getAllNotes",method = RequestMethod.GET)
    public void getAllNotes(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req, res);
        SqlSession sqlSession = DBTools.getSession();
        NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
        BaseResBean baseResBean = new BaseResBean();
        baseResBean.setData(GsonUtil.getInstance().toJson(noteMapper.selectAll()));
        sqlSession.close();
        Tools.printOut(res,baseResBean);

    }


    @RequestMapping(value = "/selectByPId",method = RequestMethod.POST)
    public void selectByPId(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req, res);
        String pid = req.getParameter("data");
        SqlSession sqlSession = DBTools.getSession();
        NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
        BaseResBean baseResBean = new BaseResBean();
        baseResBean.setData(noteMapper.selectByPId(Integer.parseInt(pid)));
        sqlSession.close();
        Tools.printOut(res,baseResBean);

    }


    @RequestMapping(value = "/selectParentById",method = RequestMethod.POST)
    public void selectParentById(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req, res);
        String id = req.getParameter("data");
        SqlSession sqlSession = DBTools.getSession();
        NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
        BaseResBean baseResBean = new BaseResBean();
        Note note = noteMapper.selectByPrimaryKey(Integer.parseInt(id));
        baseResBean.setData(noteMapper.selectByPId(note.getPid()));
        sqlSession.close();
        Tools.printOut(res,baseResBean);

    }

    //递归添加子文件夹
    public void addChildNote(NoteMapper noteMapper, Note note, OnFinishI onFinishI){
        ArrayList<Note> childNotes = (ArrayList<Note>) noteMapper.selectByPId(note.getId());
        if(childNotes==null||childNotes.size()==0){
            onFinishI.onFinish(note);
        }else{
            note.setChildNote(childNotes);

            ArrayList<Note> notes = (ArrayList<Note>) noteMapper.selectByPId(childNotes.get(0).getId());


        }
    }
}
