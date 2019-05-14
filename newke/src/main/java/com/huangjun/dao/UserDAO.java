package com.huangjun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.User;

 

public interface UserDAO {
    List<User> findAll();
    User findById(@Param("id") int id);
    User findByName(@Param("name") String name);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(@Param("id") int id);
    
    
    

}
