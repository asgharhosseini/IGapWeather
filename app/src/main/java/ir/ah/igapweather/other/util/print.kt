package ir.ah.igapweather.other.util

import ir.ah.igapweather.BuildConfig

fun Exception.print() {
    if (BuildConfig.DEBUG)
        printStackTrace()
}

fun Throwable.print() {
    if (BuildConfig.DEBUG)
        printStackTrace()
}