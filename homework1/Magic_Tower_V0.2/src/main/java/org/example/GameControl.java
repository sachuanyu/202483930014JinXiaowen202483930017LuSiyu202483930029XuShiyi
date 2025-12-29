package org.example;

import java.util.Scanner;

public class GameControl {
	GameData gameData;
	Menu menu;
	GUI gui;

	GameControl(GameData gameData, Menu menu, GUI gui) {
		this.gameData = gameData;
		this.menu = menu;
		this.gui = gui;
	}
	void gameStart() {
		Scanner keyboardInput = new Scanner(System.in);
		while (true) {
			String input = keyboardInput.next();
			if (input.length() != 1 || (input.charAt(0) != 'a' && input.charAt(0) != 's' && input.charAt(0) != 'd'
					&& input.charAt(0) != 'w' && input.charAt(0) != '0')) {
				System.out.println("Wrong Input.");
				continue;
			}
			if (input.charAt(0) == '0')
				menu.enterMenu();
			else
				handleInput(input.charAt(0));
			gameData.printMap();
			gui.refreshGUI();
		}
	}

	void handleInput(char inC) {
		int tX = 0, tY = 0;
		if (inC == 'a') {
			tX = gameData.pX;
			tY = gameData.pY - 1;
		}
		if (inC == 's') {
			tX = gameData.pX + 1;
			tY = gameData.pY;
		}
		if (inC == 'd') {
			tX = gameData.pX;
			tY = gameData.pY + 1;
		}
		if (inC == 'w') {
			tX = gameData.pX - 1;
			tY = gameData.pY;
		}
		// 检查边界
		if (tX < 0 || tX >= gameData.H || tY < 0 || tY >= gameData.W) {
			return; // 不能移出边界
		}

		// 检查目标位置是否为墙壁
		if (gameData.map[gameData.currentLevel][tX][tY] == 0) { // 0表示墙壁
			return; // 不能穿过墙壁
		}

		if (gameData.map[gameData.currentLevel][tX][tY] == 2) {
			gameData.keyNum++;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 3 && gameData.keyNum > 0) {
			gameData.keyNum--;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 4) {
			gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1; // 清除当前层原位置
			gameData.currentLevel++; // 切换楼层
			// 在下一层找到楼梯位置作为英雄新位置
			for (int i = 0; i < gameData.H; i++) {
				for (int j = 0; j < gameData.W; j++) {
					if (gameData.map[gameData.currentLevel][i][j] == 4) { // 找到下一层的楼梯
						gameData.pX = i;
						gameData.pY = j;
						// 在新位置设置英雄标识
						gameData.map[gameData.currentLevel][i][j] = 6;
						break;
					}
				}
			}
		}
		else if (gameData.map[gameData.currentLevel][tX][tY] == 5 && gameData.heroHealth > 0 ) {
			if (gameData.JewelleryNum < 2){
				System.out.println("You didn't find all the jewellery. Please continue to try!");
			}else {
				System.out.print("You Win!!");
				System.exit(0);
			}
		}else if (gameData.map[gameData.currentLevel][tX][tY] == 9) {
			boolean found = false;
			for (int i = 0; i < gameData.H && !found; i++) {
				for (int j = 0; j < gameData.W && !found; j++) {
					// 找到另一个传送门位置（排除当前位置）
					if (gameData.map[gameData.currentLevel][i][j] == 9 && (i != tX || j != tY)) {
						// 记录原位置是否为传送点
						boolean wasTeleporter = gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] == 9;

						// 清除原位置（如果是传送点则保持为9，否则设为1）
						if (!wasTeleporter) {
							gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
						}

						// 移动到目标传送点
						gameData.pX = i;
						gameData.pY = j;
						found = true;
					}
				}
			}
			if (!found) {
				moveHero(tX, tY); // 如果没找到其他传送门，正常移动
			}
		}
		else if (gameData.map[gameData.currentLevel][tX][tY] >= 10) {
			gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY];
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 1) {
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] < 0) {
			if (gameData.map[gameData.currentLevel][tX][tY] + gameData.heroHealth <= 0) {
				System.out.print("That monster has " + Integer.toString(-gameData.map[gameData.currentLevel][tX][tY])
						+ " power, You Lose!!");
				System.exit(0);
			} else {
				gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY];
				moveHero(tX, tY);
			}
		}else if ((gameData.map[gameData.currentLevel][tX][tY] == 8 ||
				gameData.map[gameData.currentLevel][tX][tY] == 7) && gameData.JewelleryNum< 2) {
			gameData.JewelleryNum++;
			moveHero(tX, tY);
			if (gameData.JewelleryNum < 2) {
				System.out.println("You found a Jewellery! Please continue to try!");
			} else {
				System.out.println("You found all the jewellery!! Try to find the end point!");
			}
		}

	}

	void moveHero(int tX, int tY) {
		// 检查原位置是否为传送点，如果是则保持为9
		if (gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] != 9) {
			gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1; // 清除原位置
		}

		// 检查目标位置是否为传送点或加血点
		if (gameData.map[gameData.currentLevel][tX][tY] != 9 && gameData.map[gameData.currentLevel][tX][tY] <= 10) {
			gameData.map[gameData.currentLevel][tX][tY] = 6; // 设置新位置为英雄
		}
		// 对于加血点（>10），保持原值不变，只更新坐标

		gameData.pX = tX;
		gameData.pY = tY;
	}

}
