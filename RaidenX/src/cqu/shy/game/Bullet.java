package cqu.shy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cqu.shy.resource.ImageResource;

class Bullet extends FlyingObject{

	private MyImage image;
	private int Dx;
	private int Dy;
	private int x;
	private int y;
	private int move_speed;
	private boolean isDeath=false;
	private boolean goToDeath=false;
	private int count_death=1;
	private int death_interval=6;
	private int attack;
	boolean isLaser=false;  //专门为第三个BOSS的激光使用
	int laser_time=1;
	boolean isSkill=false;   //为Boss的技能使用
	public Bullet(BufferedImage img,int move_speed,int attack) {
		// TODO Auto-generated constructor stub
		image = new MyImage(img, img.getWidth(), img.getHeight(), 0, 0);
		this.move_speed = move_speed;
		this.attack = attack;
		Dx=0;Dy=0;
	}
	public boolean hit(EnemyPlane other) {
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}
	public boolean hit(HeroPlane other){
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}
	public boolean hit(Boss other){
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}
	public boolean hit(Bullet other){
		if((this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(this.getX()>=other.getX()&&this.getX()<=other.getX()+other.getImage().getWidth()&&other.getY()>=this.getY()&&other.getY()<=this.getY()+image.getHeight())||
				(other.getX()>=this.getX()&&other.getX()<=this.getX()+image.getWidth()&&this.getY()>=other.getY()&&this.getY()<=other.getY()+other.getImage().getHeight()))	
			return true;
		else
			return false;
	}
	@Override
	public void init() {
		
	}
	public void doDamage(HeroPlane other){
		other.setHP(other.getHP()-attack);
	}
	public void doDamage(Boss other){
		other.setHP(other.getHP()-attack);
	}
	public void doDamage(EnemyPlane other){
		other.setHP(other.getHP()-attack);
	}
	@Override
	public void move() {
		x +=Dx*move_speed;
		y +=Dy*move_speed;
		if(isLaser)
			laser_time++;
	}
	@Override
	public void draw(Graphics g) {
		if(!isDeath)
		{
			g.drawImage(image.getImage(), x, y, image.getWidth(), image.getHeight(), null);
		}
		else {
			if(count_death<death_interval){
				if(isLaser){
					g.drawImage(ImageResource.BossBullet3_2IMG, x, y, ImageResource.BossBullet3_2IMG.getWidth(),
							ImageResource.BossBullet3_2IMG.getHeight(), null);
				}
				else{
					g.drawImage(ImageResource.ExplosionBulletIMG, x, y, ImageResource.ExplosionBulletIMG.getWidth(), 
							ImageResource.ExplosionBulletIMG.getHeight(), null);
				}
			}
			else{
				goToDeath=true;
			}
			count_death++;
		}
	}
	
	
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
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
	public MyImage getImage() {
		return image;
	}
	public void setImage(MyImage image) {
		this.image = image;
	}
	public int getMove_speed() {
		return move_speed;
	}
	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}
	
}
