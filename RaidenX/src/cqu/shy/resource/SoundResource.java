package cqu.shy.resource;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class SoundResource {
	//主界面音乐
	public static AudioClip WelcomeMusic;
	//帮助界面音乐
	public static AudioClip HelpSceneMusic;
	//游戏过程中的音效
	public static AudioClip FireSound;
	public static AudioClip HeroGetHurtSound;
	public static AudioClip EnemyDeathSound;
	public static AudioClip GetHPSound;
	public static AudioClip GetMPSound;
	public static AudioClip GetcrystalSound;
	public static AudioClip GetBulletSound;
	public static AudioClip HeroPowerFulSound;
	public static AudioClip HeroDeathSound;
	public static AudioClip SkillZSound;
	//Boss相关音乐
	public static AudioClip BossWarningSound;
	public static AudioClip Bossbattle1Music;
	public static AudioClip Bossbattle2Music;
	public static AudioClip Bossbattle3Music;
	public static AudioClip BossDeathMusic;
	//游戏通关
	public static AudioClip GameFinishMusic;
	static {
		try {
			WelcomeMusic = Applet.newAudioClip( new File("src/sound/MainScene.wav").toURI().toURL() ) ;
			
			HelpSceneMusic = Applet.newAudioClip( new File("src/sound/HelpScene.wav").toURI().toURL() ) ;
			
			FireSound = Applet.newAudioClip( new File("src/sound/Fire.wav").toURI().toURL() ) ;
			HeroGetHurtSound = Applet.newAudioClip( new File("src/sound/Herogethurt.wav").toURI().toURL() ) ;
			EnemyDeathSound = Applet.newAudioClip( new File("src/sound/Enemy_death.wav").toURI().toURL() ) ;
			GetHPSound = Applet.newAudioClip( new File("src/sound/GetHP.wav").toURI().toURL() ) ;
			GetMPSound = Applet.newAudioClip( new File("src/sound/GetMP.wav").toURI().toURL() ) ;
			GetcrystalSound = Applet.newAudioClip( new File("src/sound/Getcrystal.wav").toURI().toURL() ) ;
			GetBulletSound  = Applet.newAudioClip( new File("src/sound/GetBullet.wav").toURI().toURL() ) ;
			HeroPowerFulSound = Applet.newAudioClip( new File("src/sound/energyfull.wav").toURI().toURL() ) ;
			HeroDeathSound = Applet.newAudioClip( new File("src/sound/HeroDeath.wav").toURI().toURL() ) ;
			SkillZSound = Applet.newAudioClip( new File("src/sound/SkillZ.wav").toURI().toURL() ) ;
			
			BossWarningSound = Applet.newAudioClip( new File("src/sound/Bosswarning.wav").toURI().toURL() ) ;
			Bossbattle1Music = Applet.newAudioClip( new File("src/sound/Bossbattle1.wav").toURI().toURL() ) ;
			Bossbattle2Music = Applet.newAudioClip( new File("src/sound/Bossbattle2.wav").toURI().toURL() ) ;
			Bossbattle3Music = Applet.newAudioClip( new File("src/sound/Bossbattle3.wav").toURI().toURL() ) ;
			BossDeathMusic = Applet.newAudioClip( new File("src/sound/BossDeath.wav").toURI().toURL() ) ;
			
			GameFinishMusic = Applet.newAudioClip( new File("src/sound/GameFinish.wav").toURI().toURL() ) ;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
