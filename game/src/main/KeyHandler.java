
package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.NPC_Merchant;


public class KeyHandler implements KeyListener{

   public boolean upPressed;
   public boolean rightPressed;
   public boolean leftPressed;
   public boolean downPressed;
   public boolean enterPressed;
   public boolean shotKeyPressed;
   
   //DEBUG
   boolean showDebugText = false;
   GamePanel gp;

   public KeyHandler(GamePanel gp) {
	   this.gp = gp;
   }
   
    @Override
    public void keyTyped(KeyEvent e) {

        
    }

    @Override
    public void keyPressed(KeyEvent e) {

    int code = e.getKeyCode();
    
    	// TITLE STATE
    if(gp.gameState == gp.titleState) { 
    	titleState(code);
    	}
    
    	// PLAYSTATE
    else if(gp.gameState == gp.playState) {
    	playState(code);
    	}
    
       // PAUSE STATE
        
    else if(gp.gameState == gp.pauseState) {
    	pauseState(code);
    	}
        
        // DIALOGUE STATE
    else if(gp.gameState == gp.dialogueState) {
    	dialogueState(code);
    	}
    	// CHARACTER STATE
    else if(gp.gameState == gp.characterState) {
    	characterState(code);
    	}
    	// OPTIONS STATE
    else if(gp.gameState == gp.optionsState) {
    	optionsState(code);
    }
    	// OVER STATE
    else if(gp.gameState == gp.gameOverState || gp.gameState == gp.gameFinish) {
    	gameOverState(code);
    }
    	// TRADE STATE
    else if(gp.gameState == gp.tradeState) {
    	tradeState(code);
    }
    else if(gp.gameState == gp.setPlayer) {
    	setPlayer(code);
    }
 
    }
    
    public void titleState(int code) {

    	
    	if(gp.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_W ){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0) {
                	gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S ){
                gp.ui.commandNum ++ ;
                if(gp.ui.commandNum > 2) {
                	gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER ){
            	if(gp.ui.commandNum == 0) {
            		gp.ui.changeTitleScreenState(1);	
            	}
            	if(gp.ui.commandNum == 1) {
            		gp.ui.changeTitleScreenState(2);
            	}
            	if(gp.ui.commandNum == 2) {
            		System.exit(0);
            	}

            }
    	}
    	
    	else if(gp.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_W ){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0) {
                	gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_S ){
                gp.ui.commandNum ++ ;
                if(gp.ui.commandNum > 3) {
                	gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER ){
            	if(gp.ui.commandNum == 0) {
            		
            		gp.gameState = gp.playState;
            		gp.player.amount=0;
//            		gp.playMusic(0);            		
            	}
            	if(gp.ui.commandNum == 1) {
            		
            		gp.gameState = gp.playState;
            		gp.player.amount = 2;
            	}
            	if(gp.ui.commandNum == 2) {
            		
            		gp.ui.changeTitleScreenState(-1);
            		
            	}
            }
    	}
    	else if(gp.ui.titleScreenState == 2) {
    		if(code == KeyEvent.VK_ESCAPE) {
        		gp.gameState = gp.titleState;
        		gp.ui.changeTitleScreenState(-2);
        	}
        	if(code == KeyEvent.VK_ENTER) {
        		enterPressed = true;
        	}
        	
        	int maxCommandNum = 0;
        	switch(gp.ui.subState) {
        	case 0: maxCommandNum = 5; break;
        	case 3: maxCommandNum = 1; break;
        	}
        	if(code == KeyEvent.VK_W) {
        		gp.ui.commandNum--;
        		gp.playSE(9);
        		if(gp.ui.commandNum < 0) {
        			gp.ui.commandNum = maxCommandNum;
        		}
        	}
        	if(code == KeyEvent.VK_S) {
        		gp.ui.commandNum++;
        		gp.playSE(9);
        		gp.playSE(9);
        		if(gp.ui.commandNum > maxCommandNum) {
        			gp.ui.commandNum = 0;
        		}
        	}
        	if(code == KeyEvent.VK_A) {
        		if(gp.ui.subState == 0) {
        			if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
        				gp.music.volumeScale--;
        				gp.music.checkVolume();
        				gp.playSE(9);
        			}

        			if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
        				gp.se.volumeScale--;
        				gp.se.checkVolume();
        				gp.playSE(9);
        			}
        		
        		}
        	}
        	if(code == KeyEvent.VK_D) {
        		if(gp.ui.subState == 0) {
        			if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
        				gp.music.volumeScale++;
        				gp.music.checkVolume();
        				gp.playSE(9);
        			}
        			if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
        				gp.se.volumeScale++;
        				gp.se.checkVolume();
        				gp.playSE(9);
        			}
        		}
        	}
        	
    	}
    
    	
    }
    public void playState(int code) {

		if(code == KeyEvent.VK_ALT) {
			gp.gameState = gp.setPlayer;
		}
	    
        if(code == KeyEvent.VK_W ){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S ){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D ){
               rightPressed = true;
        }
        if(code == KeyEvent.VK_A ){
            leftPressed = true;
        }
     
        if(code == KeyEvent.VK_P ){
        	gp.gameState = gp.pauseState;
        	gp.zoomInOut(-40);

        }
        if(code == KeyEvent.VK_C) {
        	gp.gameState = gp.characterState;
        }
        
        if(code == KeyEvent.VK_ENTER ){
        	enterPressed = true;

        }
        if(code == KeyEvent.VK_F) {
        	shotKeyPressed = true;
        }
        
        if(code == KeyEvent.VK_ESCAPE) {
        	gp.gameState = gp.optionsState;
        }
        
        if(code == KeyEvent.VK_T) {
        	if(showDebugText == false) {
        		showDebugText = true;
        	}
        	else if(showDebugText == true) {
        		showDebugText = false;
        	}
        }
        if(code == KeyEvent.VK_R) {
        	switch(gp.currentMap) {
        	case 0:  	gp.tileM.loadMap("/maps/world03.txt", 0); break;
        	case 1: gp.tileM.loadMap("/maps/worldmap 2.txt", 1); break;
        	}
        }
	
    	
    }
    public void pauseState(int code) {

    	if(code == KeyEvent.VK_P) {
    		gp.gameState = gp.playState;
    		gp.zoomInOut(40);
    	}
    	
    
    	
    }
    public void dialogueState(int code) {
    	
    	if(code == KeyEvent.VK_ESCAPE) {
            	
            	gp.gameState = gp.playState;
         }
    	if(code == KeyEvent.VK_ENTER) {
    		enterPressed=true;
    		int i = gp.cChecker.checkEntity(gp.player, gp.npc);
    		
    		if(i==999) {
    			gp.gameState = gp.playState;
    		}
    		else if((gp.npc[gp.currentMap][i] instanceof NPC_Merchant) == true) {
    			gp.gameState = gp.playState;
    			enterPressed=false;
    		}
    		
    	}
    	
    
    	
    }
    public void characterState(int code) {

    	
		if(code == KeyEvent.VK_C) {
			gp.gameState= gp.playState;
		}
		
        if(code == KeyEvent.VK_ENTER) {
        	gp.player.selectItem();
        }
        playerInventory(code);

    }
    
    public void setPlayer(int code) {
    	if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_ALT) {
    		gp.gameState = gp.playState;
    		gp.ui.commandNum=0;
    	}
        if(code == KeyEvent.VK_W ){
            gp.ui.commandNum --;
            if(gp.ui.commandNum < 0) {
            	gp.ui.commandNum = 5;
            }
        }
        if(code == KeyEvent.VK_S ){
            gp.ui.commandNum ++ ;
            if(gp.ui.commandNum > 5) {
            	gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_D ){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_A ){
         leftPressed = true;
        }
    }
    
    
    public void optionsState(int code) {
    	
    	if(code == KeyEvent.VK_ESCAPE) {
    		gp.gameState = gp.playState;
    	}
    	if(code == KeyEvent.VK_ENTER) {
    		enterPressed = true;
    	}
    	
    	int maxCommandNum = 0;
    	switch(gp.ui.subState) {
    	case 0: maxCommandNum = 5; break;
    	case 3: maxCommandNum = 1; break;
    	}
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		gp.playSE(9);
    		if(gp.ui.commandNum < 0) {
    			gp.ui.commandNum = maxCommandNum;
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum++;
    		gp.playSE(9);
    		gp.playSE(9);
    		if(gp.ui.commandNum > maxCommandNum) {
    			gp.ui.commandNum = 0;
    		}
    	}
    	if(code == KeyEvent.VK_A) {
    		if(gp.ui.subState == 0) {
    			if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
    				gp.music.volumeScale--;
    				gp.music.checkVolume();
    				gp.playSE(9);
    			}

    			if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
    				gp.se.volumeScale--;
    				gp.se.checkVolume();
    				gp.playSE(9);
    			}
    		
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(gp.ui.subState == 0) {
    			if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
    				gp.music.volumeScale++;
    				gp.music.checkVolume();
    				gp.playSE(9);
    			}
    			if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
    				gp.se.volumeScale++;
    				gp.se.checkVolume();
    				gp.playSE(9);
    			}
    		}
    	}
    	
    }

    public void gameOverState(int code) {
    	if(code == KeyEvent.VK_ENTER) {

    		if(gp.ui.commandNum == 0) {
    			gp.gameState = gp.playState;
    			gp.restart();;
//    			gp.playMusic(0);
    		}
    		else if(gp.ui.commandNum == 1) {
    			gp.gameState = gp.titleState;
    			gp.restart();
    		}
    	}
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		if(gp.ui.commandNum <0) {
    			gp.ui.commandNum = 1;
    		}
    		gp.playSE(9);
    	}
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum ++;
    		if(gp.ui.commandNum> 1) {
    			gp.ui.commandNum = 0;
    		}
    		gp.playSE(9);
    	}
    
    }
    
    public void tradeState(int code) {
    	
    	if(code == KeyEvent.VK_ENTER) {
    		enterPressed = true;
    	}
    	if(code == KeyEvent.VK_ESCAPE) {
    		gp.gameState = gp.playState;
    	}
    	if(gp.ui.subState == 0) {
    		if(code == KeyEvent.VK_W) {
    			gp.ui.commandNum --;
    			if(gp.ui.commandNum < 0) {
    				gp.ui.commandNum = 2;
    			}
    			gp.playSE(9);
    		}
    		if(code == KeyEvent.VK_S) {
    			gp.ui.commandNum ++;
    			if(gp.ui.commandNum >2) {
    				gp.ui.commandNum = 0;
    			}
    			gp.playSE(9);
    		}
    	}
    	if(gp.ui.subState == 1) {
    		npcInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			gp.ui.subState = 0;
    		}
    	}
    	
    	if(gp.ui.subState == 2) {
    		playerInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			gp.ui.subState = 0;
    		}
    	}
    }
    
    public void playerInventory(int code) {
    	
        if(code == KeyEvent.VK_W ){
        	if(gp.ui.playerSlotRow !=0) {
            gp.ui.playerSlotRow--;
            gp.playSE(9);}
            
        }
        if(code == KeyEvent.VK_S ){
        	if(gp.ui.playerSlotRow !=3) {
        	gp.playSE(9);
        	gp.ui.playerSlotRow++;}
        }
        if(code == KeyEvent.VK_D ){
        	if(gp.ui.playerSlotCol !=4) {
            gp.ui.playerSlotCol++;
            gp.playSE(9);}
        }
        if(code == KeyEvent.VK_A ){
        	if(gp.ui.playerSlotCol !=0) {
        	gp.ui.playerSlotCol--;
        	gp.playSE(9);}
        }
    }
    
    public void npcInventory(int code) {
    	
        if(code == KeyEvent.VK_W ){
        	if(gp.ui.npcSlotRow !=0) {
            gp.ui.npcSlotRow--;
            gp.playSE(9);}
            
        }
        if(code == KeyEvent.VK_S ){
        	if(gp.ui.npcSlotRow !=3) {
        	gp.playSE(9);
        	gp.ui.npcSlotRow++;}
        }
        if(code == KeyEvent.VK_D ){
        	if(gp.ui.npcSlotCol !=4) {
            gp.ui.npcSlotCol++;
            gp.playSE(9);}
        }
        if(code == KeyEvent.VK_A ){
        	if(gp.ui.npcSlotCol !=0) {
        	gp.ui.npcSlotCol--;
        	gp.playSE(9);}
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

            int code = e.getKeyCode();
            if(code == KeyEvent.VK_W ){
                upPressed = false;
            }
            if(code == KeyEvent.VK_S ){
                downPressed = false;
            }
            if(code == KeyEvent.VK_D ){
                   rightPressed = false;
            }
            if(code == KeyEvent.VK_A ){
                leftPressed = false;
            }
            if(code == KeyEvent.VK_F) {
            	shotKeyPressed = false;
            }
          
    }
        
}
