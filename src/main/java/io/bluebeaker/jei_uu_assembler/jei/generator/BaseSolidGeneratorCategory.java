package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.IC2Drawables;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public abstract class BaseSolidGeneratorCategory<T extends IRecipeWrapper> extends GenericRecipeCategory<T> {
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public BaseSolidGeneratorCategory(IGuiHelper guiHelper, int width, int height) {
        super(guiHelper, width, height);
        this.bgHeatBar = guiHelper.createDrawable(IC2Drawables.GUI_PATH, 97, 80, 14, 14);
        this.heatBar = guiHelper.drawableBuilder(IC2Drawables.GUI_PATH, 112, 80, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
    }
}
