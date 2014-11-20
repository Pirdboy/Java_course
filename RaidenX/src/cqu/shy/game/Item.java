package cqu.shy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Item extends FlyingObject{

	//道具的图片,两张交替显示
	private MyImage image1;
	private MyImage image2;
	private int x,y;
	private int Dx,Dy;
	private int kind;   // 1表示加分,2表示加子弹,3表示加血,4表示加蓝
	private int pic_interval=20;
	private int count=1;
	private int move_speed;
	
	public Item(BufferedImage image1, BufferedImage image2, int kind, int move_speed) {
		super();
		this.image1 = new MyImage(image1, image1.getWidth(), image1.getHeight(), 0, 0);
		this.image2 = new MyImage(image2, image2.getWidth(), image2.getHeight(), 0, 0);
		this.kind = kind;
		this.move_speed = move_speed;
		x=y=Dx=Dy=0;
	}

	public boolean hit(HeroPlane other) {
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image1.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image1.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image1.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image1.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		x += Dx*move_speed;
		y += Dy*move_speed;
	}

	@Override
	public void draw(Graphics g) {
		if(count%pic_interval>=0 && count%pic_interval<10){
			g.drawImage(image1.getImage(), x, y, image1.getWidth(),
					image1.getHeight(), null);
		}
		else if(count%pic_interval>=10){
			g.drawImage(image2.getImage(), x, y, image2.getWidth(),
					image2.getHeight(), null);
		}
		count++;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDx() {
		return Dx;
	}

	public void setDx(int dx) {
		Dx = dx;
	}

	public int getDy() {
		return Dy;
	}

	public void setDy(int dy) {
		Dy = dy;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public MyImage getImage() {
		return image1;
	}

	public void setImage1(MyImage image1) {
		this.image1 = image1;
	}
	public void setImage2(MyImage image2){
		this.image2 = image2;
	}
}
