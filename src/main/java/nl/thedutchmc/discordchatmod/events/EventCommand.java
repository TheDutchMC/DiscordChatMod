package nl.thedutchmc.discordchatmod.events;

import net.dv8tion.jda.api.JDA;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.thedutchmc.discordchatmod.DiscordChatMod;
import nl.thedutchmc.discordchatmod.DiscordListener;

@Mod.EventBusSubscriber(modid = "discordchatmod")
public class EventCommand {
	
	static JDA jda = DiscordChatMod.instance.getJda();
	static DiscordListener db = DiscordChatMod.instance.getDl();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void commandEvent(CommandEvent event) {
		
		String name = event.getCommand().getName();
		if(name.equalsIgnoreCase("say")) {
			String[] commandArgs = event.getParameters();
			String message = "";
			for(String s : commandArgs) {
				message += s + " ";
			}
			
            message.substring(0, message.length() - 1);
            
    		db.sendToDiscord("**[" + event.getSender().getName() + "]** " + message, jda, DiscordChatMod.chatLinkChannelId);

		}
	}
}
