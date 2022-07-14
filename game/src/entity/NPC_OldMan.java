package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Sword_Normal;

public class NPC_OldMan extends Entity {

	GamePanel gp;
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		direction = "down";
		
		speed = 0;
		
		onPath = false;
        solidArea = new Rectangle();
        
		solidArea.x = 6;
		solidArea.y = 6;
		solidArea.width = 34;
		solidArea.height = 34;
		solidAreaDefaultX = solidArea.x ;
		solidAreaDefaultY = solidArea.y;
		
		
		getImage();
		setDialogue();
	}
	
    public void getImage(){

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    
    public void setDialogue() {
    	dialogues[NVcounter][0] = "Go go go";
    	dialogues[0][0] = "OMG! Why are you here?";
    	dialogues[0][1] = "This is dangerous";
    	dialogues[0][2] = "Take this sword\n and kill all monsters.";
    	dialogues[0][3] = "Let's go";
    	dialogues[1][0] = "Let's found the axe and \n go back!";
    	dialogues[2][0] = "OMG, i don't know how to leave\n this map.";
    	dialogues[2][1] = "Can you take me out here?";

    }
    
    public void setAction() {
    	
    	if(onPath == true) {
    		
//    		int goalCol = 15;
//    		int goalRow = 20;
    		
    		int goalCol = (gp.player.worldX + gp.player.solidAreaDefaultX)/gp.tileSize;
    		int goalRow = (gp.player.worldY + gp.player.solidAreaDefaultY)/gp.tileSize;
    		
    		searchPath(goalCol, goalRow);
 
    	}
    	else {
    		actionLockCounter ++;
        	
        	if(actionLockCounter == 120) {
        	   	
            	Random random = new Random();
            	int i = random.nextInt(100) + 1; //pick up a number from 1 to 100
            	
            	if( i <= 25) {
            		direction = "up";
            	}
            	if(i > 25 && i<= 50) {
            		direction = "down";
            	}
            	if(i> 50 && i<=75) {
            		direction = "right";
            	}
            	if(i>75 && i<=100) {
            		direction = "left";
            	}
            	
            	actionLockCounter = 0;
            	onPath = true;
        	}
     
    	} 	
    }
    
    public void speak() {
    	
    	// Do this character specific stuff
    	
    	super.speak();
       	if(dialogueIndex == 3 && amount==1) {
    		amount++;
    		NVcounter++;
    		dropItem(new OBJ_Sword_Normal(gp));
    	}
       	if(NVcounter == 2 && gp.currentMap == 1) {
       		NVcounter++;
       		
       	}
    	
    	
    }
	
}
