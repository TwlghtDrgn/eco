package com.willfp.eco.internal.gui.slot

import com.willfp.eco.core.gui.slot.functional.SlotHandler
import com.willfp.eco.core.gui.slot.functional.SlotProvider
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class EcoCaptiveSlot(
    provider: SlotProvider,
    private val captiveFromEmpty: Boolean,
    private val notCaptiveFor: (Player) -> Boolean,
    private val captiveFilter: (ItemStack) -> Boolean
) : EcoSlot(
    provider,
    captiveWithTest(notCaptiveFor),
    captiveWithTest(notCaptiveFor),
    captiveWithTest(notCaptiveFor),
    captiveWithTest(notCaptiveFor),
    captiveWithTest(notCaptiveFor),
    { _, _, prev -> prev }
) {
    override fun isCaptive(): Boolean {
        return true
    }

    override fun isCaptiveFromEmpty(): Boolean {
        return captiveFromEmpty
    }

    override fun isNotCaptiveFor(player: Player): Boolean {
        return notCaptiveFor(player)
    }

    override fun canCaptivateItem(itemStack: ItemStack): Boolean {
        return captiveFilter(itemStack)
    }
}

private fun captiveWithTest(test: (Player) -> Boolean): SlotHandler {
    return SlotHandler { event, slot, _ ->
        val item = event.currentItem
        event.isCancelled = false

        if (!test(event.whoClicked as Player)) {
            event.isCancelled = true
        }

        if (item != null && !slot.canCaptivateItem(item)) {
            event.isCancelled = true
        }
    }
}
