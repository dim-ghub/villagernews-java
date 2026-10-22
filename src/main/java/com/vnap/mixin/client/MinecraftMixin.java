package com.vnap.mixin.client;

import com.vnap.client.VillagerNewsPriorityPack;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@ModifyArg(
		method = "<init>",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V")
	)
	private RepositorySource[] vnap$addPriorityPack(RepositorySource[] sources) {
		RepositorySource[] extended = Arrays.copyOf(sources, sources.length + 1);
		extended[sources.length] = new VillagerNewsPriorityPack();
		return extended;
	}
}
