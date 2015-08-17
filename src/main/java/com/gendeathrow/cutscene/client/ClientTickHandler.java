package com.gendeathrow.cutscene.client;


import java.io.IOException;

import org.apache.logging.log4j.Level;

import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.utils.GsonReader;
import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientTickHandler{

	/**
	 *  Boolean for First time Game loads up, just before Main menu starts.
	 */
    private boolean firstload = true;

    @SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void RenderTickEvent(RenderTickEvent event) 
    {
    	/**
    	 * This loads up right after the Forge loading screen happens. 
    	 */
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) 
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (firstload && mc != null) 
            {
            	try{
            		mc.displayGuiScreen(new CutSceneGui("/customcutscenes/splashscreen.json"));
            	}catch(NullPointerException e)
            	{
            		CutScene.logger.log(Level.ERROR, "Could not load Custom Scene. You done messed up bro! \n " + e);
            		e.printStackTrace();
            	}
            	firstload = false;
            	
            }
        }
        
    }
}
