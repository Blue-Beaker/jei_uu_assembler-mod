package io.bluebeaker.jei_uu_assembler.mixin;

import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TileEntityMassFabricator.class,remap = false)
public interface AccessorTileEntityMassFabricator {
    @Accessor("REQUIRED_SCRAP")
    static int getRequiredScrap() {
        throw new AssertionError();
    }
}
