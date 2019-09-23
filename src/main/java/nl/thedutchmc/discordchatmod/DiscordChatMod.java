package nl.thedutchmc.discordchatmod;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.reflect.Field;
import java.util.Set;

import javax.security.auth.login.LoginException;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DiscordChatMod.MODID, name = DiscordChatMod.MODNAME, version = DiscordChatMod.MODVERSION, acceptableRemoteVersions = "*")
public class DiscordChatMod {

	public static final String MODID = "discordchatmod";
	public static final String MODNAME = "DiscordChatMod";
	public static final String MODVERSION = "0.0.1";
	
	private static String token = "";
	public static String chatLinkChannelId = "";
	public static String botCommandChannelId = "";
	public static String commandPrefix = "";
	
	private JDA jda;
	DiscordListener dl = new DiscordListener();	
	
	public static boolean enableWhitelisting, restrictBotCommandsToChannel;
	
	public DiscordChatMod() throws NoSuchFieldException, IllegalAccessException {
		Field field = LaunchClassLoader.class.getDeclaredField("classLoaderExceptions");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		Set<String> exclusions = (Set<String>) field.get(Launch.classLoader);
		exclusions.remove("org.apache.commons.");
		exclusions.add("org.apache.commons.lang3.");
		exclusions.add("org.apache.commons.compress.");
		exclusions.add("org.apache.commons.io.");
		exclusions.add("org.apache.commons.logging.");
		exclusions.add("org.apache.commons.codec.");
	}
	
	public JDA getJda() {
		return jda;
	}
	
	public DiscordListener getDl() {
		return dl;
	}
		
	@Mod.Instance
	public static DiscordChatMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig(config);
		try {
			jda = new JDABuilder(token).build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		jda.addEventListener(dl);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	public static void syncConfig(Configuration config) {
		try {
			config.load();
			Property tokenProp = config.get(Configuration.CATEGORY_GENERAL, "token", "BOT TOKEN HERE");
			Property chatLinkChannelIdProp = config.get(Configuration.CATEGORY_GENERAL, "chatLinkChannelId", "CHAT LINK CHAHNEL ID HERE");
			Property botCommandChannelIdProp = config.get(Configuration.CATEGORY_GENERAL, "botCommandChannelId", "CHANNEL ID FOR BOT COMMANDS CHANNEL HERE");
			Property enableWhitelistingProp = config.get(Configuration.CATEGORY_GENERAL, "enableWhitelisting", "false");
			Property restrictBotCommandsToChannelProp = config.get(Configuration.CATEGORY_GENERAL, "restrictBotCommandsToChannel", "true");
			Property commandPrefixProp = config.get(Configuration.CATEGORY_GENERAL, "commandPrefix", "&");
			
			token = tokenProp.getString(); 
			chatLinkChannelId = chatLinkChannelIdProp.getString();
			botCommandChannelId = botCommandChannelIdProp.getString();
			enableWhitelisting = enableWhitelistingProp.getBoolean();
			restrictBotCommandsToChannel = restrictBotCommandsToChannelProp.getBoolean();
			commandPrefix = commandPrefixProp.getString();
			
		} catch (Exception e) {

		} finally {
			if(config.hasChanged()) {
				config.save();
			}
		}
	}
}
