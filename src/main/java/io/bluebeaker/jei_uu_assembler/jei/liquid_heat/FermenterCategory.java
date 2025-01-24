package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

import ic2.api.recipe.IFermenterRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.init.MainConfig;
import ic2.core.item.type.CropResItemType;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.Constants;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FermenterCategory extends GenericRecipeCategory<FluidHeatConversionRecipe> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableStatic bgArrow2;
    private static final ItemStack fertilizer = ItemName.crop_res.getItemStack(CropResItemType.fertilizer);

    private static final int WIDTH = 116;
    private static final int HEIGHT = 50;

    private static final int TEXT_X = WIDTH/2;
    private static final int TEXT_Y = 16;

    public static final String UID = "jei_uu_assembler.fermenter";

    public FermenterCategory(IGuiHelper guiHelper) {
        super(guiHelper,WIDTH,HEIGHT);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 17, 48, 10);
        this.bgArrow2 = guiHelper.createDrawable(Constants.GUI_0, 0, 27, 48, 4);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, TEXT_X-24, TEXT_Y);
        this.bgArrow2.draw(minecraft, TEXT_X-24, TEXT_Y+10);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidHeatConversionRecipe wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,WIDTH-26,HEIGHT-26);
        guiItemStackGroup.set(0, fertilizer);

        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8,HEIGHT-35);
        guiFluidStackGroup.set(0,wrapper.inputStack);
        this.addFluidSlot(guiFluidStackGroup,1,WIDTH-26,HEIGHT-44);        guiFluidStackGroup.set(1,wrapper.outputStack);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.fermenter);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FermenterRecipe> getRecipes(IJeiHelpers jeiHelpers) {

        List<FermenterRecipe> recipes = new ArrayList<>();
        for (Fluid inputFluid : Recipes.fermenter.getAcceptedFluids()) {
            IFermenterRecipeManager.FermentationProperty property = Recipes.fermenter.getFermentationInformation(inputFluid);
            recipes.add(new FermenterRecipe(jeiHelpers,new FluidStack(inputFluid, property.inputAmount),property.getOutput(), property.heat));
        }

        return recipes;
    }

    public static class FermenterRecipe extends FluidHeatConversionRecipe {
        int fertilizer_count = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/biomass_per_fertilizier");
        private final IDrawableAnimated arrow1;
        private final IDrawableAnimated arrow2;
        public FermenterRecipe(IJeiHelpers jeiHelpers, FluidStack input, FluidStack output, long energy) {
            super(jeiHelpers, input, output, energy);
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
            arrow1= guiHelper.drawableBuilder(Constants.GUI_0, 48, 17, 48, 10).buildAnimated((int) (this.energy/100), IDrawableAnimated.StartDirection.LEFT,false);
            arrow2= guiHelper.drawableBuilder(Constants.GUI_0, 48, 27, 48, 4).buildAnimated((int) (this.energy/100*fertilizer_count/input.amount), IDrawableAnimated.StartDirection.LEFT,false);
        }
        @Override
        public void getIngredients(IIngredients iIngredients) {
            super.getIngredients(iIngredients);
            iIngredients.setOutput(VanillaTypes.ITEM,fertilizer);
        }
        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.arrow1.draw(minecraft, TEXT_X-24, TEXT_Y);
            this.arrow2.draw(minecraft, TEXT_X-24, TEXT_Y+10);
            RenderUtils.drawTextAlignedMiddle(this.energy+getPowerUnit(), TEXT_X, TEXT_Y - minecraft.fontRenderer.FONT_HEIGHT, Color.gray.getRGB());
            RenderUtils.drawTextAlignedRight("+"+this.inputStack.amount*100/fertilizer_count+"%", recipeWidth-27, recipeHeight-16, Color.gray.getRGB());
        }
    }
}
