package nl.thedutchmc.discordchatmod;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DiscordBot implements EventListener {
	
	
	@Override
	public void onEvent(GenericEvent event) {
		
		if(event instanceof MessageReceivedEvent) {
			String sender = ((MessageReceivedEvent) event).getAuthor().getName();
			String content = ((MessageReceivedEvent) event).getMessage().getContentDisplay();
			if(!(((MessageReceivedEvent) event).getAuthor().isBot())) {
				if(((GenericMessageEvent) event).getChannel().getId().contentEquals(DiscordChatMod.channelId)) {
			        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(TextFormatting.BLUE + sender + TextFormatting.WHITE + ": " + content));
				}
			}
		}
	}
	
	public void sendToDiscord(String message, JDA jda) {
		jda.getTextChannelById(DiscordChatMod.channelId).sendMessage(message).queue();
	}
}