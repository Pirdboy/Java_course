package demo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Test_Mouse2  implements MouseMotionListener,MouseListener{
  Frame frame = new Frame("关于鼠标的多重监听器");
  TextField tField = new TextField(30);
  public Test_Mouse2(){
    Label label = new Label("请按下鼠标左键并拖动");
    frame.add(label, "North");
    frame.add(tField, "South");
    frame.setBackground(new Color(180, 255, 255));
    frame.addMouseListener(this);
    frame.addMouseMotionListener(this);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        System.exit(0);
      }
    });
    frame.setSize(300, 200);
    frame.setLocation(400, 250);
    frame.setVisible(true);
  }
  public static void main(String[] args) {
    new Test_Mouse2();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    System.out.print("鼠标点击----" + "\t");
    if (e.getClickCount()==1) {
      System.out.println("单击！");
    } else if (e.getClickCount()==2) {
      System.out.println("双击！");
    } else if (e.getClickCount()==3) {
      System.out.println("三连击！！");
    }
    
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    System.out.println("鼠标按下");
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    System.out.println("鼠标松开");
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    tField.setText("鼠标已经进入窗体");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    tField.setText("鼠标已经移出窗体");
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
    String string = "鼠标拖动到位置：（" + e.getX() + "，" + e.getY() +"）";
    tField.setText(string);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub
  }

}