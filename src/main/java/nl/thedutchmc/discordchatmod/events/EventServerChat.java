package nl.thedutchmc.discordchatmod.events;

import net.dv8tion.jda.api.JDA;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.thedutchmc.discordchatmod.DiscordListener;
import nl.thedutchmc.discordchatmod.DiscordChatMod;

@Mod.EventBusSubscriber(modid = "discordchatmod")
public class EventServerChat {
	
	static JDA jda = DiscordChatMod.instance.getJda();
	static DiscordListener db = DiscordChatMod.instance.getDl();
	
	@SubscribeEvent
	public static void serverChatEvent(ServerChatEvent event) {
		String message = event.getMessage().toString();
		String author = event.getPlayer().getName();
		db.sendToDiscord("[**"+ author + "**]: " + message, jda, DiscordChatMod.chatLinkChannelId);
	}
}
