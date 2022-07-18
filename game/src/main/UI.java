package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.NPC_Merchant;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState=0; // 0: the first screen, 1: the second screen
	public int countTitle = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int subState = 0;
	public int counter = 0;
	public Entity npc;

	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
		
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		// CREAT HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
	}
	
	public void changeTitleScreenState(int i) {
		titleScreenState  += i;
	}
	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		
		// PLAY STATE
		if(gp.gameState== gp.playState) {
			drawPlayerLife();
			drawMessage();
			
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
			
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
			
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();			
		}
		
		// GAME DONE
		if(gp.gameState == gp.gameFinish) {
			drawGameFinish();
		}
		// SET PLAYER
		if(gp.gameState == gp.setPlayer) {
			drawSetPlayer();
		}
		
	}
	
	public void drawPlayerLife() {
		
		
		int x = gp.tileSize/2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		// DRAW BLANK HEART
		while(i < gp.player.maxLife/2) {
			
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		 x = gp.tileSize/2;
		 y = gp.tileSize / 2;
		 i = 0;
		 
		 // DRAW CURRENT LIFE
		 while(i < gp.player.life) {
			 g2.drawImage(heart_half, x, y, null);
			 i++;
			 if(i < gp.player.life) {
				 g2.drawImage(heart_full, x, y, null);
			 }
			 i++;
			 x += gp.tileSize;
		 }
		 
		 // DRAW MAX MANA
		 x = gp.tileSize/2 - 5;
		 y = (int) (gp.tileSize*1.5);
		 i = 0;
		 while(i < gp.player.maxMana) {
			 g2.drawImage(crystal_blank, x, y, null);
			 i++;
			 x += 35;
		 }
		 
		 // DRAW CURRENT MANA
		 x = gp.tileSize/2 - 5;
		 y = (int) (gp.tileSize*1.5);
		 i = 0;
		 while(i < gp.player.mana) {
			 g2.drawImage(crystal_full, x, y, null);
			 i++;
			 x+= 35;
		 }
		
	}
	
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
		
		for(int i = 0; i< message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);
				g2.setColor(Color.white);
				
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter  = messageCounter.get(i) + 1; // messageCounter++;
				messageCounter.set(i, counter); // set the counter to the array
				messageY += 50;
				
				if(messageCounter.get(i) > 50) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	public void drawMonster(int i) {
		// WINDOW
		int x = gp.tileSize *13;
		int y = gp.tileSize / 2;
		int width = 5*gp.tileSize;
		int height = 3*gp.tileSize;
			
		drawSubWindow(x, y, width, height, new Color(139,69,19, 200));
		
		
	}
	
	public void drawTitleScreen(){
		
		if(titleScreenState == 0) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			BufferedImage img = null;
			try {
				img = ImageIO.read(getClass().getResourceAsStream("/tile/nen.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g2.drawImage(img, 0,  0, gp.screenWidth, gp.screenHeight, null);
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "Game RPG OOP 28";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			
			// MAIN COLOR
			
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// BLUE BOY IMAGE
			x = gp.screenWidth / 2  -  gp.tileSize;
			y += gp.tileSize * 2;
			countTitle ++ ;
			if(countTitle > 15) {
				g2.drawImage(gp.player.down2, x, y, gp.tileSize * 6, gp.tileSize * 6, null);
				if(countTitle == 30) countTitle = 0;
			} else {
				g2.drawImage(gp.player.down1, x, y, gp.tileSize * 6, gp.tileSize * 6, null);
			}
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize * 3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
//				g2.drawImage(gp.npc[0].down1, x- gp.tileSize, y-gp.tileSize, gp.tileSize , gp.tileSize , null);
				g2.drawString(">", x- gp.tileSize, y);
			}
			
			text = "OPTION";
			x = getXforCenteredText(text);
			y += gp.tileSize ;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
//				g2.drawImage(gp.npc[0].down1, x- gp.tileSize, y-gp.tileSize, gp.tileSize , gp.tileSize , null);
				g2.drawString(">", x- gp.tileSize, y);
			}
			
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize ;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
//				g2.drawImage(gp.npc[0].down1, x- gp.tileSize, y-gp.tileSize, gp.tileSize , gp.tileSize , null);
				g2.drawString(">", x- gp.tileSize, y);
			}
		}
		else if(titleScreenState == 1) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			
			// CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your skill!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);
			
			text = "FireBall";
			x = getXforCenteredText(text);
			y += gp.tileSize ;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Immortal";
			x = getXforCenteredText(text);
			y += gp.tileSize ;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		else if(titleScreenState == 2) {
			drawOptionsScreen();
		}

		
	}
	
	public void drawPauseScreen() {
		
		// CREAT A FRAME
		final int frameX = 48*12;
		final int frameY = 48*2;
		final int frameWidth  = 48 * 4;
		final int frameHeight = 48 * 3;
		
		int textX = frameX + 20;
		int textY = frameY + 48;
		final int lineHeight = 36;

		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		g2.setColor(Color.RED);
		String text = "@";
		int x = gp.player.worldX;
		
		int y = gp.player.worldY+gp.tileSize;
		
		gp.tileM.draw2(g2);
		g2.drawString(text, x, y);
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, new Color(255, 255, 255, 60));
		g2.setColor(Color.black);
		g2.drawString("You are standing on @", textX, textY);
		textY += lineHeight;
		
		int cursorX = 0;
		int cursorY = 0;
		int cursorWidth = 48 * 8 + 18; 
		int cursorHeight = 48 * 8 + 18;
		
		g2.setColor(new Color(139,69,19, 200));
		g2.setStroke(new BasicStroke(10));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int x = gp.tileSize * 3;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 6);
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height, new Color(0,0,0,100));
		int i = gp.cChecker.checkEntity(gp.player, gp.npc);
		if(i!=999 ) {
		if(gp.keyH.enterPressed == true && (gp.npc[gp.currentMap][i] instanceof NPC_Merchant) == false) {

			gp.player.keyH.enterPressed = true;
			
			if(i!=999 ) {
				int j = gp.npc[gp.currentMap][i].dialogueIndex;
				gp.gameState = gp.playState;
				if(gp.npc[gp.currentMap][i].dialogues[Entity.NVcounter][++j] == null) {
//					gp.npc[gp.currentMap][i].dialogueIndex=0;
						
						gp.player.keyH.enterPressed = false;
					
				}
			}
			
		}}
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;
		if(currentDialogue !=null) {
			for(String line : currentDialogue.split("\n")) {
				g2.setColor(new Color(248,248,255));
				g2.drawString(line, x, y);
				y += 40;
			}
		}
		
	}
	
	public void drawCharacterScreen() {
		
		// CREAT A FRAME
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth  = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, Color.black);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F)); 
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 36;
		
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("EXP", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		OBJ_Coin_Bronze c = new OBJ_Coin_Bronze(gp);
		g2.drawImage(c.down1, textX + gp.tileSize*2-14, textY-gp.tileSize+18, gp.tileSize*3/4, gp.tileSize*3/4, null);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Shield", textX, textY);
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// Reset TextY
		textY = frameY + gp.tileSize;
		String value;
		
		value  = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value  = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight - 25;
		
		if(gp.player.currentWeapon != null) {
			g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		}
	
		textY += lineHeight + 10;

		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}
	
	public void drawSetPlayer() {
		// SUB WINDOW
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, Color.black);
		
		int textX;
		int textY;
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		// TITLE
		String text = "SETTING";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		textX = frameX + 20;
		textY += 2* gp.tileSize;
		final int lineHeight = 36;

		// NAMES
		g2.drawString("Speed", textX, textY);
		if(commandNum == 0) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.speed++;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.speed--;
				gp.keyH.leftPressed = false;
			}
		}
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		if(commandNum == 1) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.strength++;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.strength--;
				gp.keyH.leftPressed = false;
			}
			gp.player.attack = gp.player.getAttack();
		}
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		if(commandNum == 2) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.dexterity++;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.dexterity--;
				gp.keyH.leftPressed = false;
			}
			gp.player.defense = gp.player.getDefense();
		}
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		if(commandNum == 3) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.coin++;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.coin--;
				gp.keyH.leftPressed = false;
			}
		}
		textY += lineHeight;
		
		g2.drawString("Skill", textX, textY);
		if(commandNum == 4) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.amount = 0;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.amount = 2;
				gp.keyH.leftPressed = false;
			}
		}
		textY += lineHeight;
		
		g2.drawString("Collison", textX, textY);
		if(commandNum == 5) {
			g2.drawString("A<>D", textX+gp.tileSize*3-20, textY);
			if(gp.keyH.rightPressed == true) {
				gp.player.price = 0;
				gp.keyH.rightPressed = false;
			}
			if(gp.keyH.leftPressed == true) {
				gp.player.price = 1;
				gp.keyH.leftPressed = false;
			}
		}
		
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
						// Reset TextY
		textY = frameY + 3*gp.tileSize;
			String value;
						
			value  = String.valueOf(gp.player.speed);
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString("<< "+ value+" >>", textX-2*gp.tileSize, textY);
			textY += lineHeight;
			
			value  = String.valueOf(gp.player.getAttack());
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString("<< "+ value+" >>", textX-2*gp.tileSize, textY);
			textY += lineHeight;
			
			value  = String.valueOf(gp.player.getDefense());
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString("<< "+ value+" >>", textX-2*gp.tileSize, textY);
			textY += lineHeight;
			
			value  = String.valueOf(gp.player.coin);
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString("<< "+ value+" >>", textX-2*gp.tileSize, textY);
			textY += lineHeight;
			

			if(gp.player.amount == 0) {
				value = "Fire Ball";
			}
			else {
				value = "Bat tu";
			}
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString(value, textX-14, textY);
			textY += lineHeight;
			
			
			if(gp.player.price == 0) {
				value = "On";
			}
			else {
				value = "Off";
			}
			
			textX = getXforAlignToRightText(value, tailX);
			g2.drawString("<< "+ value+" >>", textX-2*gp.tileSize, textY);
			textY += lineHeight;
			
	}
	
	public void drawInventory(Entity entity, boolean cursor) {
		
		int frameX=0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		if(entity == gp.player) {
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;	
		}
		
		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, new Color(139,69,19, 200));
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY= slotYstart;
		int slotSize = gp.tileSize + 3;
		
		// DRAW PLAYER'S ITEMS
		for(int i  =0; i< entity.inventory.size(); i++) {
			
			// EQUIP CURSOR
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield
					) {
				if(entity.useCost == 0) {
					g2.setColor(new Color(240, 190,90));
				} else {
					g2.setColor(Color.red);
				}
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
				
			if(entity.inventory.get(i) != null) {
				g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			}
	
			
			// DISPLAY AMOUNT
			if(entity.inventory.get(i) != null) {
		
				
				if(entity.inventory.get(i).amount > 1) {
					g2.setFont(g2.getFont().deriveFont(32f));
					int amountX;
					int amountY;
					String s = "" + entity.inventory.get(i).amount;
					amountX = getXforAlignToRightText(s, slotX+ 44);
					amountY = slotY + gp.tileSize;
					
					// SHADOW
					g2.setColor(new Color(60,60,60));
					g2.drawString(s, amountX, amountY);
					// NUMBER
					g2.setColor(Color.white);
					g2.drawString(s, amountX-3, amountY-3);
				}
				
			}
			

			slotX += gp.tileSize;
			
			if(i == 4 || i==9 || i == 14 ) {
				slotX = slotXstart;
				slotY += slotSize;
			}
					
		}
		
		// CURSOR
		if(cursor == true) {
			int cursorX = slotXstart + (gp.tileSize * slotCol);
			int cursorY = slotYstart + (gp.tileSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			
			// DRAW CURSOR
			g2.setColor(new Color(64,224,208));
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, new Color(139,69,19, 200));
			
			// DRAW DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28f));
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if (itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, new Color(139,69,19, 200));
				
				if(entity.inventory.get(itemIndex) != null) {
					for( String lines : entity.inventory.get(itemIndex).description.split("\n")) {
						
						
						g2.drawString(lines , textX, textY);
						textY += 32;
						}
				}
				
			
			}
		}

	}
		
	public void drawTransition() {
		
		counter ++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 20) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	
	public void drawTradeScreen() {
		
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		
		gp.keyH.enterPressed = false;
		
	}
	
	public void trade_select() {
		
		drawDialogueScreen();
		
		// DRAW WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height, new Color(139,69,19, 200));
		
		// DRAW TEXTS
		x+= gp.tileSize;
		y += gp.tileSize;
		
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y+= gp.tileSize;
		
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y+= gp.tileSize;
		
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again, hihi";
			}
		}
	}
	
	public void trade_buy() {
		
		// DRAW PLAYER INVNETORY
		drawInventory(gp.player, false);
		// DRAW NPC INVENTORY
		drawInventory(npc, true);
		
		// DRAW HINT WINDOW
		int x= gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow( x, y, width, height, new Color(139,69,19, 200));
		g2.drawString("[ESC] Back", x+24, y+gp.tileSize );
		
		// DRAW PLAYER COIN WINDOW
		x= gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow( x, y, width, height, new Color(139,69,19, 200));
		g2.drawString("Your coin: " + gp.player.coin, x+24, y+gp.tileSize);
	
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			
			x = (int)(gp.tileSize *5.5);
			y = (int)(gp.tileSize *5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height, new Color(139,69,19, 200));
			g2.drawImage(coin, x+10, y+8, 32, 32,null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = ""+ price;
			x = getXforAlignToRightText(text, gp.tileSize * 8-20);
			g2.drawString(text, x, y+32);
		// BUY AN ITEM
		if(gp.keyH.enterPressed == true) {
			if(npc.inventory.get(itemIndex).price > gp.player.coin) {
				subState = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "You need more coin";
//				drawDialogueScreen();
				}
			else if(gp.player.inventory.size()== gp.player.maxInventorySize) {
				subState = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "You cannot carry any more";

				}
			else {
				gp.player.coin -= npc.inventory.get(itemIndex).price;
				if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true ) {
					gp.ui.currentDialogue = "Success";
				}
				
				}
			}
		}
	}
	
	public void trade_sell() {
		
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);
		
		int x;
		int y;
		int height;
		int width;
		
		// DRAW HINT WINDOW
		 x= gp.tileSize * 2;
		 y = gp.tileSize * 9;
		 width = gp.tileSize * 6;
		 height = gp.tileSize * 2;
		drawSubWindow( x, y, width, height, new Color(139,69,19, 200));
		g2.drawString("[ESC] Back", x+24, y+gp.tileSize);
		
		// DRAW PLAYER COIN WINDOW
		x= gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow( x, y, width, height, new Color(139,69,19, 200));
		g2.drawString("Your coin: " + gp.player.coin, x+24, y+gp.tileSize);
	
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			
			x = (int)(gp.tileSize *15.5);
			y = (int)(gp.tileSize *5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height, new Color(139,69,19, 200));
			g2.drawImage(coin, x+10, y+8, 32, 32,null);
			
			int price = gp.player.inventory.get(itemIndex).price/2;
			String text = ""+ price;
			x = getXforAlignToRightText(text, gp.tileSize * 18-20);
			g2.drawString(text, x, y+32);
			
			// SELL AN ITEM
			if(gp.keyH.enterPressed == true) {
				
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
						gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "you connot sell an equipped item";
				}
				else {
					gp.player.inventory.remove(itemIndex);
					gp.player.coin += price;
				}
					
			}
			
		}
		
		
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}
	
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// Back to the title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y+= 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public void drawOptionsScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));
		
		// SUB WINDOW
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, Color.black);
		
		switch(subState) {
		case 0: options_top(frameX, frameY) ; break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
		
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		
		}
		
		// MUSIC
		
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum ==1) {
			g2.drawString(">", textX-25, textY);
		}
		
		// SE
		
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		// CONTROL
		
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true){
				subState = 2;
				commandNum = 0;
			}
		}
		 
		// END GAME 
		
		textY += gp.tileSize;
		g2.drawString("End game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// BACK
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.player.amount == 1) {
					gp.gameState = gp.titleState;
					gp.ui.changeTitleScreenState(-2);
				}
				else {
					gp.gameState = gp.playState;
				}
				
			}
		}
		
		// FULL SCREEN CHECK BOX
		textX = (int) (frameX + gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY,24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24;
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will take \neffect after restarting\nthe game.";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
			
		}
		
		// BACK
		textY =  frameY + gp.tileSize* 6;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
		
	}
	
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		
		// TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX  = gp.tileSize + frameX;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Skill", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Mini Map", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("Enter", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("Esc", textX, textY); textY += gp.tileSize;
		
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed==true) {
				subState = 0;
			}
		}
	}
	
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		
		currentDialogue = "Quit Game?";
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum ==0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				titleScreenState = 0;
				gp.gameState = gp.titleState;
				
			}
		}
		
		// NO
		
		 text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum ==1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	
	public void drawGameFinish() {

		
		g2.setColor(new Color(176,196,222,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		if(counter > 0 && counter < 10) {
			g2.setColor(Color.red);
		}
		else if(counter >=10 && counter < 20) {
			g2.setColor(Color.red);
		}
		else if(counter >=20 && counter < 30) {
			g2.setColor(Color.blue);
		}
		else if(counter >=30 && counter < 40) {
			g2.setColor(Color.green);
		}
		else if(counter >=40 && counter < 50) {
			g2.setColor(Color.yellow);
		}
		else if(counter >=50 && counter < 60) {
			g2.setColor(Color.PINK);
		}
		else counter = 0;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "Congratulation!";
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		// Shadow
		g2.setColor(Color.black);

		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// Back to the title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y+= 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	
	}
	
	public void drawSubWindow(int x, int y, int width, int height, Color color) {
		
		Color c = color;
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(205,133,63);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y+5, width-10, height-10,25 ,25 );
	}
	
	
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length /2;
		
		return x;
	}
	public int getXforAlignToRightText(String text, int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		
		return x;
	}
}
