package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Manager;

@Mapper
public interface ManagerMapper {
    Manager getManagerByName(String manager_name);
}
