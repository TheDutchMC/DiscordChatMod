package nl.thedutchmc.discordchatmod;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DiscordListener implements EventListener {
	
	@Override
	public void onEvent(GenericEvent event) {
		
		if(event instanceof MessageReceivedEvent) {
			String sender = ((MessageReceivedEvent) event).getAuthor().getName();
			String message = ((MessageReceivedEvent) event).getMessage().getContentDisplay();
			if(!(((MessageReceivedEvent) event).getAuthor().isBot())) {
				if(((GenericMessageEvent) event).getChannel().getId().contentEquals(DiscordChatMod.channelId)) {
			        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(TextFormatting.BLUE + sender + TextFormatting.WHITE + ": " + message));
				} else if(((MessageReceivedEvent) event).getChannel().getId().contentEquals(DiscordChatMod.whitelistChannelId)) {
					if(message.equalsIgnoreCase("!whitelist") && DiscordChatMod.enableWhitelisting) {
						FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.executeCommand(FMLCommonHandler.instance().getMinecraftServerInstance(), "whitelist add " + sender);
						sendToDiscord("Added " + sender + " to the whitelist!", DiscordChatMod.instance.getJda(), DiscordChatMod.whitelistChannelId);
					} else if(message.equalsIgnoreCase("!help")) {
						sendToDiscord("**Help Page:**\n"
									+ "> **!whitelist**: Add yourself to the whitelist, the command uses your **discord nickname**, so make sure it is the same as your in-game name! This only works if it is enabled in config!\n"
									+ "> **!help**: Gives this page.", DiscordChatMod.instance.getJda(), DiscordChatMod.whitelistChannelId);
					}
				}
			}
		}
	}
	
	public void sendToDiscord(String message, JDA jda, String channel) {
		jda.getTextChannelById(channel).sendMessage(message).queue();
	}
}