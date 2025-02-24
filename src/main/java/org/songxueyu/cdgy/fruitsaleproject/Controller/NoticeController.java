package org.songxueyu.cdgy.fruitsaleproject.Controller;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Notice;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.NoticeService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/getallnotice")
    @ResponseBody
    public MyResponse GetAllNotice(@RequestBody QueryInfo queryInfo){

        List<Notice> notices = noticeService.GetAllNotice(queryInfo);
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(notices)
                .info(String.valueOf(notices.size())).build();

    }
    @RequestMapping("/deletenotice")
    @ResponseBody
    public MyResponse DeleteNotice(@RequestParam String notice_id){

        noticeService.DeleteNotice(notice_id);
        return MyResponse.builder()
                .code("200")
                .msg("删除成功")
                .build();


    }

    @RequestMapping("/addnotice")
    @ResponseBody
    public MyResponse AddNotice(@RequestBody Notice notice){

        noticeService.AddNotice(notice);
        return MyResponse.builder()
                .code("200")
                .msg("添加成功")
                .build();

    }
}
