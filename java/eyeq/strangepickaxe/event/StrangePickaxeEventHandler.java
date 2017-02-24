package eyeq.strangepickaxe.event;

import eyeq.strangepickaxe.StrangePickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StrangePickaxeEventHandler {
    @SubscribeEvent
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        if(player.isCreative()) {
            return;
        }
        ItemStack itemStack = event.getItemStack();
        if(itemStack.getItem() != StrangePickaxe.strangePickaxe) {
            return;
        }
        BlockPos pos = event.getPos().add(0.5, 0.5, 0.5);
        boolean isWater = false;
        boolean isLava = false;
        for(EnumFacing enumFacing : EnumFacing.values()) {
            Material material = world.getBlockState(pos.offset(enumFacing)).getMaterial();
            isWater |= material == Material.WATER;
            isLava |= material == Material.LAVA;
        }
        if(isWater && isLava) {
            Block.spawnAsEntity(world, pos, new ItemStack(Blocks.COBBLESTONE));
            itemStack.damageItem(1, player);
            if(itemStack.getCount() == 0) {
                player.inventory.deleteStack(itemStack);
            }
        }
    }
}
