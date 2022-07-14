package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_OrangeSlime extends Entity {


	GamePanel gp;
	
	public MON_OrangeSlime(GamePanel gp) {
		
		
		super(gp);
		 
		this.gp = gp;
		
		type = type_monster;
		name = "Orange Slime";
		
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxLife = 4;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;
		mana = 0;
		
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x ;
		solidAreaDefaultY = solidArea.y;
		onPath = false;
		getImage();
	}
	
	public void setUpdate() {
		maxLife++;
		life = maxLife;
		exp++;
		attack++;
		defense++;
	}
	
	public void getImage() {
		
		up1 = setup("/monster/monster1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/monster2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/monster1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/monster2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/monster1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/monster2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/monster1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/monster2", gp.tileSize, gp.tileSize);
	}
	
	public void update() {
		
		super.update();
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		if(onPath == false && tileDistance < 5)
		{
			
			int i = new Random().nextInt(100) + 1;
			if(i>20) {
				onPath  = true;
			}
		}
		if(onPath == true && tileDistance > 8) {
			onPath = false;
		}
	}
	
	public void setAction() {
		
		if(onPath == true) {
			
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
	        	
	       
	    	}
	    	
	 
	    
		
		}
		
	}
	public void damageReaction() {
		
		actionLockCounter = 0;
		if(gp.player.keyH.shotKeyPressed == false) {
			generateParticle(gp.player.projectile, this, (gp.player.getAttack() - this.defense) + "");
		}
		
		onPath = true;
	}
	
	public void checkDrop() {
		
		// CAST A DIE
		int i = new Random().nextInt(100) + 1;
		
		// SET THE MONSTER DROP
		if(i < 100) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i > 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i > 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
	
	


}
