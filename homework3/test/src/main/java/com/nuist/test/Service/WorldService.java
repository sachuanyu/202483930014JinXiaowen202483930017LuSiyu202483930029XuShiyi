package com.nuist.test.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 新增：事务注解

import com.nuist.test.DAO.WorldDAO;
import com.nuist.test.Entity.PlayerTable;
import com.nuist.test.Entity.WorldTable;

@Service
@Transactional // 新增：事务管理（保证数据库操作一致性）
public class WorldService {
	@Autowired
	private WorldDAO worldDAO;

	// 查询某个世界下所有玩家（优化空指针，增强稳定性）
	public Set<PlayerTable> allPlayers(Integer wid) {
		// 新增：wid空值校验（避免传入null导致查询报错）
		if (wid == null) {
			return new HashSet<>(); // 返回空集合，比null更友好，避免前端处理报错
		}
		WorldTable worldTable = worldDAO.findByWid(wid);
		// 新增：世界不存在时返回空集合，避免空指针异常
		return worldTable != null ? worldTable.getPlayers() : new HashSet<>();
	}

	// 新增：根据世界ID查询世界详情（备用扩展，如游戏中加载世界配置）
	public WorldTable findWorldById(Integer wid) {
		if (wid == null) {
			return null;
		}
		// 假设DAO层有findById方法（若没有，后续可在WorldDAO中补充）
		return worldDAO.findByWid(wid);
	}

	// 新增：新增世界（备用扩展，如后台添加新关卡）
	public void addWorld(WorldTable worldTable) {
		if (worldTable != null && worldTable.getWorldname() != null && !worldTable.getWorldname().trim().isEmpty()) {
			worldDAO.save(worldTable);
		}
	}
}