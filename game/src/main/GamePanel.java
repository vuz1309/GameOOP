/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;
import entity.Projectile;
import monster.MON_RedSlime;
import object.OBJ_Key;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ai.PathFinder;
import ai.nv;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
  
	// SCREEN SETT
	final int originalTilesize = 16; //16x16 tile
    final int scale = 3;
    public  int tileSize = originalTilesize * scale; //48 *48 tile
    public  int maxScreenCol = 20;
    public  int maxScreenRow = 12;
    public  int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public  int screenHeight = tileSize * maxScreenRow; // 576 pixels
    
    // WORLD settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FOR FULL SCREEN
    public int screenWidth2 = screenWidth;
    int screenHeight2= screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    public TileManager tileM = new TileManager(this);
    
    public KeyHandler keyH = new KeyHandler(this);
    
    Sound music = new Sound();
    Sound se = new Sound();
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
//    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    
    Thread gameThread;
    
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);    
    public Entity obj[][] = new Entity[maxMap][20];  
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    ArrayList<Entity> entityList = new ArrayList<>();
    
    public Entity projectile[][] = new Entity[maxMap][20];
    
    public ArrayList<Entity> particleList = new ArrayList<>();
    
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int gameFinish = 9;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }     
    
    public void setupGame() {

    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	aSetter.setInteractiveTile();
    	gameState = titleState;
    	
    	tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    	g2 = (Graphics2D)tempScreen.getGraphics();
    	
//    	setFullScreen();
    	
    }
    
    public void retry() {
    	
    	player.setDefaultPosition();
    	player.restoreLifeAndMan();
    	aSetter.setNPC();
    	aSetter.setMonster();
    }
    
    public void restart() {
    	
    	player.setDefaultValues();
    	player.setDefaultPosition();
    	player.restoreLifeAndMan();
    	player.setItems();
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	aSetter.setInteractiveTile();
    	player.getPlayerImage();
    }
    
    
    
    public void setFullScreen() {
    	// GET LOCAL SCREEN DEVICE
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(Main.window);
    	
    	// GET FULL SCREEN WIDTH AND HEIGHT
    	screenWidth2 = Main.window.getWidth();
    	screenHeight2 = Main.window.getHeight();
    }
    
// ZOOM IN OUT
    public void zoomInOut(int i) {   	
    	int oldWordWidth = tileSize *maxWorldCol; // 2400 1200 = 0.5 	
    	tileSize += i;
  	
    	int newWorldWidth = tileSize * maxWorldCol; //2350
    	double muitiplier = (double)newWorldWidth/oldWordWidth;
    	
    	
    	double newPlayerWorldX = player.worldX * muitiplier;
    	double newPlayerWorldY = player.worldY * muitiplier;
    	
    	
    	player.worldX = (int) newPlayerWorldX;
    	player.worldY = (int) newPlayerWorldY;
	
    }
    
    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    } 
    @Override    
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        ui.counter++;
//        int drawCount = 0;
        
        while(gameThread != null){
        
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
//                repaint();
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
                delta--;
//                drawCount++;
            }
            
            if(timer >= 1000000000){
//                System.out.println("FPS: " + drawCount);
//                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
    
    	if(gameState == playState) {
    		// Player
    	       player.update();
    	    // NPC
    	       for(int i =0 ; i < npc[currentMap].length; i++) {
    	    	   
    	    	   if(npc[currentMap][i] != null)
    	    	   {
    	    		   npc[currentMap][i].update();
    	    	   }
    	       }
    	       // MONSTER
    	       for(int i =0 ; i < monster[currentMap].length; i++) {
    	    	   if(monster[currentMap][i] != null) {
    	    		   
    	    		   if(monster[currentMap][i].alive == false) {
    	    			   if(monster[currentMap][i] instanceof MON_RedSlime) {
    	    				   monster[currentMap][i] = null;
    	    				   player.strength = 10; // strongger to destroy Tree in map 02
    	    				   player.useCost = 1; // change Color of CurrentWeaPon in Inventory
    	    				   ui.currentDialogue = "You just drink blood of red slime\nNow, you are very strong";
    	    				   gameState = dialogueState;
    	    			   }
    	    			   else {
    	    				// drop key
        	    			   if(player.stackable == false) {
        	    				   for(int j = 0; j<obj[currentMap].length; j++) {
        	    					   if(obj[currentMap][j] == null) {
        	    						   obj[currentMap][j] = new OBJ_Key(this);
        	    						   obj[currentMap][j].worldX = monster[currentMap][i].worldX;
        	    						   obj[currentMap][j].worldY = monster[currentMap][i].worldY;
        	    						   player.stackable = true;
        	    						   break;
        	    					   }
        	    				   }
        	    			   }
        	    			   
        	    			   monster[currentMap][i].mana++;
        	    			   monster[currentMap][i].type = monster[currentMap][i].type_monster_die; 
        	    			   
//        	    			   Hồi sinh Monster
        	    			   if(monster[currentMap][i].mana >= 1200) {
        	    				   monster[currentMap][i].type =  monster[currentMap][i].type_monster;

        	    				   
        	    				   monster[currentMap][i].mana = 0;
        	    				   
        	    				   monster[currentMap][i].alive = true;
        	    				   
        	    				   monster[currentMap][i].dying = false;
        	    				   
        	    				   monster[currentMap][i].setUpdate();
        	    			   }
        	    		   }
    	    		   }
        	    		   else {
            	    		   if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
            	    			   
            	    			   monster[currentMap][i].update();	   
            	    		   }
        	    		   }
        	    		   

    	    			   
    	    		
    	    	   }
    	       }
    	       // PROJECTILELIST
    	       for(int i =0 ; i<projectile[currentMap].length; i++) {
    	    	   if(projectile[currentMap][i] != null) {
    	    		   if(projectile[currentMap][i].alive == true) {
    	    			   projectile[currentMap][i].update();	   
    	    		   }
    	    		   if(projectile[currentMap][i].alive == false) {
    	    			   projectile[currentMap][i] = null;
    	    		   }
    	    		
    	    	   }
    	       }
    	       
    	       for(int i =0 ; i<particleList.size(); i++) {
    	    	   if(particleList.get(i) != null) {
    	    		   if(particleList.get(i).alive == true) {
    	    			   particleList.get(i).update();	   
    	    		   }
    	    		   if(particleList.get(i).alive == false) {
    	    			   particleList.remove(i);
    	    		   }
    	    		
    	    	   }
    	       }
    	       
    	       // DRYTREE
    	       for(int i = 0; i< iTile[currentMap].length; i++) {
    	    	   if(iTile[currentMap][i] != null) {
    	    		   iTile[currentMap][i].update();
    	    	   }
    	       }
    	}
    	if(gameState == pauseState) {
    		// nothing
    	}
    }
    
    public void drawToTempScreen() {
    	
    	
    	// DEBUG
        long drawStart = 0;
        if(keyH.showDebugText == true) {
        	drawStart = System.nanoTime();
        }
        
        // TITLESCREEN
        if(gameState == titleState) {
        	ui.draw(g2);
        	
        }
        
        // OTHERS
        else {
        	
        	// TILE
            tileM.draw(g2);
            
            // INTERACTIVE TILE
            for(int i = 0; i< iTile[1].length; i++) {
            	if(iTile[currentMap][i] != null) {
            		iTile[currentMap][i].draw(g2);
            	}
            }
            
            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            
            
            for(int i = 0; i< npc[currentMap].length; i++) {
            	if(npc[currentMap][i] != null) {
            		entityList.add(npc[currentMap][i]);
            	}
            }
            
            for(int i = 0; i<obj[currentMap].length; i++) {
            	if(obj[currentMap][i]!= null) {
            		entityList.add(obj[currentMap][i]);
            	}
            }
            
            for(int i = 0; i<monster[currentMap].length; i++) {
            	if(monster[currentMap][i]!= null) {
            		entityList.add(monster[currentMap][i]);
            	}
            }
            for(int i = 0; i< projectile[currentMap].length; i++) {
            	if(projectile[currentMap][i]!= null) {
            		entityList.add(projectile[currentMap][i]);
            	}
            }
            for(int i = 0; i<particleList.size(); i++) {
            	if(particleList.get(i)!= null) {
            		entityList.add(particleList.get(i));
            	}
            }
            
            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
            	
            	@Override
            	public int compare(Entity e1, Entity e2) {
            		
            		int result = Integer.compare(e1.worldY, e2.worldY);
            		return result;
            	}
            });
            
            // DRAW ENTITIES
            for (int i = 0; i< entityList.size(); i++) {
            	entityList.get(i).draw(g2);
            	
            }
            
            
            // EMPTY ENTITY LIST
            entityList.clear();
            
            // UI
            ui.draw(g2);
        }
        

        
        // DEBUG
        if(keyH.showDebugText == true) {
        	
        	long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            
            g2.setFont(new Font("Arial", Font.PLAIN,20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            
            g2.drawString("WorldX" + player.worldX, x, y); y+= lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y);  y+= lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y); y+= lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y);
            y+= lineHeight;
            g2.drawString("Draw Time: " + passed, x , y);
        }
        
        
    }
       
    public void drawToScreen() {
    	
    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
    }
    
    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	
    	music.stop();
    }
    
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    }
}

