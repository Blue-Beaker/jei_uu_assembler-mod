package io.bluebeaker.jecustom.jei.uu;

import java.util.ArrayList;
import java.util.List;

public class UURecipeMaker {

	private UURecipeMaker() {
	}

	public static List<UURecipeWrapper> getUuRecipe() {
		List<UURecipeWrapper> recipes = new ArrayList<>();
		// for (ICentrifugeRecipe recipe : RecipeManagers.centrifugeManager.recipes()) {
		// 	recipes.add(new UURecipeWrapper(recipe));
		// }
		return recipes;
	}

}