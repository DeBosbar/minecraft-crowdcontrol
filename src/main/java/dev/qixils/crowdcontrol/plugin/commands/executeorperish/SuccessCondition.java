package dev.qixils.crowdcontrol.plugin.commands.executeorperish;

import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface SuccessCondition {
    Component getComponent();

    List<World.Environment> getAllowedEnvironments();

    boolean hasSucceeded(Player player);
}
