package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity{

	Entity generator;
	Color color;
	int size;
	int xd;
	int yd;
	String mes;
	
	public Particle(GamePanel gp, Entity generator, Color color, int size, int speed
			,int maxLife, int xd, int yd, String mes) {
		super(gp);
		
		this.generator = generator;
		this.color = color;
		this.size =size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;
		
		this.mes = mes;
		
		life = maxLife;
		int offset = (gp.tileSize/2) - (size/2);
		worldX = generator.worldX+offset;
		worldY = generator.worldY+offset;
	}
	
	public void update() {
		
		life--;
		
		if(life < maxLife/2) {
			yd++;
		}
		
		worldX += xd*speed;
		worldY += yd*speed;
		
		if(life ==0) {
			alive = false;
		}
	}
	public void draw(Graphics2D g2) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		int tempScreenX = screenX;
	    int tempScreenY = screenY;
		

        int x = tempScreenX;
        int y = tempScreenY;
        if(tempScreenX > worldX) {
      	  x = worldX;
        }
        if(tempScreenY > worldY) {
      	  	y = worldY;
        }
        int rightOffset = gp.screenWidth - tempScreenX;
        if(rightOffset > gp.worldWidth - worldX) {
    			x = gp.screenWidth - (gp.worldWidth - worldX);
    	}
        int bottomOffset = gp.screenHeight - tempScreenY;
        if(bottomOffset > gp.worldHeight - worldY) {
      	  	y = gp.screenHeight - (gp.worldHeight - worldY);
    	}
        
	    
		g2.setColor(color);
//		g2.fillRect(x, y, size, size);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.drawString(" - " + mes +" damage", x, y);
		
	}
}
