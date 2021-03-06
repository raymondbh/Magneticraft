package com.cout970.magneticraft.tileentity.modules

import com.cout970.magneticraft.block.core.IOnActivated
import com.cout970.magneticraft.block.core.OnActivatedArgs
import com.cout970.magneticraft.misc.fluid.Tank
import com.cout970.magneticraft.misc.world.isServer
import com.cout970.magneticraft.registry.ITEM_FLUID_HANDLER
import com.cout970.magneticraft.registry.fromItem
import com.cout970.magneticraft.tileentity.core.IModule
import com.cout970.magneticraft.tileentity.core.IModuleContainer
import net.minecraftforge.fluids.FluidUtil

/**
 * Created by cout970 on 2017/08/29.
 */
class ModuleBucketIO(
        val tank: Tank,
        override val name: String = "module_bucket_io"
) : IModule, IOnActivated {

    override lateinit var container: IModuleContainer

    override fun onActivated(args: OnActivatedArgs): Boolean = args.run {
        val handler = ITEM_FLUID_HANDLER!!.fromItem(playerIn.getHeldItem(hand)) ?: return false
        if (worldIn.isServer) {
            handler.tankProperties.forEach { prop ->
                val result0 = FluidUtil.tryFillContainer(heldItem, tank, prop.capacity, playerIn, true)
                if (result0.isSuccess) {
                    playerIn.setHeldItem(hand, result0.result)
                    container.sendUpdateToNearPlayers()
                    return@forEach
                }
                val result1 = FluidUtil.tryEmptyContainer(heldItem, tank, prop.capacity, playerIn, true)
                if (result1.isSuccess) {
                    playerIn.setHeldItem(hand, result1.result)
                    container.sendUpdateToNearPlayers()
                    return@forEach
                }
            }
        }
        return true
    }
}