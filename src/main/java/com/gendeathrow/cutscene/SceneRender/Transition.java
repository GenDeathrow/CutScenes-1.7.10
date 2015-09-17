package com.gendeathrow.cutscene.SceneRender;

import java.lang.reflect.Type;

import org.apache.logging.log4j.Level;

import com.gendeathrow.cutscene.core.CutScene;
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
	
	private double fadeSteps = 0;
	
	
	public Transition()
	{
		this.alpha = 255;
		this.fadeSteps = 0;
		this.init = false;
	}
	
	public void update(ActorObject actor)
	{

		//System.out.println(actor.getActorDuration() +"<="+ actor.transitionIn.duration +"&&"+ TransitionType.isType(actor.transitionIn) +"="+ (actor.getActorDuration() <= actor.transitionIn.duration && TransitionType.isType(actor.transitionIn)));
		
		//System.out.println(actor.getActorDuration() +">="+ (actor.getLength() - actor.transitionOut.duration) +"&&"+ TransitionType.isType(actor.transitionOut) +"="+ (actor.getActorDuration() >= (actor.getLength() - actor.transitionOut.duration) && TransitionType.isType(actor.transitionOut)));
		
		
		if(actor.getActorDuration() <= actor.transitionIn.duration && TransitionType.isType(actor.transitionIn)) Fade(actor, true);
		else if(actor.getActorDuration() >= (actor.getLength() - actor.transitionOut.duration) && TransitionType.isType(actor.transitionOut)) Fade(actor, false);
		
		this.transitionTicks++;
	}
	
	public void Fade(ActorObject actor, boolean transitionIn)
	{
		if(transitionIn) 
		{
			this.fadeSteps += (double)( 255 /  (double)actor.transitionIn.duration);
			
			int add = (int) Math.round(this.fadeSteps);
			
			this.alpha = (this.alpha + add) > 255 ? 255 : this.alpha + add;
			
		}else
		{
			this.fadeSteps -= (double)( 255 /  (double)actor.transitionOut.duration);
			
			int add = (int) Math.round(this.fadeSteps);
			
			this.alpha = (this.alpha - add) < 0 ? 0 : this.alpha - add;
		}
		
		this.init = true;
	}
	
	public void Slide(ActorObject actor, boolean transitionIn)
	{
		
	}
	
	public enum TransitionType
	{
		Fade("fade", 500, 0),
		Slide("slide", 500, 1);
		
		String type;
		long duration;
		int side;
		
		TransitionType(String type, long duration)
		{
			this.type=type;
			this.duration = duration;
			this.side = 0;
		}
		
		TransitionType(String type, long duration, int outDuration)
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
		
		TransitionType setInDuration(long newDuration)
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
        	  long val = 0;
        	  
			  JsonArray timeList = data.get(1).getAsJsonArray();

			  int minInMilisecs = 0;
			  int secInMilisecs = 0;
			  int milisecs = 0;
			  
			  if(timeList.size() != 3) 
			  {
				  CutScene.logger.log(Level.ERROR, "Tried to Deserialize Duration into Milisecs! Time is not in correct format in json [min,secs,milisecs]");
			  }
			  minInMilisecs = (timeList.get(0).getAsInt() * 60000);
			  secInMilisecs = (timeList.get(1).getAsInt() * 1000);
			  milisecs = timeList.get(2).getAsInt();
			  
			  val = (long)(minInMilisecs + secInMilisecs + milisecs);
			  
	
	    	  if(data.size() == 3)
	    	  {
	    		  return type.setInDuration(val).setSide(data.get(2).getAsInt());
	    	  }
	    	  else if(data.size() == 2)
	    	  {
	    		  return type.setInDuration(val);
	    	  }
	        
	      }
	    }
	    return null;
	  }

	}
	
}
