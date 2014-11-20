package cqu.shy.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class GameFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setTitle("雷电X");
					frame.setSize(500, 680);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Dimension dem=Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((int)(dem.getWidth()-400)/2,(int)(dem.getHeight()-600)/2);
					frame.setResizable(false);//不可改变窗体大小
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		GameCanvas gameCanvas = new GameCanvas();
		contentPane.add(gameCanvas, BorderLayout.CENTER);
		gameCanvas.setFocusable(true);
		gameCanvas.requestFocusInWindow();
		gameCanvas.Welcome_game();
	}

}
