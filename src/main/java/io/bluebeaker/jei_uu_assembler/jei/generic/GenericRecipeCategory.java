package io.bluebeaker.jei_uu_assembler.jei.generic;

import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer;
import mezz.jei.util.Translator;

import javax.annotation.Nullable;

public abstract class GenericRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    private final IDrawable background;
    private final IDrawable slotBackground;
    private final FluidStackRenderer fluidStackRenderer = new FluidStackRenderer(1,false,16,16,null);
    public final int GUI_WIDTH;
    public final int GUI_HEIGHT;
    public IDrawable icon = null;

    public GenericRecipeCategory(IGuiHelper guiHelper) {
        this(guiHelper,116,54);
    }

    public GenericRecipeCategory(IGuiHelper guiHelper, int width, int height) {
        this.background = guiHelper.createBlankDrawable(width, height);
        this.slotBackground = guiHelper.getSlotDrawable();
        this.GUI_WIDTH = width;
        this.GUI_HEIGHT = height;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    public abstract String getTranslationKey();

    @Override
    public String getTitle() {
        return Translator.translateToLocal(this.getTranslationKey());
    }

    @Override
    public String getModName() {
        return JeiUuAssemblerMod.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T wrapper, IIngredients ingredients) {

    }

    public void addItemSlot(IGuiItemStackGroup guiItemStacks, int id, int x,int y){
        guiItemStacks.init(id, true, x, y);
        guiItemStacks.setBackground(id, slotBackground);
    }

    public void addFluidSlot(IGuiFluidStackGroup guiFluidStackGroup, int id, int x,int y){
        guiFluidStackGroup.init(id, true, fluidStackRenderer,x, y,18,18,1,1);
        guiFluidStackGroup.setBackground(id, slotBackground);
    }
}
