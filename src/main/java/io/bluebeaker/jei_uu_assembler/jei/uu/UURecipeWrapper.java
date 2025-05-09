package io.bluebeaker.jei_uu_assembler.jei.uu;

import ic2.core.block.machine.tileentity.TileEntityAssemblyBench.UuRecipe;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.ItemName;
import io.bluebeaker.jei_uu_assembler.mixin.AccessorUuRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UURecipeWrapper implements IRecipeWrapper {
	private final IJeiHelpers jeiHelpers;
	private final UuRecipe recipe;

	public UURecipeWrapper(IJeiHelpers jeiHelpers, UuRecipe recipe) {
		this.jeiHelpers = jeiHelpers;
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack recipeOutput = recipe.getRecipeOutput();
		List<List<ItemStack>> inputLists = getInputLists();
		ingredients.setInputLists(ItemStack.class, inputLists);
		ingredients.setOutput(ItemStack.class, recipeOutput);
	}
	private List<List<ItemStack>> getInputLists(){
      	ItemStack uu = ItemName.misc_resource.getItemStack(MiscResourceType.matter);
		List<List<ItemStack>> lists = new ArrayList<>();
		int height=3;
		int width=3;
		for(int y=0; y < height; ++y) {
			boolean[] layer = ((AccessorUuRecipe) recipe).getShape()[y];
			for(int x = 0; x < width; ++x) {
				if (layer[x]) {
					lists.add(x+y*3,Collections.singletonList(uu));
				}else{
					lists.add(x+y*3,null);
				}
			}
		}
		return lists;
	}

}