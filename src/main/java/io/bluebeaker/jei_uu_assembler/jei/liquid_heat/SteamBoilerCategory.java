package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

import ic2.core.ref.FluidName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.Constants;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SteamBoilerCategory extends GenericRecipeCategory<FluidHeatConversionRecipe> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableAnimated arrow;

    public static final String UID = "jei_uu_assembler.steam_boiler";

    public SteamBoilerCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,38);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 17, 48, 10);
        this.arrow = guiHelper.drawableBuilder(Constants.GUI_0, 48, 17, 48, 10).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 34, 22);
        this.arrow.draw(minecraft, 34, 22);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidHeatConversionRecipe wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8,GUI_HEIGHT-25);
        guiFluidStackGroup.set(0,wrapper.inputStack);
        this.addFluidSlot(guiFluidStackGroup,1,this.GUI_WIDTH-24,GUI_HEIGHT-25);        guiFluidStackGroup.set(1,wrapper.outputStack);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.steam_generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidHeatConversionRecipe> getRecipes(IJeiHelpers jeiHelpers) {

        List<FluidHeatConversionRecipe> recipes = new ArrayList<>();
        FluidStack water = new FluidStack(FluidRegistry.WATER, 1);
        FluidStack distilledWater = new FluidStack(FluidName.distilled_water.getInstance(), 1);
        FluidStack steam = new FluidStack(FluidName.steam.getInstance(), 100);
        FluidStack superheatedSteam = new FluidStack(FluidName.superheated_steam.getInstance(), 100);
        recipes.add(new SteamBoilerRecipe(jeiHelpers, water, steam,100,100,0,true));
        recipes.add(new SteamBoilerRecipe(jeiHelpers, distilledWater, steam,100,100,0,false));
        recipes.add(new SteamBoilerRecipe(jeiHelpers, water, superheatedSteam,100,374,220,true));
        recipes.add(new SteamBoilerRecipe(jeiHelpers, distilledWater, superheatedSteam,100,374,220,false));
        return recipes;
    }

    public static class SteamBoilerRecipe extends FluidHeatConversionRecipe {
        public final int temp;
        public final int pressure;
        public final boolean calcify;
        public SteamBoilerRecipe(IJeiHelpers jeiHelpers, FluidStack input, FluidStack output, long energy, int temp, int pressure, boolean calcify) {
            super(jeiHelpers, input, output, energy);
            this.temp = temp;
            this.pressure = pressure;
            this.calcify = calcify;
        }
        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth/2;
            int yPos = 1;
            RenderUtils.drawTextAlignedMiddle(temp+"°C "+pressure+"Bar", xPos, yPos, Color.gray.getRGB());
            yPos += minecraft.fontRenderer.FONT_HEIGHT+1;
            RenderUtils.drawTextAlignedMiddle(this.energy+getPowerUnit(), xPos, yPos, Color.gray.getRGB());
        }
    }
}
