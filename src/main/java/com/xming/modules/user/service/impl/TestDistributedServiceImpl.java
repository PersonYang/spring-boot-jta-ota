package com.xming.modules.user.service.impl;


import com.xming.modules.primary.bean.UsersInfo;
import com.xming.modules.primary.dao.UsersDao;
import com.xming.modules.secondary.bean.Student;
import com.xming.modules.secondary.dao.StudentDao;
import com.xming.modules.user.service.TestDistributedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mc on 17/9/1.
 */

@Service
public class TestDistributedServiceImpl implements TestDistributedService {


   @Autowired
   StudentDao studentDao;

    @Autowired
    UsersDao usersDao;
    @Transactional
    @Override
    public void TestDistribute(String isDistribute) {
        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setFname("James");
        usersInfo.setLname("Harden");

        usersDao.insertUserInfo(usersInfo);

        Student student = new Student();
        student.setName("James");
        student.setAge(34);
        student.setSchool("Cleaveland");
        studentDao.insertStudent(student);
        if(!"1".equals(isDistribute)){
            throw  new RuntimeException("测试分布式事务");
        }
    }
}
