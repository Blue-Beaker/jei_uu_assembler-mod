package io.bluebeaker.jei_uu_assembler.jei.uu;

import java.util.ArrayList;
import java.util.List;

import ic2.core.block.machine.tileentity.TileEntityAssemblyBench;
import ic2.core.block.machine.tileentity.TileEntityAssemblyBench.UuRecipe;
import io.bluebeaker.jei_uu_assembler.jei.PackPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.crafting.IRecipe;

public class UURecipeMaker {

	private UURecipeMaker() {
	}
	public static List<IRecipeWrapper> getUuRecipe() {
		List<IRecipeWrapper> recipes = new ArrayList<>();
		for (IRecipe recipe : TileEntityAssemblyBench.RECIPES) {
			if(recipe instanceof UuRecipe)
			recipes.add(new UURecipeWrapper(PackPlugin.modRegistry.getJeiHelpers(), (UuRecipe)recipe));
		}
		return recipes;
	}
}