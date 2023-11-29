package model

import java.util.*

object RepoHelper {

    private val ids: MutableList<Long> = mutableListOf()

    fun getNewID(): Long {
        var id: Long = Random().nextLong()
        while (ids.contains(id)) {
            id = Random().nextLong()
        }
        ids.add(id)
        return id
    }
}