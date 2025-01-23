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
    protected final IDrawableAnimated arrow;
    private static final ItemStack fertilizer = ItemName.crop_res.getItemStack(CropResItemType.fertilizer);
    public static final String UID = "jei_uu_assembler.fermenter";

    public FermenterCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 17, 48, 10);
        this.arrow = guiHelper.drawableBuilder(Constants.GUI_0, 48, 17, 48, 10).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, GUI_WIDTH/2-9-24, GUI_HEIGHT/2);
        this.arrow.draw(minecraft, GUI_WIDTH/2-9-24, GUI_HEIGHT/2);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidHeatConversionRecipe wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,this.GUI_WIDTH-20,GUI_HEIGHT-30);
        guiItemStackGroup.set(0, fertilizer);

        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,2,GUI_HEIGHT-30);
        guiFluidStackGroup.set(0,wrapper.inputStack);
        this.addFluidSlot(guiFluidStackGroup,1,this.GUI_WIDTH-38,GUI_HEIGHT-30);        guiFluidStackGroup.set(1,wrapper.outputStack);
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
        public FermenterRecipe(IJeiHelpers jeiHelpers, FluidStack input, FluidStack output, long energy) {
            super(jeiHelpers, input, output, energy);
        }
        @Override
        public void getIngredients(IIngredients iIngredients) {
            super.getIngredients(iIngredients);
            iIngredients.setOutput(VanillaTypes.ITEM,fertilizer);
        }
        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            RenderUtils.drawTextAlignedMiddle(this.energy+getPowerUnit(), recipeWidth/2-9, recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT, Color.gray.getRGB());
            RenderUtils.drawTextAlignedRight("+"+this.inputStack.amount*100/fertilizer_count+"%", recipeWidth-2, recipeHeight-9, Color.gray.getRGB());
        }
    }
}
