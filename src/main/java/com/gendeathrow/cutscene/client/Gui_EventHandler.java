package com.gendeathrow.cutscene.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent.Load;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.utils.GsonReader;

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
	public void onPlayerLoggedIn(Load event)
	{
		System.out.println("World Load: "+ event.world.getWorldInfo().getWorldName());
		this.mc.displayGuiScreen(new CutSceneGui(GsonReader.GsonReadFromFile("test.json")));
		
	}

}
