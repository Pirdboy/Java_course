package cqu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class FileList extends JList {

	FileListModel dataModel;
	static final long serialVersionUID = 10;

	public FileList() {
		dataModel = new FileListModel();
		setModel(dataModel);
		this.setCellRenderer(new MyCellRenderer());
	}

	public void TreeSelectionChanged(FileNode node) {
		dataModel.setNode(node);
		updateUI();
	}
}

class FileListModel implements ListModel {
	FileNode node;
	char fileType = FileNode.ALL;

	public void setNode(FileNode node) {
		this.node = node;
	}
	public FileNode getNode(){
		return node;
	}

	public Object getElementAt(int index) {
		if (node != null) {
			return ((FileNode) node).getChild(fileType, index);
		} else {
			return null;
		}
	}

	public int getSize() {
		if (node != null) {
			return ((FileNode) node).getChildCount(fileType);
		} else {
			return 0;
		}
	}

	public void addListDataListener(ListDataListener l) {

	}

	public void removeListDataListener(ListDataListener l) {
	}
}

class MyCellRenderer extends JLabel implements ListCellRenderer {
	public MyCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		MyNode node = (MyNode) value;
		setFont(new Font("新宋体",Font.PLAIN,12));
		setIcon(node.getIcon());
		setText(value.toString());
		setBackground(isSelected ? (new Color(37, 173, 223)) : Color.WHITE);
		setForeground(isSelected ? Color.WHITE : Color.BLACK);
		return this;
	}
}