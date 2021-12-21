package dev.qixils.crowdcontrol.plugin.commands.executeorperish;

import dev.qixils.crowdcontrol.plugin.utils.TextBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Condition {

    // ANYWHERE
    STAND_ON_GRAVEL(new StandOnBlockCondition("a %s block", Material.GRAVEL)),
    STAND_ON_CRAFTING_TABLE(new StandOnBlockCondition("a %s block", Material.CRAFTING_TABLE)),
    STAND_ON_FURNACE(new StandOnBlockCondition("a %s block", Material.FURNACE)),
    STAND_ON_COBBLESTONE(new StandOnBlockCondition("a %s block", Material.COBBLESTONE)),
    STAND_ON_A_PLANK(new StandOnBlockCondition(new TextBuilder("a &awooden plank").build(),
            Material.OAK_PLANKS,
            Material.BIRCH_PLANKS,
            Material.ACACIA_PLANKS,
            Material.CRIMSON_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.WARPED_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.SPRUCE_PLANKS
    )),
    STAND_ON_A_STRIPPED_LOG(new StandOnBlockCondition(new TextBuilder("a &astripped log").build(),
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_CRIMSON_STEM,
            Material.STRIPPED_WARPED_STEM,
            Material.STRIPPED_OAK_WOOD,
            Material.STRIPPED_BIRCH_WOOD,
            Material.STRIPPED_ACACIA_WOOD,
            Material.STRIPPED_JUNGLE_WOOD,
            Material.STRIPPED_DARK_OAK_WOOD,
            Material.STRIPPED_SPRUCE_WOOD,
            Material.STRIPPED_CRIMSON_HYPHAE,
            Material.STRIPPED_WARPED_HYPHAE
    )),

    // OVERWORLD ONLY
    STAND_ON_DIRT(new StandOnBlockCondition("a %s block", Material.DIRT).allowInEnvironment(World.Environment.NORMAL)),
    STAND_ON_STONE(new StandOnBlockCondition("a %s block", Material.STONE).allowInEnvironment(World.Environment.NORMAL)),
    STAND_ON_SAND(new StandOnBlockCondition("a %s block", Material.SAND).allowInEnvironment(World.Environment.NORMAL)),
    STAND_ON_CLAY(new StandOnBlockCondition("a %s block", Material.CLAY).allowInEnvironment(World.Environment.NORMAL)),
    STAND_ON_DEEPSLATE(new StandOnBlockCondition("a %s block", Material.DEEPSLATE).allowInEnvironment(World.Environment.NORMAL)),
    STAND_ON_A_BED(new StandOnBlockCondition(new TextBuilder("a &abed").build(),
            Material.WHITE_BED,
            Material.ORANGE_BED,
            Material.MAGENTA_BED,
            Material.LIGHT_BLUE_BED,
            Material.YELLOW_BED,
            Material.LIME_BED,
            Material.PINK_BED,
            Material.GRAY_BED,
            Material.LIGHT_GRAY_BED,
            Material.CYAN_BED,
            Material.PURPLE_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.GREEN_BED,
            Material.RED_BED,
            Material.BLACK_BED
    ).allowInEnvironment(World.Environment.NORMAL)),

    // NETHER ONLY
    STAND_ON_NETHERRACK(new StandOnBlockCondition("a %s block", Material.NETHERRACK).allowInEnvironment(World.Environment.NETHER)),
    STAND_ON_SOUL_SAND(new StandOnBlockCondition("a %s block", Material.SOUL_SAND).allowInEnvironment(World.Environment.NETHER)),
    STAND_ON_SOUL_SOIL(new StandOnBlockCondition("a %s block", Material.SOUL_SOIL).allowInEnvironment(World.Environment.NETHER)),
    STAND_ON_NETHER_WART_BLOCK(new StandOnBlockCondition("a %s block", Material.NETHER_WART_BLOCK).allowInEnvironment(World.Environment.NETHER)),
    STAND_ON_WARPED_WART_BLOCK(new StandOnBlockCondition("a %s block", Material.WARPED_WART_BLOCK).allowInEnvironment(World.Environment.NETHER)),

    // THE END ONLY
    STAND_ON_BEDROCK(new StandOnBlockCondition("a %s block", Material.OBSIDIAN).allowInEnvironment(World.Environment.THE_END)),

    // HYBRIDS
    STAND_ON_OBSIDIAN(new StandOnBlockCondition("a %s block", Material.OBSIDIAN)
            .allowInEnvironment(World.Environment.NETHER) // Back to the portal we go :)
            .allowInEnvironment(World.Environment.THE_END)
    ),

    // OBTAINS
    OBTAIN_STONE_HOE(new ObtainItemCondition("a %s", Material.STONE_HOE)),
    OBTAIN_WOODEN_HOE(new ObtainItemCondition("a %s", Material.WOODEN_HOE)),
    OBTAIN_STONE(new ObtainItemCondition("a %s block", Material.STONE)),
    OBTAIN_BONE_MEAL(new ObtainItemCondition("a %s", Material.BONE_MEAL)),
    OBTAIN_ARROW(new ObtainItemCondition("an %s", Material.ARROW)),
    OBTAIN_IRON_NUGGET(new ObtainItemCondition("an %s", Material.IRON_NUGGET)),
    OBTAIN_STRING(new ObtainItemCondition("a %s", Material.STRING)),
    OBTAIN_FLINT_AND_STEEL(new ObtainItemCondition("a %s", Material.FLINT_AND_STEEL)),
    OBTAIN_FLINT(new ObtainItemCondition("a %s", Material.FLINT)),
    OBTAIN_GUNPOWDER(new ObtainItemCondition("a %s", Material.GUNPOWDER)),
    OBTAIN_TORCH(new ObtainItemCondition("a %s", Material.TORCH)),
    OBTAIN_CLAY_BALL(new ObtainItemCondition("a %s", Material.CLAY_BALL)),
    OBTAIN_WHEAT_SEEDS(new ObtainItemCondition("%s", Material.WHEAT_SEEDS)),
    ;

    private static final List<SuccessCondition> CONDITIONS;

    static {
        List<SuccessCondition> conditions = new ArrayList<>(values().length);
        for (Condition cond : values())
            conditions.add(cond.getCondition());
        CONDITIONS = Collections.unmodifiableList(conditions);
    }

    private final SuccessCondition condition;

    public static List<SuccessCondition> getValidItemsForEnvironments(List<World.Environment> worldEnvironments) {
        return CONDITIONS.stream().filter(c -> c.getAllowedEnvironments().stream().anyMatch(worldEnvironments::contains)).toList();
    }
}
