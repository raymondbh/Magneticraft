package com.cout970.magneticraft.tileentity

import com.cout970.magneticraft.misc.block.getOrientation
import com.cout970.magneticraft.misc.tileentity.RegisterTileEntity
import com.cout970.magneticraft.tileentity.core.TileBase
import com.cout970.magneticraft.tileentity.modules.*
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable

/**
 * Created by cout970 on 2017/07/07.
 */

@RegisterTileEntity("computer")
class TileComputer : TileBase(), ITickable {

    val facing: EnumFacing get() = getBlockState().getOrientation()

    val invModule = ModuleInventory(1)
    val monitorModule = ModuleMonitor(container.ref)
    val floppyDriveModule = ModuleFloppyDrive(container.ref, invModule)
    val networkCardModule = ModuleNetworkCard(container.ref)

    val computerModule = ModuleComputer(
            devices = mapOf(
                    0x00 to monitorModule.monitor,
                    0x01 to floppyDriveModule.drive,
                    0x02 to networkCardModule.networkCard
            )
    )

    init {
        initModules(computerModule, invModule, monitorModule, floppyDriveModule, networkCardModule)
    }

    override fun update() {
        super.update()
    }
}