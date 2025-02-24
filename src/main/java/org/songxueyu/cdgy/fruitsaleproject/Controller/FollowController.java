package org.songxueyu.cdgy.fruitsaleproject.Controller;

import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.FollowService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.management.MemoryUsage;
import java.util.List;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @RequestMapping("/getfollowing")
    @ResponseBody
    public MyResponse GetFollowing(@RequestParam String follow_user_from){

        List<String> list = followService.GetUserFollowing(follow_user_from);
        if (!list.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .info(String.valueOf(list.size()))
                    .object(list).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("暂无关注")
                    .build();

    }

    @RequestMapping("/getfans")
    @ResponseBody
    public MyResponse GetFans(@RequestParam String follow_user_to ){

        List<String> list = followService.GetUserFans(follow_user_to);
        if (!list.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .info(String.valueOf(list.size()))
                    .object(list).build();

        else
            return MyResponse.builder()
                    .code("201")
                    .msg("暂无粉丝").build();


    }
    @RequestMapping("/followuser")
    @ResponseBody
    public MyResponse FollowUser(@RequestParam String follow_user_from,@RequestParam String follow_user_to  ){
        int i = followService.FollowUser(follow_user_from, follow_user_to);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("关注成功").build();
        else   return MyResponse.builder()
                .code("201")
                .msg("关注失败").build();
    }

    @RequestMapping("/unfollowuser")
    @ResponseBody
    public MyResponse UnFollowUser(@RequestParam String follow_user_from,@RequestParam String follow_user_to  ){

        int i = followService.UnFollowUser(follow_user_from, follow_user_to);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("取消关注成功").build();
        else
            return MyResponse.builder()
                .code("201")
                .msg("取消关注失败").build();

    }


}
