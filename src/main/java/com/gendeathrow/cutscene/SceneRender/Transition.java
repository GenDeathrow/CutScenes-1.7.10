package com.gendeathrow.cutscene.SceneRender;

import java.lang.reflect.Type;

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

		if(actor.actorTick <= actor.transitionIn.duration && TransitionType.isType(actor.transitionIn)) Fade(actor, true);
		else if(actor.actorTick >= (actor.tickLength - actor.transitionOut.duration) && TransitionType.isType(actor.transitionOut)) Fade(actor, false);
		
		
		this.transitionTicks++;
	}
	
	public void Fade(ActorObject actor, boolean transitionIn)
	{
		if(transitionIn) 
		{
			int fadeSteps = ( 255 / actor.transitionIn.duration);
			
			this.alpha = (this.alpha + fadeSteps) > 255 ? 255 : this.alpha + fadeSteps;
			
		}else
		{
			int fadeSteps = ( 255 / actor.transitionOut.duration);
			
			this.alpha = (this.alpha - fadeSteps) < 0 ? 0 : this.alpha - fadeSteps;
		}
		this.init = true;
	}
	
	public void Slide(ActorObject actor, boolean transitionIn)
	{
		
	}
	
	public enum TransitionType
	{
		Fade("fade", 5, 0),
		Slide("slide", 5, 1);
		
		String type;
		int duration;
		int side;
		
		TransitionType(String type, int duration)
		{
			this.type=type;
			this.duration = duration;
			this.side = 0;
		}
		
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

		TransitionType setSide(int side)
		{
			this.side = side;
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
	    	
	      if (type.type.equals(data.get(0).getAsString()))
	      {
	    	  if(data.size() == 3)
	    	  {
	    		  return type.setInDuration(data.get(1).getAsInt()).setSide(data.get(2).getAsInt());
	    	  }
	    	  else if(data.size() == 2)
	    	  {
	    		  return type.setInDuration(data.get(1).getAsInt());
	    	  }
	        
	      }
	    }
	    return null;
	  }

	}
	
}
