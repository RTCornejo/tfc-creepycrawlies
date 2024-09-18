package net.rtcornejo.creepycrawlies.common.items;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WormstickItem extends Item {

    public static final TagKey<Block> WORMS_CAN_SPAWN_ON = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation("creepycrawlies", "worms_can_spawn_on"));

    public WormstickItem(Properties pProperties) {
        super(pProperties.durability(8));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand); // Start the use action (required for onUseTick to trigger)
        return InteractionResultHolder.consume(itemstack); // Indicate the item was used
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntityIn, ItemStack stack, int countLeft) {
        if (!level.isClientSide) {
            if (livingEntityIn instanceof Player player) {
                BlockHitResult blockHit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
                if (blockHit.getType() == HitResult.Type.BLOCK) {
                    BlockPos hitPos = blockHit.getBlockPos();
                    BlockState hoveredBlock = level.getBlockState(hitPos);
                    if (hoveredBlock.is(WORMS_CAN_SPAWN_ON) && (countLeft == 1)) {
                        if (!player.isCreative()){
                            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                        }
                        double wormChance = 0.2 * (level.isRainingAt(hitPos) ? 1.5 : 1); // Roll to see if a worm should be given
                        if (level.random.nextDouble() < wormChance) {
                            player.addItem(ModItems.WORM.get().getDefaultInstance());
                        }
                    }
                }
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72;
    }
}
