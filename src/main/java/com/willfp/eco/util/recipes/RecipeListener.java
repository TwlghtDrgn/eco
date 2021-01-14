package com.willfp.eco.util.recipes;

import com.willfp.eco.util.internal.PluginDependent;
import com.willfp.eco.util.plugin.AbstractEcoPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class RecipeListener extends PluginDependent implements Listener {
    /**
     * Pass an {@link AbstractEcoPlugin} in order to interface with it.
     *
     * @param plugin The plugin to manage.
     */
    public RecipeListener(@NotNull final AbstractEcoPlugin plugin) {
        super(plugin);
    }

    /**
     * Called on item craft.
     *
     * @param event The event to listen for.
     */
    @EventHandler
    public void prepareCraftListener(@NotNull final PrepareItemCraftEvent event) {
        if (!(event.getRecipe() instanceof ShapedRecipe)) {
            return;
        }

        ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();

        if (!recipe.getKey().getNamespace().equals(this.getPlugin().getPluginName().toLowerCase())) {
            return;
        }

        EcoShapedRecipe internalRecipe = this.getPlugin().getRecipeManager().getShapedRecipe(recipe.getKey().getKey());

        if (internalRecipe == null) {
            return;
        }

        ItemStack[] matrix = event.getInventory().getMatrix();

        if (internalRecipe.test(matrix)) {
            event.getInventory().setResult(internalRecipe.getOutput());
        } else {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    /**
     * Called on item craft.
     *
     * @param event The event to listen for.
     */
    @EventHandler
    public void craftListener(@NotNull final CraftItemEvent event) {
        if (!(event.getRecipe() instanceof ShapedRecipe)) {
            return;
        }

        ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();

        if (!recipe.getKey().getNamespace().equals(this.getPlugin().getPluginName().toLowerCase())) {
            return;
        }

        EcoShapedRecipe internalRecipe = this.getPlugin().getRecipeManager().getShapedRecipe(recipe.getKey().getKey());

        if (internalRecipe == null) {
            return;
        }

        ItemStack[] matrix = event.getInventory().getMatrix();

        if (internalRecipe.test(matrix)) {
            event.getInventory().setResult(internalRecipe.getOutput());
            event.setCancelled(true);
        } else {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
