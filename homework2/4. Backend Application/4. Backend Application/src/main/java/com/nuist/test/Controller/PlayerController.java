package com.nuist.test.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuist.test.DAO.PlayerDAO;
import com.nuist.test.Entity.PlayerTable;

@RestController
public class PlayerController {
	@Autowired
	private PlayerDAO playerDAO;
	@GetMapping("userAll")
	public List<PlayerTable> findAll() {
        System.out.println("查询所有数据:");
        return playerDAO.findAll();
    }
	@GetMapping("validate")
	public PlayerTable login(String username, String password) {
		return playerDAO.findByUsernameAndPassword(username,password);
	}
}
