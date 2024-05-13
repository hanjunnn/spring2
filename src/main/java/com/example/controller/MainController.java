package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.DTOUser;
import com.example.entity.EntityUser;
import com.example.repository.UserRepository;
import com.example.service.ServiceUser;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class MainController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ServiceUser _serviceUser;

	@GetMapping("/Home")
	public String loginHome(Model m, HttpSession session) {
		if(session.getAttribute("LoginOK") == "LoginOK")
		{
			m.addAttribute("tableList", _serviceUser.getAllUser());
			return "Mypage";
		}
		else{
		return "Login";
		}
	}

	@GetMapping("/makeDummy")
	public String makeDummy() {
		for(int i = 0; i < 10; i++) {
			DTOUser user = new DTOUser();
			user.User_Name = String.valueOf(i);
			user.User_Pass = String.valueOf(i);
			_serviceUser.Join(user);
		}
		return "Login";
	}
	@PostMapping("/register")
    public String userregister(DTOUser user, HttpSession session) {

        _serviceUser.Join(user);
        return "Login";
    }
	@GetMapping("PassChange/{id}/{pass}/{repass}")
	public String userPassChange(
		@PathVariable String id,
		@PathVariable String pass,
		@PathVariable String repass)
		{
			_serviceUser.RePass(id, pass, repass);			
			return "Login";	
		}
		@PostMapping("/login")
		public String userLogin(DTOUser user, Model m, HttpSession session) {
	
			if (session.getAttribute("LoginOK") == "LoginOK") {
				m.addAttribute("tableList", _serviceUser.getAllUser());
				return "Mypage";
			}else {
				boolean b = _serviceUser.login(user);
			
				if (b) {
					session.setAttribute("LoginOK", "LoginOK");
					m.addAttribute("tableList", _serviceUser.getAllUser());
					return "Mypage";
				} else
					return "Login";
			}
		}
	@GetMapping("/logout")
	public String userLogout(HttpSession session) {
		session.invalidate();
		//session.removeAttribute("LoginOK");
		return "Login";
	}
	@PostMapping("/update")
	public String update(DTOUser user, HttpSession session, @RequestParam("newPass") String newPass, Model m) {
		_serviceUser.Update(user, newPass);
		m.addAttribute("tableList", _serviceUser.getAllUser()); // 업데이트 후에도 tableList 다시 설정
		return "Mypage";
	}
	@GetMapping("/delete")
	public String delete(HttpSession session, @RequestParam("id") String id, @RequestParam("pass") String pass, Model m) {
		_serviceUser.Delete(id, pass);
		m.addAttribute("tableList", _serviceUser.getAllUser()); // 삭제 후에도 tableList 다시 설정
		return "Mypage";
	}
		
	}

	

	
//	@PostMapping("/dtoTest")
//	@ResponseBody
//	public String DTOTest(DTOUser user) {
//		System.out.println(user.User_Name);
//		System.out.println(user.User_Pass);
//		userRepo.save(user.ToEntity());
//		return user.toString();
//	}
//	
//	@GetMapping("/dtoDeleteTest/{id}")
//	@ResponseBody
//	public String DTODeleteTest(@PathVariable int id) {
//		userRepo.deleteById(id);
//		return "";
//	}
//	
//	@GetMapping("/hello")
//	public String Hello(Model m) {
//		m.addAttribute("value", "Hello");
//		return "template";
//	}
//	
////	
//	@GetMapping("/getTest/{id}")
//	@ResponseBody
//	public String GetTest(@PathVariable int id) {
//		return String.valueOf(id);
//	}
////	
//	@PostMapping("/postTest")
//	@ResponseBody
////	@RequestBody : 서버에 요청
//	public String PostTest(@RequestBody String param) {
//		return param;
//	}
//	
//	@GetMapping("/dtoGetTest/{name}")
//	@ResponseBody
//	public String DTOGetTest(@PathVariable String name, String pass) {
//		List<EntityUser> list = userRepo.findDataQuery(name, pass);
//		if(list.size() == 0)
//			return "no user";
//		else
//			return list.get(0).getUser_Name();
//		
//	}
	

	

//	    
//		
//	}
//	
//	    
//	@GetMapping("/example/{id}")
//	public ResponseEntity<String> getExampleById(@PathVariable Long id){
//		if(id == 1) {
//			return ResponseEntity.ok("Example with ID 1 found");
//		}else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Example not found");
//		}
//	}
	
	
	

