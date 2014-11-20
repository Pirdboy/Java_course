package cqu.shy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cqu.shy.resource.ImageResource;


class SkillZ extends FlyingObject{
	private int MPconsume;   //蓝量消耗
	private boolean iscold;   //是否冷却中
	private int coldtime;      //冷却时间
	private BufferedImage icon = ImageResource.SkillZ_iconIMG;
	private BufferedImage icon_cold = ImageResource.SkillZCold_iconIMG;
	private Bullet Wave_middle;
	private Bullet Wave_left;
	private Bullet Wave_right;
	private int attack;
	private int x_m,x_l,x_r,y_m,y_l,y_r;
	public SkillZ(int mp_cm,int px,int py,int attack,int CD){
		coldtime = CD;
		iscold=false;
		MPconsume = mp_cm;
		this.attack = attack;
		Wave_middle = new Bullet(ImageResource.SkillZ_middleIMG,5,this.attack);
		Wave_left = new Bullet(ImageResource.SkillZ_leftIMG,5,this.attack);
		Wave_right = new Bullet(ImageResource.SkillZ_rightIMG,5,this.attack);
		init(px,py);
		//设置发射方向
		Wave_middle.setDx(0);
		Wave_middle.setDy(2*(-1));
		Wave_left.setDx(-1);
		Wave_left.setDy(2*(-1));
		Wave_right.setDx(1);
		Wave_right.setDy(2*(-1));
	}
	public void init(int px,int py){
		//设置三道波的起始位置
		x_m = px-Wave_middle.getImage().getWidth()/2;
		y_m = py-Wave_middle.getImage().getHeight();
		x_l = x_m-10;
		y_l = y_m;
		x_r = x_m+10;
		y_r = y_m;
		Wave_middle.setX(x_m);
		Wave_middle.setY(y_m);
		Wave_left.setX(x_l);
		Wave_left.setY(y_l);
		Wave_right.setX(x_r);
		Wave_right.setY(y_r);
	}
	public void reduceMP(HeroPlane hero){
		hero.setMP(hero.getMP()-MPconsume);
	}
	
	public int getMPconsume() {
		return MPconsume;
	}
	public void setMPconsume(int mPconsume) {
		MPconsume = mPconsume;
	}
	public void setCold(boolean s){
		iscold = s;
	}
	public Boolean isCold(){
		return iscold;
	}
	public void setColdTime(int t){
		coldtime = t;
	}
	public int getColdTime(){
		return coldtime;
	}
	public void doDamage(Boss boss){
		if(Wave_left.hit(boss)){
			Wave_left.doDamage(boss);
		}
		if(Wave_right.hit(boss)){
			Wave_right.doDamage(boss);
		}
		if(Wave_middle.hit(boss)){
			Wave_middle.doDamage(boss);
		}
	}
	public void doDamage(EnemyPlane e){
		if(Wave_left.hit(e)){
			Wave_left.doDamage(e);
		}
		if(Wave_right.hit(e)){
			Wave_right.doDamage(e);
		}
		if(Wave_middle.hit(e)){
			Wave_middle.doDamage(e);
		}
	}
	public void drawIcon(Graphics g){
		if(iscold){
			g.drawImage(icon_cold, 25+icon_cold.getWidth(), 100, icon_cold.getWidth(), icon_cold.getHeight(), null);
		}
		else{
			g.drawImage(icon, 25+icon_cold.getWidth(), 100, icon.getWidth(), icon.getHeight(), null);
		}
	}
	public void drawSkill(Graphics g){
		g.drawImage(Wave_left.getImage().getImage(), Wave_left.getX(), Wave_left.getY(), Wave_left.getImage().getWidth(), Wave_left.getImage().getHeight(), null);
		g.drawImage(Wave_middle.getImage().getImage(), Wave_middle.getX(), Wave_middle.getY(), Wave_middle.getImage().getWidth(), Wave_middle.getImage().getHeight(), null);
		g.drawImage(Wave_right.getImage().getImage(), Wave_right.getX(), Wave_right.getY(), Wave_right.getImage().getWidth(), Wave_right.getImage().getHeight(), null);
	}
	public void move(){
		Wave_left.move();
		Wave_right.move();
		Wave_middle.move();
	}
	public boolean hit(Bullet b){
		return Wave_left.hit(b) || Wave_middle.hit(b) || Wave_right.hit(b);
	}
	public boolean hit(EnemyPlane other){
		return Wave_left.hit(other) || Wave_middle.hit(other) || Wave_right.hit(other);
	}
	public boolean hit(Boss other){
		return Wave_left.hit(other) || Wave_middle.hit(other) || Wave_right.hit(other);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
