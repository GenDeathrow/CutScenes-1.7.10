package com.gendeathrow.cutscene.client;


import java.awt.image.BufferedImage;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

import org.apache.logging.log4j.Level;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.core.CutScene;

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
    private boolean flag = false;
    BufferedImage[] something = null;
    int frame = 0;
    int tick = 0;

	private static TextureManager manager = Minecraft.getMinecraft().getTextureManager(); 
	
	
    @SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void RenderTickEvent(RenderTickEvent event) throws IOException 
    {

    	/**
    	 * This loads up right after the Forge loading screen happens. 
    	 * 
    	 * 
    	 */
    	
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) 
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (firstload && mc != null) 
            {
            	try
            	{
            		mc.displayGuiScreen(new CutSceneGui("/customcutscenes/splashscreen.json"));
            		//mc.displayGuiScreen(new GuiCutscene(file));
            		flag = true;
            		
            	}catch(NullPointerException e)
            	{
            		CutScene.logger.log(Level.ERROR, "Could not load Custom Scene. You done messed up bro! \n " + e);
            		e.printStackTrace();
            	}
            	firstload = false;
            	
            }
        }
        
        if(flag) 
        	{
        			try
        			{
//        			RenderAssist.bindTexture(new ResourceLocation(manager.getDynamicTextureLocation("", tex).getResourcePath()));
//        				RenderAssist.drawTexturedModalRect(0, 0, 600, 600);
        			}
        			catch(NullPointerException e)
        			{
        				e.printStackTrace();
        				flag = false;
        			}
        		

        	}
        
    }
}
