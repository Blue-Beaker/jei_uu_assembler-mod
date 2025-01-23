package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

import ic2.core.init.MainConfig;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.Constants;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SteamTurbineCategory extends GenericRecipeCategory<FluidHeatConversionRecipe> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableAnimated arrow;
    private static final ItemStack turbine = ItemName.crafting.getItemStack(CraftingItemType.steam_turbine);
    public static final String UID = "jei_uu_assembler.steam_turbine";

    public SteamTurbineCategory(IGuiHelper guiHelper) {
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
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,GUI_WIDTH/2-9,GUI_HEIGHT-35);
        guiItemStackGroup.set(0, turbine);

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

    public static List<SteamTurbineRecipe> getRecipes(IJeiHelpers jeiHelpers) {
        float outputModifier = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/steam");

        List<SteamTurbineRecipe> recipes = new ArrayList<>();
        FluidStack distilledWater = new FluidStack(FluidName.distilled_water.getInstance(), 1);
        FluidStack steam = new FluidStack(FluidName.steam.getInstance(), 100);
        FluidStack superheatedSteam = new FluidStack(FluidName.superheated_steam.getInstance(), 100);
        recipes.add(new SteamTurbineRecipe(jeiHelpers, steam, distilledWater, (long) (200*outputModifier)));
        recipes.add(new SteamTurbineRecipe(jeiHelpers, superheatedSteam, steam, (long) (400*outputModifier)));
        return recipes;
    }

    public static class SteamTurbineRecipe extends FluidHeatConversionRecipe {
        public SteamTurbineRecipe(IJeiHelpers jeiHelpers, FluidStack input, FluidStack output, long energy) {
            super(jeiHelpers, input, output, energy);
        }
        @Override
        public void getIngredients(IIngredients iIngredients) {
            super.getIngredients(iIngredients);
            iIngredients.setInput(VanillaTypes.ITEM,turbine);
        }
        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int xPos = recipeWidth/2;
            int yPos = 1;
            RenderUtils.drawTextAlignedRight(this.energy+getPowerUnit(), recipeWidth-8, yPos, Color.gray.getRGB());
        }
        @Override
        public String getPowerUnit(){
            return EnergyUnit.KU.name;
        }
    }
}
