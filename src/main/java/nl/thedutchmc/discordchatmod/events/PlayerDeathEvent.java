package nl.thedutchmc.discordchatmod.events;

import net.dv8tion.jda.api.JDA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.thedutchmc.discordchatmod.DiscordChatMod;
import nl.thedutchmc.discordchatmod.DiscordListener;

@Mod.EventBusSubscriber(modid = "discordchatmod")
public class PlayerDeathEvent {

	
	static JDA jda = DiscordChatMod.instance.getJda();
	static DiscordListener db = DiscordChatMod.instance.getDl();
	
	@SubscribeEvent
	public static void onPlayerDeath(LivingDeathEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			String deathMessage = event.getSource().getDeathMessage(event.getEntityLiving()).getUnformattedText();
			
			System.out.println(deathMessage);
    		db.sendToDiscord("**" + deathMessage + "**", jda, DiscordChatMod.chatLinkChannelId);

		}
	}
}
