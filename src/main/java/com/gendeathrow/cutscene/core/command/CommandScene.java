package com.gendeathrow.cutscene.core.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.network.packet.PacketScene;
import com.gendeathrow.cutscene.network.packet.PacketScene.SceneControls;

public class CommandScene extends CommandBase 
{

	@Override
	public String getCommandName() 
	{
		return "cutscene";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) 
	{
		return "/cutscene <playername> <play> <CutScene ID>";
	}

	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] astring) 
	{
		EntityPlayerMP player = getPlayer(commandsender, astring[0]);
		
		String sceneID = astring[2];
		
		CutScene.instance.network.sendTo(new PacketScene(sceneID, SceneControls.Play), player);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] strings)
    {
        if(strings.length == 1)
        {
        	return getListOfStringsMatchingLastWord(strings, MinecraftServer.getServer().getAllUsernames());
        } else if(strings.length == 2)
        {
        	return getListOfStringsMatchingLastWord(strings, new String[]{"play"});
        } 
        // TODO have a list of all registered jsons.
//        else if(strings.length == 3)
//        {
//        	return getListOfStringsMatchingLastWord(strings, new String[]{temp, sanity, water, air});
//        } 
        else
        {
        	return new ArrayList<String>();
        }
    }

}
