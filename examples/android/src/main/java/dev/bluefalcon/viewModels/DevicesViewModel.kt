package dev.bluefalcon.viewModels

import android.Manifest
import androidx.core.app.ActivityCompat
import dev.bluefalcon.*
import dev.bluefalcon.activities.DevicesActivity
import dev.bluefalcon.adapters.DevicesAdapter
import dev.bluefalcon.views.DevicesActivityUI

class DevicesViewModel(private val devicesActivity: DevicesActivity) : BlueFalconDelegate {

    val devices: MutableList<BluetoothPeripheral> = arrayListOf()
    val devicesAdapter = DevicesAdapter(this)
    val devicesActivityUI = DevicesActivityUI(this)

    fun setupBluetooth() {
        try {
            BlueFalconApplication.instance.blueFalcon.delegates.add(this)
            BlueFalconApplication.instance.blueFalcon.scan()
        } catch (exception: PermissionException) {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        val permission = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(devicesActivity, permission, 0)
    }

    override fun didDiscoverDevice(bluetoothPeripheral: BluetoothPeripheral) {
        if (devices.firstOrNull { it.bluetoothDevice == bluetoothPeripheral.bluetoothDevice } == null) {
            devices.add(bluetoothPeripheral)
            devicesAdapter.notifyDataSetChanged()
        }
    }

    override fun didConnect(bluetoothPeripheral: BluetoothPeripheral) {}

    override fun didDisconnect(bluetoothPeripheral: BluetoothPeripheral) {}

    override fun didDiscoverServices(bluetoothPeripheral: BluetoothPeripheral) {}

    override fun didDiscoverCharacteristics(bluetoothPeripheral: BluetoothPeripheral) {}

    override fun didCharacteristcValueChanged(
        bluetoothPeripheral: BluetoothPeripheral,
        bluetoothCharacteristic: BluetoothCharacteristic
    ) {}

}