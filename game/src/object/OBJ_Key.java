package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	
	GamePanel gp;
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		stackable = true;
		type = type_consumable;
		name = "key";
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\n It opens a door";
		price = 15;
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You use key, now you can open the door";
		entity.value += 1;
	}
	
}
