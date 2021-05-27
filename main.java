package BrickBreaker;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {

		Gameplay gameplay = new Gameplay();
		
		JFrame obj = new JFrame();
		
		obj.setBounds(10,10,709,600);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		obj.add(gameplay);
		
		
	}

}
