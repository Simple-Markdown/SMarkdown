package indi.midreamsheep.app.tre.shared.api.tre

import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool

interface TREClassId {
    fun getId() = getIdFromPool(this::class.java)
}