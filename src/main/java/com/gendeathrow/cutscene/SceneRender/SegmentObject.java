package com.gendeathrow.cutscene.SceneRender;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.utils.RenderAssist;
import com.gendeathrow.cutscene.utils.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SegmentObject
{
	
	public transient int tickLength;
	public ArrayList<ActorObject> actorArray;
	public int segmentTick;
	
	public transient SceneObject scene;
	public transient long segmentLength;
	
	public SegmentObject()
	{
	
	}
	
	public void init(SceneObject sceneObj)
	{
		this.scene = sceneObj;
		
		this.segmentLength = this.getLength();

		for(ActorObject actor : this.actorArray)
		{
				actor.init(this);
		}
		
		Collections.sort(this.actorArray);
		
		// Sort ActorList by Z level
			
	}
	
	public long getLength()
	{
		long totalTicks = 0;
		long check = 0;
		long max = 0;
		for(ActorObject actor : this.actorArray)
		{
			check = actor.getStopTime();
			if(check > max) 
			{
				max = check; 
			}
		}
		
		totalTicks += max;
		return totalTicks;
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
	
	boolean firstload = true;
	long startTime;
	
	private long getSegmentDuration()
	{
		return (Minecraft.getSystemTime() - this.startTime);
	}
	
	@SideOnly(Side.CLIENT)
	public void DrawSegment(SceneObject sceneObject, Minecraft mc)
	{
		if (firstload) { firstload = false;  this.startTime = Minecraft.getSystemTime();}
		
			int width = mc.currentScreen.width;
			
			if(sceneObject.showDebug) 
			{
					sceneObject.guiParent.fontObj.drawString("Current Segment: "+sceneObject.guiParent.currentPhase,0, mc.fontRenderer.FONT_HEIGHT * 2, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
					sceneObject.guiParent.fontObj.drawString("Duration:"+ Utils.getTimeFormater(this.getSegmentDuration()),0, mc.fontRenderer.FONT_HEIGHT * 3 + 3, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
					sceneObject.guiParent.fontObj.drawString("Total Duration: "+ Utils.getTimeFormater(this.segmentLength),0, mc.fontRenderer.FONT_HEIGHT * 4 + 6, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
			}

			this.segmentTick++;
			
			for(ActorObject actor : this.actorArray)
			{
				if(this.getSegmentDuration() >= actor.getStartTime() && this.getSegmentDuration() < actor.getStopTime())
				{
					actor.DrawActor(sceneObject, this, mc);
				}				
				
				
			}

			if(this.getSegmentDuration() >= this.segmentLength)
			{
				sceneObject.guiParent.currentPhase++;
			}
				
	}
}
