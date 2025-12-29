package org.example;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.lang.reflect.Field;

public class GameData implements Serializable {
	int L, H, W, currentLevel;
	int pX, pY, heroHealth, keyNum, JewelleryNum;
	int[][][] map;

	void readMapFromFile(String filePath) {
		currentLevel = 0;
		heroHealth = 105;
		keyNum = 0;
		JewelleryNum = 0;


		try {
			Scanner in = new Scanner(new File(filePath));
			// 读取维度
			if (!in.hasNextInt()) throw new RuntimeException("文件缺少 L 值");
			L = in.nextInt();
			if (!in.hasNextInt()) throw new RuntimeException("文件缺少 H 值");
			H = in.nextInt();
			if (!in.hasNextInt()) throw new RuntimeException("文件缺少 W 值");
			W = in.nextInt();
			map = new int[L][H][W];
			// 读取地图数据
			boolean heroFound = false;
			for (int i = 0; i < L; i++) {
				for (int j = 0; j < H; j++) {
					for (int k = 0; k < W; k++) {
						if (!in.hasNextInt()) {
							throw new RuntimeException(
									String.format("地图数据不完整，期望 %d x %d x %d 个整数", L, H, W));
						}
						map[i][j][k] = in.nextInt();
						// 找到英雄初始位置（值为6的位置）
						if (i == 0 && map[i][j][k] == 6 && !heroFound) {
							pX = j;
							pY = k;
							heroFound = true;
						}
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Error with files:" + e.toString());
		} catch (RuntimeException e) {
			System.out.println("数据格式错误: " + e.getMessage());
		}
	}


	void printMap() {
		char C[] = { 'W', ' ', 'K', 'D', 'S', 'E', 'H' ,'1','2','T','+'};
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (map[currentLevel][j][k] < 0)
					System.out.print("M ");
				else if (map[currentLevel][j][k] >= 10)
					System.out.print("P ");
				else if(j==pX && k==pY){
					System.out.print("H ");
				}else{
					System.out.print(C[map[currentLevel][j][k]] + " ");
				}
			}
			System.out.print("\n");
		}
		System.out.print(
				"Health:" + Integer.toString(heroHealth) + "  KeyNum:" + Integer.toString(keyNum) + "  JewelleryNum:" + Integer.toString(JewelleryNum) + "  Menu:press 0\n");
	}


	void copyFields(Object source) {
		try {
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(source);
				field.set(this, value);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
