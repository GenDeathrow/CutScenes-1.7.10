package com.gendeathrow.cutscene.client;


import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
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
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) 
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (firstload && mc != null) 
            {
            	mc.displayGuiScreen(new CutSceneGui(GsonReader.GsonReadFromFile("test.json")));
            	firstload = false;
            }
        }
        
	//	RenderAssist.bindExternalTexture("config/test.png");
		//System.out.println(this.startX +" "+ this.startY+" "+ this.endX+" "+ this.endY);
		//RenderAssist.drawTexturedModalRect(0, 0, 0, 0, 128, 128);
    }
}
