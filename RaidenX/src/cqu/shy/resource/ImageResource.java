package cqu.shy.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImageResource {
	//欢迎界面
	public static BufferedImage WelcomePlane1IMG;
	public static BufferedImage WelcomePlane2IMG;
	public static BufferedImage BackgroundIMG;
	//帮助界面
	public static BufferedImage HelpIMG;
	//主机相关图片
	public static BufferedImage HeroplaneIMG;
	public static BufferedImage HerodeathIMG;
	public static BufferedImage Herobullet1IMG;
	public static BufferedImage Herobullet2IMG;
	public static BufferedImage Herobullet3IMG;
	public static BufferedImage Herobullet4IMG;
	public static BufferedImage HerobulletPowerIMG;
	public static BufferedImage SkillZ_iconIMG;
	public static BufferedImage SkillZCold_iconIMG;
	public static BufferedImage SkillZ_leftIMG;
	public static BufferedImage SkillZ_middleIMG;
	public static BufferedImage SkillZ_rightIMG;
	//敌机相关图片
	public static BufferedImage EnemysmallIMG;
	public static BufferedImage EnemymiddleIMG;
	public static BufferedImage EnemylargeIMG;
	public static BufferedImage EnemysmallbulletIMG;
	public static BufferedImage EnemymiddlebulletIMG;
	public static BufferedImage EnemylargebulletIMG;
	public static BufferedImage ExplosionsmallIMG;
	public static BufferedImage ExplosionmiddleIMG;
	public static BufferedImage ExplosionlargeIMG;
	public static BufferedImage ExplosionBulletIMG;
	//道具相关
	public static BufferedImage item1_1IMG;
	public static BufferedImage item1_2IMG;
	public static BufferedImage item2_1IMG;
	public static BufferedImage item2_2IMG;
	public static BufferedImage item3_1IMG;
	public static BufferedImage item3_2IMG;
	public static BufferedImage item4_1IMG;
	public static BufferedImage item4_2IMG;
	//BOSS相关
	public static BufferedImage Boss1IMG;
	public static BufferedImage Boss1_flashIMG;
	public static BufferedImage Boss2IMG;
	public static BufferedImage Boss2_flashIMG;
	public static BufferedImage Boss3IMG;
	public static BufferedImage Boss3_flashIMG;
	public static BufferedImage ExplosionBOSSIMG;
	public static BufferedImage ExplosionBOSS2IMG;
	public static BufferedImage BossWarningIMG;
	public static BufferedImage BossBullet1_1IMG;
	public static BufferedImage BossBullet1_2IMG;
	public static BufferedImage BossBullet2_1LeftIMG;
	public static BufferedImage BossBullet2_1MiddleIMG;
	public static BufferedImage BossBullet2_1RightIMG;
	public static BufferedImage BossBullet2_2IMG;
	public static BufferedImage BossBullet3_1IMG;
	public static BufferedImage BossBullet3_1SideIMG;
	public static BufferedImage BossBullet3_2IMG;

	//游戏通关
	public static BufferedImage GameFinishIMG;
	static{
		try {
			WelcomePlane1IMG= ImageIO.read(ImageResource.class.getResourceAsStream("/image/Welcome/WelcomePlane1.png"));
			WelcomePlane2IMG= ImageIO.read(ImageResource.class.getResourceAsStream("/image/Welcome/WelcomePlane2.png"));
			BackgroundIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Welcome/Background.png"));
			HelpIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/HelpScene/Help.png"));
			
			HeroplaneIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Hero/hero_plane.png"));
			HerodeathIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Hero/hero_death.png"));
			Herobullet1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_hero1.png"));
			Herobullet2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_hero2.png"));
			Herobullet3IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_hero3.png"));
			Herobullet4IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_hero4.png"));
			HerobulletPowerIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_HeroPower.png"));
			SkillZ_iconIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Skill/skillZ_icon.png"));
			SkillZCold_iconIMG= ImageIO.read(ImageResource.class.getResourceAsStream("/image/Skill/skillZcold_icon.png"));
			SkillZ_leftIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Skill/skill_Z-left.png"));
			SkillZ_middleIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Skill/skill_Z.png"));
			SkillZ_rightIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Skill/skill_Z-right.png"));
			
			EnemysmallIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Enemy/enemy_small.png"));
			EnemymiddleIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Enemy/enemy_middle.png"));
			EnemylargeIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Enemy/enemy_large.png"));
			EnemysmallbulletIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_small.png"));
			EnemymiddlebulletIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_middle.png"));
			EnemylargebulletIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_large.png"));
			ExplosionsmallIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/Explosion_small.png"));
			ExplosionmiddleIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/Explosion_middle.png"));
			ExplosionlargeIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/Explosion_large.png"));
			ExplosionBulletIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/ExplosionBullet.png"));
			
			item1_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item1_1.png"));
			item1_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item1_2.png"));
			item2_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item2_1.png"));
			item2_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item2_2.png"));
			item3_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item3_1.png"));
			item3_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item3_2.png"));
			item4_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item4_1.png"));
			item4_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Item/Item4_2.png"));
			
			Boss1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss1.png"));
			Boss1_flashIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss1_flash.png"));
			Boss2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss2.png"));
			Boss2_flashIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss2_flash.png"));
			Boss3IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss3.png"));
			Boss3_flashIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/Boss3_flash.png"));
			ExplosionBOSSIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/ExplosionBoss.png"));
			ExplosionBOSS2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Explosion/ExplosionBoss2.png"));
			BossWarningIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Boss/BossWarning.png"));
			BossBullet1_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss1_1.png"));
			BossBullet1_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss1_2.png"));
			BossBullet2_1LeftIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss2_1-left.png"));
			BossBullet2_1RightIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss2_1-right.png"));
			BossBullet2_1MiddleIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss2_1.png"));
			BossBullet2_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss2_2.png"));
			BossBullet3_1IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss3_1.png"));
			BossBullet3_1SideIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss3_1-side.png"));
			BossBullet3_2IMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/Bullet/bullet_boss3_2.png"));
			
			GameFinishIMG = ImageIO.read(ImageResource.class.getResourceAsStream("/image/GameScene/GameFinish.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}