package com.gendeathrow.cutscene.client.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.gendeathrow.cutscene.MovieRender.MovieReader;
import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.gendeathrow.cutscene.SceneRender.SegmentObject;
import com.gendeathrow.cutscene.utils.GsonReader;
import com.gendeathrow.cutscene.utils.RenderAssist;
import com.gendeathrow.cutscene.utils.RenderAssist.Alignment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutSceneGui extends GuiScreen
{
	private String scenePath;
	public int renderTicks;
	public int closeOnTick;
	public SceneObject scene;
	public int currentPhase;
	public ArrayList<SegmentObject> segmentList;
	public MovieReader Movie;
	public BufferedImage[] frames;
	public FontRenderer fontObj;
	
	public CutSceneGui() {}
	
	public CutSceneGui(String scenePath)
	{

		this.scenePath = scenePath;
		this.renderTicks = 0;
		this.currentPhase =0;
		this.scene = GsonReader.GsonReadFromFile(scenePath);
		this.closeOnTick = this.scene.getCloseOnTicks();
		
		this.scene.init(this);
	}
	
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
	
	GuiButton reloadButton;
	GuiButton stopButton;
	
	@Override
    public void initGui() 
    {
		this.fontObj = this.fontRendererObj;
		
		this.reloadButton = new GuiButton(0, this.width - 50, this.height - 30, 15, 20,  "R");
		this.reloadButton.visible = false;
		
		this.stopButton = new GuiButton(1, this.width - 35, this.height - 30, 15, 20,  "S");
		
		this.buttonList.add(this.reloadButton);

    }

    
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	
	protected void actionPerformed(GuiButton p_146284_1_)
	{
		switch (p_146284_1_.id)
		{
	       case 0:
           	mc.displayGuiScreen(new CutSceneGui(this.scenePath));
           	break;
	    }
	 }
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		if(!this.scene.finalize)    this.reloadButton.visible = true;
		if(this.renderTicks >= this.closeOnTick && this.scene.finalize) 	mc.displayGuiScreen(null);
		
		this.drawBackground();

		this.scene.DrawCutScene();
		
		if(this.scene.showDebug) this.fontRendererObj.drawString("Render Ticks: "+this.renderTicks,0, 0, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
		
		super.drawScreen(par1, par2, par3);
		this.renderTicks++;
	}
	
	public void drawBackground()
	{
		RenderAssist.drawRect(0, 0, this.width, this.height, RenderAssist.getColorFromRGBA(0, 0, 0, 180));
		
	}
	
}
