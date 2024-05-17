package com.example.controller;

import java.security.Provider.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.example.dto.DTOBoard;
import com.example.entity.EntityBoard;
import com.example.repository.BoardRepository;
import com.example.service.ServiceBoard;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ServiceUser _serviceUser;

	@Autowired
	ServiceBoard _serviceBoard;

	@GetMapping("/Home")
	public String loginHome(Model m, HttpSession session) {
		if (session.getAttribute("LoginOK") == "LoginOK") {
			m.addAttribute("tableList", _serviceUser.getAllUser());
			return "Board";
		} else {
			return "Login";
		}
	}

	@GetMapping("/makeDummy")
	public String makeDummy() {
		for (int i = 0; i < 10; i++) {
			DTOUser user = new DTOUser();
			user.User_Name = String.valueOf(i);
			user.User_Pass = String.valueOf(i);
			user.Id = i;
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
			@PathVariable String repass) {
		_serviceUser.RePass(id, pass, repass);
		return "Login";
	}

	@PostMapping("/login")
    public String userLogin(DTOUser user, Model m, HttpSession session) {
        if ("LoginOK".equals(session.getAttribute("LoginOK"))) {
            // 이미 로그인되어 있는 경우
            m.addAttribute("tableList", _serviceBoard.getAllBoard());
            return "Board";
        } else {
            boolean b = _serviceUser.login(user, session);
            if (b) {
                // 로그인 성공 시
                session.setAttribute("LoginOK", "LoginOK");
                session.setAttribute("currentUserName", user.User_Name);

                // 사용자 이름을 기반으로 ID를 가져와 세션에 저장
                EntityUser currentUser = _serviceUser.getUserByUsername(user.User_Name);
                if (currentUser != null) {
                    session.setAttribute("currentUserID", currentUser.getId());
					System.out.println("currentUserID: " + currentUser.getId());
                }

                m.addAttribute("tableList", _serviceBoard.getAllBoard());
                return "Board";
            } else {
                // 로그인 실패 시
                return "Login";
            }
        }
    }
	

	@GetMapping("/logout")
	public String userLogout(HttpSession session) {
		session.invalidate();
		// session.removeAttribute("LoginOK");
		return "Login";
	}

	@PostMapping("/update")
	public String update(DTOUser user, HttpSession session, @RequestParam("newPass") String newPass, Model m) {
		_serviceUser.Update(user, newPass);
		m.addAttribute("tableList", _serviceUser.getAllUser()); // 업데이트 후에도 tableList 다시 설정
		return "Mypage";
	}

	@GetMapping("/delete")
	public String delete(HttpSession session, @RequestParam("id") String id, @RequestParam("pass") String pass,
			Model m) {
		_serviceUser.Delete(id, pass);
		m.addAttribute("tableList", _serviceUser.getAllUser()); // 삭제 후에도 tableList 다시 설정
		return "Mypage";
	}

	@GetMapping ("/board")
	public String
	boardHome (Model m, HttpSession session) {
	m.addAttribute("tableList", _serviceBoard.getAllBoard ());
	return "Home";
	}



	@PostMapping("/write")
	public String write(DTOBoard board, Model m, HttpSession session) {
		String currentUserName = (String) session.getAttribute("currentUserName");
		Integer currentUserID = (Integer) session.getAttribute("currentUserID"); // 세션에서 UserID 가져오기
		System.out.println("currentUserID retrieved: " + currentUserID); // 디버깅 메시지
	
		if (currentUserName != null && currentUserID != null) {
			board.setBoard_Author(currentUserName);
			board.setBoard_Userid(currentUserID); // 현재 사용자의 ID를 게시물에 설정
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatDateTime = now.format(formatter);
			board.setBoard_Date(formatDateTime); // 현재 날짜와 시간 설정
			_serviceBoard.Write(board);
			m.addAttribute("tableList", _serviceBoard.getAllBoard());
			return "Board";
		} else {
			return "Login";
		}
	}
	
}
// @PostMapping("/dtoTest")
// @ResponseBody
// public String DTOTest(DTOUser user) {
// System.out.println(user.User_Name);
// System.out.println(user.User_Pass);
// userRepo.save(user.ToEntity());
// return user.toString();
// }
//
// @GetMapping("/dtoDeleteTest/{id}")
// @ResponseBody
// public String DTODeleteTest(@PathVariable int id) {
// userRepo.deleteById(id);
// return "";
// }
//
// @GetMapping("/hello")
// public String Hello(Model m) {
// m.addAttribute("value", "Hello");
// return "template";
// }
//
////
// @GetMapping("/getTest/{id}")
// @ResponseBody
// public String GetTest(@PathVariable int id) {
// return String.valueOf(id);
// }
////
// @PostMapping("/postTest")
// @ResponseBody
//// @RequestBody : 서버에 요청
// public String PostTest(@RequestBody String param) {
// return param;
// }
//
// @GetMapping("/dtoGetTest/{name}")
// @ResponseBody
// public String DTOGetTest(@PathVariable String name, String pass) {
// List<EntityUser> list = userRepo.findDataQuery(name, pass);
// if(list.size() == 0)
// return "no user";
// else
// return list.get(0).getUser_Name();
//
// }

//
//
// }
//
//
// @GetMapping("/example/{id}")
// public ResponseEntity<String> getExampleById(@PathVariable Long id){
// if(id == 1) {
// return ResponseEntity.ok("Example with ID 1 found");
// }else {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Example not found");
// }
// }
