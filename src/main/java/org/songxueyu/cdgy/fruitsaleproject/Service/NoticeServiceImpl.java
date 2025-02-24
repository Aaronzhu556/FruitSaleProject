package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Notice;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.NoticeMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.NoticeService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> GetAllNotice(QueryInfo queryInfo){
        if (queryInfo.getQuerytext().equals("")) return noticeMapper.GetAllNotice();
        else return noticeMapper.GetNoticeByName(queryInfo.getQuerytext());
    }
    @Override
    public int DeleteNotice(String notice_id){
        return noticeMapper.DeleteNotice(notice_id);
    }

    @Override
    public int AddNotice(Notice notice){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        notice.setNotice_time(simpleDateFormat.format(date));
        notice.setNotice_id(UuidUtil.getUuid());
        return noticeMapper.AddNotice(notice);
    }
}
