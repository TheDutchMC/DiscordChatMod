package nl.thedutchmc.discordchatmod;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.reflect.Field;
import java.util.Set;

import javax.security.auth.login.LoginException;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DiscordChatMod.MODID, name = DiscordChatMod.MODNAME, version = DiscordChatMod.MODVERSION, acceptableRemoteVersions = "*")
public class DiscordChatMod {

	public static final String MODID = "discordchatmod";
	public static final String MODNAME = "DiscordChatMod";
	public static final String MODVERSION = "0.0.1";
	
	private final String TOKEN = "TOKEN HERE";	
	private JDA jda;
	DiscordBot db = new DiscordBot();
	
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
	
	public DiscordBot getDb() {
		return db;
	}
	
	
	final CommonProxy commonProxy = new CommonProxy();
	
	@Mod.Instance
	public static DiscordChatMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		commonProxy.preInit(event);
		
		try {
			jda = new JDABuilder(TOKEN).build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		jda.addEventListener(db);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		commonProxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		commonProxy.postInit(event);
	}
}
