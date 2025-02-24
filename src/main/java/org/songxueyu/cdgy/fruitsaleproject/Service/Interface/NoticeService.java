package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Notice;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {
    public List<Notice> GetAllNotice(QueryInfo queryInfo);
    public int DeleteNotice(String notice_id);
    public int AddNotice(Notice notice);
}
