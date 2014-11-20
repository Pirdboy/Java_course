package cqu.shy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cqu.shy.resource.ImageResource;

class Boss extends FlyingObject{
	
	private MyImage image1;
	private MyImage image2;
	private int x,y,Dx,Dy;
	private int attack;
	private int move_speed;
	private int HP;
	private int MaxHP;
	private int Score;
	private int kind;
	private boolean isDeath;
	private boolean goToDeath;
	private int count_flash=1;   //闪烁计时
	private int flash_interval=8;
	private int count_death=1;   //爆炸计时
	private int count_fire=1;
	private int fire_interval=50;   //子弹产生间隔
	private int Bullet_status=1;
	private int death_interval=8;
	private List<Bullet> Bullets = new ArrayList<Bullet>();
	private List<Bullet> BulletShouldDel = new ArrayList<Bullet>();
	public Boss(BufferedImage image1, BufferedImage image2,int HP,int attack,int move_speed,int kind) {
		super();
		this.image1 = new MyImage(image1,image1.getWidth(),image1.getHeight(),0,0);
		this.image2 = new MyImage(image2,image2.getWidth(),image2.getHeight(),0,0);
		this.HP = HP;
		this.MaxHP = HP;
		this.attack = attack;
		this.move_speed = move_speed;
		Score=0;
		this.kind = kind;
		x=y=-300;
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
		x=y=-300;
		Bullets.clear();
		BulletShouldDel.clear();
		HP = MaxHP;
		count_flash=1;
		flash_interval=50;
		count_death=1;
		death_interval=8;
		isDeath=false;
		goToDeath=false;
	}

	@Override
	public void move() {
		if(!isDeath){
			x += Dx*move_speed;
			y += Dy*move_speed;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(!isDeath){
			if(HP>MaxHP/5){
				g.drawImage(image1.getImage(), x,y , image1.getWidth(), image1.getHeight(), null);
			}
			else{  //血量过少，闪烁提示
				if(count_flash%flash_interval<=6){
					g.drawImage(image1.getImage(), x,y , image1.getWidth(), image1.getHeight(), null);
				}
				else{
					g.drawImage(image2.getImage(), x,y , image2.getWidth(), image2.getHeight(), null);
				}
				count_flash++;
			}
		}
		else{
			if(count_death<death_interval){
				g.drawImage(ImageResource.ExplosionBOSSIMG, x+image1.getWidth()/2, y, 
						ImageResource.ExplosionBOSSIMG.getWidth(),ImageResource.ExplosionBOSSIMG.getHeight(),
						null);
			}
			else if(count_death<death_interval*2){
				g.drawImage(ImageResource.ExplosionBOSS2IMG, x+image1.getWidth()/2, y, 
						ImageResource.ExplosionBOSS2IMG.getWidth(),ImageResource.ExplosionBOSS2IMG.getHeight(),
						null);
			}
			else{
				goToDeath=true;
			}
			count_death++;
		}
	}
	public void fire(){
		if(count_fire %fire_interval==0){
			//根据Boss种类添加子弹
			switch (kind) {
			case 1:
				if(Bullet_status==1){
					//第一种子弹
					for(int ff=0;ff<3;ff++){
						Bullet b = new Bullet(ImageResource.BossBullet1_1IMG, 6, attack);
						b.setDx(0);
						b.setDy(1);
						switch (ff) {
						case 0:b.setX(this.x);b.setY(this.y+image1.getHeight());break;
						case 1:b.setX(this.x+image1.getWidth()/2-b.getImage().getWidth()/2);
								b.setY(this.y+image1.getHeight());break;
						case 2:b.setX(this.x+image1.getWidth()-b.getImage().getWidth());
								b.setY(this.y+image1.getHeight());break;
						default:
							break;
						}
						Bullets.add(b);
					}
					Bullet_status=2;
				}
				else if(Bullet_status==2){
					Bullet b = new Bullet(ImageResource.BossBullet1_2IMG,6,attack);
					b.setDx(0);
					b.setDy(1);
					b.setX(this.x+image1.getWidth()/4-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getHeight());
					Bullets.add(b);
					b = new Bullet(ImageResource.BossBullet1_2IMG,6,attack);
					b.setDx(0);
					b.setDy(1);
					b.setX(this.x+image1.getWidth()*3/4-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getHeight());
					Bullets.add(b);
					Bullet_status=1;
				}
				break;
			case 2:
				if(Bullet_status==1){
					Bullet b = new Bullet(ImageResource.BossBullet2_1LeftIMG,3,attack);
					b.setDx(-1);
					b.setDy(2);
					b.setX(this.x+image1.getWidth()/2-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getHeight());
					Bullets.add(b);
					b= new Bullet(ImageResource.BossBullet2_1MiddleIMG,3,attack);
					b.setDx(0);
					b.setDy(2);
					b.setX(this.x+image1.getWidth()/2-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getHeight());
					Bullets.add(b);
					b= new Bullet(ImageResource.BossBullet2_1RightIMG,3,attack);
					b.setDx(1);
					b.setDy(2);
					b.setX(this.x+image1.getWidth()/2-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getHeight());
					Bullets.add(b);
					Bullet_status=2;
				}
				else if(Bullet_status==2){
					for(int ss=0;ss<5;ss++){
						Bullet b1 = new Bullet(ImageResource.BossBullet2_2IMG,5,attack);
						Bullet b2 = new Bullet(ImageResource.BossBullet2_2IMG,5,attack);
						b1.setDx(0);
						b1.setDy(1);
						b2.setDx(0);
						b2.setDy(1);
						b1.setX(this.x+image1.getWidth()/4-b1.getImage().getWidth()/2);
						b1.setY(this.y+image1.getHeight()+b1.getImage().getHeight()*ss+2*ss);
						b2.setX(this.x+image1.getWidth()*3/4-b2.getImage().getWidth()/2);
						b2.setY(this.y+image1.getHeight()+b2.getImage().getHeight()*ss+2*ss);
						Bullets.add(b1);
						Bullets.add(b2);
						Bullet_status=1;
					}
				}
				break;
			case 3:
				if(Bullet_status==1){
					for(int aa=-2;aa<=2;aa++){
						Bullet b = new Bullet(ImageResource.BossBullet3_1SideIMG,2,attack);
						int Dx=0,Dy=aa;
						int X=this.x + image1.getImage().getWidth()/2-b.getImage().getWidth()/2;
						int Y=this.y + image1.getHeight();
						if(aa<0){
							Dx=-1;Dy=-aa;
						}
						else if(aa==0){
							Dx=0;Dy=2;
						}
						else if(aa>0){
							Dx=1;Dy=aa;
						}
						b.setX(X);
						b.setY(Y);
						b.setDx(Dx);
						b.setDy(Dy);
						Bullets.add(b);
					}
					Bullet_status=2;
				}
				else if(Bullet_status==2){
					Bullet b = new Bullet(ImageResource.BossBullet3_2IMG,2,attack);
					b.setDx(0);
					b.setDy(0);
					b.setX(this.x+image1.getImage().getWidth()/2-b.getImage().getWidth()/2);
					b.setY(this.y+image1.getImage().getHeight()-6);
					b.isLaser=true;
					Bullets.add(b);
					Bullet_status=1;
				}
				break;
			default:
				break;
			}
		}
		count_fire++;
		for(Bullet b:Bullets){
			b.move();
		}
		//检查是否到达屏幕外
		for(Bullet b:Bullets){
			if(b.isLaser){
				if(b.laser_time>6){
					BulletShouldDel.add(b);
				}
			}
			else if(b.getX()<-b.getImage().getWidth()-1 || b.getX()>Canvas_width+1 || 
					b.getY()>Canvas_height+1 || b.getY()<-b.getImage().getHeight()-1){
				BulletShouldDel.add(b);
			}
		}
		if(!BulletShouldDel.isEmpty())
			Bullets.removeAll(BulletShouldDel);
		BulletShouldDel.clear();
	}
	public MyImage getImage() {
		return image1;
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

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
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

	public List<Bullet> getBullets() {
		return Bullets;
	}

	public void setBullets(List<Bullet> bullets) {
		Bullets = bullets;
	}

	public List<Bullet> getBulletShouldDel() {
		return BulletShouldDel;
	}

	public void setBulletShouldDel(List<Bullet> bulletShouldDel) {
		BulletShouldDel = bulletShouldDel;
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
	public int getMaxHP(){
		return MaxHP;
	}
}
