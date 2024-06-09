package indi.midreamsheep.app.tre.shared.api.tre

import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.tool.id.removeIdFromPool

interface TREId {

    fun getId() = getIdFromPool(this)

    fun remove() = removeIdFromPool(this)
}