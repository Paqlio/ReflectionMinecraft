package Paqlio;

import Paqlio.Commands.CommandInfo;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class template extends JavaPlugin {

    @Override
    public void onEnable() {
        var packetName = getClass().getPackage().getName();
        for (Class<? extends CommandExecutor> clazz1 : new Reflections(packetName + ".Commands")
                .getSubTypesOf(CommandExecutor.class)) {
            try {
                CommandExecutor commandExecutor = clazz1.getDeclaredConstructor().newInstance();
                getCommand(commandExecutor.getClass().getAnnotation(CommandInfo.class).name()).setExecutor(commandExecutor);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packetName + ".Events")
                .getSubTypesOf(Listener.class)) {
            try {
                Listener Listener = (Listener) clazz
                        .getConstructor()
                        .newInstance();
                getServer().getPluginManager().registerEvents(Listener, this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e ) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
