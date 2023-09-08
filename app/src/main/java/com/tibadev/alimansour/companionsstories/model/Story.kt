package com.tibadev.alimansour.companionsstories.model

data class Story(
    var companion: String = "",
    var title: String = "",
    var content: String = ""
) {
    override fun toString(): String {
        return companion
    }
}