package cqu.shy.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.net.ssl.HostnameVerifier;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientLoginUi extends JFrame {

	private JPanel contentPane;
	private JTextField userNametext;
	private JTextField serverIptext;
	private JTextField porttext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLoginUi frame = new ClientLoginUi();
					frame.setTitle("网络白板登录客户端");
					frame.setSize(345, 332);
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
	public ClientLoginUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 345, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel userNameLabel = new JLabel("用户名:");
		userNameLabel.setFont(new Font("SimSun",Font.PLAIN,14));
		
		JLabel serverIpLabel = new JLabel("服务器地址:");
		serverIpLabel.setFont(new Font("SimSun",Font.PLAIN,14));
		
		JLabel portLabel = new JLabel("端口:");
		portLabel.setFont(new Font("SimSun",Font.PLAIN,14));
		
		userNametext = new JTextField();
		userNametext.setText("");
		userNametext.setColumns(10);
		
		serverIptext = new JTextField();
		serverIptext.setText("127.0.0.1");
		serverIptext.setColumns(10);
		
		porttext = new JTextField();
		porttext.setColumns(10);
		porttext.setText("9003");
		porttext.setEditable(false);
		
		JLabel titleLabel = new JLabel("网络白板登录");
		titleLabel.setFont(new Font("SimSun",Font.BOLD,15));
		titleLabel.setForeground(Color.BLUE);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientFrame clientframe = new ClientFrame(userNametext.getText(), serverIptext.getText());
				clientframe.Launch();
				ClientLoginUi.this.setVisible(false);
				ClientLoginUi.this.dispose();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(116, Short.MAX_VALUE)
					.addComponent(titleLabel)
					.addGap(107))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(userNameLabel)
						.addComponent(serverIpLabel)
						.addComponent(portLabel))
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(porttext)
						.addComponent(serverIptext)
						.addComponent(userNametext, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
					.addContainerGap(84, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(119)
					.addComponent(btnNewButton)
					.addContainerGap(143, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(titleLabel)
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameLabel)
						.addComponent(userNametext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(serverIpLabel)
						.addComponent(serverIptext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(portLabel)
						.addComponent(porttext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addGap(27))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
