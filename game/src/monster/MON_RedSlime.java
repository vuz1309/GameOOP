package monster;

import java.util.Random;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.OBJ_Fireball;
import object.OBJ_Rock;

public class MON_RedSlime extends Entity {
	GamePanel gp;
	
	Projectile projectile1, projectile2, projectile3;
	
	public MON_RedSlime(GamePanel gp) {
		
		
		super(gp);
		 
		this.gp = gp;
		
		type = type_monster;
		name = "Green Slime";
		
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 3;
		defense = 2;
		exp = 2;
		projectile = new OBJ_Fireball(gp);
		projectile1 = new OBJ_Fireball(gp);
		projectile2 = new OBJ_Fireball(gp);
		projectile3 = new OBJ_Fireball(gp);
		mana = 0;
		
		
		solidArea.x = 0;
		solidArea.y = 18;
		solidArea.width = 50;
		solidArea.height = 50;
		solidAreaDefaultX = solidArea.x ;
		solidAreaDefaultY = solidArea.y;
		onPath = false;
		getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/red1", gp.tileSize*3/2, gp.tileSize*3/2);
		up2 = setup("/monster/red2", gp.tileSize*3/2, gp.tileSize*3/2);
		down1 = setup("/monster/red1", gp.tileSize*3/2, gp.tileSize*3/2);
		down2 = setup("/monster/red2", gp.tileSize*3/2, gp.tileSize*3/2);
		right1 = setup("/monster/red1", gp.tileSize*3/2, gp.tileSize*3/2);
		right2 = setup("/monster/red2", gp.tileSize*3/2, gp.tileSize*3/2);
		left1 = setup("/monster/red1", gp.tileSize*3/2, gp.tileSize*3/2);
		left2 = setup("/monster/red2", gp.tileSize*3/2, gp.tileSize*3/2);
	}
	
	public void update() {
		
		super.update();
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		if(onPath == false && tileDistance < 3)
		{
			
			int i = new Random().nextInt(100) + 1;
			if(i>50) {
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
    		int i = new Random().nextInt(200) + 1;
    		if(i > 197 && projectile.alive == false && shotAvailableCounter == 15) {
    			
    			projectile.set(worldX, worldY, direction, true, this);
    			
	    		// CHECK VACACY
	    		for(int j = 0; j< gp.projectile[gp.currentMap].length; j++) {
	    			if(gp.projectile[gp.currentMap][j] == null) {
	    				gp.projectile[gp.currentMap][j] = projectile;
	    				break;
	    			}
	    		}
    			shotAvailableCounter  = 0;
    		}
			
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
	    	
	    	int i = new Random().nextInt(100) + 1;
	    	if(i > 97 && projectile.alive == false && shotAvailableCounter == 15) {
	    		
	    		projectile.set(worldX, worldY, "left", true, this);
	    		

	    		// CHECK VACACY
	    		for(int j = 0; j< gp.projectile[gp.currentMap].length; j++) {
	    			if(gp.projectile[gp.currentMap][j] == null) {
	    				gp.projectile[gp.currentMap][j] = projectile;
	    				break;
	    			}
	    		}
	    		projectile1.set(worldX, worldY, "right", true, this);
	    		

	    		// CHECK VACACY
	    		for(int j = 0; j< gp.projectile[gp.currentMap].length+2; j++) {
	    			if(gp.projectile[gp.currentMap][j] == null) {
	    				gp.projectile[gp.currentMap][j] = projectile1;
	    				break;
	    			}
	    		}
	    		projectile2.set(worldX, worldY, "up", true, this);
	    		

	    		// CHECK VACACY
	    		for(int j = 0; j< gp.projectile[gp.currentMap].length+3; j++) {
	    			if(gp.projectile[gp.currentMap][j] == null) {
	    				gp.projectile[gp.currentMap][j] = projectile2;
	    				break;
	    			}
	    		}
	    		projectile3.set(worldX, worldY, "down", true, this);
	    		

	    		// CHECK VACACY
	    		for(int j = 0; j< gp.projectile[gp.currentMap].length+4; j++) {
	    			if(gp.projectile[gp.currentMap][j] == null) {
	    				gp.projectile[gp.currentMap][j] = projectile3;
	    				break;
	    			}
	    		}
	    		
	    		
	    		shotAvailableCounter = 0;
	    		
	    	}
	 
	    
		
		}
		
	}
	public void damageReaction() {
		
		actionLockCounter = 0;
		if(gp.player.keyH.shotKeyPressed == false) {
			int damage = (gp.player.getAttack() - this.defense);
			if(damage < 0) damage = 0;
			generateParticle(gp.player.projectile, this, damage + "");
		}
	
		onPath = true;
	}
}
