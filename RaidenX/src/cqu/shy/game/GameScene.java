package cqu.shy.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cqu.shy.resource.ImageResource;

class GameScene extends FlyingObject{

	//要显示的文字部分
	private int HPMax,MPMax;
	private int HPMaxlength,MPMaxlength;
	private int BOSSHPMax,BOSSHPMaxlength;
	private int HPbarWidth,MPbarWidth,BOSSHPbarWidth;
	private boolean isBoss;
	private boolean isBossWarning;
	private boolean isGameFinish;
	private int stage;
	private final String Score_label="Score:";
	private final String Stage_label="Stage:";
	private final String HPbar_label="生命值:";
	private final String MPbar_label="能量值:";
	private final String Skill_label = "技能:";
	private String text_score="0";
	private String text_stage="1";
	private MyImage BossWarning;
	MyImage GameFinish;
	private int count_flash=1;
	private int flash_interval=12;
	//背景
	private MyImage Background;
	public GameScene() {
		Background = new MyImage(ImageResource.BackgroundIMG, 
				ImageResource.BackgroundIMG.getWidth(), ImageResource.BackgroundIMG.getHeight(),
				0, -ImageResource.BackgroundIMG.getHeight()/2-50);
		BossWarning = new MyImage(ImageResource.BossWarningIMG,ImageResource.BossWarningIMG.getWidth(),
				ImageResource.BossWarningIMG.getHeight(),Canvas_width/2-ImageResource.BossWarningIMG.getWidth()/2,
				Canvas_height/2-ImageResource.BossWarningIMG.getHeight()/2-50);
		GameFinish = new MyImage(ImageResource.GameFinishIMG,
				ImageResource.GameFinishIMG.getWidth(), ImageResource.GameFinishIMG.getHeight(),0, 0);
		text_score="0";
		text_stage="1";
		stage=1;
		isBoss=false;
		isBossWarning=false;
		isGameFinish=false;
		HPMax = 800; HPbarWidth=HPMaxlength = 130;
		MPMax = 500; MPbarWidth=MPMaxlength = 80;
		BOSSHPMax=30000; BOSSHPbarWidth=BOSSHPMaxlength = 200;
	}
	
	@Override
	public void init() {
		text_score="0";
		text_stage="1";
		stage=1;
		isBoss=false;
		isBossWarning=false;
		isGameFinish=false;
		HPMax = 800; HPbarWidth=HPMaxlength = 130;
		MPMax = 500; MPbarWidth=MPMaxlength = 80;
		BOSSHPMax=30000; BOSSHPbarWidth=BOSSHPMaxlength = 200;
	}

	@Override
	public void move() {
		Background.setY(Background.getY()+1);
		if(Background.getY()>0)
			Background.setY(-Background.getHeight()/2);
		if(isGameFinish && GameFinish.getY()<250){
			GameFinish.setY(GameFinish.getY()+2);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(Background.getImage(), Background.getX(), Background.getY(),
				Background.getWidth(), Background.getHeight(), null);
		g.setFont(new Font("新宋体", Font.PLAIN, 12));
		
		//画血条
		g.setColor(new Color(255,51,102));
		g.drawString(HPbar_label, 7, 60);
		g.setColor(new Color(236,0,77));
		g.fillRoundRect(50, 53, HPbarWidth, 7, 2, 2);
		//画能量条
		g.setColor(new Color(0,204,255));
		g.drawString(MPbar_label, 7, 78);
		g.setColor(new Color(26,141,210));
		g.fillRoundRect(50, 70, MPbarWidth, 7, 2, 2);
		//画技能栏
		g.setColor(new Color(255,255,0));
		g.drawString(Skill_label, 7, 110);
		//画分数
		g.setFont(new Font("Monaco", Font.BOLD, 11));
		g.setColor(new Color(255,204,255));
		g.drawString(Score_label, 400, 60);
		//画关卡
		g.setColor(new Color(255,204,255));
		g.drawString(text_score, 403, 75);
		
		g.setColor(new Color(255,204,255));
		g.drawString(Stage_label, 400, 100);
		
		g.setColor(new Color(255,204,255));
		g.drawString(text_stage, 403, 115);
		
		if(isBossWarning){
			if(count_flash%flash_interval<=7){
				g.drawImage(BossWarning.getImage(), BossWarning.getX(), BossWarning.getY(), 
						BossWarning.getImage().getWidth(), BossWarning.getImage().getHeight(), null);
			}
			count_flash++;
		}
		if(isGameFinish){
			g.drawImage(GameFinish.getImage(), GameFinish.getX(), GameFinish.getY(), GameFinish.getImage().getWidth(),
					GameFinish.getImage().getHeight(), null);
		}
		if(isBoss){
			//画Boss的血量
			g.setColor(new Color(255,251,219));
			g.drawRect(Canvas_width/2-BOSSHPMaxlength/2, 20, BOSSHPMaxlength, 13);
			g.setColor(new Color(234,27,21));
			g.fillRect(Canvas_width/2-BOSSHPMaxlength/2, 20, BOSSHPbarWidth, 13);
		}
	}

	public String getScoreText(){
		return text_score;
	}
	public void setScoreText(int t){
		text_score = String.valueOf(t);
	}
	public int getStage(){
		return stage;
	}
	public void setStage(int s){
		stage =s;
		text_stage = String.valueOf(stage);
	}
	public boolean isBossCome(){
		return isBoss;
	}
	public void setBossCome(boolean isboss){
		isBoss = isboss;
	}
	
	public MyImage getBossWarningImg() {
		return BossWarning;
	}

	public void setBossWarningImg(MyImage bossWarning) {
		BossWarning = bossWarning;
	}
	
	
	public boolean isGameFinish() {
		return isGameFinish;
	}

	public void setGameFinish(boolean isGameFinish) {
		this.isGameFinish = isGameFinish;
	}

	public int getHPMax() {
		return HPMax;
	}

	public void setHPMax(int hPMax) {
		HPMax = hPMax;
	}

	public int getMPMax() {
		return MPMax;
	}

	public void setMPMax(int mPMax) {
		MPMax = mPMax;
	}

	public int getHPMaxlength() {
		return HPMaxlength;
	}

	public void setHPMaxlength(int hPMaxlength) {
		HPMaxlength = hPMaxlength;
	}

	public int getMPMaxlength() {
		return MPMaxlength;
	}

	public void setMPMaxlength(int mPMaxlength) {
		MPMaxlength = mPMaxlength;
	}

	public int getBOSSHPMax() {
		return BOSSHPMax;
	}

	public void setBOSSHPMax(int bOSSHPMax) {
		BOSSHPMax = bOSSHPMax;
	}

	public int getBOSSHPMaxlength() {
		return BOSSHPMaxlength;
	}

	public void setBOSSHPMaxlength(int bOSSHPMaxlength) {
		BOSSHPMaxlength = bOSSHPMaxlength;
	}

	public int getHPbarWidth() {
		return HPbarWidth;
	}

	public void setHPbarWidth(int hPbarWidth) {
		HPbarWidth = hPbarWidth;
	}

	public int getMPbarWidth() {
		return MPbarWidth;
	}

	public void setMPbarWidth(int mPbarWidth) {
		MPbarWidth = mPbarWidth;
	}

	public int getBOSSHPbarWidth() {
		return BOSSHPbarWidth;
	}

	public void setBOSSHPbarWidth(int bOSSHPbarWidth) {
		BOSSHPbarWidth = bOSSHPbarWidth;
	}
	
	public boolean isBossWarning() {
		return isBossWarning;
	}

	public void setBossWarning(boolean isBossWarning) {
		this.isBossWarning = isBossWarning;
	}

	public MyImage getBosswarningImg(){
		return BossWarning;
	}
}
