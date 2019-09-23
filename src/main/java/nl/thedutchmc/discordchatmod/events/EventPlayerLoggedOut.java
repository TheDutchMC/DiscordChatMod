package nl.thedutchmc.discordchatmod.events;

import net.dv8tion.jda.api.JDA;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import nl.thedutchmc.discordchatmod.DiscordListener;
import nl.thedutchmc.discordchatmod.DiscordChatMod;

@Mod.EventBusSubscriber(modid = "discordchatmod")
public class EventPlayerLoggedOut {
	
	static JDA jda = DiscordChatMod.instance.getJda();
	static DiscordListener db = DiscordChatMod.instance.getDl();
	
	@SubscribeEvent
	public static void playerLoggedOutEvent(PlayerLoggedOutEvent event) {
		db.sendToDiscord(":heavy_minus_sign: **" + event.player.getName() + "** left the server", jda, DiscordChatMod.chatLinkChannelId);

	}
}
