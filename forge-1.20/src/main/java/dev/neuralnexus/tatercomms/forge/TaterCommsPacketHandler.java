package dev.neuralnexus.tatercomms.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.minecraftforge.network.NetworkRegistry.ACCEPTVANILLA;

public class TaterCommsPacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel PLAYER_ADVANCEMENT_FINISHED_INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("tatercomms", "player_advancement_finished"),
            () -> PROTOCOL_VERSION,
            ACCEPTVANILLA::equals,
            ACCEPTVANILLA::equals
    );
    public static final SimpleChannel PLAYER_DEATH_INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("tatercomms", "player_death"),
            () -> PROTOCOL_VERSION,
            ACCEPTVANILLA::equals,
            ACCEPTVANILLA::equals
    );
}
