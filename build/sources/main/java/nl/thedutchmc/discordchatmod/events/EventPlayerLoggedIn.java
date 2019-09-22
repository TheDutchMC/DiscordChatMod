package nl.thedutchmc.discordchatmod.events;

import net.dv8tion.jda.api.JDA;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import nl.thedutchmc.discordchatmod.DiscordBot;
import nl.thedutchmc.discordchatmod.DiscordChatMod;

@Mod.EventBusSubscriber(modid = "discordchatmod")
public class EventPlayerLoggedIn {
	
	static JDA jda = DiscordChatMod.instance.getJda();
	static DiscordBot db = DiscordChatMod.instance.getDb();
	
	@SubscribeEvent
	public static void playerLoggedInEvent(PlayerLoggedInEvent event) {
		db.sendToDiscord(":heavy_plus_sign: **" + event.player.getName() + "** joined the server", jda);
	}
}