package main;

import entity.Entity;
import object.OBJ_Axe;
import tile_interactive.IT_DryTree;
import tile_interactive.InteractiveTile;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x ;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col ++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row ++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}

	}
	
	public void checkEvent() {
		
		// check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			
			if(hit(0,48, 43, "any") == true || hit(0,48, 44, "any") == true ||
					hit(0,49, 44, "any") == true || hit(0,49, 43, "any") == true) {
				teleport(1, 2, 24);
				if(Entity.NVcounter == 2) {
					
					gp.player.currentWeapon = null;
					for(int i = 0; i<gp.player.inventory.size(); i++) {
						if(gp.player.inventory.get(i) instanceof OBJ_Axe) {
						gp.player.inventory.remove(i);
						break;}
					}
					gp.player.getPlayerImage();
				}
			}
			
			else if(hit(1,1,24, "left") == true || hit(1,1,25, "left") == true ) {
				
				teleport(0,47,44);
			}
			else if(hit(1, 28 ,1, "any") == true || hit(1,28,2, "any") == true) {
				close();
			}
			else if(hit(1,18,42, "any") == true) {
				telePortInMap(1, 10, 48, gp.dialogueState);
			}
			else if(hit(1,5,38, "any") == true) {
				telePortInMap(1, 17, 42, gp.dialogueState);
			}
			
		}
	
		
	}
	
	public boolean hit(int map, int col, int row, String regDirection) {
		
		boolean hit = false;
		
		
		if(map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				
				if(gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
			
		}

		
		return hit;
	}
	
	public void teleport(int map, int col, int row) {
		
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(13);
	}
	
	public void telePortInMap(int map, int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize * col;
		gp.player.worldY = gp.tileSize * row;
		canTouchEvent = false;
		
	}
	
	public void damagePit(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit";
		gp.player.life -=1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void close() {
		gp.tileM.loadMap("/maps/worldmap2.txt", 1);
	}
	
	public void healingPool(int col, int row,int gameState) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			gp.ui.currentDialogue = "You drink the water.\nYour life has been recovered";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.aSetter.setMonster();
//			eventRect[col][row].eventDone = true;
		}

	}
	public void speak(Entity entity) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}

}
