package cqu;

import java.awt.Component;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MyTree extends JTree {

    public MyTree() {
    }

    static final long serialVersionUID = 0;

    private FileList theList;

    public MyTree(FileList list) {
        theList = list;
        setModel(new FileSystemModel(new MyNode()));
        this.setCellRenderer(new FolderRenderer());
        addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
		        TreePath tp = e.getPath();
		        Object o = tp.getLastPathComponent();
		        theList.TreeSelectionChanged((MyNode) o);
				
			}
		});
        addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeExpanded(TreeExpansionEvent event) {
			}
			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				TreePath curPath=event.getPath();
		    	
		        TreePath selectionPath = getSelectionPath();
		        if (curPath.isDescendant(selectionPath)) {
		           setSelectionPath(curPath);
		        }
			}
		});
        this.setSelectionRow(0);
    }
}

class FileSystemModel implements TreeModel {
    FileNode theRoot;
    char fileType = FileNode.DIRECTORY;

    public FileSystemModel(FileNode fs) {
        theRoot = fs;
    }

    public Object getRoot() {
        return theRoot;
    }
    public void setRoot(FileNode node){
    	theRoot=node;
    }
    public Object getChild(Object parent, int index) {
        return ((FileNode) parent).getChild(fileType, index);
    }

    public int getChildCount(Object parent) {
        return ((FileNode) parent).getChildCount(fileType);
    }

    public boolean isLeaf(Object node) {
        return ((FileNode) node).isLeaf(fileType);
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((FileNode) parent).getIndexOfChild(fileType, child);
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }
}

interface FileNode {
    final public static char DIRECTORY = 'D';
    final public static char FILE = 'F';
    final public static char ALL = 'A';

    public Icon getIcon();
    public FileNode getChild(char fileType, int index);
    public int getChildCount(char fileType);
    public boolean isLeaf(char fileType);
    public int getIndexOfChild(char fileType, Object child);
    public void Refresh();
    public String toString();
}

class MyNode implements FileNode {
    private static FileSystemView fsView;
    private static boolean useFileHiding= true;
    private File theFile;
    private Vector<File> all = new Vector<File>();
    private Vector<File> folder = new Vector<File>();
    
    public static void setShowHiden(boolean ifshow) {
    	useFileHiding = (!ifshow);
    }
    public File getFile(){
    	return theFile;
    }
    public void Refresh(){
    	all.clear();
    	folder.clear();
    	prepareChildren();
    }
    public Icon getIcon() {
    	if(theFile.isFile())
    		return IconResource.fileIcon;
    	else 
    		return IconResource.dirIcon;
    }

    public String toString() {
        return fsView.getSystemDisplayName(theFile);
    }

    /**
    * 创建一个根节点,以/home/asus,即我的计算机的用户目录为根
    */
    public MyNode() {
        fsView = FileSystemView.getFileSystemView();
        theFile = fsView.getHomeDirectory();
        prepareChildren();
    }
    
    private void prepareChildren() {
        File[] files = fsView.getFiles(theFile, useFileHiding);
        for (int i = 0; i < files.length; i++) {
            all.add(files[i]);
            if (files[i].isDirectory()    && !files[i].toString().toLowerCase().endsWith(".lnk")) {
                folder.add(files[i]);
            }
        }
    }

    private MyNode(File file) {
        theFile = file;
        prepareChildren();
    }

    public MyNode getChild(char fileType, int index) {
        if (FileNode.DIRECTORY == fileType) {
        	if(index>=0 && index<folder.size())
        		return new MyNode(folder.get(index));
        	else
        		return null;
        } else if (FileNode.ALL == fileType) {
        	if(index>=0 && index<all.size())
        		return new MyNode(all.get(index));
        	else
        		return null;
        } else if (FileNode.FILE == fileType) {
            return null;
        } else {
            return null;
        }
    }

    public int getChildCount(char fileType) {
        if (FileNode.DIRECTORY == fileType) {
            return folder.size();
        } else if (FileNode.ALL == fileType) {
            return all.size();
        } else if (FileNode.FILE == fileType) {
            return -1;
        } else {
            return -1;
        }
    }

    public boolean isLeaf(char fileType) {
        if (FileNode.DIRECTORY == fileType) {
            return folder.size() == 0;
        } else if (FileNode.ALL == fileType) {
            return all.size() == 0;
        } else if (FileNode.FILE == fileType) {
            return true;
        } else {
            return true;
        }
    }

    public int getIndexOfChild(char fileType, Object child) {
        if (child instanceof MyNode) {
            if (FileNode.DIRECTORY == fileType) {
                return folder.indexOf(((MyNode) child).theFile);
            } else if (FileNode.ALL == fileType) {
                return all.indexOf(((MyNode) child).theFile);
            } else if (FileNode.FILE == fileType) {
                return -1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}

class FolderRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,    boolean hasFocus) {
        FileNode node = (FileNode) value;
        Icon icon = node.getIcon();

        setLeafIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);

        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}

