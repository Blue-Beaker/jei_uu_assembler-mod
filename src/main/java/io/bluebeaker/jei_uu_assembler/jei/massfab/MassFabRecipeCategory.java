package io.bluebeaker.jei_uu_assembler.jei.massfab;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.Recipes;
import ic2.core.init.MainConfig;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import io.bluebeaker.jei_uu_assembler.utils.Variables;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MassFabRecipeCategory extends GenericRecipeCategory<MassFabRecipeCategory.MassFabRecipe> {

    public static final String UID = "jei_uu_assembler.mass_fabricator";;
    public static final ItemStack uu = ItemName.misc_resource.getItemStack(MiscResourceType.matter);

    private static final int WIDTH = 116;
    private static final int HEIGHT = 54;
    public static final int TEXT_X = WIDTH / 2;
    public static final int TEXT_Y = HEIGHT / 2-5;

    public MassFabRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper,WIDTH,HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.mass_fabricator));
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.mass_fabricator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MassFabRecipe wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8, TEXT_Y -9);
        guiItemStackGroup.set(0, wrapper.inputStack);
        this.addItemSlot(guiItemStackGroup,1,WIDTH-26, TEXT_Y -9);
        guiItemStackGroup.set(1, wrapper.outputStack);
    }

    public static List<MassFabRecipe> getRecipes(IJeiHelpers jeiHelpers) {
        List<MassFabRecipe> recipes = new ArrayList<>();
        for (MachineRecipe<IRecipeInput, Integer> recipe : Recipes.matterAmplifier.getRecipes()) {
            recipes.add(new MassFabRecipe(jeiHelpers,recipe.getInput().getInputs().get(0), uu,
                    recipe.getOutput()));
        }
        return recipes;
    }

    public static class MassFabRecipe implements IRecipeWrapper {

        protected final IJeiHelpers jeiHelpers;
        protected final ItemStack inputStack;
        protected final ItemStack outputStack;
        public final int scrapValue;
        public final int neededScrap;
        private final int energy;

        public MassFabRecipe(IJeiHelpers jeiHelpers, ItemStack input, ItemStack output, int scrapValue) {
            this.jeiHelpers = jeiHelpers;
            this.inputStack = input.copy();
            this.outputStack = output;
            this.neededScrap = Variables.requiredScrap;
            this.scrapValue = scrapValue;
            this.energy = (int) (1000000.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor"));
            this.inputStack.setCount((int) Math.ceil((double) neededScrap /scrapValue));
        }

        @Override
        public void getIngredients(IIngredients iIngredients) {
            iIngredients.setInput(VanillaTypes.ITEM, inputStack);
            iIngredients.setOutput(VanillaTypes.ITEM,outputStack);
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            RenderUtils.drawTextAlignedLeft("+"+scrapValue, 27, 13, Color.gray.getRGB());
            RenderUtils.drawTextAlignedLeft("/"+neededScrap, 27, 23 , Color.gray.getRGB());
            RenderUtils.drawTextAlignedLeft(energy+"EU", 8, 42, Color.gray.getRGB());
        }
    }
}
