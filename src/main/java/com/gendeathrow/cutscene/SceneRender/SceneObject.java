package com.gendeathrow.cutscene.SceneRender;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SceneObject 
{

	public boolean finalize;
	public boolean showDebug;
	public String id;
	
	private int[] backgroundColor;
	

	private String backgroundTexturePath;
	//public ResourceLocation backgroundResourceLocation;

	/** Segments contain new frames within the current Cutscene Object	 */
	public ArrayList<SegmentObject> screenSegments;
	private int curSegment;
	public transient CutSceneGui guiParent;
	
	public transient static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("m:s:A");
	public transient boolean closeScene = false;
	
	
	public SceneObject()
	{
		this.id = null;
		this.finalize = false;
		this.showDebug = false;
		this.backgroundColor = new int[]{0,0,0}; // Black
	}
	
	
	public void init(CutSceneGui gui)
	{
		this.guiParent = gui;
		
		Minecraft.getMinecraft().getSoundHandler().stopSounds();
		
		for(SegmentObject segment : this.screenSegments)
		{
			segment.init(this);
		}
	}
	
	public boolean shouldClose()
	{
		return closeScene;
	}
	
	public int getBackgroundColor()
	{
		if(this.backgroundColor.length != 3) this.backgroundColor = new int[]{0,0,0};
		
		return RenderAssist.getColorFromRGBA(this.backgroundColor[0], this.backgroundColor[1], this.backgroundColor[2], 255);
	}
	
	/**
	 * Gets Default Background Texture if any
	 * @return
	 */
	public String getBackgroundTexture()
	{
		return this.backgroundTexturePath;
	}
	
	public SceneObject setBackgroundTexture(String path)
	{
		this.backgroundTexturePath = path;
		return this;
	}
	
	/**
	 * Segments are individual Scenes Wrapped in the main screen object to tell long stories.
	 * @param segment
	 * @return
	 */
	public SegmentObject getSegment(int segment)
	{
		return this.screenSegments.get(segment);
	}
	
	@SideOnly(Side.CLIENT)
	public void DrawCutScene()
	{
		if(this.screenSegments != null && this.guiParent.currentPhase <= this.screenSegments.size()-1 && !this.closeScene)
		{
			((SegmentObject)getSegment(this.guiParent.currentPhase)).DrawSegment(this, Minecraft.getMinecraft());
		}
		else this.closeScene=true;
	}
	
}
