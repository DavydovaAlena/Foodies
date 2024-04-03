package ru.adavydova.component.util

fun Int.toRuble(): Int {
    return (this.toDouble()/100).toInt()
}