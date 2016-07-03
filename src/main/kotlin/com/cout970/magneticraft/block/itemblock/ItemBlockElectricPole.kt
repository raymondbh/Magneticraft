package com.cout970.magneticraft.block.itemblock

import com.cout970.magneticraft.block.BlockElectricPole
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Created by cout970 on 30/06/2016.
 */
class ItemBlockElectricPole : ItemBlockBase(BlockElectricPole) {

    override fun onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, p: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        var pos = p
        val iblockstate = worldIn.getBlockState(pos)
        val block = iblockstate.block

        if (!block.isReplaceable(worldIn, pos)) {
            pos = pos.offset(facing)
        }

        if (stack.stackSize != 0 && playerIn.canPlayerEdit(pos, facing, stack)
                && worldIn.canBlockBePlaced(this.block, pos, false, facing, null, stack)
                && worldIn.canBlockBePlaced(this.block, pos.offset(EnumFacing.UP, 1), false, facing, null, stack)
                && worldIn.canBlockBePlaced(this.block, pos.offset(EnumFacing.UP, 2), false, facing, null, stack)
                && worldIn.canBlockBePlaced(this.block, pos.offset(EnumFacing.UP, 3), false, facing, null, stack)) {
            val i = this.getMetadata(stack.metadata)
            val iblockstate1 = this.block.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, i, playerIn)

            if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
                val soundtype = this.block.soundType
                worldIn.playSound(playerIn, pos, soundtype.placeSound, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f)
                stack.stackSize--
            }
            return EnumActionResult.SUCCESS
        } else {
            return EnumActionResult.PASS
        }
    }
}