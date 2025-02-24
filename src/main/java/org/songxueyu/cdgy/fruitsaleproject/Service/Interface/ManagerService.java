package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Manager;
import org.springframework.stereotype.Service;

@Service
public interface ManagerService {
    public String managerLogin(Manager manager);
    public Manager getManagerByName(String manager_name);
}
