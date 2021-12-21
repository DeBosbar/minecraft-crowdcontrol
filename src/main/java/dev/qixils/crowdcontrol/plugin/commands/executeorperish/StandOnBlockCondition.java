package dev.qixils.crowdcontrol.plugin.commands.executeorperish;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class StandOnBlockCondition implements SuccessCondition {
    private final Set<Material> blocks;
    private final Component component;
    private List<World.Environment> allowedEnvironments = new ArrayList<>();

    public StandOnBlockCondition(String displayText, Material displayItem, Material... otherItems) {
        this.blocks = EnumSet.of(displayItem, otherItems);

        component = Component.text("Stand on ").append(Component.text(displayText)
                .replaceText(builder -> builder.matchLiteral("%s").once()
                        .replacement(Component.translatable(displayItem).color(NamedTextColor.GREEN)))
        );
    }

    public StandOnBlockCondition(Component display, Material first, Material... other) {
        this.blocks = EnumSet.of(first, other);
        this.component = Component.text("Stand on ").append(display);
    }

    public StandOnBlockCondition allowInEnvironment(World.Environment worldEnvironment) {
        this.allowedEnvironments.add(worldEnvironment);
        return this;
    }

    @Override
    public List<World.Environment> getAllowedEnvironments() {
        return this.allowedEnvironments;
    }

    @Override
    public boolean hasSucceeded(Player player) {
        Location location = player.getLocation();
        return blocks.contains(location.getBlock().getType())
                || blocks.contains(location.subtract(0, 1, 0).getBlock().getType());
    }
}
