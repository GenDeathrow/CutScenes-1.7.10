package com.gendeathrow.cutscene.SceneRender;

import java.util.ArrayList;

import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SceneObject 
{

	public int closeOnTicks;
	private int defaultBackgroundColor;

	private String backgroundTexturePath;
	//public ResourceLocation backgroundResourceLocation;

	/** Segments contain new frames within the current Cutscene Object	 */
	public ArrayList<SegmentObject> screenSegments;
	private int curSegment;
	
	
	public SceneObject(){}
	
	
	public void init()
	{
		for(SegmentObject segment : this.screenSegments)
		{
			segment.init();
		}
	}
	
	/**
	 * Total Length of Ticks the Cutsceen will show.
	 * @return
	 */
	public int getCloseOnTicks()
	{
		return this.closeOnTicks;
	}
	
	public SceneObject setCloseOnTicks(int close)
	{
		this.closeOnTicks = close;
		return this;
	}
	
	/** 
	 * Returns Default Background color in Argb int
	 * @return
	 */
	public int getDefaltBackgroundColor()
	{
		return this.defaultBackgroundColor;
	}
	
	public SceneObject setBackgroundColor(int red, int blue, int green, int alpha)
	{
		this.defaultBackgroundColor = RenderAssist.getColorFromRGBA(red, green, blue, alpha);
		return this;
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
		
	}
	
}
