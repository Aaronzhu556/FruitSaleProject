package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Notice;

import java.util.List;

@Mapper
public interface NoticeMapper {
    /*
    *     @Select("select * from t_notice")
    public List<Notice> GetAllNotice();
    @Delete("delete from t_notice where notice_id=#{notice_id}")
    public int DeleteNotice(int notice_id);
    @Insert("insert into t_notice(notice_title,notice_content,notice_time) values(#{notice_title},#{notice_content},#{notice_time})")
    public int AddNotice(Notice notice);
    @Select("select * from t_notice where notice_content  like '%' #{notice_content} '%'")
    public List<Notice> GetNoticeByName(String notice_content);
    *
    *
    * */
    List<Notice> GetAllNotice();
    int DeleteNotice(String notice_id);
    int AddNotice(Notice notice);
    List<Notice> GetNoticeByName(String notice_content);

}
