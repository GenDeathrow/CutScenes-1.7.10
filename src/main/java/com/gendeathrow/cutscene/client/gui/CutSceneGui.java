package com.gendeathrow.cutscene.client.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.gendeathrow.cutscene.SceneRender.ActorObject;
import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.gendeathrow.cutscene.SceneRender.SegmentObject;
import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutSceneGui extends GuiScreen
{
	public int renderTicks;
	public int closeOnTick;
	public SceneObject scene;
	public int currentPhase;
	public ArrayList<SegmentObject> segmentList;
	
	public CutSceneGui()
	{
		this.renderTicks = 0;
		this.closeOnTick = 90;
	}
	
	public CutSceneGui(SceneObject scene)
	{
		this.renderTicks = 0;
		this.currentPhase =0;
		this.scene = scene;
		this.closeOnTick = this.scene.getCloseOnTicks();
		this.segmentList = this.scene.screenSegments;
		this.scene.init();
		//
	}
	
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
	@Override
    public void initGui() 
    {

    }

    
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawBackground();
		if(this.renderTicks >= this.closeOnTick) mc.displayGuiScreen(null);
		

		if(this.segmentList != null && this.currentPhase <= this.segmentList.size()-1)
		{
			((SegmentObject)this.segmentList.get(this.currentPhase)).DrawSegment(this, Minecraft.getMinecraft(), this.fontRendererObj);
		}
		
		

		this.fontRendererObj.drawString("Render Ticks: "+this.renderTicks,0, 0, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
		super.drawScreen(par1, par2, par3);
		

		this.renderTicks++;
	}
	
	public void drawBackground()
	{
		RenderAssist.drawRect(0, 0, this.width, this.height, RenderAssist.getColorFromRGBA(0, 0, 0, 180));
		
	}
}
