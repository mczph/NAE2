package co.neeve.nae2.mixin.upgrades;

import appeng.container.slot.SlotRestrictedInput;
import co.neeve.nae2.common.items.NAEBaseItemUpgrade;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotRestrictedInput.class)
public class MixinSlotRestrictedInput {
	@Shadow
	@Final
	private SlotRestrictedInput.PlacableItemType which;

	@Inject(method = "isItemValid", at = @At(
		value = "INVOKE",
		target = "Lappeng/api/definitions/IDefinitions;items()Lappeng/api/definitions/IItems;"
	), cancellable = true)
	private void injectValidityCheck(ItemStack is, CallbackInfoReturnable<Boolean> cir) {
		if (which == SlotRestrictedInput.PlacableItemType.UPGRADES) {
			if (is.getItem() instanceof NAEBaseItemUpgrade niu && niu.getType(is) != null) {
				cir.setReturnValue(true);
			}
		}
	}
}