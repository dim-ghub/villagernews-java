package com.vnap.client;

import com.vnap.VillagerNewsAddonPort;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Re-serves the mod's own assets as a resource pack pinned above every other
 * pack, so packs such as Fresh Animations cannot replace the Villager News
 * models, textures, and sounds. Only offered while Fresh Animations
 * compatibility is enabled.
 */
public final class VillagerNewsPriorityPack implements RepositorySource {
	public static final String ID = VillagerNewsAddonPort.MOD_ID + "/priority";
	private static final Component TITLE = Component.literal("Villager News Priority");
	private static final Pack.Metadata METADATA = new Pack.Metadata(
		Component.literal("Keeps Villager News above other resource packs"),
		PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of());
	// Required packs are always selected; a fixed top position keeps packs enabled later beneath it.
	private static final PackSelectionConfig SELECTION = new PackSelectionConfig(true, Pack.Position.TOP, true);

	@Override
	public void loadPacks(Consumer<Pack> consumer) {
		if (!VillagerNewsClientSettings.freshAnimationsCompatibility()) return;
		Optional<Path> root = assetRoot();
		if (root.isEmpty()) {
			VillagerNewsAddonPort.LOGGER.warn("Could not find the Villager News assets for the priority resource pack");
			return;
		}
		PackLocationInfo location = new PackLocationInfo(ID, TITLE, PackSource.BUILT_IN, Optional.empty());
		consumer.accept(new Pack(location, new PathPackResources.PathResourcesSupplier(root.get()), METADATA, SELECTION));
	}

	public static void setEnabled(boolean enabled) {
		if (VillagerNewsClientSettings.freshAnimationsCompatibility() == enabled) return;
		VillagerNewsClientSettings.setFreshAnimationsCompatibility(enabled);
		Minecraft minecraft = Minecraft.getInstance();
		PackRepository repository = minecraft.getResourcePackRepository();
		repository.reload();
		minecraft.options.updateResourcePacks(repository);
		minecraft.reloadResourcePacks();
	}

	private static Optional<Path> assetRoot() {
		return FabricLoader.getInstance().getModContainer(VillagerNewsAddonPort.MOD_ID).stream()
			.flatMap(container -> container.getRootPaths().stream())
			.filter(path -> Files.isDirectory(path.resolve("assets").resolve(VillagerNewsAddonPort.MOD_ID)))
			.findFirst();
	}
}
