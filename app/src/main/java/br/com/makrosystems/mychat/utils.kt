package br.com.makrosystems.mychat

import android.graphics.Color
import java.util.*


class utils {

    private val kleur = arrayOf(
        "#39add1",  // light blue
        "#3079ab",  // dark blue
        "#3FD52D",  // green
        "#FFF000",  // red
        "#FFFFF0",
        "#708090",
        "#0000CD",
        "#EECFA1",
        "#FFB90F",
        "#8B4513",
        "#FF4500",
        "#556B2F"
    )

    fun getRandomColor(): Int {
        val rand = Random()
        val color: Int = rand.nextInt(kleur.size)
        return Color.parseColor(kleur[color])
    }
}