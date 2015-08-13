package com.gendeathrow.cutscene.SceneRender.transitions;

import java.lang.reflect.Type;

import com.gendeathrow.cutscene.SceneRender.ActorObject;
import com.gendeathrow.cutscene.SceneRender.ActorObject.ActorType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class Transition
{

	private int transitionTicks;
	
	public int alpha;
	
	private int posX;
	private int posY;
	
	private boolean init;
	
	
	public Transition()
	{
		this.alpha = 255;
		this.init = false;
	}
	
	public void update(ActorObject actor)
	{
		
		if(actor.actorTick <= actor.transitionIn.duration && TransitionType.isType(actor.transitionIn)) Fade(actor, "in");
		else if(actor.actorTick >= (actor.tickLength - actor.transitionOut.duration) && TransitionType.isType(actor.transitionOut)) Fade(actor, "out");
		
		
		this.transitionTicks++;
	}
	
	public void Fade(ActorObject actor, String fadeDir)
	{
		
		
		if(fadeDir == "in") 
		{
			int fadeSteps = ( 255 / actor.transitionIn.duration);
			
			if(this.init == false) this.alpha = 0;
			
			this.alpha = (this.alpha + fadeSteps) > 255 ? 255 : this.alpha + fadeSteps;
			
		}
		
		if(fadeDir == "out")
		{
			int fadeSteps = ( 255 / actor.transitionOut.duration);
			
			if(this.init == false) this.alpha = 255;
			
			this.alpha = (this.alpha - fadeSteps) < 0 ? 0 : this.alpha - fadeSteps;
		}
		this.init = true;
		
		
		
	}
	
	public int getAlpha()
	{
		int alpha = 255;
		
		return alpha; 
	}
	
	public enum TransitionType
	{
		Fade("fade", 5, 5),
		Slide("slide", 5, 5);
		
		String type;
		int duration;
		//int outDuration;
		
		TransitionType(String type, int duration, int outDuration)
		{
			this.type=type;
			this.duration = duration;
			//this.outDuration = outDuration;
		}
		
		static boolean isType(TransitionType in)
		{
			  TransitionType[] types = TransitionType.values();
			    for (TransitionType type : types)
			    {
			    	if(type.type.equals(in.type)) return true;
			    }
			    
			    return false;
		}
		
		TransitionType setInDuration(int newDuration)
		{
			this.duration = newDuration;
			return this;
		}
		
	}
	
	public class TransitionTypeDeserializer implements JsonDeserializer<TransitionType>
	{
	  @Override
	  public TransitionType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  throws JsonParseException
	  {
		  TransitionType[] types = TransitionType.values();
	    for (TransitionType type : types)
	    {
	    	JsonArray data = json.getAsJsonArray();
	    	
	    System.out.println(data.get(0).getAsString());	
	      if (type.type.equals(data.get(0).getAsString()))
	        return type.setInDuration(data.get(1).getAsInt());
	    }
	    return null;
	  }

	}
	
}
