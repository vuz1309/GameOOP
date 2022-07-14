package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red  extends Entity{
	
	GamePanel gp;
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		stackable = true;
		type = type_consumable;
		name = "Red Potion";
		value = 5;
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = "[Red Potion]\nHeals ";
		price = 15;
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "Your drink the " + name + "!";
		entity.life += value;
		if(entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
		
		gp.playSE(2);
	}
	
}
