package com.nuist.test.DAO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nuist.test.Entity.PlayerTable;
public interface PlayerDAO extends JpaRepository<PlayerTable, Integer> {
	PlayerTable findByUsernameAndPassword(String username,String password);
	List<PlayerTable> findAll();
}

