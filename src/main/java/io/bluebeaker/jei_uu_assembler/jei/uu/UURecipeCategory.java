package io.bluebeaker.jei_uu_assembler.jei.uu;

import java.util.List;

import ic2.core.IC2;
import ic2.core.block.TeBlockRegistry;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class UURecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    static IJeiHelpers jeiHelpers;
	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;
    public static final String UID = "jei_uu_assembler.uuassembly";

	public static final int width = 116;
	public static final int height = 54;

	private final IDrawable background;
	private final String localizedName;
	private final ICraftingGridHelper craftingGridHelper;

    public UURecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Constants.RECIPE_GUI_VANILLA;
		background = guiHelper.createDrawable(location, 0, 60, width, height);
		localizedName = Utils.Translator.localize(TeBlockRegistry.get(TeBlock.uu_assembly_bench.getIdentifier()).getItemStack(TeBlock.uu_assembly_bench).getTranslationKey(), null);
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(craftOutputSlot, false, 94, 18);

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		}
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		if (recipeWrapper instanceof UURecipeWrapper) {
			craftingGridHelper.setInputs(guiItemStacks, inputs, 3, 3);
		}
		guiItemStacks.set(craftOutputSlot, outputs.get(0));
    }

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return JeiUuAssemblerMod.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

    
}
