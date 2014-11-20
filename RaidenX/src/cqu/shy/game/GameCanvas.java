package cqu.shy.game;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;


import cqu.shy.resource.ImageResource;
import cqu.shy.resource.SoundResource;

public class GameCanvas extends JPanel {

	private JButton startBtn;
	private JButton musicBtn;
	private JButton helpBtn;
	private JButton exitBtn;
	private JButton helpreturnBtn;
	private boolean allowSound;
	//主界面(欢迎界面)
	private boolean iswelcome=false;
	private Welcome welcome;
	private WelcomeThread wt;
	private AudioClip MainSceneMusic;
	private AudioClip HelpSceneMusic;
	//帮助界面
	private boolean isHelpScene=false;
	private HelpScene helpScene;
	//游戏中的界面
		//游戏中过程判断
	private boolean isGameAction=false;
	private boolean isHeroDeath=false;
	private boolean isBossWarning=false;
	private boolean isBossCome=false;
	private boolean isBossDeath=false;
	private boolean isGameFinish=false;
		//关卡设置
	private int LEVEL=1;
		//游戏中的元素:主机、敌人、子弹、道具
	private GameScene gameScene;
	private GameActionThread gat;
	private HeroPlane Hero;
	private SkillZ skillz = new SkillZ(250,0,0,70,2*1000);  //技能
	private boolean isSkillZ;
	private int Key_Z;
	private List<EnemyPlane> Enemies = new ArrayList<EnemyPlane>();
	private List<EnemyPlane> EnemyShouldDel = new ArrayList<EnemyPlane>();
	private List<Bullet> EnemyBullets = new ArrayList<Bullet>();
	private List<Bullet> EnemyBulletsShouldDel = new ArrayList<Bullet>();
	private List<Item> Items = new ArrayList<Item>();
	private List<Item> ItemShouldDel = new ArrayList<Item>();
		//BOSS相关
	private Boss[] Bosses = new Boss[3];
	private int[] BossComeTime = {15*1000,20*1000,25*1000}; 
	//整个游戏所需线程
	//欢迎界面线程
	private class WelcomeThread extends Thread{
		private int rate=1;
		public void run(){
			while(iswelcome){
				if(rate ==2*1000/20){
					startBtn.setVisible(true);
					helpBtn.setVisible(true);
					musicBtn.setVisible(true);
					exitBtn.setVisible(true);
				}
				rate++;
				welcome.move();
				repaint();
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	//游戏过程中的线程
	private class GameActionThread extends Thread{
		private Random random = new Random();
		private int ThreadSleepTime=18;
		private int addEnemyinterval= Math.abs(random.nextInt())%(88-(LEVEL-1)*4)+(30-(LEVEL-1)*4);
		private int rate=1;
		private int rate_skillz=1;
		private int addEnemysmallBulletTime=80-(LEVEL-1)*10;   //小敌机子弹产生间隔
		private int addEnemymiddleBulletTime=80-(LEVEL-1)*10;    //中敌机子弹产生间隔
		private int addEnemylargeBulletTime=80-(LEVEL-1)*10;    //大敌机子弹产生间隔
		private boolean BossShouldLeft=true;    //BOSS的出现路线,先下落出现，然后左右交替(先左)
		private int addItemTime=(random.nextInt(3)+6)*1000/18;
		private int addHeroMPTime = 6;      //回蓝的间隔
		public void run(){
			while(isGameAction){
				gameScene.move();
				Hero.move();
				
				if(rate_skillz%(skillz.getColdTime()/18)==0 ){
					if(Hero.getMP()<skillz.getMPconsume())
					{
						//蓝不够
						skillz.setCold(true);
					}
					else{
						skillz.setCold(false);
					}
					isSkillZ=false;
					rate_skillz=1;
				}
				//回蓝
				if(rate%addHeroMPTime==0 && Hero.getMP()<Hero.getMaxMP()){
					Hero.setMP(Hero.getMP()+1);
					gameScene.setMPbarWidth(Hero.getMP()*gameScene.getMPMaxlength()/gameScene.getMPMax());
				}
				if(skillz.isCold()){
					rate_skillz++;
				}
				//System.out.println("线程:当前关卡:"+LEVEL);
				//技能的移动
				SkillMove();
				if(!Hero.isDeath())
					Hero.fire();
				if(rate%addEnemyinterval==0 && !isBossCome &&!isBossWarning &&!Hero.isDeath()){
					addNewEnemy();
				}
				if(rate%addEnemysmallBulletTime==0 && !isBossCome && !isBossWarning &&!Hero.isDeath()){
					addEnemysmallBullets();
				}
				if(rate%addEnemymiddleBulletTime==0 && !isBossCome && !isBossWarning &&!Hero.isDeath()){
					addEnemymiddleBullets();	
				}
				if(rate%addEnemylargeBulletTime==0 && !isBossCome && !isBossWarning &&!Hero.isDeath()){
					addEnemylargeBullets();
				}
				if(rate%addItemTime==0 &&!Hero.isDeath()){
					addItem();
					addItemTime=(random.nextInt(3)+6)*1000/18;
				}
				if(rate%((BossComeTime[LEVEL-1]-1050*2)/ThreadSleepTime)==0 && !isBossCome && !isBossWarning){
					//BOSS warning
					//设定BOSS起始位置
					Bosses[LEVEL-1].setX(GameCanvas.this.getWidth()/2-Bosses[LEVEL-1].getImage().getWidth()/2);
					Bosses[LEVEL-1].setY(-Bosses[LEVEL-1].getImage().getHeight());
					isBossWarning=true;
					isBossCome=false;
					gameScene.setBossWarning(isBossWarning);
					gameScene.setBossCome(isBossCome);
					if(allowSound){
						SoundResource.BossWarningSound.play();
					}
				}
				if(rate%(BossComeTime[LEVEL-1]/ThreadSleepTime)==0&& !isBossCome){
					//BOSS出现
					isBossWarning=false;
					isBossCome = true;
					gameScene.setBossWarning(isBossWarning);
					gameScene.setBossCome(isBossCome);
					gameScene.setBOSSHPMax(Bosses[LEVEL-1].getMaxHP());
					
					if(allowSound){
						switch (LEVEL) {
						case 1:
							SoundResource.Bossbattle1Music.loop();
							break;
						case 2:
							SoundResource.Bossbattle2Music.loop();
							break;
						case 3:
							SoundResource.Bossbattle3Music.loop();
							break;
						default:
							break;
						}
					}
				}
				if(!Hero.isDeath() && isBossCome && !isBossDeath){
					BossMove();
					Bosses[LEVEL-1].fire();
					CollisionBetweenBossAndHero();
				}
				if(!Hero.isDeath() && isBossDeath){
					if(Bosses[LEVEL-1].isGoToDeath()){
						gameScene.setBossCome(false);
						if(LEVEL==3){
							//游戏结束
							isGameAction=false;
							isGameFinish=true;
							gameScene.setGameFinish(isGameFinish);
						}
						else{
							isBossCome=false;
							isBossDeath=false;
							LEVEL++;
							rate=1;
							gameScene.setStage(LEVEL);
						}
						addEnemyinterval= Math.abs(random.nextInt())%(88-(LEVEL-1)*4)+(30-(LEVEL-1)*4);
						addEnemysmallBulletTime=80-(LEVEL-1)*10;   //小敌机子弹产生间隔
						addEnemymiddleBulletTime=80-(LEVEL-1)*10;    //中敌机子弹产生间隔
						addEnemylargeBulletTime=80-(LEVEL-1)*10;    //大敌机子弹产生间隔
					}
				}
				if(!Hero.isDeath())
				{
					CollisionBetweenHeroAndEnemy();
					CollisionBetweenItemAndHero();		
				}
				for(EnemyPlane ep:Enemies){
					ep.move();	
				}
				if(Hero.isGoToDeath()){
					isGameAction=false;
				}
				FindgoToDeathAndClean();
				ItemMove();
				EnemyOutOfScreen();
				EnemyBulletsOufOfScreen();
				EnemyBulletsMove();
				repaint();
				rate++;
				try {
					Thread.sleep(ThreadSleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isGameFinish){
				GameFinishThread gft = new GameFinishThread();
				gft.start();
			}
			else if(Hero.isGoToDeath()){
				if(allowSound){
					SoundResource.HeroDeathSound.play();
				}
				repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stopAllbgm();
				Enemies.clear();
				EnemyShouldDel.clear();
				EnemyBullets.clear();
				EnemyBulletsShouldDel.clear();
				Items.clear();
				ItemShouldDel.clear();
				Bosses[0].init();
				Bosses[1].init();
				Bosses[2].init();
				Hero.init();
				isGameAction=false;
				isHeroDeath=false;
				isBossWarning=false;
				isBossCome=false;
				isBossDeath=false;
				isGameFinish=false;
				iswelcome=false;
				isHelpScene=false;
				LEVEL=1;
				Welcome_game();
			}
		}
		private void addNewEnemy(){
			int num =Math.abs(random.nextInt()%LEVEL)+1;
			for(int n=0;n<num;n++){
				int P = Math.abs(random.nextInt())%1000;
				int move_speed = Math.abs(random.nextInt())%LEVEL+2;
				EnemyPlane e =null;
				if(P>=0 && P<500-(LEVEL-1)*10){
					//产生小敌机
					e = new EnemyPlane(ImageResource.EnemysmallIMG,0,0,100+(LEVEL-1)*50,40,move_speed,1);
					e.setScore(100*LEVEL);
				}
				else if(P>=500-(LEVEL-1)*10 && P<800-(LEVEL-1)*10){
					//产生中敌机
					e = new EnemyPlane(ImageResource.EnemymiddleIMG,0,0,150+(LEVEL-1)*50,80,move_speed,2);
					e.setScore(200*LEVEL);
				}
				else if(P>=800-(LEVEL-1)*10 && P<1000){
					//产生大敌机
					e = new EnemyPlane(ImageResource.EnemylargeIMG,0,0,200+(LEVEL-1)*50,120,move_speed,3);
					e.setScore(300*LEVEL);
				}
				int pos_x = (Math.abs(random.nextInt())%150)*3;
				int pos_y = -e.getImage().getHeight()+6*random.nextInt(5);
				e.setX(pos_x);
				e.setY(pos_y);
				e.setDx(0);
				e.setDy(e.getMove_speed());
				int P_fire = Math.abs(random.nextInt())%1000;
				if(P_fire>=0 && P_fire<333)    //大约三分之一的概率产生一个不发子弹的飞机
					e.setAllowfire(false);
				Enemies.add(e);
			}
		}
		private void addEnemysmallBullets(){
			for(EnemyPlane ep: Enemies){
				if(ep.isAllowfire() && ep.getKind()==1)
				{
					Bullet b = ep.basicbullet();
					Bullet b1 = new Bullet(ImageResource.EnemysmallbulletIMG,b.getMove_speed(),b.getAttack());
					b1.setX(b.getX());
					b1.setY(b.getY());
					b1.setDx(0);
					b1.setDy(1);
					EnemyBullets.add(b1);
				}
			}
		}
		private void addEnemymiddleBullets(){
			for(EnemyPlane ep: Enemies){
				if(ep.isAllowfire() && ep.getKind()==2)
				{
					Bullet b = ep.basicbullet();
					Bullet b2 = new Bullet(ImageResource.EnemymiddlebulletIMG,b.getMove_speed()-1,b.getAttack());
					b2.setX(b.getX()-4);
					b2.setY(b.getY());
					b2.setDx(-1);
					b2.setDy(2);
					EnemyBullets.add(b2);
					b2 = new Bullet(ImageResource.EnemymiddlebulletIMG,b.getMove_speed()-1,b.getAttack());
					b2.setX(b.getX()+4);
					b2.setY(b.getY());
					b2.setDx(1);
					b2.setDy(2);
					EnemyBullets.add(b2);
				}
			}
		}
		private void addEnemylargeBullets(){
			for(EnemyPlane ep: Enemies){
				if(ep.isAllowfire() && ep.getKind()==3)
				{
					Bullet b = ep.basicbullet();
					for(int vv=0;vv<3;vv++){
						Bullet b3 = new Bullet(ImageResource.EnemylargebulletIMG,b.getMove_speed(),b.getAttack());
						b3.setX(b.getX());
						b3.setY(b.getY()+vv*10);
						b3.setDx(0);
						b3.setDy(1);
						EnemyBullets.add(b3);
					}
				}
			}
		}
		private void addItem(){
			int P_item = random.nextInt(10000);
			int move_speed= 2;
			Item item=null;
			if(P_item>=0 && P_item<1600+50*(LEVEL-1)){
				  //产生加血
				item = new Item(ImageResource.item4_1IMG, ImageResource.item4_2IMG, 4, move_speed);
			}
			else if(P_item>=1400+50*(LEVEL-1) && P_item<2400+50*(LEVEL-1)){
				//产生加蓝
				item = new Item(ImageResource.item3_1IMG, ImageResource.item3_2IMG, 3, move_speed);
			}
			else if(P_item>=2400+50*(LEVEL-1) && P_item<5000+50*(LEVEL-1)){
				//子弹增强
				item = new Item(ImageResource.item2_1IMG, ImageResource.item2_2IMG, 2, move_speed);
			}
			else if(P_item>=5000+50*(LEVEL-1) && P_item<10000){
				//分数水晶
				item = new Item(ImageResource.item1_1IMG, ImageResource.item1_2IMG, 1, move_speed);
			}
			//设定道具的初始位置
			int X = Math.abs(random.nextInt())%410 + 30;
			int Y = -item.getImage().getHeight()-4;
			item.setX(X);
			item.setY(Y);
			item.setDx(0);
			item.setDy(1);
			Items.add(item);
		}
		private void BossMove(){
			if(Bosses[LEVEL-1].getY()<43){  //向下走
				Bosses[LEVEL-1].setDx(0);
				Bosses[LEVEL-1].setDy(2);
				Bosses[LEVEL-1].move();
			}
			else {
				//左右循环
				if(BossShouldLeft){   //向左走
					Bosses[LEVEL-1].setDx(-1);
					Bosses[LEVEL-1].setDy(0);
					Bosses[LEVEL-1].move();
					if(Bosses[LEVEL-1].getX()<1){    //到达最左了
						BossShouldLeft=false;
					}
				}
				else{        //向右走
					Bosses[LEVEL-1].setDx(1);
					Bosses[LEVEL-1].setDy(0);
					Bosses[LEVEL-1].move();
					if(Bosses[LEVEL-1].getX()>GameCanvas.this.getWidth()-Bosses[LEVEL-1].getImage().getWidth()-1){    //到达最右了
						BossShouldLeft=true;
					}
				}
			}
		}
		private void ItemMove(){
			for(Item it:Items){
				it.move();
			}
		}
		private void EnemyBulletsMove(){
			for(Bullet b:EnemyBullets){
				b.move();
			}
		}
		private void SkillMove(){
			if(isSkillZ){
				skillz.move();
			}
		}
		private void EnemyBulletsOufOfScreen(){
			for(Bullet e:EnemyBullets){
				if(e.getX()<-e.getImage().getWidth()-1 || e.getX()>GameCanvas.this.getWidth()+1 ||
						e.getY()>GameCanvas.this.getHeight()+1 || e.getY()<-e.getImage().getHeight()-1){
					EnemyBulletsShouldDel.add(e);
				}
			}
			EnemyBullets.removeAll(EnemyBulletsShouldDel);
			EnemyBulletsShouldDel.clear();
		}
		private void EnemyOutOfScreen(){
			//检查敌机是否到达屏幕外
			for(EnemyPlane e:Enemies){
				if(e.getX()<-e.getImage().getWidth()-1 || e.getX()>GameCanvas.this.getWidth()+1 ||
						e.getY()>GameCanvas.this.getHeight()+1 || e.getY()<-e.getImage().getHeight()-1){
					EnemyShouldDel.add(e);
				}
			}
			if(!EnemyShouldDel.isEmpty())
				Enemies.removeAll(EnemyShouldDel);
			EnemyShouldDel.clear();
		}
		private void CollisionBetweenHeroAndEnemy(){
			//主机与敌机
			for(EnemyPlane ep:Enemies){
				if(ep.hit(Hero) && !ep.isDeath() && !Hero.isDeath()){
					ep.doDamage(Hero);
					Hero.setFire_Level(Hero.getFire_Level()==1?1:Hero.getFire_Level()-1);
					//播放主机受伤音效
					if(allowSound){
						SoundResource.HeroGetHurtSound.play();
					}
					if(Hero.getHP()<=0)
						gameScene.setHPbarWidth(0);
					else
						gameScene.setHPbarWidth(Hero.getHP()*gameScene.getHPMaxlength()/gameScene.getHPMax());
					if(Hero.getHP()<=0){  //主机死亡
						Hero.setDeath(true);
						isHeroDeath=true;
					}
					Hero.setScore(Hero.getScore()+ep.getScore());
					gameScene.setScoreText(Hero.getScore());
					ep.setDeath(true);
				}
			}
			//主机与敌机子弹
			for(Bullet b:EnemyBullets)
			{
				if(b.hit(Hero) && !b.isDeath()&& !Hero.isDeath()){
					b.doDamage(Hero);
					Hero.setFire_Level(Hero.getFire_Level()==1?1:Hero.getFire_Level()-1);
					if(allowSound){
						SoundResource.HeroGetHurtSound.play();
					}
					if(Hero.getHP()<=0)
						gameScene.setHPbarWidth(0);
					else
						gameScene.setHPbarWidth(Hero.getHP()*gameScene.getHPMaxlength()/gameScene.getHPMax());
					if(Hero.getHP()<=0){  //主机死亡
						Hero.setDeath(true);
						isHeroDeath=true;
					}
					b.setDeath(true);
				}
			}
			//主机用子弹打中敌机
			for(Bullet b:Hero.getBullets()){
				for(EnemyPlane ep : Enemies)
				{
					if(b.hit(ep) && !b.isDeath() && !ep.isDeath() &&!Hero.isDeath()){
						Hero.doDamage(ep);
						if(ep.getHP()<=0){
							ep.setDeath(true);
							Hero.setScore(Hero.getScore()+ep.getScore());
							gameScene.setScoreText(Hero.getScore());
							if(allowSound){
								SoundResource.EnemyDeathSound.play();
							}
						}
						b.setDeath(true);
					}
				}
			}
			if(isSkillZ){
				for(Bullet b:EnemyBullets){
					if(skillz.hit(b)){
						EnemyBulletsShouldDel.add(b);
					}
				}
				for(EnemyPlane e:Enemies){
					if(skillz.hit(e)){
						skillz.doDamage(e);
						if(e.getHP()<=0){
							//主机加分
							Hero.setScore(Hero.getScore()+e.getScore());
							gameScene.setScoreText(Hero.getScore());
							EnemyShouldDel.add(e);
							if(allowSound){
								SoundResource.EnemyDeathSound.play();
							}
						}
					}
				}

			}
		}
		private void CollisionBetweenBossAndHero(){
			//主机与BOSS本体碰撞,直接死亡
			if(Hero.hit(Bosses[LEVEL-1])){
				Hero.setHP(0);
				gameScene.setHPbarWidth(0);
				Hero.setDeath(true);
				isHeroDeath=true;
			}
			//主机与BOSS子弹碰撞,掉血
			for(Bullet b : Bosses[LEVEL-1].getBullets()){
				if(b.hit(Hero) && !b.isDeath()&& !Hero.isDeath()){
					b.doDamage(Hero);
					Hero.setFire_Level(Hero.getFire_Level()==1?1:Hero.getFire_Level()-1);
					//播放主机受伤音效
					if(allowSound){
						SoundResource.HeroGetHurtSound.play();
					}
					if(Hero.getHP()<=0)
						gameScene.setHPbarWidth(0);
					else
						gameScene.setHPbarWidth(Hero.getHP()*gameScene.getHPMaxlength()/gameScene.getHPMax());
					if(Hero.getHP()<=0){  //主机死亡
						Hero.setDeath(true);
						isHeroDeath=true;
					}
					b.setDeath(true);
				}
			}
			//主机子弹击中BOSS
			for(Bullet b:Hero.getBullets()){
				if(b.hit(Bosses[LEVEL-1]) && !b.isDeath() 
						&& !Hero.isDeath() && !Bosses[LEVEL-1].isDeath()){
					Hero.doDamage(Bosses[LEVEL-1]);
					Hero.getBulletShouldDel().add(b);
					if(Bosses[LEVEL-1].getHP()<=0){
						// BOSS已死
						gameScene.setBOSSHPbarWidth(0);
						Bosses[LEVEL-1].setDeath(true);
						Hero.setScore(Hero.getScore()+Bosses[LEVEL-1].getScore());
						gameScene.setScoreText(Hero.getScore());
						isBossDeath=true;
						Bosses[LEVEL-1].getBullets().clear();
						Bosses[LEVEL-1].getBulletShouldDel().clear();
						if(allowSound){
							SoundResource.BossDeathMusic.play();
						}
						SoundResource.Bossbattle1Music.stop();
						SoundResource.Bossbattle2Music.stop();
						SoundResource.Bossbattle3Music.stop();
					}
					else{
						gameScene.setBOSSHPbarWidth(
								Bosses[LEVEL-1].getHP()*gameScene.getBOSSHPMaxlength()/gameScene.getBOSSHPMax());
					}
					b.setDeath(true);
				}
			}
			if(isSkillZ){
				//技能打中BOSS
				if(isBossCome){
					for(Bullet b:Bosses[LEVEL-1].getBullets()){
						if(skillz.hit(b)){
							Bosses[LEVEL-1].getBulletShouldDel().add(b);
						}
					}
					Bosses[LEVEL-1].getBullets().removeAll(Bosses[LEVEL-1].getBulletShouldDel());
					Bosses[LEVEL-1].getBulletShouldDel().clear();
					if(skillz.hit(Bosses[LEVEL-1]) && !Bosses[LEVEL-1].isDeath()){
						skillz.doDamage(Bosses[LEVEL-1]);
						//调试
						if(Bosses[LEVEL-1].getHP()<=0){
							//BOSS死亡
							//主机加分
							gameScene.setBOSSHPbarWidth(0);
							System.out.println("Boss的分数:"+Bosses[LEVEL-1].getScore());
							Bosses[LEVEL-1].setDeath(true);
							Hero.setScore(Hero.getScore()+Bosses[LEVEL-1].getScore());
							gameScene.setScoreText(Hero.getScore());
							isBossDeath=true;
							Bosses[LEVEL-1].getBullets().clear();
							Bosses[LEVEL-1].getBulletShouldDel().clear();
							if(allowSound){
								SoundResource.BossDeathMusic.play();
							}
							SoundResource.Bossbattle1Music.stop();
							SoundResource.Bossbattle2Music.stop();
							SoundResource.Bossbattle3Music.stop();
						}
						else{
							gameScene.setBOSSHPbarWidth(
									Bosses[LEVEL-1].getHP()*gameScene.getBOSSHPMaxlength()/gameScene.getBOSSHPMax());
						}
					}
				}
			}
		}
		private void CollisionBetweenItemAndHero(){
			for(Item it:Items){
				if(it.hit(Hero) && !Hero.isDeath()){
					ItemShouldDel.add(it);
					int kind = it.getKind();
					switch (kind) {
					case 1:   //加分数
						if(allowSound)
							SoundResource.GetcrystalSound.play();
						Hero.setScore(Hero.getScore()+600);
						gameScene.setScoreText(Hero.getScore());
						break;
					case 2:   //加子弹
						if(allowSound)
							SoundResource.GetBulletSound.play();
						Hero.setFire_Level(Hero.getFire_Level()+1);
						if(Hero.getFire_Level()>=5){
							if(allowSound){
								SoundResource.HeroPowerFulSound.play();
							}
						}
						break;
					case 3:    //加血
						if(allowSound)
							SoundResource.GetHPSound.play();
						Hero.setHP(Hero.getHP()+200);
						if(Hero.getHP()>=Hero.getMaxHP())
							Hero.setHP(Hero.getMaxHP());
						gameScene.setHPbarWidth(Hero.getHP()*gameScene.getHPMaxlength()/gameScene.getHPMax());
						break;
					case 4:   //加蓝
						if(allowSound)
							SoundResource.GetMPSound.play();
						Hero.setMP(Hero.getMP()+200);
						if(Hero.getMP()>=Hero.getMaxMP())
							Hero.setMP(Hero.getMaxMP());
						gameScene.setMPbarWidth(Hero.getMP()*gameScene.getMPMaxlength()/gameScene.getMPMax());
						break;
					default:
						break;
					}
				}
			}
			Items.removeAll(ItemShouldDel);
			ItemShouldDel.clear();
		}
		
		private void FindgoToDeathAndClean(){
			// 将那些死亡的物体清除
			//敌机
			for(EnemyPlane ep:Enemies){
				if(ep.isGoToDeath()){
					EnemyShouldDel.add(ep);
				}
			}
			Enemies.removeAll(EnemyShouldDel);
			EnemyShouldDel.clear();
			//敌机子弹
			for(Bullet b:EnemyBullets){
				if(b.isGoToDeath()){
					EnemyBulletsShouldDel.add(b);
				}
			}
			EnemyBullets.removeAll(EnemyBulletsShouldDel);
			EnemyBulletsShouldDel.clear();
			//主机子弹
			for(Bullet b:Hero.getBullets()){
				if(b.isGoToDeath()){
					Hero.getBulletShouldDel().add(b);
				}
			}
			Hero.getBullets().removeAll(Hero.getBulletShouldDel());
			Hero.getBulletShouldDel().clear();
			//BOSS子弹
			if(isBossCome){
				for(Bullet b:Bosses[LEVEL-1].getBullets()){
					if(b.isGoToDeath()){
						Bosses[0].getBulletShouldDel().add(b);
					}
				}
				Bosses[LEVEL-1].getBullets().removeAll(Bosses[LEVEL-1].getBulletShouldDel());
				Bosses[LEVEL-1].getBulletShouldDel().clear();
			}
		}
	}
	//游戏通关的进程
	private class GameFinishThread extends Thread{
		public void run(){
			gameScene.GameFinish.setX(GameCanvas.this.getWidth()/2-gameScene.GameFinish.getWidth()/2);
			gameScene.GameFinish.setY(-gameScene.GameFinish.getHeight());
			if(allowSound){
				SoundResource.GameFinishMusic.loop();
			}
			while(isGameFinish){
				gameScene.move();
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public GameCanvas() {
		allowSound=true;
		MainSceneMusic = SoundResource.WelcomeMusic;
		HelpSceneMusic = SoundResource.HelpSceneMusic;
		//欢迎界面
		welcome = new Welcome();
		//帮助界面
		helpScene = new HelpScene();
		//游戏过程界面
		gameScene = new GameScene();
		Hero = new HeroPlane();
		Bosses[0] = new Boss(ImageResource.Boss1IMG, ImageResource.Boss1_flashIMG, 15000, 200, 2, 1);
		Bosses[1] = new Boss(ImageResource.Boss2IMG, ImageResource.Boss2_flashIMG, 20000, 280, 3, 2);
		Bosses[2] = new Boss(ImageResource.Boss3IMG, ImageResource.Boss3_flashIMG, 30000, 350, 3, 3);
		Bosses[0].setScore(7000);
		Bosses[1].setScore(15000);
		Bosses[2].setScore(24000);
		//窗体按钮初始化
		startBtn = new JButton("开始游戏");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_game();
			}
		});
		startBtn.setForeground(Color.MAGENTA);
		startBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		startBtn.setContentAreaFilled(false);	
		startBtn.setVisible(false);
		musicBtn = new JButton("音乐  on");
		musicBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allowSound){
					musicBtn.setText("音乐  off");
					allowSound = false;
					if(iswelcome){
						MainSceneMusic.stop();
					}
					if(isBossCome){
						SoundResource.Bossbattle1Music.stop();
						SoundResource.Bossbattle2Music.stop();
						SoundResource.Bossbattle3Music.stop();
					}
				}
				else{
					musicBtn.setText("音乐  on");
					if(iswelcome){
						MainSceneMusic.loop();
					}
					if(isBossCome){
						//...
					}
				}
			}
		});
		musicBtn.setForeground(Color.PINK);
		musicBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		musicBtn.setContentAreaFilled(false);
		musicBtn.setVisible(false);
		helpBtn = new JButton("帮助");
		helpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help_game();
			}
		});
		helpBtn.setForeground(Color.CYAN);
		helpBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		helpBtn.setContentAreaFilled(false);
		helpBtn.setVisible(false);
		exitBtn = new JButton("退出游戏");
		exitBtn.setForeground(new Color(51, 255, 0));
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		exitBtn.setContentAreaFilled(false);
		exitBtn.setVisible(false);
		
		helpreturnBtn = new JButton("返回");
		helpreturnBtn.setContentAreaFilled(false);
		helpreturnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isHelpScene=false;
				iswelcome=true;
				HelpSceneMusic.stop();
				Welcome_game();
			}
		});
		helpreturnBtn.setForeground(Color.YELLOW);
		helpreturnBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		helpreturnBtn.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(202)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(exitBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(helpBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(musicBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(startBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
					.addContainerGap(202, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(385, Short.MAX_VALUE)
					.addComponent(helpreturnBtn)
					.addGap(22))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addComponent(helpreturnBtn)
					.addGap(142)
					.addComponent(startBtn, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addComponent(musicBtn, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(66)
					.addComponent(helpBtn, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(73)
					.addComponent(exitBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(136, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int k = e.getKeyCode();
				switch(k)
				{
				case KeyEvent.VK_UP:
					Hero.setKeyUp(false);
					break;
				case KeyEvent.VK_DOWN:
					Hero.setKeyDown(false);
					break;
				case KeyEvent.VK_LEFT:
					Hero.setKeyLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					Hero.setKeyRight(false);
					break;
				case KeyEvent.VK_Z:
					Key_Z = 0;
					break;
				}
				Hero.changeDirection();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				switch(k)
				{
				case KeyEvent.VK_UP:
					Hero.setKeyUp(true);
					break;
				case KeyEvent.VK_DOWN:
					Hero.setKeyDown(true);
					break;
				case KeyEvent.VK_LEFT:
					Hero.setKeyLeft(true);
					break;
				case KeyEvent.VK_RIGHT:
					Hero.setKeyRight(true);
					break;
				case KeyEvent.VK_Z:  //技能Z
					if(!skillz.isCold() && Hero.getMP()>=skillz.getMPconsume()){
						if(Key_Z==0){
							skillz.reduceMP(Hero);
							if(allowSound)
								SoundResource.SkillZSound.play();
							if(Hero.getMP()<=0)
								gameScene.setMPbarWidth(0);
							else
								gameScene.setMPbarWidth(Hero.getMP()*gameScene.getMPMaxlength()/gameScene.getMPMax());
							Key_Z++;
							isSkillZ=true;
							skillz.init(Hero.getX()+Hero.getImage().getWidth()/2, Hero.getY());
							skillz.setCold(true);
						}
					}
					break;
				}
				Hero.changeDirection();
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(iswelcome){
			welcome.draw(g);
		}
		else if(isHelpScene){
			helpScene.draw(g);
		}
		else if(isGameAction){
			gameScene.draw(g);
			skillz.drawIcon(g);
			Hero.draw(g);
			if(!Hero.isDeath()){
				for(Bullet hb:Hero.getBullets()){
					hb.draw(g);
				}
			}
			for(EnemyPlane e:Enemies){
				e.draw(g);
			}
			for(Bullet b:EnemyBullets){
				b.draw(g);
			}
			for(Item it:Items){
				it.draw(g);
			}
			if(gameScene.isBossCome()){
				Bosses[LEVEL-1].draw(g);
				for(Bullet bb:Bosses[LEVEL-1].getBullets()){
					bb.draw(g);
				}
			}
		}
		else if(isGameFinish){
			gameScene.draw(g);
		}
		else if(isHeroDeath){
			gameScene.draw(g);
		}
		if(isSkillZ){
			skillz.drawSkill(g);
		}
	}
	
	public void Welcome_game(){
		//欢迎界面
		startBtn.setVisible(false);
		musicBtn.setVisible(false);
		helpBtn.setVisible(false);
		exitBtn.setVisible(false);
		helpreturnBtn.setVisible(false);
		welcome.init();
		isGameAction=false;
		iswelcome = true;
		isHelpScene=false;
		wt = new WelcomeThread();
		wt.start();
		if(allowSound)
			MainSceneMusic.loop();
	}
	public void Help_game(){
		startBtn.setVisible(false);
		musicBtn.setVisible(false);
		helpBtn.setVisible(false);
		exitBtn.setVisible(false);
		helpreturnBtn.setVisible(true);
		isGameAction=false;
		isHelpScene=true;
		iswelcome=false;
		helpScene.init();
		MainSceneMusic.stop();
		if(allowSound){
			HelpSceneMusic.loop();
		}
		repaint();
	}
	public void start_game(){
		gameScene.setCanvasWidthAndHeight(this.getWidth(), this.getHeight());
		startBtn.setVisible(false);
		musicBtn.setVisible(false);
		helpBtn.setVisible(false);
		exitBtn.setVisible(false);
		helpreturnBtn.setVisible(false);
		isGameAction=true;
		iswelcome = false;
		isHelpScene=false;
		MainSceneMusic.stop();
		gameScene.init();
		LEVEL=1;
		Hero.init();
		Hero.allowSound(allowSound);
		gat = new GameActionThread();
		gat.start();
		requestFocusInWindow();
	}
	private void stopAllbgm(){
		SoundResource.Bossbattle1Music.stop();
		SoundResource.Bossbattle2Music.stop();
		SoundResource.Bossbattle3Music.stop();
		SoundResource.GameFinishMusic.stop();
		SoundResource.WelcomeMusic.stop();
	}
}
