package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.Mapper.FollowMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.FollowService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<String > GetUserFollowing(String follow_user_from){
        return followMapper.GetUserFollowing(follow_user_from);
    }
    @Override
    public List<String>GetUserFans(String follow_user_to){
        return followMapper.GetUserFans(follow_user_to);
    }
    @Override
    public int FollowUser(String follow_user_from,String follow_user_to){
        String follow_id = UuidUtil.getUuid();
        return followMapper.FollowUser(follow_id,follow_user_from, follow_user_to);
    }

    @Override
    public int UnFollowUser(String follow_user_from,String follow_user_to){
        return followMapper.UnFollowUser(follow_user_from, follow_user_to);
    }
}
