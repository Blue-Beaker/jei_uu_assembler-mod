package io.bluebeaker.jecustom.jei;

import java.util.List;

import io.bluebeaker.jecustom.JecustomMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public abstract class JECustomRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
	private final IDrawable background;
	private final String localizedName;

	public JECustomRecipeCategory(IDrawable background, String unlocalizedName) {
		this.background = background;
		this.localizedName = (unlocalizedName);
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return JecustomMod.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
}