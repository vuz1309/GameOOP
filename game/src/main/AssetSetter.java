package main;

import entity.NPC_Goku;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import monster.MON_OrangeSlime;
import monster.MON_RedSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Sword_Normal;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_Trunk;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		}
	
	public void setObject() {
		int numMap  = 0;
		int i = 0;
		
		gp.obj[numMap][i] = new OBJ_Chest(gp);
		gp.obj[numMap][i].worldX = gp.tileSize * 15;
		gp.obj[numMap][i].worldY = gp.tileSize * 12;
		i++;
		
		gp.obj[numMap][i] = new OBJ_Door(gp);
		gp.obj[numMap][i].worldX = gp.tileSize * 16;
		gp.obj[numMap][i].worldY = gp.tileSize * 17;
		i++;
		
		numMap = 1;
		i=0;
//		gp.obj[numMap][i] = new OBJ_Chest(gp);
//		gp.obj[numMap][i].amount = 1000;
//		gp.obj[numMap][i].worldX = gp.tileSize * 7;
//		gp.obj[numMap][i].worldY = gp.tileSize * 2;
//		i++;
		gp.obj[numMap][i] = new OBJ_Axe(gp);

		gp.obj[numMap][i].worldX = gp.tileSize * 36;
		gp.obj[numMap][i].worldY = gp.tileSize * 4;
		i++;
		
		gp.obj[numMap][i] = new OBJ_Shield_Blue(gp);

		gp.obj[numMap][i].worldX = gp.tileSize * 36;
		gp.obj[numMap][i].worldY = gp.tileSize * 6;
		i++;
	}
	
	public void setNPC() {
		int numMap  = 0;
		int i = 0;
		gp.npc[numMap][i] = new NPC_OldMan(gp);
		gp.npc[numMap][i].worldX = gp.tileSize*5;
		gp.npc[numMap][i].worldY = gp.tileSize* 2;
		i++;
		
		gp.npc[numMap][i] = new NPC_Merchant(gp);
		gp.npc[numMap][i].worldX = gp.tileSize*37;
		gp.npc[numMap][i].worldY = gp.tileSize* 22;
		i++;

		
		// Map 02
		numMap = 1;
		i = 0;
		gp.npc[numMap][i] = new NPC_OldMan(gp);
		gp.npc[numMap][i].speed = 4;
		
		gp.npc[numMap][i].worldX = gp.tileSize*5;
		gp.npc[numMap][i].worldY = gp.tileSize* 22;
		i++;
		
		gp.npc[numMap][i] = new NPC_Goku(gp);
		gp.npc[numMap][i].worldX = gp.tileSize*7;
		gp.npc[numMap][i].worldY = gp.tileSize* 2;
		i++;
	}	

	
	public void setMonster() { 
		int numMap  = 0;
		int i = 0;
		
		gp.monster[numMap][i] = new MON_GreenSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 30;
		gp.monster[numMap][i].worldY = gp.tileSize * 4;
		i++;
		
		gp.monster[numMap][i] = new MON_GreenSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 28;
		gp.monster[numMap][i].worldY = gp.tileSize * 22;
		i++;
		
		gp.monster[numMap][i] = new MON_GreenSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 26;
		gp.monster[numMap][i].worldY = gp.tileSize * 34;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 22;
		gp.monster[numMap][i].worldY = gp.tileSize * 45;
		i++;
		
		gp.monster[numMap][i] = new MON_GreenSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 5;
		gp.monster[numMap][i].worldY = gp.tileSize * 40;
		i++;
		
		gp.monster[numMap][i] = new MON_GreenSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 4;
		gp.monster[numMap][i].worldY = gp.tileSize * 11;
		i++;
		
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 4;
		gp.monster[numMap][i].worldY = gp.tileSize * 13;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 36;
		gp.monster[numMap][i].worldY = gp.tileSize * 37;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 38;
		gp.monster[numMap][i].worldY = gp.tileSize * 45;
		i++;
		
		numMap = 1;
		i=0;
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 7;
		gp.monster[numMap][i].worldY = gp.tileSize * 41;
		i++;
		
		gp.monster[numMap][i] = new MON_RedSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 7;
		gp.monster[numMap][i].worldY = gp.tileSize * 44;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 9;
		gp.monster[numMap][i].worldY = gp.tileSize * 42;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 14;
		gp.monster[numMap][i].worldY = gp.tileSize * 15;
		i++;
		
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 11;
		gp.monster[numMap][i].worldY = gp.tileSize * 15;
		i++;
		gp.monster[numMap][i] = new MON_OrangeSlime(gp);
		gp.monster[numMap][i].worldX = gp.tileSize * 21;
		gp.monster[numMap][i].worldY = gp.tileSize * 10;
		i++;
	}
	
	public void setInteractiveTile() {
		int numMap  = 0;
		int i = 0;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 48, 42);
		i++;
		
		gp.iTile[numMap][i] = new IT_DryTree(gp, 47, 46);
		i++;
		
		// map 2
		numMap = 1;
		i = 0;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 46, 24);
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 45, 24);
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 16, 37);
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 8, 21);
		gp.iTile[numMap][i].defense = 10;
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 8, 20);
		gp.iTile[numMap][i].defense = 10;
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 8, 19);
		gp.iTile[numMap][i].defense = 10;
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 8, 18);
		gp.iTile[numMap][i].defense = 10;
		i++;
		gp.iTile[numMap][i] = new IT_DryTree(gp, 8, 17);
		gp.iTile[numMap][i].defense = 10;
		i++;
	}
}
