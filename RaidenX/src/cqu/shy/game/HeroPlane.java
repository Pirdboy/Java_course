package cqu.shy.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cqu.shy.resource.ImageResource;
import cqu.shy.resource.SoundResource;

class HeroPlane extends FlyingObject{
	//移动相关
	private boolean keyUp=false;
	private boolean keyDown=false;
	private boolean keyLeft=false;
	private boolean keyRight=false;
	private int x,y;
	private int Dx=0;
	private int Dy=0;
	private int move_speed=4;
	//主机的属性
	private MyImage image;
	private int MaxHP=800;
	private int MaxMP=500;
	private int HP=800;
	private int MP=500;
	private int Score=0;
	private int attack=50;
	private int Fire_Level=1;
	private int fire_interval;   //子弹间隔时间
	private int count_fire=1;
	private boolean isDeath=false;
	private boolean goToDeath=false;
	private int death_interval=8;
	private int count_death=1;
	private boolean isPowerFul=false;   //是否暴走
	private int count_powerful=1;
	private int powerful_time = 3*1200/18;
	
	//发射子弹
	private List<Bullet> Bullets;
	private List<Bullet> BulletShouldDel;
	private boolean allowsound=false;
	
//	//技能
//	private boolean isSkill_Zcold=false;
//	private int count_SkillZ=1;
//	private int SkillZ_coldtime=2*1000/18;
//	private int SkillZMP_consume=200;
	MyImage SkillZ_icon = new MyImage(ImageResource.SkillZ_iconIMG, 
			ImageResource.SkillZ_iconIMG.getWidth(), ImageResource.SkillZ_iconIMG.getHeight(), 20, 110);
	MyImage SkillZCold_icon = new MyImage(ImageResource.SkillZCold_iconIMG,
			ImageResource.SkillZCold_iconIMG.getWidth() , ImageResource.SkillZCold_iconIMG.getHeight(),20, 110);
	
	public HeroPlane() {
		// TODO Auto-generated constructor stub
		Bullets = new ArrayList<Bullet>();
		BulletShouldDel = new ArrayList<Bullet>();
		image = new MyImage(ImageResource.HeroplaneIMG, ImageResource.HeroplaneIMG.getWidth(),
				ImageResource.HeroplaneIMG.getHeight(), 0, 0);
		this.x = Canvas_width/2-image.getWidth();
		this.y = Canvas_height-image.getHeight();
	}
	public List<Bullet> getBullets(){
		return Bullets;
	}
	public List<Bullet> getBulletShouldDel(){
		return BulletShouldDel;
	}
	@Override
	public void init() {
		Bullets.clear();
		BulletShouldDel.clear();
		keyUp=false;
		keyDown=false;
		keyLeft=false;
		keyRight=false;
		Dx = Dy = 0;
		Fire_Level=1;
		move_speed=4;
		count_fire=1;
		fire_interval=16;
		HP = MaxHP;
		MP = MaxMP;
		isDeath = false;
		goToDeath=false;
		x = Canvas_width/2 - image.getWidth()/2;
		y = Canvas_height - image.getHeight();
		Score=0;
	}

	@Override
	public void move() {
		if(!isDeath)
		{
			int oldx = x;
			int oldy = y;
			this.x += Dx*move_speed;
			this.y += Dy*move_speed;
			if(x<-image.getWidth()/2+5 || x>Canvas_width-image.getWidth()/2-5)
				this.x = oldx;
			if(y<-6 || y>Canvas_height-image.getHeight()+6)
				this.y = oldy;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(!isDeath)
		{
			g.drawImage(image.getImage(), x, y, image.getWidth(), image.getHeight(), null);
		}
		else {
			if(count_death<death_interval){
				//显示爆炸图片
				g.drawImage(ImageResource.HerodeathIMG, x, y, ImageResource.HerodeathIMG.getWidth(), 
						ImageResource.HerodeathIMG.getHeight(), null);
			}
			else{
				goToDeath=true;
			}
			count_death++;
		}
	}

	//碰撞、开火
	public boolean hit(EnemyPlane other) {
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
	public void doDamage(Boss other){
		other.setHP(other.getHP()-attack);
	}
	public void doDamage(EnemyPlane other){
		other.setHP(other.getHP()-attack);
	}
	public void fire(){
		if(Fire_Level>=5){
			isPowerFul=true;
			fire_interval=4;
		}
		//添加子弹
		if(count_fire % fire_interval==0){
			if(allowsound)
				SoundResource.FireSound.play();
			for(int u=0;u<Fire_Level;u++){
				if(u==0){
					Bullet b= new Bullet(ImageResource.Herobullet1IMG, 6,attack);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 - 7);
					b.setY(this.y-4);
					b.setDx(0);
					b.setDy(-1);
					Bullets.add(b);
					b = new Bullet(ImageResource.Herobullet1IMG, 6,attack);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 + 7);
					b.setY(this.y-4);
					b.setDx(0);
					b.setDy(-1);
					Bullets.add(b);
				}
				else if(u==1){
					Bullet b = new Bullet(ImageResource.Herobullet1IMG, 2,attack-10);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 - 6-2);
					b.setY(this.y-4);
					b.setDx(-1);
					b.setDy(-3);
					Bullets.add(b);
					b = new Bullet(ImageResource.Herobullet1IMG, 2,attack-10);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 + 6+2);
					b.setY(this.y-4);
					b.setDx(1);
					b.setDy(-3);
					Bullets.add(b);
				}
				else if(u==2){
					Bullet b = new Bullet(ImageResource.Herobullet2IMG, 2,attack-15);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 -6-4);
					b.setY(this.y-4);
					b.setDx(-2);
					b.setDy(-3);
					Bullets.add(b);
					b = new Bullet(ImageResource.Herobullet2IMG, 2,attack-15);
					b.setX(this.x + this.image.getWidth()/2-b.getImage().getWidth()/2 +6+4);
					b.setY(this.y-4);
					b.setDx(2);
					b.setDy(-3);
					Bullets.add(b);
				}
				else if(u==3){
					Bullet b = new Bullet(ImageResource.Herobullet3IMG, 6,attack);
					b.setX(this.x + this.image.getWidth());
					b.setY(this.y+15);
					b.setDx(2);
					b.setDy(0);
					Bullets.add(b);
					b = new Bullet(ImageResource.Herobullet4IMG, 6,attack);
					b.setX(this.x-1);
					b.setY(this.y+15);
					b.setDx(-2);
					b.setDy(0);
					Bullets.add(b);
				}
				else if(u==4){
					//暴走的子弹
					Bullet power_b = new Bullet(ImageResource.HerobulletPowerIMG,6,attack);
					power_b.setX(this.x + this.image.getWidth()/2-power_b.getImage().getWidth()/2);
					power_b.setY(this.y-power_b.getImage().getHeight()+3);
					power_b.setDx(0);
					power_b.setDy(-2);
					Bullets.add(power_b);
				}
			}
		}
		if(isPowerFul){
			if(count_powerful<=powerful_time){
				setMove_speed(6);
				count_powerful++;
			}
			else{
				count_powerful=1;
				isPowerFul=false;
				Fire_Level--;
				fire_interval=16;
				setMove_speed(4);
			}
		}
		count_fire++;
		for(Bullet b:Bullets){
			b.move();
		}
		//检查是否到达屏幕外
		for(Bullet b:Bullets){
			if(b==null){
				System.out.println("子弹为null");
			}
			if(b.getX()<-b.getImage().getWidth()-1 || b.getX()>Canvas_width+1 || 
					b.getY()>Canvas_height+1 || b.getY()<-b.getImage().getHeight()-1){
				BulletShouldDel.add(b);
			}
		}
		if(!BulletShouldDel.isEmpty())
			Bullets.removeAll(BulletShouldDel);
		BulletShouldDel.clear();
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
	public int getFire_Level() {
		return Fire_Level;
	}
	public void setFire_Level(int fire_Level) {
		Fire_Level = fire_Level;
	}
	
	public boolean isPowerFul() {
		return isPowerFul;
	}
	public void setPowerFul(boolean isPowerFul) {
		this.isPowerFul = isPowerFul;
	}
	public MyImage getImage() {
		return image;
	}
	public void setImage(MyImage image) {
		this.image = image;
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
	
	public int getMove_speed() {
		return move_speed;
	}
	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
	
	public int getMaxHP() {
		return MaxHP;
	}
	public void setMaxHP(int maxHP) {
		MaxHP = maxHP;
	}
	public int getMaxMP() {
		return MaxMP;
	}
	public void setMaxMP(int maxMP) {
		MaxMP = maxMP;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getFire_interval() {
		return fire_interval;
	}
	public void setFire_interval(int fire_interval) {
		this.fire_interval = fire_interval;
	}
	public boolean isKeyUp() {
		return keyUp;
	}
	public void setKeyUp(boolean keyUp) {
		this.keyUp = keyUp;
	}
	public boolean isKeyDown() {
		return keyDown;
	}
	public void setKeyDown(boolean keyDown) {
		this.keyDown = keyDown;
	}
	public boolean isKeyLeft() {
		return keyLeft;
	}
	public void setKeyLeft(boolean keyLeft) {
		this.keyLeft = keyLeft;
	}
	public boolean isKeyRight() {
		return keyRight;
	}
	public void setKeyRight(boolean keyRight) {
		this.keyRight = keyRight;
	}
	
	//改变主机移动方向
	public void changeDirection(){
		if(keyUp && !keyDown && !keyLeft && !keyRight){  //向上
			Dx=0;
			Dy=-1;
		}
		else if(!keyUp && keyDown && !keyLeft && !keyRight){   //向下
			Dx=0;
			Dy=1;
		}
		else if(!keyUp && !keyDown && keyLeft && !keyRight){   //向左
			Dx=-1;
			Dy=0;
		}
		else if(!keyUp && !keyDown && !keyLeft && keyRight){   //向右
			Dx=1;
			Dy=0;
		}
		else if(keyUp && !keyDown && keyLeft && !keyRight){   //左上
			Dx=-1;
			Dy=-1;
		}
		else if(!keyUp && keyDown && keyLeft && !keyRight){   //左下
			Dx=-1;
			Dy=1;
		}
		else if(keyUp && !keyDown && !keyLeft && keyRight){   //右上
			Dx=1;
			Dy=-1;
		}
		else if(!keyUp && keyDown && !keyLeft && keyRight){   //右下
			Dx=1;
			Dy=1;
		}
		else{        //不移动
			Dx=0;
			Dy=0;
		}
	}
	public void allowSound(boolean a){
		allowsound=a;
	}
}
