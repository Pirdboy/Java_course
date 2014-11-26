package cqu.shy.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import cqu.shy.data.DataPacket;

public class ServerFrame extends JFrame{

	private boolean isstart=false;
	ServerSocket ss = null;
	List<Client> clients = new ArrayList<Client>();// 用于存客户端
	
	//界面
	private JTextArea text=null;
	public ServerFrame(){
		
		this.getContentPane().setLayout(new BorderLayout(4,4));
		//标题
		JLabel title = new JLabel("服务器");
		title.setForeground(Color.blue);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		//处于中间的可以滚动的文本显示区域
		text = new JTextArea("",10,18);
		text.setEditable(false); // 不可录入
		text.setLineWrap(true);   //允许自动换行
		text.setWrapStyleWord(true);  //允许断行不断字
		text.setFont(new Font("SimSun",Font.PLAIN,14)); //设置字体, JTextArea不支持改变字体颜色
		text.setTabSize(4);
		text.setBackground(new Color(208,240,213));  //设置对话框背景颜色
		JScrollPane scrollPane = new JScrollPane(text);   //让对话框超出后可以出现滚动条
		add(title, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		this.setSize(350, 280);
		setLocation(100, 100);
		this.setResizable(false);//不可改变窗体大小
		this.setVisible(true);
		this.setTitle("网络白板服务器..");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("窗口关闭");
				isstart=false;
				//这里应该还需要将List<Client>处理一下
				System.exit(0);
			}
			
		});
	}
	public void Lanuch(){
		try {
			ss = new ServerSocket(9003);
			isstart = true;
			text.append("服务器已经运行...\n");
		} catch (BindException e) { // Sever端已经运行，当重复运行时抛异常
			JOptionPane.showMessageDialog(this, "端口已经被占用，请不要重复打开服务器","错误",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (isstart) {
				Socket s = ss.accept();
				text.append("有客户端连接进来...\n");
				Client c = new Client(s); // 每建立一个客户端，就new一个客户端对象，启动一个线程
				c.start();
				clients.add(c); // 勿忘写，将每个客户端加入到容器里
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		ServerFrame sf = new ServerFrame();
		sf.Lanuch();
	}
	class Client extends Thread{
		private Socket s;
		private ObjectInputStream object_in;
		private ObjectOutputStream object_out;
		private boolean isConnected = false;

		public Client(Socket s) {
			this.s = s;
			try {
				object_in = new ObjectInputStream(s.getInputStream());
				object_out = new ObjectOutputStream(s.getOutputStream());
				isConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void send(Object data) { // 用于发送给客户端
			try {
				object_out.writeObject((DataPacket)data);
			} catch (IOException e) {
				clients.remove(this); // 移除那个退出的对象
				System.out.println("一个客户退出了");
			}
		}

		public void run() {
			try {
				while (isConnected) {
					try {
						Object data = (DataPacket)object_in.readObject();
						for (int i = 0; i < clients.size(); i++) {
							Client c = clients.get(i); // 取客户端
							c.send(data);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (EOFException e) { 
				System.out.println("Client closed!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (object_in != null)
						object_in.close();
					if (object_out != null)
						object_out.close();
					if (s != null) {
						s.close();
						s = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
