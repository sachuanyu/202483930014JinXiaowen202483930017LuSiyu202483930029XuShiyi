package org.example;

import java.awt.Image;
import javax.swing.*;
public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;
	GUI(GameData gameData) {
		this.gameData = gameData;
		f = new JFrame("Magic Tower");
		b = new JLabel[gameData.H][gameData.W];
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j]=new JLabel();
				b[i][j].setBounds(j*50, i*50, 50, 50);
				f.add(b[i][j]);
			}
		}
		f.setSize(gameData.H*50+5, gameData.W*50+20);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		refreshGUI();
	}
	public void refreshGUI()
	{
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j]);
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}
	}
	private static Image chooseImage(int index){
		ImageIcon[] icons = new ImageIcon[12];
		Image scaledImage;
		icons[0]= new ImageIcon("img/Wall.jpg");
		icons[1]= new ImageIcon("img/Floor.jpg");
		icons[2]= new ImageIcon("img/Key.jpg");
		icons[3]= new ImageIcon("img/Door.jpg");
		icons[4]= new ImageIcon("img/Stair.jpg");
		icons[5]= new ImageIcon("img/Exit.jpg");
		icons[6]= new ImageIcon("img/Hero.jpg");
		icons[7]= new ImageIcon("img/珠宝.jpg");
		icons[8]= new ImageIcon("img/珠宝.jpg");
		icons[9]= new ImageIcon("img/传送门.jpg");
		icons[10]= new ImageIcon("img/Potion.jpg");
		icons[11]= new ImageIcon("img/Monster.jpg");
		if(index>10)
			scaledImage = icons[10].getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		else if(index<0)
			scaledImage = icons[11].getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		else
			scaledImage = icons[index].getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		return scaledImage;
	}
	
}
