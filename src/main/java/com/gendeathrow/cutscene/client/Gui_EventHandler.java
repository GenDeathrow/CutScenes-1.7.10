package com.gendeathrow.cutscene.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.world.WorldEvent.Load;

import org.apache.logging.log4j.Level;

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
		
		//SaveData playerData = SaveData.get(Minecraft.getMinecraft().thePlayer);
		
		// if First Log in has happened and always play is  false than end function here
		//if(playerData.firstLogin == true && Settings.trigger_OnPlayer_Login_Always == false) return;
		
		if(firstload && Settings.trigger_OnPlayer_Login && Settings.file_OnPlayer_Login != "")
		{
        	try
        	{
            	firstload = false;
        		event.setCanceled(true);
        		this.mc.displayGuiScreen(new CutSceneGui("/customcutscenes/"+Settings.file_OnPlayer_Login));
        		

        	}catch(NullPointerException e)
        	{
        	    CutScene.logger.log(Level.ERROR, "Error Reading \"/customcutscenes/" + Settings.file_OnPlayer_Login +"\"");
        	}


		}
	}
	
	@SubscribeEvent
	public void onPlayerLogout(GuiOpenEvent event)
	{
		if(event.gui == null) return;
		
		if(event.gui instanceof GuiMainMenu)
		{
			firstload = true;
		}
	
	}
	
	@SubscribeEvent
	public void onSoundPlay(PlaySoundEvent17 event)
	{
		if(event.category == SoundCategory.MUSIC && Minecraft.getMinecraft().currentScreen instanceof CutSceneGui)
		{
			event.result = null;
		}
			
	}
}	
