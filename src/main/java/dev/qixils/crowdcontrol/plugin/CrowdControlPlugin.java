package dev.qixils.crowdcontrol.plugin;

import dev.qixils.crowdcontrol.CrowdControl;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class CrowdControlPlugin extends JavaPlugin {
    // actual stuff
    private CrowdControl crowdControl = null;
    public static final TextColor USER_COLOR = TextColor.color(0x9f44db);
    public static final TextColor CMD_COLOR = TextColor.color(0xb15be3);

    @Override
    public void onEnable() {
        crowdControl = new CrowdControl(58431);
        List<Command> commands = RegisterCommands.register(this);
        if (false)
            RegisterCommands.writeCommands(this, commands);
    }

    @Override
    public void onDisable() {
        if (crowdControl == null) return;
        crowdControl.shutdown();
    }

    public static List<Player> getPlayers() {
        return Bukkit.getServer().getOnlinePlayers().stream().filter(player -> !player.isDead()).collect(Collectors.toCollection(ArrayList::new));
    }

    public void registerCommand(String name, Command command) {
        name = name.toLowerCase(Locale.ENGLISH);
        crowdControl.registerHandler(name, command::executeAndNotify);
        getLogger().fine("Registered CC command '"+name+"'");
    }
}
