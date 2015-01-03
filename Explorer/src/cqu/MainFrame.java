package cqu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {
	private MyTree tree;
	private FileList list;
	//顶部菜单栏
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu aboutMenu = new JMenu("About");
	private JMenuItem showHideItem = new JMenuItem("  显示隐藏文件");
	private JMenuItem notShowHideItem = new JMenuItem("  不显示隐藏文件");
	private JMenuItem closeItem = new JMenuItem("  Close");
	private JMenuItem aboutItem = new JMenuItem("  作者信息");
	//右键菜单
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem openItem = new JMenuItem("打开文件夹");
	private JMenu subMenu1= new JMenu("新建");
	private JMenuItem newFileItem=new JMenuItem("文件");
	private JMenuItem newDirItem=new JMenuItem("文件夹");
	private JMenuItem copyItem=new JMenuItem("复制");
	private JMenuItem cutItem=new JMenuItem("剪切");
	private JMenuItem pasteItem=new JMenuItem("粘贴");
	private JMenuItem refreshItem = new JMenuItem("刷新");
	private JMenuItem deleteItem=new JMenuItem("删除");
	private JMenuItem renameItem=new JMenuItem("重命名");
	private JScrollPane scrollPane;
	//右键菜单需要的变量
	private String copyPath=null;
	private String copyName=null;
	private boolean isCut=false;
	private boolean isCopy=false;
	private boolean isFile=false;
	
	public MainFrame() {
		this.setLayout(new BorderLayout(6, 6));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(800, 600);
		this.setLocation(100, 60);
		this.setTitle("操作系统课程设计-资源管理器");
		initComponent();
		setJMenuBar(menuBar);
		JScrollPane temp1 = new JScrollPane(tree);
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp1,
				scrollPane);

		pane.setDividerLocation(300);
		pane.setDividerSize(4);
		this.add(pane,BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		MainFrame ui = new MainFrame();
		ui.setVisible(true);
	}
	private void refreshListAndTree(){
		FileNode parentNode = ((FileListModel)list.getModel()).getNode();
		parentNode.Refresh();
		list.updateUI();
		tree.updateUI();
	}
	private boolean fileExist(String name){
		File f = new File(name);
		if(f.exists())
			return true;
		else
			return false;
	}
	private void initComponent(){
		
		//顶部菜单栏
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		fileMenu.add(showHideItem);
		fileMenu.add(notShowHideItem);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);
		aboutMenu.add(aboutItem);
		menuBar.setVisible(true);
		showHideItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				MyNode.setShowHiden(true);
				FileNode parentNode = ((FileListModel)list.getModel()).getNode();
				System.out.println(parentNode);
				parentNode.Refresh();
				list.updateUI();
				tree.updateUI();
			}
		});
		notShowHideItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyNode.setShowHiden(false);
				FileNode parentNode = ((FileListModel)list.getModel()).getNode();
				System.out.println(parentNode);
				parentNode.Refresh();
				list.updateUI();
				tree.updateUI();
			}
		});
		closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(MainFrame.this, "<html><font size=5>"+"操作系统课程设计-资源管理器</font></html>\n"
						+ "<html><font size=4>小组成员:</font></html>\n"
						+ "<html><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;石鸿宇20124994</font></html>\n"
						+ "<html><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;郑斌20124990</font></html>\n"
						+ "<html><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;严双超20124982</font></html>\n"
						+ "<html><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;管秀清20125007</font></html>\n"
						+ "<html><font size=3>\u00A9Copyright 2014, powered By Java 1.8.0_20</font></html>", "关于作者", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, IconResource.javaIcon);
			}
		});
		//右键菜单
		subMenu1.add(newFileItem);
		subMenu1.add(newDirItem);
		popupMenu.add(openItem);
		popupMenu.add(subMenu1);
		popupMenu.add(cutItem);
		popupMenu.add(copyItem);
		popupMenu.add(pasteItem);
		popupMenu.add(refreshItem);
		popupMenu.add(deleteItem);
		popupMenu.add(renameItem);
		changeFont();
		//树结构和List结构
		list = new FileList();
		tree = new MyTree(list);
		scrollPane = new JScrollPane(list);
		openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = list.getModel().getElementAt(
						list.getSelectedIndex());
				MyNode node = (MyNode)selected;
				list.TreeSelectionChanged(node);
			}
		});
		newFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//通过list获取父亲节点
				Object parentNode = ((FileListModel)(list.getModel())).getNode();
				//获得父目录
				MyNode node = (MyNode)parentNode;
				String parent = node.getFile().getAbsolutePath();
				//输入新文件名
				JLabel label = new JLabel("请输入文件名");
				label.setFont(new Font("新宋体",Font.PLAIN,13));
				String newName = JOptionPane.showInputDialog(MainFrame.this, label, "创建文件", JOptionPane.INFORMATION_MESSAGE);
				if(!(newName==null || newName.equals(""))){
					//Runtime r = Runtime.getRuntime();
					if(fileExist(parent+"/"+newName)){
						label = new JLabel("创建失败,该文件已经存在");
						label.setFont(new Font("新宋体",Font.PLAIN,13));
						JOptionPane.showConfirmDialog(MainFrame.this, label, "创建文件", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);  
						return;
					}

					File newFile = new File(parent+"/"+newName);
					if(!newFile.exists()){
						try {
							newFile.createNewFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				//更新显示
				refreshListAndTree();
			}
		});
		newDirItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//通过list获取父亲节点
				Object parentNode = ((FileListModel)(list.getModel())).getNode();
				//获得父目录
				MyNode node = (MyNode)parentNode;
				String parent = node.getFile().getAbsolutePath();
				JLabel label = new JLabel("请输入名称");
				label.setFont(new Font("新宋体",Font.PLAIN,13));
				String newName = JOptionPane.showInputDialog(MainFrame.this, label, "创建文件夹", JOptionPane.INFORMATION_MESSAGE);
				if(!(newName==null || newName.equals(""))){
					if(fileExist(parent+"/"+newName)){
						label = new JLabel("创建失败,该文件夹已经存在");
						label.setFont(new Font("新宋体",Font.PLAIN,13));
						JOptionPane.showConfirmDialog(MainFrame.this, label, "创建文件夹", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE); 
						return;
					}
					File newDir = new File(parent+"/"+newName);
					if(!newDir.exists()){
						newDir.mkdir();
					}
				}
				//更新显示
				refreshListAndTree();
			}
		});
		copyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = list.getModel().getElementAt(
						list.getSelectedIndex());
				MyNode node = (MyNode)selected;
				File file = node.getFile();
				isCopy=true;
				isCut=false;
				if(file.isFile())
					isFile=true;
				else if(file.isDirectory())
					isFile=false;
				copyPath = file.getAbsolutePath();
				copyName = file.getName();
				System.out.println("要复制文件的绝对路径:"+copyPath);
				System.out.println("要复制文件的名称:"+copyName);
				System.out.println();
			}
		});
		cutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = list.getModel().getElementAt(
						list.getSelectedIndex());
				MyNode node = (MyNode)selected;
				File file = node.getFile();
				isCopy=false;
				isCut=true;
				if(file.isFile())
					isFile=true;
				else if(file.isDirectory())
					isFile=false;
				copyPath=file.getAbsolutePath();
				copyName=file.getName();
			}
		});
		pasteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//通过list获取父亲节点
				Object parentNode = ((FileListModel)(list.getModel())).getNode();
				//获得父目录
				MyNode node = (MyNode)parentNode;
				String parent = node.getFile().getAbsolutePath();
				String test=new String(parent+"/"+copyName);
				File testFile = new File(test);
				//如果要复制的目录下已经存在同名的文件,则弹出提示
				int choose=0;
				if(testFile!=null && testFile.exists()){
					JLabel label = new JLabel("该目录下存在同名文件,是否要替换");
					label.setFont(new Font("新宋体",Font.PLAIN,13));
					choose = JOptionPane.showConfirmDialog(MainFrame.this, label, 
							isCut?"移动文件":"复制文件", JOptionPane.YES_NO_OPTION);					
				}
				if(choose==1)
					return;
				if(isCut){  //剪切
					if(isFile){
						FileOperation.copyFile(copyPath, test);
						File df = new File(copyPath);
						FileOperation.delete(df);
					}
					else{
						FileOperation.copyDir(copyPath, test);
						File df = new File(copyPath);
						FileOperation.delete(df);
					}
					isCut=false;
					isCopy=false;
				}
				else if(isCopy){  //复制
					if(isFile){
						FileOperation.copyFile(copyPath, test);
					}
					else{
						FileOperation.copyDir(copyPath, test);
					}
				}
				//更新显示
				refreshListAndTree();
			}
		});
		refreshItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshListAndTree();
			}
		});
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = list.getModel().getElementAt(
						list.getSelectedIndex());
				MyNode node = (MyNode)selected;
				File file = node.getFile();
				FileOperation.delete(file);
				//更新显示
				refreshListAndTree();
			}
		});
		renameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = list.getModel().getElementAt(
						list.getSelectedIndex());
				MyNode node = (MyNode)selected;
				File file = node.getFile();
				JLabel label = new JLabel("请输入新名称");
				label.setFont(new Font("新宋体",Font.PLAIN,13));
				String newName = JOptionPane.showInputDialog(MainFrame.this, label, "重命名", JOptionPane.INFORMATION_MESSAGE);
				if(!(newName==null || newName.equals(""))){
					String test = file.getParent()+"/"+newName;
					if(fileExist(test)){
						label = new JLabel("重命名失败, 存在同名文件");
						label.setFont(new Font("新宋体",Font.PLAIN,13));
						JOptionPane.showConfirmDialog(MainFrame.this, label, "重命名", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE); 
						return;
					}
					FileOperation.changeName(file, newName);
				}
				//更新显示
				refreshListAndTree();
			}
		});
		
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				list.setSelectedIndex(list.locationToIndex(e.getPoint())); // 获取鼠标点击的项
				this_mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			// 弹出菜单
			private void this_mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					System.out.println(list.getSelectedIndex());
					int index = list.getSelectedIndex();
					if(index!=-1 && index>=0 && index<
							((FileListModel)list.getModel()).getSize()){
						Object selected = list.getModel().getElementAt(
								list.getSelectedIndex());
						MyNode node = (MyNode)selected;
						File f = node.getFile();
						if(f.isDirectory())
							openItem.setEnabled(true);
						else
							openItem.setEnabled(false);
						newFileItem.setEnabled(true);
						newDirItem.setEnabled(true);
						cutItem.setEnabled(true);
						copyItem.setEnabled(true);
						if(isCut || isCopy)
							pasteItem.setEnabled(true);
						else
							pasteItem.setEnabled(false);
						deleteItem.setEnabled(true);
						renameItem.setEnabled(true);
						refreshItem.setEnabled(true);
					}
					else{
						openItem.setEnabled(false);
						newFileItem.setEnabled(true);
						newDirItem.setEnabled(true);
						cutItem.setEnabled(false);
						copyItem.setEnabled(false);
						if(isCut || isCopy)
							pasteItem.setEnabled(true);
						else
							pasteItem.setEnabled(false);
						deleteItem.setEnabled(false);
						renameItem.setEnabled(false);
						refreshItem.setEnabled(true);
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
	}
	private void changeFont(){
		//右键菜单
		popupMenu.setFont(new Font("新宋体",Font.PLAIN,13));
		subMenu1.setFont(new Font("新宋体",Font.PLAIN,13));
		openItem.setFont(new Font("新宋体",Font.PLAIN,13));
		newFileItem.setFont(new Font("新宋体",Font.PLAIN,13));
		newDirItem.setFont(new Font("新宋体",Font.PLAIN,13));
		cutItem.setFont(new Font("新宋体",Font.PLAIN,13));
		copyItem.setFont(new Font("新宋体",Font.PLAIN,13));
		pasteItem.setFont(new Font("新宋体",Font.PLAIN,13));
		deleteItem.setFont(new Font("新宋体",Font.PLAIN,13));
		renameItem.setFont(new Font("新宋体",Font.PLAIN,13));
		refreshItem.setFont(new Font("新宋体",Font.PLAIN,13));
		//顶部菜单
		menuBar.setFont(new Font("新宋体",Font.PLAIN,13));
		showHideItem.setFont(new Font("新宋体",Font.PLAIN,13));
		notShowHideItem.setFont(new Font("新宋体",Font.PLAIN,13));
		closeItem.setFont(new Font("新宋体",Font.PLAIN,13));
		fileMenu.setFont(new Font("新宋体",Font.PLAIN,13));
		aboutMenu.setFont(new Font("新宋体",Font.PLAIN,13));
		aboutItem.setFont(new Font("新宋体",Font.PLAIN,13));
	}
	private String dealSpace(String s){
		StringBuffer sb = new StringBuffer(s);
		for(int i=0;i<sb.length();i++){
			if(sb.charAt(i)==' '){
				sb.insert(i, '\\');
				i++;
			}
		}
		return sb.toString();
	}
}

