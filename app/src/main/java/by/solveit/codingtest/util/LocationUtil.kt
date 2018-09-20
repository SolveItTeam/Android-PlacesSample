package by.solveit.codingtest.util

import android.Manifest
import android.content.Context
import android.support.v4.content.PermissionChecker

fun isLocationPermissionGranted(context: Context): Boolean {
    val coarseLocationGranted = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PermissionChecker.PERMISSION_GRANTED
    val fineLocationGranted = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
    ) == PermissionChecker.PERMISSION_GRANTED
    return coarseLocationGranted || fineLocationGranted
}