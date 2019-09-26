package nl.thedutchmc.discordchatmod;

import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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
			
			//Had to do all this to get the nickname, instead of event.getAuthor().getName(), which gives the user's discord name, not their server nickname
			String author = "";
			List<Member> members = ((MessageReceivedEvent) event).getGuild().getMembers();
			for(Member m : members) {
				if(!m.getUser().isBot()) {
					if(m.getId().equals(((MessageReceivedEvent) event).getAuthor().getId())) {
						if(m.getNickname() != null) {
							author = m.getNickname();
						} else {
							author = ((MessageReceivedEvent) event).getAuthor().getName();
						}
						break;
					}
				}
			}
			
			String message = ((MessageReceivedEvent) event).getMessage().getContentRaw();
			if(!(((MessageReceivedEvent) event).getAuthor().isBot())) {
				if(((GenericMessageEvent) event).getChannel().getId().contentEquals(DiscordChatMod.chatLinkChannelId)) {
			        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(TextFormatting.GRAY + "[Discord] " + TextFormatting.WHITE + author + ": " + message));	
				} if(DiscordChatMod.restrictBotCommandsToChannel) {
					if(((MessageReceivedEvent) event).getChannel().getId().contentEquals(DiscordChatMod.botCommandChannelId)) {
						if(message.equalsIgnoreCase(DiscordChatMod.commandPrefix + "whitelist") && DiscordChatMod.enableWhitelisting) {
							for(Role r : ((MessageReceivedEvent) event).getMember().getRoles()) {
								if(r.getId().equals(DiscordChatMod.whitelistRoleId)) {
									processCommand(event, message, author, "whitelist");
									break;
									}
							} 
						} else if(message.equalsIgnoreCase(DiscordChatMod.commandPrefix + "help")) {
							processCommand(event, message, author, "help");
						}
					}
				} else {
					if(message.equalsIgnoreCase(DiscordChatMod.commandPrefix + "whitelist") && DiscordChatMod.enableWhitelisting) {
						for(Role r : ((MessageReceivedEvent) event).getMember().getRoles()) {
							if(r.getId().equals(DiscordChatMod.whitelistRoleId)) {
								processCommand(event, message, author, "whitelist");
							}
						}
					} else if(message.equalsIgnoreCase(DiscordChatMod.commandPrefix + "help")) {
						processCommand(event, message, author,"help");
					}
				}
			}
		}
	}
	
	public void processCommand(GenericEvent event, String message, String sender, String command) {
		switch(command) {
		case "whitelist":
			FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.executeCommand(FMLCommonHandler.instance().getMinecraftServerInstance(), "whitelist add " + sender);
			sendToDiscord("Added " + sender + " to the whitelist!", DiscordChatMod.instance.getJda(), ((MessageReceivedEvent) event).getChannel().getId());
			break;
		case "help":
			sendToDiscord("**Help Page:**\n"
					+ "> **!whitelist**: Add yourself to the whitelist, the command uses your **discord nickname**, so make sure it is the same as your in-game name! This only works if it is enabled in config!\n"
					+ "> **!help**: Gives this page.", DiscordChatMod.instance.getJda(), ((MessageReceivedEvent) event).getChannel().getId());
			break;
		}
	}
	
	public void sendToDiscord(String message, JDA jda, String channel) {
		jda.getTextChannelById(channel).sendMessage(message).queue();
	}
}