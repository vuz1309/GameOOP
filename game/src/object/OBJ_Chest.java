package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		
		
		type = type_chest;
		name = "chest";
		
		
		down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/chest2", gp.tileSize, gp.tileSize);
		price = 15;
	}
}
