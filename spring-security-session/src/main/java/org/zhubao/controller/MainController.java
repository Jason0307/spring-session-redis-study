package org.zhubao.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zhubao.entity.User;

@RestController
@Slf4j
public class MainController {
	
	@Autowired
    private FindByIndexNameSessionRepository<? extends ExpiringSession> sessionRepository;

	@GetMapping({"/index", "/"})
	public User index(@RequestParam(name = "name", defaultValue = "Jason") String name, HttpServletRequest request, HttpSession session) {
		User user = new User();
		user.setId(10001);
		user.setAge(26);
		user.setEmail("zrb0307@gmail.com");
		Object existName = session.getAttribute("name");
		if(null == existName) {
			log.info("Name is not exist in session, put name = {} into current session.", name);
			session.setAttribute("name", name);
			user.setUsername(name);
		} else {
			log.info("Name is already exist in session, get name = {} from current session.", name);
			user.setUsername(existName.toString());
		}
		
		Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
		Arrays.asList(cookies).forEach(cookie -> {
			log.info("Cookie name = {}, value = {}", cookie.getName(), cookie.getValue());
		});
		
		return user;
	}
	
	@GetMapping("/sessions")
	public Map<String, Object> getSessions(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", session.getId());
		map.put("value", session.getAttribute("name"));
		return map;
	}
	
	
    @GetMapping("/user/query")
    @ResponseBody
    public Map<String, ? extends ExpiringSession> findByUsername(@RequestParam String name) {
        Map<String, ? extends ExpiringSession> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, name);
        return usersSessions;
    }
}