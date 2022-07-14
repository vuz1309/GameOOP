
package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

/**
 *
 * @author DELL
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public boolean drawPath = false;
    
    public TileManager(GamePanel gp){
        this.gp = gp;
        
        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        
        
        getTileImage();
        loadMap("/maps/world03.txt", 0);
        loadMap("/maps/worldmap 2.txt", 1);
 
    }
    
    public void getTileImage(){
    	
    
    	setup(0, "grass", false);
       	setup(1, "tree", true);
       	setup(2, "earth05", false);
       	setup(3, "wall01", true);
       	setup(4, "grassearthbottom", false);
       	setup(5, "grassearthunder", false);
       	setup(6, "grassearthleft", false);
       	setup(7, "grassearthright", false);
       	setup(8, "grassearthbottomright", false);
       	setup(9, "grassearthbottomleft", false);
       	setup(10, "grassearthunderright", false);
    	setup(11, "grassearthunderleft", false);
       	setup(12, "water01", true);
       	setup(13, "watergrassbottom", true);
       	setup(14, "watergrassunder", true);
       	setup(15, "watergrassleft", true);
       	setup(16, "watergrassright", true);
       	setup(17, "watergrassbottomright", true);
       	setup(18, "watergrassbottomleft", true);
       	setup(19, "watergrassunderright", true);
       	setup(20, "watergrassunderleft", true);
       	setup(21, "bone 47", true);
       	setup(22, "bone2 49", true);
       	setup(23, "cactustree", true);
       	setup(24, "dinosaur 48", false);
       	setup(25, "fire 32", true);
       	setup(26, "fire+soilleft 35", true);
       	setup(27, "fire+soilleft 39", true);
       	setup(28, "fire+soilright 38", true);
       	setup(29, "firesoil 36", true);
       	setup(30, "firesoillefttop 43", true);
       	setup(31, "firesoilright 37", true);
       	setup(32, "firesoilrighttop 44", true);
       	setup(33, "firesoiltop 42", true);
       	setup(34, "lavarock 51", true);
       	setup(35, "magma 31", true);
       	setup(36, "magmaSoil 33", true);
       	setup(37, "rock 50", true);
       	setup(38, "skull1 45", true);
       	setup(39, "skull2 46", true);
       	setup(40, "soil 30", false);
       	setup(41, "soilmagma 34", true);
       	setup(42, "treeonsoil 53", true);
    	setup(43, "brigde", false);
    }
    
    
    public void setup(int index, String imagePath, boolean collision) {
    	
    	UtilityTool uTool = new UtilityTool();
    	
    	try {
    		tile[index] = new Tile();
    		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tile/" + imagePath + ".png"));
    		tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
    		tile[index].collision = collision;
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void loadMap(String filePath, int map) {
    	try {
    		InputStream is = getClass().getResourceAsStream(filePath);
    		BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		
    		int col = 0;
    		int row = 0;
    		
    		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
    			
    			String line = br.readLine();
    			
    			while(col < gp.maxWorldCol ) {
    				
    				String numbers[] = line.split(" ");
    				
    				int num = Integer.parseInt(numbers[col]);
    				
    				mapTileNum[map][col][row] = num;
    				col++;
    			}
    			if(col == gp.maxWorldCol) {
    				col = 0;
    				row ++;
    			}
    		}
    		br.close();
    		
    		
    	}catch(Exception e) {
    		
    	}
    }
    
    // Draw miniMap
    public void draw2(Graphics2D g2) {
    	int worldCol = 0;
        int worldRow=0;
        
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
  
        	int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
        	
        	int worldX = worldCol * gp.tileSize;
        	int worldY = worldRow * gp.tileSize;

        	g2.drawImage(tile[tileNum].image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        	

            worldCol++;
                 
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow=0;
        
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
  
        	int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
        	
        	int worldX = worldCol * gp.tileSize;
        	int worldY = worldRow * gp.tileSize;
        	double screenX = worldX - gp.player.worldX + gp.player.screenX;
        	double screenY = worldY - gp.player.worldY + gp.player.screenY;
        	
        	// Stop moving the camera at the edge
        	if(gp.player.screenX > gp.player.worldX) {
        		screenX = worldX;
        	}
        	if(gp.player.screenY > gp.player.worldY) {
        		screenY = worldY;
        	}
        	int rightOffset = gp.screenWidth - gp.player.screenX;
        	if(rightOffset > gp.worldWidth - gp.player.worldX) {
        		screenX = gp.screenWidth - (gp.worldWidth - worldX);
        	}
        	int bottomOffset = gp.screenHeight - gp.player.screenY;
        	if(bottomOffset > gp.worldHeight - gp.player.worldY) {
        		screenY = gp.screenHeight - (gp.worldHeight - worldY);
        	}
        	
        	
        	
        	
//            if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//            	worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//            	worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//            	worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
//            	
//            	g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
//            }
//            else if(gp.player.screenX > gp.player.worldX ||
//            		gp.player.screenY > gp.player.worldY ||
//            		rightOffset > gp.worldHeight - gp.player.worldX ||
//            		bottomOffset > gp.worldHeight - gp.player.worldY) {
//            	
//            	g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
//            }
//              
        	g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;
                 
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
                
            }
        }
        
		if(drawPath == true) {
        	g2.setColor(new Color(255,0,0,50));
        	
        	for(int i = 0; i< gp.pFinder.pathList.size(); i++) {
        		int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
            	int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
            	int screenX = worldX - gp.player.worldX + gp.player.screenX;
            	int screenY = worldY - gp.player.worldY + gp.player.screenY;
            	if(gp.player.screenX > gp.player.worldX) {
            		screenX = worldX;
            	}
            	if(gp.player.screenY > gp.player.worldY) {
            		screenY = worldY;
            	}
            	int rightOffset = gp.screenWidth - gp.player.screenX;
            	if(rightOffset > gp.worldWidth - gp.player.worldX) {
            		screenX = gp.screenWidth - (gp.worldWidth - worldX);
            	}
            	int bottomOffset = gp.screenHeight - gp.player.screenY;
            	if(bottomOffset > gp.worldHeight - gp.player.worldY) {
            		screenY = gp.screenHeight - (gp.worldHeight - worldY);
            	}
            	
            	g2.fillRect(screenX, screenY, gp.tileSize/2, gp.tileSize/2);
        	}
        }
        
    }
}
