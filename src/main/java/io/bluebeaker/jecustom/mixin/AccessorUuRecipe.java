package io.bluebeaker.jecustom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import ic2.core.block.machine.tileentity.TileEntityAssemblyBench.UuRecipe;

@Mixin(UuRecipe.class)
public interface AccessorUuRecipe {
    @Accessor
    boolean[][] getShape();
}
