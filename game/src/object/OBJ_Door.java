package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		name = "door";
		down1 = setup("/objects/door1", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/door2", gp.tileSize, gp.tileSize);
		
		type = type_door;
		
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 48;
		solidArea.height = 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
	
}
