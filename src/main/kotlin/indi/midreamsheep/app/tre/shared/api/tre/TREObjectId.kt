package indi.midreamsheep.app.tre.shared.api.tre

import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.tool.id.removeIdFromPool

interface TREObjectId {

    fun getId() = getIdFromPool(this)

    fun removeId() = removeIdFromPool(this)
}
