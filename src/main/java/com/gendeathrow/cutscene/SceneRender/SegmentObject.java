package com.gendeathrow.cutscene.SceneRender;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SegmentObject
{

	public int tickLength;
	public ArrayList<ActorObject> actorArray;
	public int segmentTick;
	
	public SegmentObject()
	{
	
	}
	
	public void init()
	{
		for(ActorObject actor : this.actorArray)
		{
				actor.init();
		}
			
	}
	
	public int getTickLength()
	{
		return this.tickLength;
	}
	
	public void setTickLength(int length)
	{
		this.tickLength = length;
	}
	
	public ArrayList getActorArray()
	{
		return this.actorArray;
	}
	
	public ActorObject getActorIndex(int index)
	{
		return (ActorObject) this.actorArray.get(index);
	}
	
	public void removeStringfromIndex(int index)
	{
		this.actorArray.remove(index);
	}
	
	
	@SideOnly(Side.CLIENT)
	public void DrawSegment(CutSceneGui cutSceneGui, Minecraft mc, FontRenderer fontObj)
	{
			int width = mc.currentScreen.width;
			fontObj.drawString("CurSegment: "+cutSceneGui.currentPhase + " Segment Frame:"+ this.segmentTick,width/2-30, 0, RenderAssist.getColorFromRGBA(255, 255, 255, 255));

			this.segmentTick++;
			
			for(ActorObject actor : this.actorArray)
			{
				if(this.segmentTick >= actor.startTick && this.segmentTick <= actor.getEndTick())
				{
					actor.DrawActor(cutSceneGui, this, mc, fontObj);
				}
			}
			
			if(this.segmentTick >= this.getTickLength())
			{
				cutSceneGui.currentPhase++;
			}
				
	}
}
