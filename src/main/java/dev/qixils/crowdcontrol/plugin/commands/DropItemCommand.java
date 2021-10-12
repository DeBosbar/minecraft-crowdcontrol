package dev.qixils.crowdcontrol.plugin.commands;

import dev.qixils.crowdcontrol.plugin.CrowdControlPlugin;
import dev.qixils.crowdcontrol.plugin.ImmediateCommand;
import dev.qixils.crowdcontrol.socket.Request;
import dev.qixils.crowdcontrol.socket.Response;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class DropItemCommand extends ImmediateCommand {
	public DropItemCommand(CrowdControlPlugin plugin) {
		super(plugin);
	}

	private final String effectName = "drop_item";
	private final String displayName = "Drop Held Item";

	@Override
	public Response.@NotNull Builder executeImmediately(@NotNull Request request) {
		Response.Builder result = Response.builder().type(Response.ResultType.FAILURE).message("No players were holding items");
		for (Player player : CrowdControlPlugin.getPlayers()) {
			if (!player.getInventory().getItemInMainHand().getType().isEmpty()) {
				Bukkit.getScheduler().runTask(plugin, () -> {
					player.dropItem(true);
					player.updateInventory();
				});
				result.type(Response.ResultType.SUCCESS).message("SUCCESS");
			}
		}
		return result;
	}
}
