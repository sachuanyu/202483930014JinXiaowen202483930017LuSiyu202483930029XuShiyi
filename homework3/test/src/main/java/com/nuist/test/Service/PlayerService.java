package com.nuist.test.Service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuist.test.DAO.PlayerDAO;
import com.nuist.test.Entity.PlayerTable;
import com.nuist.test.Entity.WorldTable;

@Service
@Transactional
public class PlayerService {
	@Autowired
	private PlayerDAO playerDAO;

	public boolean loginService(String username, String password) {
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return false;
		}
		return playerDAO.findByUsernameAndPassword(username, password) != null;
	}

	public Set<WorldTable> allWorlds(Integer pid) {
		if (pid == null) {
			return null;
		}
		PlayerTable playerTable = playerDAO.findByPid(pid);
		return playerTable != null ? playerTable.getWorlds() : null;
	}

	public PlayerTable findByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			return null;
		}
		return playerDAO.findByUsername(username);
	}

	public boolean login(String username, String password) {
		return loginService(username, password);
	}
}
