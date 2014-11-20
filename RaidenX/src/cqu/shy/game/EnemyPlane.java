package cqu.shy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cqu.shy.resource.ImageResource;
import cqu.shy.resource.SoundResource;

class EnemyPlane extends FlyingObject{

	//飞机自身属性
	private MyImage image;
	private int kind;   //1表示小敌机  2表示中敌机  3表示大敌机
	private int HP;
	private int attack;
	private int move_speed;
	private int x,y,Dx,Dy;
	private boolean allowfire=true;
	private boolean isDeath=false;
	private boolean goToDeath=false;
	private int count_death=1;
	private int death_interval=5;
	private int Score=0;

	//子弹
	public EnemyPlane(BufferedImage img,int pos_x,int pos_y,int HP,int attack,int move_speed,int kind) {
		image = new MyImage(img, img.getWidth(), img.getHeight(), 0, 0);
		this.attack = attack;
		this.HP = HP;
		x=pos_x;y=pos_y;
		this.kind=kind;
		this.move_speed = move_speed;
		
	}
	
	public MyImage getImage() {
		return image;
	}

	public void setImage(MyImage image) {
		this.image = image;
	}

	public boolean isAllowfire() {
		return allowfire;
	}

	public void setAllowfire(boolean allowfire) {
		this.allowfire = allowfire;
	}

	public boolean hit(HeroPlane other) {
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}
	
	public void doDamage(HeroPlane other){
		other.setHP(other.getHP()-attack);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		x += Dx;
		y += Dy;
	}

	@Override
	public void draw(Graphics g) {
		if(!isDeath)
		{
			g.drawImage(image.getImage(), x, y, image.getWidth(), image.getHeight(), null);
		}
		else {
			if(count_death<death_interval){
				//显示爆炸图片
				switch (kind) {
				case 1:
					g.drawImage(ImageResource.ExplosionsmallIMG, x, y, ImageResource.ExplosionsmallIMG.getWidth(), 
							ImageResource.ExplosionsmallIMG.getHeight(), null);
					break;
				case 2:
					g.drawImage(ImageResource.ExplosionmiddleIMG, x, y,ImageResource.ExplosionmiddleIMG.getWidth(),
							ImageResource.ExplosionmiddleIMG.getHeight(), null);
					break;
				case 3:
					g.drawImage(ImageResource.ExplosionlargeIMG, x, y,ImageResource.ExplosionlargeIMG.getWidth(),
							ImageResource.ExplosionlargeIMG.getHeight(), null);
					break;
				default:
					break;
				}
			}
			else {
				goToDeath=true;
			}
			count_death++;
		}
		
	}
	public Bullet basicbullet(){
		if(!allowfire)
			return null;
		Random r =new Random();
		Bullet b =null;
		switch (kind) {
		case 1:   //小敌机
			b = new Bullet(ImageResource.EnemysmallbulletIMG,this.move_speed+1,attack );
			break;
		case 2:    //中敌机
			b = new Bullet(ImageResource.EnemymiddlebulletIMG,this.move_speed+r.nextInt(2)+1,attack);
			break;
		case 3:    //大敌机
			b = new Bullet(ImageResource.EnemylargebulletIMG,this.move_speed+1,attack);
			break;
		default:
			break;
		}
		b.setX(this.x+this.getImage().getWidth()/2-b.getImage().getWidth()/2);
		b.setY(this.y+this.getImage().getHeight()+1);
		return b;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}

	public boolean isGoToDeath() {
		return goToDeath;
	}

	public void setGoToDeath(boolean goToDeath) {
		this.goToDeath = goToDeath;
	}

	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getMove_speed() {
		return move_speed;
	}
	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}
	
	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
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
	
}
