package com.gendeathrow.cutscene.SceneRender;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SegmentObject
{
	
	public transient int tickLength;
	public ArrayList<ActorObject> actorArray;
	public int segmentTick;
	
	public transient SceneObject scene;
	
	public SegmentObject()
	{
	
	}
	
	public void init(SceneObject sceneObj)
	{
		this.scene = sceneObj;

		for(ActorObject actor : this.actorArray)
		{
				actor.init(this);
		}
		
		Collections.sort(this.actorArray);
		
		// Sort ActorList by Z level
			
	}
	
	public int getTickLength()
	{
		int totalTicks = 0;
		int check = 0;
		int longestDuration = 0;
		for(ActorObject actor : this.actorArray)
		{
			check = (actor.tickLength + actor.startTick);
			if(check > longestDuration) longestDuration = check; 
		}
		totalTicks += check + 10;
		return totalTicks;
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
	public void DrawSegment(SceneObject sceneObject, Minecraft mc)
	{
			int width = mc.currentScreen.width;
			if(sceneObject.showDebug) sceneObject.guiParent.fontObj.drawString("CurSegment: "+sceneObject.guiParent.currentPhase + " Segment Frame:"+ this.segmentTick,width/2-30, 0, RenderAssist.getColorFromRGBA(255, 255, 255, 255));

			this.segmentTick++;
			
			for(ActorObject actor : this.actorArray)
			{
				if(this.segmentTick >= actor.startTick && this.segmentTick < actor.getEndTick())
				{
					actor.DrawActor(sceneObject, this, mc);
				}
			}
			
			if(this.segmentTick >= this.getTickLength())
			{
				sceneObject.guiParent.currentPhase++;
			}
				
	}
}
