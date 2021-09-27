package dev.qixils.crowdcontrol.plugin.commands;

import dev.qixils.crowdcontrol.plugin.Command;
import dev.qixils.crowdcontrol.plugin.CrowdControlPlugin;
import dev.qixils.crowdcontrol.plugin.utils.TextUtil;
import dev.qixils.crowdcontrol.socket.Request;
import dev.qixils.crowdcontrol.socket.Response;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

@Getter
public class SummonEntityCommand extends Command {
    protected final EntityType entityType;
    protected static final int SPAWN_RADIUS = 7;
    private final String effectName;
    private final String displayName;

    public SummonEntityCommand(CrowdControlPlugin plugin, EntityType entityType) {
        super(plugin);
        this.entityType = entityType;
        this.effectName = "entity_" + entityType.name();
        this.displayName = "Summon " + TextUtil.translate(entityType);
    }

    @Override
    public Response.@NotNull Result execute(@NotNull Request request) {
        for (Player player : CrowdControlPlugin.getPlayers()) {
            Bukkit.getScheduler().runTask(plugin, () -> spawnEntity(request.getViewer(), player));
        }
        return Response.Result.SUCCESS;
    }

    protected Entity spawnEntity(String viewer, Player player) {
        Entity entity = player.getWorld().spawnEntity(player.getLocation(), entityType);
        entity.setCustomName(viewer);
        entity.setCustomNameVisible(true);
        if (entity instanceof Tameable tameable)
            tameable.setOwner(player);
        return entity;
    }
}
