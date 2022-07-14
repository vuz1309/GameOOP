package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity {



	public NPC_Merchant(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 0;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		getImage(); 
		setItems();
	}
    public void getImage(){

        up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
    }
    
    public void setDialogue() {
    	
    	dialogues[NVcounter][0]= "Hello man, i have some item!";
    	dialogues[NVcounter][1] = "Do you wannna buy?";
    	dialogues[1][0] = "Hello man, i have a axe\nIf you have 5 Coin, you can take this axe";
    	dialogues[1][1] = "Give me 5 Coin";
    	dialogues[2][0] = "Wow, oke Man, this axe for you\n I have some item, comeback here if you want";
    }
    
    public void setItems() {
    	
    	inventory.add(new OBJ_Potion_Red(gp));
    	inventory.add(new OBJ_Key(gp));
    	inventory.add(new OBJ_Shield_Blue(gp));
    	inventory.add(new OBJ_Shield_Wood(gp));
    	inventory.add(new OBJ_Sword_Normal(gp));
    	inventory.add(new OBJ_Axe(gp));
    	inventory.add(new OBJ_ManaCrystal(gp));
    	inventory.add(new OBJ_Boots(gp));
    	
    }
    public void speak() {
    	setDialogue();
    	if(NVcounter == 1) {
    		if(gp.player.coin >= 5 && stackable == true) {
    			NVcounter++;
    			gp.player.coin-=5;
    			dropItem(new OBJ_Axe(gp));
    		}
    		stackable = true;
    	}
    	else {
    	
        	gp.gameState = gp.tradeState;
        	gp.ui.npc = this;
    	}
    	super.speak();
    }
}
