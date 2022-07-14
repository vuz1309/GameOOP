package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class NPC_Goku extends Entity{
	
	GamePanel gp;
		public NPC_Goku(GamePanel gp) {
			super(gp);
			
			this.gp = gp;
			
			direction = "down";
			
			speed = 0;
			
			onPath = false;
	        solidArea = new Rectangle();
	        
			solidArea.x = 0;
			solidArea.y = 0;
			solidArea.width = 48;
			solidArea.height = 48;
			solidAreaDefaultX = solidArea.x ;
			solidAreaDefaultY = solidArea.y;
			
			
			getImage();
			setDialogue();
	
	}
		public void getImage() {
			down1 = setup("/player/dragon_down", gp.tileSize*2, gp.tileSize*2);
			down2 = setup("/player/dragon_down", gp.tileSize*2, gp.tileSize*2);
		}
		
		public void setDialogue() {
			dialogues[NVcounter][0] = "Wow, thanks for helping my dad";
			dialogues[NVcounter][1]="";
			
		}
		public void speak() {
			gp.tileM.loadMap("worldmap 2.txt", 1);
			setDialogue();
			if(NVcounter >= 3) {
				super.speak();
			}
			
		}

}
