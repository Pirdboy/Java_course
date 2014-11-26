package cqu.shy.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import cqu.shy.data.DataPacket;
import cqu.shy.data.Graph;
import cqu.shy.data.ImagePacket;
import cqu.shy.paintmode.PaintMode;



public class ClientFrame extends JFrame {
	private static final long serialVersionUID = -2397593626990759111L;

	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private boolean isConnected = false;
	private Thread dataThread = null;
	private String userName;
	private String hostAddress;

	public void Launch() {
		try {
			socket = new Socket(hostAddress, 9003);
			System.out.println("服务器地址:" + hostAddress);
			System.out.println("客户端已连接");
			isConnected = true;
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("socket的输入输出流阻塞结束");
			dataThread.start();
		} catch (UnknownHostException e) {
			// e.printStackTrace();
			// 服务器地址不对
			JOptionPane.showMessageDialog(this, "服务器地址未知", "错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ConnectException c) {
			// 服务器拒绝访问
			JOptionPane.showMessageDialog(this, "服务器访问失败,请检查服务器是否开启", "错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GUI部分,1:聊天面板
	private JLabel title;
	private JTextArea text = null; // 显示对话内容,
	private JButton SendBtn = null, ClearBtn = null;
	private JTextArea inputText = null; // 文字输入框
	// GUI部分,2:画板部分
	private JLabel title2;
	private JButton LineBtn;
	private JButton RectangleBtn;
	private JButton EllipseBtn;
	private JButton PencilBtn;
	private JButton RubberBtn;
	private JButton PaperColorBtn;
	private JComboBox<Integer> LineWidthBox;
	private JButton ColorBtn;
	private JButton ClearPaperBtn;
	private JButton SendImageBtn;
	private JCheckBox RealTimeBtn;
	private PaintPanel paintPanel;
	private JLabel statusBar;
	private String Tooltip;
	private String Colortip;
	private String BgColortip;

	public ClientFrame(String username, String host) {
		this.userName = username;
		this.hostAddress = host;
		dataThread = new Thread() {
			// 接受数据的线程
			public void run() {
				while (isConnected) {
					try {
						
						DataPacket data = (DataPacket) in.readObject();
						if (data.getPacketType().equals("Image")) {
							// 如果接受到的是图像
							byte[] ob = (byte[]) data.getData();
							ByteArrayInputStream bin = new ByteArrayInputStream(
									ob);
							BufferedImage image = ImageIO.read(bin);
							paintPanel.setImage(image);
							bin.close();
						} else if (data.getPacketType().equals("Text")) {
							// 如果接受到的是文字
							text.append((String)data.getData());
						} else if(data.getPacketType().equals("Graph")){
							String temp_name = ((Graph)data.getData()).userName;
							if(RealTimeBtn.isSelected() && !temp_name.equals(userName)){
								System.out.println(userName+",实时按钮被选上,获取的data的用户名是"+temp_name);
								paintPanel.addGraph((Graph)data.getData());
							}
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//System.out.println("客户端被关闭");
					}
				}
			}
		};

		this.getContentPane().setLayout(new GridLayout(1, 2, 8, 20));
		// 聊天部分GUI初始化
		// 标题Label
		title = new JLabel("聊天");
		title.setForeground(Color.MAGENTA);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		// 处于中间的可以滚动的文本显示区域
		text = new JTextArea("", 10, 18);
		text.setEditable(false); // 不可录入
		text.setLineWrap(true); // 允许自动换行
		text.setWrapStyleWord(true); // 允许断行不断字
		text.setFont(new Font("SimSun", Font.BOLD, 14)); // 设置字体,
															// JTextArea不支持改变字体颜色
		text.setTabSize(4);
		text.setBackground(new Color(208, 240, 213)); // 设置对话框背景颜色
		JScrollPane scrollPane = new JScrollPane(text); // 让对话框超出后可以出现滚动条
		// Box,包含按钮和文本输入框
		inputText = new JTextArea("", 5, 40);
		inputText.setEditable(true); // 可录入
		inputText.setLineWrap(true); // 允许自动换行
		inputText.setWrapStyleWord(true); // 允许断行不断字
		inputText.setFont(new Font("SimSun", Font.PLAIN, 15)); // 设置字体,
																// JTextArea不支持改变字体颜色
		inputText.setTabSize(4);
		inputText.setBackground(Color.WHITE);
		JScrollPane scrollPane2 = new JScrollPane(inputText);
		SendBtn = new JButton("发送");
		SendBtn.setFont(new Font("SimSun", Font.PLAIN, 13));
		ClearBtn = new JButton("清屏");
		ClearBtn.setFont(new Font("SimSun", Font.PLAIN, 13));
		SendBtn.addActionListener(new ActionListener() {
			// 发送信息
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = inputText.getText();
				String str = new String(userName + ":\n" + message + "\n"+"\n");
				inputText.setText("");
				// 建立一个数据包,发送出去
				DataPacket mydata = new DataPacket();
				mydata.setPacketType("Text");
				mydata.setData(str);
				try {
					out.writeObject(mydata);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inputText.requestFocusInWindow();
			}
		});
		ClearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				text.setText("");
			}
		});
		SendBtn.setFocusPainted(false);
		ClearBtn.setFocusPainted(false);
		Box box = Box.createHorizontalBox();
		Box btnBox = Box.createVerticalBox(); // 按钮Box,垂直结构
		btnBox.add(SendBtn);
		btnBox.add(Box.createVerticalStrut(5));
		btnBox.add(ClearBtn);
		btnBox.add(Box.createGlue());
		box.add(scrollPane2);
		box.add(Box.createHorizontalStrut(6)); // 输入框与按钮的间距
		box.add(btnBox);
		box.setBorder(BorderFactory.createEmptyBorder(8, 1, 2, 8)); // 边距
		inputText.requestFocusInWindow();
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout(4, 4));
		chatPanel.add(title, BorderLayout.NORTH);
		chatPanel.add(scrollPane, BorderLayout.CENTER);
		chatPanel.add(box, BorderLayout.SOUTH);
		this.add(chatPanel);

		// 画板部分GUI初始化
		Tooltip = new String("当前工具:直线");
		Colortip = new String("画笔颜色:RGB(0,0,0)");
		BgColortip = new String("画布颜色:RGB(255,255,255)");

		// 顶部标题和按钮
		Box box1 = Box.createVerticalBox();
		title2 = new JLabel("绘图");
		title2.setForeground(Color.MAGENTA);
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		title2.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createVerticalBox();
		Box box4 = Box.createHorizontalBox();
		LineBtn = new JButton();
		LineBtn.setIcon(new ImageIcon("src/Icon/LineIcon.png"));
		LineBtn.setPreferredSize(new Dimension(40, 29));
		RectangleBtn = new JButton();
		RectangleBtn.setIcon(new ImageIcon("src/Icon/RectangleIcon.png"));
		RectangleBtn.setPreferredSize(new Dimension(40, 29));
		EllipseBtn = new JButton();
		EllipseBtn.setIcon(new ImageIcon("src/Icon/EllipseIcon.png"));
		EllipseBtn.setPreferredSize(new Dimension(40, 29));
		PencilBtn = new JButton();
		PencilBtn.setIcon(new ImageIcon("src/Icon/PancilIcon.png"));
		PencilBtn.setPreferredSize(new Dimension(40, 29));
		RubberBtn = new JButton();
		RubberBtn.setIcon(new ImageIcon("src/Icon/RubberIcon.png"));
		RubberBtn.setPreferredSize(new Dimension(40, 29));
		PaperColorBtn = new JButton("画布颜色");
		PaperColorBtn.setFont(new Font("SimSun", Font.PLAIN, 12));
		box4.add(LineBtn);
		box4.add(Box.createHorizontalStrut(10));
		box4.add(RectangleBtn);
		box4.add(Box.createHorizontalStrut(10));
		box4.add(EllipseBtn);
		box4.add(Box.createHorizontalStrut(10));
		box4.add(PencilBtn);
		box4.add(Box.createHorizontalStrut(10));
		box4.add(RubberBtn);
		box4.add(Box.createHorizontalStrut(10));
		box4.add(PaperColorBtn);
		box4.add(Box.createHorizontalGlue());
		box4.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // 3个像素的边距

		Box box5 = Box.createHorizontalBox();
		ColorBtn = new JButton();
		ColorBtn.setIcon(new ImageIcon("src/Icon/ColorIcon.png"));
		ColorBtn.setPreferredSize(new Dimension(40, 29));
		JLabel linewidth_label = new JLabel("画笔宽度:");
		linewidth_label.setFont(new Font("SimSun", Font.PLAIN, 13));
		Integer[] items = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		LineWidthBox = new JComboBox<Integer>(items);
		LineWidthBox.setPreferredSize(new Dimension(40, 29));
		ClearPaperBtn = new JButton("清除图像");
		ClearPaperBtn.setFont(new Font("SimSun", Font.PLAIN, 12));
		box5.add(ColorBtn);
		box5.add(Box.createHorizontalStrut(10));
		box5.add(linewidth_label);
		box5.add(Box.createHorizontalStrut(4));
		box5.add(LineWidthBox);
		box5.add(Box.createHorizontalStrut(10));
		box5.add(ClearPaperBtn);
		box5.add(Box.createGlue());
		box5.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // 3个像素的边距

		box3.add(box4);
		box3.add(box5);

		Box box6 = Box.createVerticalBox();
		Box box7 = Box.createHorizontalBox();
		SendImageBtn = new JButton("发送");
		SendImageBtn.setFont(new Font("SimSun", Font.PLAIN, 12));
		RealTimeBtn = new JCheckBox("允许实时更新");
		RealTimeBtn.setSelected(false);
		RealTimeBtn.setFont(new Font("SimSun", Font.PLAIN, 13));
		box7.add(SendImageBtn);
		box7.add(Box.createHorizontalStrut(4));
		box7.add(Box.createGlue());
		Box box8 = Box.createHorizontalBox();
		box8.add(RealTimeBtn);
		box8.add(Box.createGlue());
		box6.add(box7);
		box6.add(box8);

		box2.add(box3);
		box2.add(box6);
		box1.add(title2);
		box1.add(box2);
		box1.add(Box.createVerticalStrut(2));

		// 中间的绘图面板
		paintPanel = new PaintPanel();
		JScrollPane temp_scroll = new JScrollPane(paintPanel);
		// 底部的状态栏
		statusBar = new JLabel();
		statusBar.setFont(new Font("SimSun", Font.PLAIN, 14));
		statusBar.setAlignmentX(CENTER_ALIGNMENT);
		statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
		// 最后将以上元素添加进去
		JPanel paintCanvas = new JPanel();
		paintCanvas.setLayout(new BorderLayout(4, 4));
		paintCanvas.add(box1, BorderLayout.NORTH);
		paintCanvas.add(temp_scroll, BorderLayout.CENTER);
		paintCanvas.add(statusBar, BorderLayout.SOUTH);
		this.add(paintCanvas);

		
		// 按钮事件
		LineBtn.addActionListener(new ActionListener() {
			// 直线
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Tooltip = "当前工具:直线";
				statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				paintPanel.setPaintmode(PaintMode.LINE);
			}
		});
		RectangleBtn.addActionListener(new ActionListener() {
			// 矩形
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Tooltip = "当前工具:矩形";
				statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				paintPanel.setPaintmode(PaintMode.RECTANGLE);
			}
		});
		EllipseBtn.addActionListener(new ActionListener() {
			// 椭圆
			@Override
			public void actionPerformed(ActionEvent e) {
				Tooltip = "当前工具:椭圆";
				statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				paintPanel.setPaintmode(PaintMode.ELLIPSE);
			}
		});
		PencilBtn.addActionListener(new ActionListener() {
			// 铅笔
			@Override
			public void actionPerformed(ActionEvent e) {
				Tooltip = "当前工具:铅笔";
				statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				paintPanel.setPaintmode(PaintMode.PENCIL);
			}
		});
		RubberBtn.addActionListener(new ActionListener() {
			// 橡皮擦
			@Override
			public void actionPerformed(ActionEvent e) {
				Tooltip = "当前工具:橡皮擦";
				statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				paintPanel.setPaintmode(PaintMode.RUBBER);
			}
		});
		PaperColorBtn.addActionListener(new ActionListener() {
			// 选择画布颜色
			@Override
			public void actionPerformed(ActionEvent e) {
				// 提示该操作会覆盖当前图像
				int choose = JOptionPane.showConfirmDialog(ClientFrame.this,
						"该操作将会覆盖当前图像,确定要进行吗?", "提示", JOptionPane.YES_NO_OPTION);
				if (choose == 1)
					return;
				Color color = JColorChooser.showDialog(ClientFrame.this,
						"画布颜色选择", paintPanel.getColor());
				if (color != null) {
					paintPanel.setPaperColor(color);
					BgColortip = "画笔颜色:RGB(" + color.getRed() + "," +color.getGreen()+","+color.getBlue()+")";
					statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				}
			}
		});
		ColorBtn.addActionListener(new ActionListener() {
			// 改变线条颜色
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(ClientFrame.this,
						"画笔颜色选择", paintPanel.getColor());
				if (color != null) {
					paintPanel.setColor(color);
					Colortip = "画笔颜色:RGB(" + color.getRed() + ","
							+ color.getGreen() + "," + color.getBlue() + ")";
					statusBar.setText("状态栏: " + Tooltip + "  " + Colortip + "  "+BgColortip);
				}
			}
		});
		ClearPaperBtn.addActionListener(new ActionListener() {
			// 清除图像
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.ClearPaper();
			}
		});
		SendImageBtn.addActionListener(new ActionListener() {
			// 发送
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = paintPanel.getImage();
				ImagePacket imgPack = new ImagePacket(image);
				ByteArrayOutputStream byte_out = imgPack.getByteOutputStream();
				byte[] datas = byte_out.toByteArray();

				try {
					DataPacket data = new DataPacket();
					data.setPacketType("Image");
					data.setData(datas);
					
					out.writeObject(data);
					out.flush();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		this.setSize(1080, 600);
		this.setTitle("网络白板客户端..." + username);
		this.setLocation(100, 100);
		this.setResizable(false);// 不可改变窗体大小
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("客户端窗口关闭");
				isConnected = false;
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 需要处理关闭事件
				System.exit(0);
			}

		});
	}
	public void sendGraph(Graph graph){
		DataPacket mydata = new DataPacket();
		mydata.setPacketType("Graph");
		mydata.setData(graph);
		try {
			out.writeObject(mydata);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// 内部类, 绘图面板
	private class PaintPanel extends JPanel implements MouseListener,
			MouseMotionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private PaintMode paintmode = PaintMode.LINE;
		private final int WIDTH = 530, HEIGHT = 440;
		private int startX = 0, startY = 0, endX = 0, endY = 0;
		// 画图过程判断
		private boolean isBegin = false;
		private boolean isPainting = false;
		private boolean isEnd = false;
		// 绘图属性
		private Color color; // 画笔颜色
		private Color paperColor; // 画布颜色,橡皮擦颜色

		// 缓存的图像
		private BufferedImage MyImage;
		private Graphics2D MyImage_g;
		private boolean isGetFromOther=false;

		public PaintPanel() {
			super();
			repaint();
			addMouseListener(this);
			addMouseMotionListener(this);
			setBackground(Color.WHITE);
			color = new Color(0, 0, 0);
			paperColor = new Color(255, 255, 255);
			MyImage = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			MyImage_g = MyImage.createGraphics();
			MyImage_g.setColor(paperColor);
			MyImage_g.fillRect(0, 0, WIDTH, HEIGHT);
			MyImage_g.setColor(color);
		}

		public PaintMode getPaintmode() {
			return paintmode;
		}

		public void setPaintmode(PaintMode paintmode) {
			this.paintmode = paintmode;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public Color getPaperColor() {
			return paperColor;
		}

		public void setPaperColor(Color paperColor) {
			this.paperColor = paperColor;
			MyImage_g.clearRect(0, 0, WIDTH, HEIGHT);
			MyImage_g.setColor(paperColor);
			MyImage_g.fillRect(0, 0, WIDTH, HEIGHT);
			isBegin = false;
			isPainting = false;
			isEnd = false;
			startX = startY = endX = endY = -1;
			repaint();
		}

		public void ClearPaper() {
			MyImage_g.clearRect(0, 0, WIDTH, HEIGHT);
			MyImage_g.setColor(paperColor);
			MyImage_g.fillRect(0, 0, WIDTH, HEIGHT);
			System.out.println("清除图像");
			startX = startY = endX = endY = -1;
			repaint();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			// 先画出已有的图像
			g.drawImage(MyImage, 0, 0, null);
			// 设置画笔属性
			Graphics2D g_2d = (Graphics2D) g;
			BasicStroke stroke = new BasicStroke(
					(float) (LineWidthBox.getSelectedIndex() + 1.0));
			g_2d.setColor(color);
			g_2d.setStroke(stroke);
			MyImage_g.setColor(color);
			MyImage_g.setStroke(stroke);
			// 针对矩形和椭圆会用到
			int realsx, realsy, realex, realey;
			realsx = startX < endX ? startX : endX;
			realex = startX > endX ? startX : endX;
			realsy = startY < endY ? startY : endY;
			realey = startY > endY ? startY : endY;
			if( (isBegin||isPainting) && !isEnd){
				// 根据所选工具进行绘图
				switch (paintmode) {
				case LINE:
					g_2d.drawLine(startX, startY, endX, endY);
					break;
				case RECTANGLE:
					g_2d.drawRect(realsx, realsy, realex - realsx, realey - realsy);
					break;
				case ELLIPSE:
					g_2d.drawOval(realsx, realsy, realex - realsx, realey - realsy);
					break;
				// 铅笔跟橡皮擦都需要边画边存
				case PENCIL:
					g_2d.drawLine(startX, startY, endX, endY);
					MyImage_g.drawLine(startX, startY, endX, endY);
					if(RealTimeBtn.isSelected() && !isGetFromOther){
						//如果允许实时同步
						Graph temp = new Graph(userName,startX,startY,endX,endY,PaintMode.PENCIL,stroke.getLineWidth(),color);
						sendGraph(temp);
					}
					break;
				case RUBBER:
					MyImage_g.setColor(paperColor);
					g_2d.setColor(paperColor);
					g_2d.drawLine(startX, startY, endX, endY);
					MyImage_g.drawLine(startX, startY, endX, endY);
					if(RealTimeBtn.isSelected() && !isGetFromOther){
						//如果允许实时同步
						Graph temp = new Graph(userName,startX,startY,endX,endY,PaintMode.RUBBER,stroke.getLineWidth(),color);
						sendGraph(temp);
					}
					break;
				default:
					break;
				}
			}
			else if (isEnd) {
				switch (paintmode) {
				case LINE:
					g_2d.drawLine(startX, startY, endX, endY);
					MyImage_g.drawLine(startX, startY, endX, endY);
					if(RealTimeBtn.isSelected()&& !isGetFromOther){
						//如果允许实时同步
						Graph temp = new Graph(userName,startX,startY,endX,endY,PaintMode.LINE,stroke.getLineWidth(),color);
						sendGraph(temp);
					}
					break;
				case RECTANGLE:
					g_2d.drawRect(realsx, realsy, realex - realsx, realey - realsy);
					MyImage_g.drawRect(realsx, realsy, realex - realsx, realey
							- realsy);
					if(RealTimeBtn.isSelected()&& !isGetFromOther){
						//如果允许实时同步
						Graph temp = new Graph(userName,startX,startY,endX,endY,PaintMode.RECTANGLE,stroke.getLineWidth(),color);
						sendGraph(temp);
					}
					break;
				case ELLIPSE:
					g_2d.drawOval(realsx, realsy, realex - realsx, realey - realsy);
					MyImage_g.drawOval(realsx, realsy, realex - realsx, realey
							- realsy);
					if(RealTimeBtn.isSelected()&& !isGetFromOther){
						//如果允许实时同步
						Graph temp = new Graph(userName,startX,startY,endX,endY,PaintMode.ELLIPSE,stroke.getLineWidth(),color);
						sendGraph(temp);
					}
					break;
				// 铅笔跟橡皮擦不需要再保存了
				default:
					break;
				}
				isBegin = false;
				isEnd = false;
				isPainting = false;
			}
			isGetFromOther=false;
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			isBegin = false;
			isPainting = true;
			isEnd = false;
			if (paintmode == PaintMode.PENCIL || paintmode == PaintMode.RUBBER) {
				startX = endX;
				startY = endY;
			}
			endX = arg0.getX();
			endY = arg0.getY();
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			isBegin = true;
			isPainting = false;
			isEnd = false;
			startX = arg0.getX();
			endX = startX;
			startY = arg0.getY();
			endY = startY;
			System.out.println("坐标:"+startX+","+(startY==endY?startY:-1));
			System.out.println("画笔宽度:"+(float) (LineWidthBox.getSelectedIndex() + 1.0));
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			isBegin = false;
			isPainting = false;
			isEnd = true;
			endX = arg0.getX();
			endY = arg0.getY();
			repaint();
		}

		public BufferedImage getImage() {
			return MyImage;
		}

		public void setImage(BufferedImage image) {
//			MyImage_g.clearRect(0, 0, WIDTH, HEIGHT);
			System.out.println("获得图像");
			startX = startY = endX = endY = -1;
			MyImage = image;
			MyImage_g = image.createGraphics();
			MyImage_g.setColor(paperColor);
			//MyImage_g.fillRect(0, 0, WIDTH, HEIGHT);
			MyImage_g.setColor(color);
			repaint();
		}
		public void addGraph(Graph graph){
			System.out.println(userName+":paintPanel添加Graph");
			BasicStroke stroke = new BasicStroke(
					graph.penWidth);
			
			MyImage_g.setColor(graph.pencolor);
			System.out.println(userName+":画笔颜色,"+graph.pencolor);//MyImage_g.getColor());
			MyImage_g.setStroke(stroke);
			int sx=graph.startX,sy=graph.startY,ex=graph.endX,ey=graph.endY;
			int realsx,realsy,realex,realey;
			// 针对矩形和椭圆会用到
			realsx = sx < ex ? sx : ex;
			realex = sx > ex ? sx : ex;
			realsy = sy < ey ? sy : ey;
			realey = sy > ey ? sy : ey;
			switch(graph.paintmode){
			case LINE:
				MyImage_g.drawLine(sx, sy, ex, ey);break;
			case RECTANGLE:
				MyImage_g.drawRect(realsx, realsy, realex-realsx, realey-realsy);break;
			case ELLIPSE:
				MyImage_g.drawOval(realsx, realsy, realex - realsx, realey - realsy);break;
			case PENCIL:
				MyImage_g.drawLine(sx, sy, ex, ey);break;
			case RUBBER:
				//橡皮擦颜色用本地画布的颜色
				MyImage_g.setColor(paperColor);
				System.out.println(userName+":paintPanel的画布颜色:"+paperColor);
				MyImage_g.drawLine(sx, sy, ex, ey);break;
			default:
				break;
			}
			isGetFromOther=true;
			repaint();
		}
	}
}
