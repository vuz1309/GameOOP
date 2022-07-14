package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{
	
	GamePanel gp;
	public OBJ_Boots(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "boots";
		type = type_consumable;
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
		description = "[Boots]\nYou become faster";
		collision = true;
		price = 15;
	}
	
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You use the " + name + ", you become faster!";
		
		entity.speed+= 2;
	}
	
}
