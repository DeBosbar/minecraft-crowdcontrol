package dev.qixils.crowdcontrol.plugin.commands;

import dev.qixils.crowdcontrol.plugin.CrowdControlPlugin;
import dev.qixils.crowdcontrol.plugin.ImmediateCommand;
import dev.qixils.crowdcontrol.plugin.utils.TextUtil;
import dev.qixils.crowdcontrol.socket.Request;
import dev.qixils.crowdcontrol.socket.Response;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class TakeItemCommand extends ImmediateCommand {
    private final Material item;
    private final String effectName;
    private final String displayName;

    public TakeItemCommand(CrowdControlPlugin plugin, Material item) {
        super(plugin);
        this.item = item;
        this.effectName = "take_" + item.name();
        this.displayName = "Take " + TextUtil.translate(item);
    }

    @Override
    public Response.@NotNull Builder executeImmediately(@NotNull List<@NotNull Player> players, @NotNull Request request) {
        for (Player player : players) {
            boolean taken = false;
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack == null) {
                    continue;
                }
                if (itemStack.getType() == item) {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    taken = true;
                    break;
                }
            }
            if (taken && item == Material.END_PORTAL_FRAME)
                break;
        }
        return request.buildResponse().type(Response.ResultType.SUCCESS);
    }
}
