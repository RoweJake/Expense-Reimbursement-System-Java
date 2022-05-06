package service;

import dao.UserDao;
import models.User;

public class UserService {

	UserDao dao;
	
	public UserService(UserDao dao) {
		this.dao = dao;
	}
	
	public User findUser(String user, String pass) {
		return dao.retrieveUser(user, pass);
	}
	
	

}
