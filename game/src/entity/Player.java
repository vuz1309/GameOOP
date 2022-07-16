
package entity;

import ai.nv;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Axe;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Player extends Entity{
	
    public KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    
    public nv currentNV = new nv();
    
    boolean moving = false;
    int pixelCounter = 0;
    
    
    public Player(GamePanel gp, KeyHandler keyH){
    	
    	super(gp);
    	
    	
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        // SOLID AREA
        solidArea = new Rectangle();
        
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 8;
        
        setDefaultValues();
        getPlayerImage();
      
        setItems();
    }
    
    public void setDefaultValues(){
    //	Map 00:
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 2;
        
//        // map 1:
//        worldX = gp.tileSize * 2;
//        worldY = gp.tileSize * 24;
        
        gp.currentMap = 0;
    	
        defaultSpeed = 7;
        speed = defaultSpeed;
        direction = "down";
        
        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // The more strength he has, the more damage he gives.
        dexterity = 1; // The more dexterity he has, the less damage he receives
        exp = 0;
        nextLevelExp = 6;
        coin = 5;
        currentWeapon = null;
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        projectile.speed = 9;
        value = 0;
        
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); // the total defense value is decided by dexterity and shield
//        defense = 10;
     
    }
    
    public void setItems() {
    	inventory.clear();
    	inventory.add(currentShield);

    }
    
    public int getAttack() {
    	if(currentWeapon == null) return 0;
    	attackArea = currentWeapon.attackArea;
    	return strength * currentWeapon.attackValue;
    }
    public int getDefense()
    
    {
    	return dexterity * currentShield.defenseValue;
    	
    }
    public void getPlayerImage(){
    	
    	if(currentWeapon == null) {
            up1 = setup("/player/up1", gp.tileSize*5, gp.tileSize*5);
            up2 = setup("/player/up2",gp.tileSize*5,gp.tileSize*5);
            down1 = setup("/player/down1",5*gp.tileSize, 5*gp.tileSize);
            down2 = setup("/player/down2", gp.tileSize*5, 5*gp.tileSize);
//            down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
//            down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/left1", gp.tileSize*5, gp.tileSize*5);
            left2 = setup("/player/left2", gp.tileSize*5, gp.tileSize*5);
            right1 = setup("/player/right2", gp.tileSize*5, gp.tileSize*5);
            right2 = setup("/player/right1", gp.tileSize*5, gp.tileSize*5);
    	}
    	
    	else if(currentWeapon.type == type_sword) {
    	
            up1 = setup("/player/sword_up_1", gp.tileSize*5, gp.tileSize*5);
            up2 = setup("/player/sword_up_2",gp.tileSize*5,gp.tileSize*5);
            down1 = setup("/player/sword_down_2",gp.tileSize*5,5*gp.tileSize);
            down2 = setup("/player/sword_down_1", gp.tileSize*5, 5*gp.tileSize);
//            down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
//            down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/sword_left_1", gp.tileSize*5, gp.tileSize*5);
            left2 = setup("/player/sword_left_2", gp.tileSize*5, gp.tileSize*5);
            right1 = setup("/player/sword_right_1", gp.tileSize*5, gp.tileSize*5);
            right2 = setup("/player/sword_right_2", gp.tileSize*5, gp.tileSize*5);
    	}
    	else if(currentWeapon.type == type_axe) {

            up1 = setup("/player/axe_up1", gp.tileSize*5, gp.tileSize*5);
            up2 = setup("/player/axe_up2",gp.tileSize*5,gp.tileSize*5);
            down1 = setup("/player/axe_down1",gp.tileSize*5,5*gp.tileSize);
            down2 = setup("/player/axe_down2", gp.tileSize*5, 5*gp.tileSize);
//            down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
//            down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/axe_left1", gp.tileSize*5, gp.tileSize*5);
            left2 = setup("/player/axe_left2", gp.tileSize*5, gp.tileSize*5);
            right1 = setup("/player/axe_right1", gp.tileSize*5, gp.tileSize*5);
            right2 = setup("/player/axe_right2", gp.tileSize*5, gp.tileSize*5);
    	}
    	
        
    }
    

    
    public void getPlayerAttackImage() {
    	if(currentWeapon != null) {
        	if(currentWeapon.type == type_sword) {
        		attackUp1 = setup("/player/sword_up_1", gp.tileSize*5, gp.tileSize*5);
            	attackUp2 = setup("/player/sword_attack_up_2", gp.tileSize*5, gp.tileSize*5);
            	attackDown1 = setup("/player/sword_attack_down_2", gp.tileSize*5, gp.tileSize*5);
            	attackDown2 = setup("/player/sword_danh_2", gp.tileSize*5, gp.tileSize*5);
            	attackLeft1 = setup("/player/sword_left_1", gp.tileSize*5, gp.tileSize*5);
            	attackLeft2 = setup("/player/sword_attack_left", gp.tileSize*5, gp.tileSize*5);
            	attackRight1 = setup("/player/sword_right_1", gp.tileSize*5, gp.tileSize*5);
            	attackRight2 = setup("/player/sword_attack_right_2", gp.tileSize*5, gp.tileSize*5);
            	

        	}
        	if(currentWeapon.type == type_axe) {
        		attackUp1 = setup("/player/axe_up1", gp.tileSize*5, gp.tileSize*5);
            	attackUp2 = setup("/player/axe_attack_up", gp.tileSize*5, gp.tileSize*5);
            	attackDown1 = setup("/player/axe_down1", gp.tileSize*5, gp.tileSize*5);
            	attackDown2 = setup("/player/axe_attack_down", gp.tileSize*5, gp.tileSize*5);
            	attackLeft1 = setup("/player/axe_left1", gp.tileSize*5, gp.tileSize*5);
            	attackLeft2 = setup("/player/axe_attack_left", gp.tileSize*5, gp.tileSize*5);
            	attackRight1 = setup("/player/axe_right1", gp.tileSize*5, gp.tileSize*5);
            	attackRight2 = setup("/player/axe_attack_right", gp.tileSize*5, gp.tileSize*5);
            	}
    	}


    	
        }
    
    
    public void update(){
    	
    	if(attacking == true) {
    		
    		attacking();
    	}
    	
    	else { 
    		if(moving == false) {
    		
    		 if(keyH.enterPressed == true || keyH.upPressed == true || keyH.downPressed == true || 
    	                keyH.leftPressed==true || keyH.rightPressed==true){
    	        
    	        if(keyH.upPressed == true){
    	            direction = "up";
    	          
    	        		}
    	        else if(keyH.downPressed == true){
    	            direction = "down";
    	       
    	        		}
    	        else if(keyH.leftPressed == true){
    	            direction = "left";
    	           
    	        		}
    	        else if(keyH.rightPressed == true){
    	            direction = "right";
    	    
    	        		}
    	        
    	        moving = true;
    	          	        
    		 }
    	        
//    	        else {
//    	        	standCounter ++;
//    	        	
//    	        	if(standCounter == 20) {
//    	        		spriteNum = 1;
//    	        		standCounter = 0;
//    	        	}
//    	        } 		 
    	}
    	if(moving == true) {
    		
    		// CHECK TILE COLLISION
	        
	        collisionOn = false;
	        gp.cChecker.checkTile(this);
	        if(invincible == true) {
	        	type = type_monster_die;
	        }
	        // CHECK OBJECT COLLISION
	        int objIndex = gp.cChecker.checkObject(this, true);
	        pickUpObject(objIndex);
	        
    		// CHECK NPC COLLISION
	        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	        interactNPC(npcIndex);
	        
	        // CHECK MONSTER COLLISTION	
	        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
	        contactMonster(monsterIndex);
	        
	        // CHECK INTERACTIVE TILE COLLISION
	        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
//	        damageInteractiveTile(iTileIndex);
	        // CHECK EVENT
	        gp.eHandler.checkEvent();
	              
	        if(price == 1) {
		        collisionOn = false;
	        }

    		// IF COLLISION IS FALSE PLAYER CAN MOVE
            if(collisionOn == false && keyH.enterPressed == false) {
            	
            	switch(direction){
            	
            	case "up": worldY -= speed; break;
            	case "down": worldY += speed; break;
            	case "right": worldX += speed; break;
            	case "left": worldX -= speed; break;
            	
            	}
            }
            
            if(keyH.enterPressed == true && attackCanceled == false && currentWeapon != null) {
//            	gp.playSE(7);
            	attacking = true;
            	spriteCounter = 0;
            }
            
            attackCanceled = false;
            
        	gp.keyH.enterPressed = false;
            
            spriteCounter++;
            if(spriteCounter > 8){
                    if(spriteNum ==1){
                        spriteNum=2;
                        
                    }
                    else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
            }
            
//            pixelCounter += speed;
//            
//            if(pixelCounter == 48 ) {
//            	
//            	moving = false;
//            	pixelCounter = 0;
//            	}
            moving = false;
    		}
    	}
    	
    	if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
    			&& shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
    		// IF SKILL IS FIREBALL
    		if(amount == 0) {
        		
        		// SUBSTRACT THE COST(MANA, AMMO ETC.)
        		projectile.substractResource(this);
        		
        		projectile.set(super.getWorldX(), super.getWorldY(), direction, true, this);
        		
        		// CHECK VACACY
        		for(int i = 0; i< gp.projectile[gp.currentMap].length; i++) {
        			if(gp.projectile[gp.currentMap][i] == null) {
        				gp.projectile[gp.currentMap][i] = projectile;
        				break;
        			}
        		}
        		
        		shotAvailableCounter = 0;
        		
        		gp.playSE(10);
    		}
    		else if(amount == 2) {
    			mana--;
    			damagePlayer(0);
    			gp.playSE(2);
    			shotAvailableCounter = 0;
    		}

    	}
    	
    	// this needs to be outside of key if statement!
    	if(invincible == true) {
    		invincibleCounter ++;
    		if(invincibleCounter > 60) {
    			invincible = false;
    			invincibleCounter = 0;
    			type= type_player;
    		}
    	}
    	if(shotAvailableCounter < 30) {
    		shotAvailableCounter++;
    	}
    	if(life > maxLife) life = maxLife;
    	if(mana > maxMana) mana = maxMana;
    	if(life <=0) {
    		gp.gameState = gp.gameOverState;
    		gp.ui.commandNum = -1;
//    		gp.stopMusic();
//    		gp.playMusic(index);
//    		gp.playSE(12);
    	}
       
    }
    
    public void attacking() {
    	
    	spriteCounter ++;
    	
    	if(spriteCounter <= 5) {
    		spriteNum = 1;
    	}
    	if(spriteCounter > 5 && spriteCounter <= 10) {
    		spriteNum = 2;
    		
    		// Save the current worldX, worldY, solidArea
    		int currentWorldX = super.getWorldX();
    		int currentWorldY = super.getWorldY();
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		// Adjust player's worldX, Y for the attackArea
    		switch(direction) {
    		case "up": worldY -= attackArea.height; break;
    		case "down": worldY += attackArea.height; break;
    		case "right": worldX += attackArea.width; break;
    		case "left": worldX -= attackArea.width; break;
    		}
    		
    		// attackArea becomes solidArea
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    		
    		// check monster collision with the updated worldX, worldY, and solidArea
    		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
    		damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
    		
    		//
    		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
    		damageInteractiveTile(iTileIndex);
    		
    		int projectTileIndex = gp.cChecker.checkEntity(this, gp.projectile);
    		damageProjectTile(projectTileIndex);
    		
    		// After checking collision, restore the original data
    		worldX = currentWorldX;
    		worldY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    		
    	}
    	if(spriteCounter > 15) {
    		spriteNum = 1;
    		spriteCounter = 0;
    		attacking = false;
    	}
    }
    
    public void pickUpObject(int i) {
    	
    	if(i != 999) {
    		
    		// PICKUP ONLY ITEMS
    		if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
    			
    			gp.obj[gp.currentMap][i].use(this);
    			gp.obj[gp.currentMap][i] = null;
    		}
    		// DOOR
    		else if(gp.obj[gp.currentMap][i].type == type_door) {
    			if(gp.player.value > 0) {
    				gp.player.value --;
    				gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].down2;
    				gp.obj[gp.currentMap][i].type = gp.obj[gp.currentMap][i].type_monster_die;
    				String text = "Open the door";
    				gp.ui.addMessage(text);
    			}
    			else {
    				String text;
    				text = "Use the key";
    				gp.gameState = gp.dialogueState;
    				gp.ui.currentDialogue = text;
    				 
    			}
    		}
    		else if(gp.obj[gp.currentMap][i].type == type_monster_die) {
    			collisionOn =false;
    		}
    		// CHEST
    		else if(gp.obj[gp.currentMap][i].type == type_chest) {
    			if(keyH.enterPressed== true) {
        			int x = gp.obj[gp.currentMap][i].worldX;
        			int y = gp.obj[gp.currentMap][i].worldY;
        			
        			gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].down2;
        			gp.obj[gp.currentMap][i].type = gp.player.type_monster_die;
        			for(int j = 0; j<gp.obj[gp.currentMap].length; j++) {
        				if(gp.obj[gp.currentMap][j] == null) {
        					if(gp.obj[gp.currentMap][i].amount == 1000) {
        						gp.gameState = gp.gameFinish;
        						gp.ui.counter = 0;
        						gp.playSE(4);
        					}
        					gp.obj[gp.currentMap][j] = new OBJ_Potion_Red(gp);
        					gp.obj[gp.currentMap][j].worldX = x;
        					gp.obj[gp.currentMap][j].worldY = y;
        					break;
        				}
        			}
    			}

    		}
    		
    		// INVENTORY ITEMS
    		else  {
        		String text;
        		
        		if(canObtainItem(gp.obj[gp.currentMap][i]) == true) {
        			
        			gp.playSE(1);
        			
        			text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
        		}
        		else {
        			text = "You cannot carry any more!";
        		}
        		gp.ui.addMessage(text);
        		gp.obj[gp.currentMap][i]  = null;
    		}

    	}
    	
    }
    
    public void interactNPC(int i) {
    	if(keyH.enterPressed == true) {
    		
        	if(i != 999) {
        			attackCanceled = true;
        			gp.gameState = gp.dialogueState;
            		gp.npc[gp.currentMap][i].speak(); 
            		if(NVcounter >= 2 && gp.npc[1][i] instanceof NPC_OldMan ) {
            			gp.npc[1][i].speed = 2;
            			gp.npc[1][i].onPath = true;
            			gp.tileM.drawPath = true;
            			
            		}
            		else if(NVcounter >= 3 && gp.npc[gp.currentMap][i] instanceof NPC_Goku) {
            			if(gp.npc[gp.currentMap][i].dialogueIndex == 2) {
            				gp.gameState = gp.gameFinish;
            			}
            		}
            		else if(NVcounter <3 && gp.npc[gp.currentMap][i] instanceof NPC_Goku) {
            			gp.gameState = gp.playState;
            		}
        	}
    	}
    	
    	
    }
    
    public void contactMonster(int i) {
    	
    	if(i!= 999) {
    		if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
    			
    			int damage = gp.monster[gp.currentMap][i].attack - defense;
    			if(damage < 0) damage = 0;
    			
    				gp.playSE(6);
    				life -= damage;
    			
    			invincible = true;
    		}
    		
    	}
    }
    
    public void damageMonster(int i, int attack, int knockBackPower) {
    	if(i != 999) {
    		
    		if(gp.monster[gp.currentMap][i].invincible == false) {
    			int damage = 0;
    			gp.ui.drawMonster(i);
    			
    			gp.playSE(5);
    			
    			if(knockBackPower > 0) {
    				knockBack(gp.monster[gp.currentMap][i], currentWeapon.knockBackPower);
    			}
    			
    			
    			
    			damage = attack - gp.monster[gp.currentMap][i].defense;
    			if(damage < 0) {
    				damage = 1;
    			}
    			gp.monster[gp.currentMap][i].life -= damage;
    			gp.ui.addMessage(damage + " damage!");  			
    			if(projectile.alive == false) {
    				gp.monster[gp.currentMap][i].damageReaction();
    			}
    			
    			gp.monster[gp.currentMap][i].invincible = true;
    			
    			
    			if(gp.monster[gp.currentMap][i].life <= 0) {
    				gp.monster[gp.currentMap][i].dying = true;
    				gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
    				gp.ui.addMessage("Exp " + gp.monster[gp.currentMap][i].exp);
    				exp += gp.monster[gp.currentMap][i].exp;
    				gp.monster[gp.currentMap][i].checkDrop(); // check drop
    				mana++; // mana++
    				checkLevelUp();
    			}
    		}
    	}
    	
    }
    
    public void damageInteractiveTile(int i) {
    	
    	if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true
    			&& gp.iTile[gp.currentMap][i].isCorrectItem(this)==true && gp.iTile[gp.currentMap][i].invincible == false) {
    		gp.iTile[gp.currentMap][i].playSE();
    		int damage = attack - gp.iTile[gp.currentMap][i].defense;
    		if(damage < 0) damage = 0;
    		gp.iTile[gp.currentMap][i].life-= damage ;
    		gp.iTile[gp.currentMap][i].invincible = true;
    		
    		// Generate particle
    		generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i], ""+ damage);
    		
    		if(gp.iTile[gp.currentMap][i].life <= 0) {
    			gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
    		}
    		
    	}
    }
    
    public void damageProjectTile(int i) {
    	if(i!=999) {
    		Entity projectTile = gp.projectile[gp.currentMap][i];
    		projectTile.alive = false;
    		generateParticle(projectTile, projectTile, "");
    	}
    }
    
    public void checkLevelUp() {

    	if(exp >= nextLevelExp) {
    		
    		level++;
    		exp= 0;
    		nextLevelExp = nextLevelExp* 2;
    		maxLife +=2;
    		life = maxLife;
    		mana = maxMana;
    		strength ++;
    		dexterity ++;
    		attack = getAttack();
    		defense = getDefense();
    		
    		
    		gp.playSE(8);
    		gp.gameState = gp.dialogueState;
    		gp.ui.currentDialogue = "You are level " + level + "now\n"
    				+ "You feel stronger!";
    	}
    }

    public void setDefaultPosition() {
    	worldX = gp.tileSize * 4;
    	worldY = gp.tileSize *2;
    	direction = "down";
    }
    
    public void restoreLifeAndMan() {
    	life = maxLife;
    	mana = maxMana;
    	invincible = false;
    }
    
    public void selectItem() {
    	int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
    	
    	if(itemIndex < inventory.size()) {
    		
    		Entity selectedItem = inventory.get(itemIndex);
    		
    		if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
    			
    			currentWeapon = selectedItem;
    			attack = getAttack();
    			getPlayerImage();
    			getPlayerAttackImage();
    		}
    		if(selectedItem.type == type_shield) {
    			currentShield = selectedItem;
    			defense = getDefense();
    		}
    		if(selectedItem.type == type_consumable) {

    			selectedItem.use(this);
    			
    			if(selectedItem.amount > 1) {
    				selectedItem.amount--;
    			} else {
    				inventory.remove(itemIndex);
    			}
    			
    		}
    	}
    }
    
    public void knockBack(Entity entity, int knockBackPower) {
    	
    	entity.direction = direction;
    	entity.speed += knockBackPower;
    	entity.knockBack = true;
    	
    	
    }
    
    public int searchItemInInventory(String itemName) {
    	
    	int itemIndex = 999;
    	for(int i = 0; i< inventory.size(); i++) {
    		if(inventory.get(i).name.equals(itemName)) {
    			itemIndex = i;
    			break;
    		}
    	}
    	return itemIndex;
    }
    
    public boolean canObtainItem(Entity item) {
    	
    	boolean canObtain = false;
    	
    	// CHECK IF STACKABLE
    	if(item.stackable == true) {
    		
    		int i = searchItemInInventory(item.name);
    		if(i != 999) {
    			inventory.get(i).amount ++;
    			canObtain = true;
    		}
    		else { // new item so need to check vacancy
    			if(inventory.size() != maxInventorySize)
    			{
    				inventory.add(item);
    				canObtain= true;
    			}
    		}
    	}
    	else { // not stackable so check vacancy
   			if(inventory.size() != maxInventorySize)
			{
				inventory.add(item);
				canObtain= true;
			}
    	}
    	return canObtain;
    }
    
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,  gp.tileSize, gp.tileSize);
    	
          BufferedImage image = null;
          int tempScreenX = screenX;
          int tempScreenY = screenY-10;
          
          
          switch(direction){
          case "up":
        	  if(attacking == false) {
        		  if(spriteNum == 1){image = up1;}
        		  if(spriteNum==2){image = up2; }
        	  }
        	  if(attacking == true) {
//        		  tempScreenY = screenY - gp.tileSize;
        		  if(spriteNum == 1){image = attackUp1;}
        		  if(spriteNum==2){image = attackUp2; }
        	  }
                 break;
          case "down":
        	  if(attacking == false) {
        		  if(spriteNum == 1){ image = down1;}
                  if(spriteNum==2){image = down2; }  
        	  }
        	  if(attacking == true) {
        		  if(spriteNum == 1){ image = attackDown1;}
                  if(spriteNum==2){image = attackDown2; }
        	  }
                 break;
          case "left":    
        	  if(attacking == false) {
        		  if(spriteNum == 1){ image = left1;}
                  if(spriteNum==2){image = left2; }  
        	  }
        	  if(attacking == true) {
        		  tempScreenX = screenX - gp.tileSize/3;
        		  if(spriteNum == 1){ image = attackLeft1;}
                  if(spriteNum==2){image = attackLeft2; }
        	  }
                 break;
          case "right":
        	  if(attacking == false) {
        		  if(spriteNum == 1){ image = right1;}
                  if(spriteNum==2){image = right2; }  
        	  }
        	  if(attacking == true) {
        		  if(spriteNum == 1){ image = attackRight1;}
                  if(spriteNum==2){image = attackRight2; }
        	  }
                 break;
          	}//end switch
          
          int x = tempScreenX;
          int y = tempScreenY;
          if(tempScreenX > worldX) {
        	  x = worldX;
          }
          if(tempScreenY > worldY) {
        	  	y = worldY-15;
          }
          int rightOffset = gp.screenWidth - tempScreenX;
          if(rightOffset > gp.worldWidth - worldX) {
      			x = gp.screenWidth - (gp.worldWidth - worldX);
      	}
          int bottomOffset = gp.screenHeight - tempScreenY;
          if(bottomOffset > gp.worldHeight - worldY) {
        	  	y = gp.screenHeight - (gp.worldHeight - worldY) - 15;
      	}
      	
          if(invincible == true) {
        	  g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
          }
          
          g2.drawImage(image, x, y, null);
          
          // Reset alpha
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
          
          
//          // DEBUG
//          g2.setFont(new Font("Arial", Font.PLAIN, 26));
//          g2.setColor(Color.white);
//          g2.drawString("Invicible: " + invicibleCounter, 10,400);
//          
    }//end draw
}
