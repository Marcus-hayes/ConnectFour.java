

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectFourGame {
	
public static void main(String[] args) {
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Connect Four");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setResizable(false);
		
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);
		
		ConnectFourWidget game = new ConnectFourWidget();
		top_panel.add(game, BorderLayout.CENTER);
		top_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		main_frame.pack();
		main_frame.setVisible(true);
		
	}

}
