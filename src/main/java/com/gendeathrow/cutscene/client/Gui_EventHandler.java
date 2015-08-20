package com.gendeathrow.cutscene.client;

import org.apache.logging.log4j.Level;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent.Load;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.core.Settings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Gui_EventHandler {
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onGuiRender(RenderGameOverlayEvent.Post event)
	{
		
		
		
	}
	
	@SubscribeEvent
	public void onPlayerEnterWorld(Load worldEvent)
	{
		//System.out.println("World Load: "+ worldEvent.world.getWorldInfo().getWorldName());
		//this.mc.displayGuiScreen(new CutSceneGui("/customcutscenes/splashscreen.json"));
		
	}
	
	boolean firstload = true;
	
	@SubscribeEvent
	public void onPlayerLoggedin(RenderGameOverlayEvent event)
	{
	
		if(firstload && Settings.trigger_OnPlayer_Login && Settings.file_OnPlayer_Login != "")
		{
        	try
        	{
        		this.mc.displayGuiScreen(new CutSceneGui("/customcutscenes/"+Settings.file_OnPlayer_Login));
        	}catch(NullPointerException e)
        	{
        	    CutScene.logger.log(Level.ERROR, "Error Reading \"/customcutscenes/" + Settings.file_OnPlayer_Login +"\"");
        	}
        	firstload = false;
		}
	}
	


}
