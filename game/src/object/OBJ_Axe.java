package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = "woodcutter's Axe";
		down1  = setup("/objects/riu", gp.tileSize, gp.tileSize);
		attackValue = 1;
		description = "[" + name + "]\nA bit rusty but still \n can cut some tree.";
		attackArea.width = 30;
		attackArea.height = 30;
		price = 10;
		knockBackPower = 8;
	}
	

}
