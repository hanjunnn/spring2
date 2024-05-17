package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.dto.DTOUser;
import com.example.entity.EntityUser;
import com.example.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiceUser {
		
	@Autowired
	UserRepository userRepo;
	public void Join(DTOUser user) {
			System.out.println(user.User_Name);
			System.out.println(user.User_Pass);
			System.out.println(user.Id);
			userRepo.save(user.ToEntity());
	}
	
	public boolean login(DTOUser user, HttpSession session) {
		System.out.println(user.User_Name);
		System.out.println(user.User_Pass);
		System.out.println(user.Id);
		List<EntityUser> list = userRepo.findByUsernameAndUserpass(user.User_Name, user.User_Pass);
		
		if(list.size() == 0) {
			return false;
	    } else {
			session.setAttribute("LoginOK",list.get(0));
	        return true;
	    }
	}
	public EntityUser getUserById(int userId) {
        EntityUser user = userRepo.findById(userId);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
	public Iterable<EntityUser> getAllUser() {
		
		return userRepo.findAll();
	}
	
	public void Update(DTOUser user, String newPass) {
		System.out.println(user.User_Name);
		String oldPass = user.User_Pass;
		System.out.println(oldPass);
		System.out.println(newPass);
		
		userRepo.updateUserPass(user.User_Name, oldPass, newPass);
	}
	public void RePass(
			String id,
			String pass,
			String repass)
			 {
				List<EntityUser> list = userRepo.findByUsernameAndUserpass(id, pass);
				if(list.size() > 0) {
					list.get(0).setUser_Pass(repass);
					userRepo.save(list.get(0));
				}
			}
			public EntityUser getUserByUsername(String userName) {
				List<EntityUser> list = userRepo.findUserData(userName);
				if (!list.isEmpty()) {
					return list.get(0);
				}
				return null;
			}
	public void Delete(String id, String pass) {
		List<EntityUser> list = userRepo.findByUsernameAndUserpass(id, pass);
		if(list.size() > 0) {
			userRepo.delete(list.get(0));
		}
	}
			
}