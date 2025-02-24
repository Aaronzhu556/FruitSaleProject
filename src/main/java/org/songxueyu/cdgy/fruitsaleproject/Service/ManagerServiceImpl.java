package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Manager;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ManagerMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public String managerLogin(Manager manager) {
        Manager manager1 = managerMapper.getManagerByName(manager.getManager_name());
        if (manager1!=null) {
            if (manager1.getManager_password().equals(manager.getManager_password())){
                return "200"; //密码正确
            }
            else return "201";//密码错误
        }
        else return "202";//该管理员不存在
    }

    @Override
    public Manager getManagerByName(String manager_name) {
        return managerMapper.getManagerByName(manager_name);
    }
}
