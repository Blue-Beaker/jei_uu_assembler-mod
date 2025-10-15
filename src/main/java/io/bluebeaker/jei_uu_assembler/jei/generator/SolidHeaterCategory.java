package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.core.block.heatgenerator.tileentity.TileEntitySolidHeatGenerator;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolidHeaterCategory extends BaseSolidGeneratorCategory<SolidHeaterCategory.Wrapper> {
    public static final String UID = "jei_uu_assembler.solid_heater";

    public SolidHeaterCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.solid_heat_generator));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        this.addItemSlot(guiItemStackGroup,1,GUI_WIDTH-26,GUI_HEIGHT/2-9);
        guiItemStackGroup.set(iIngredients);
        guiItemStackGroup.set(1,Wrapper.ashes);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.solid_heat_generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<Wrapper> getRecipes(List<Map.Entry<Integer,List<ItemStack>>> fuels){
        List<Wrapper> recipes = new ArrayList<>();
        for (Map.Entry<Integer, List<ItemStack>> fuel : fuels) {
            recipes.add(new Wrapper(fuel.getValue(),fuel.getKey()));
        }
        return recipes;
    }

    public static class Wrapper extends FuelRecipeWrapper {
        public static int Generation = TileEntitySolidHeatGenerator.emittedHU;
        public static final ItemStack ashes = ItemName.misc_resource.getItemStack(MiscResourceType.ashes);
        public Wrapper(List<ItemStack> input, int duration) {
            super(input, Generation, duration);
        }

        @Override
        public void getIngredients(IIngredients iIngredients) {
            super.getIngredients(iIngredients);
            iIngredients.setOutput(VanillaTypes.ITEM, ashes);
        }

        @Override
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

            RenderUtils.drawTextAlignedMiddle("50%", recipeWidth-17, recipeHeight/2+10, Color.gray.getRGB());
        }

        @Override
        public String getPowerUnit() {
            return EnergyUnit.HU.name;
        }
    }
}
