package com.projet.demo.Services;

import com.projet.demo.DAO.UserDAO;
import com.projet.demo.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public void addUserForTest(User u){
       /* u=new User();
        u.setAge(14);
        u.setCin("4589658");
        u.setNom("Gastli");
        u.setPrenom("Oussama");*/

        userDAO.insertUser(u);
    }

    public Map<String, Object> getUserById(String id){
       return userDAO.getUserById(id);
    }
}
