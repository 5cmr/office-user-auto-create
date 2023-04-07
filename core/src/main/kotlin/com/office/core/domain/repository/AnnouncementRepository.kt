package com.office.core.domain.repository

import com.office.core.domain.entity.MAnnouncement
import com.office.core.domain.po.TAnnouncement
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AnnouncementRepository {
    /**
     * 查询数据
     * @param id
     * @return 数据
     */
    fun find(id: Long): MAnnouncement {
        var model: MAnnouncement? = null
        transaction {
            model = TAnnouncement.select(TAnnouncement.id.eq(id)).map {
                MAnnouncement(
                    content = it[TAnnouncement.content],
                    id = it[TAnnouncement.id],
                    enable = it[TAnnouncement.enable],
                    createTime = it[TAnnouncement.createTime],
                    status = it[TAnnouncement.status]
                )
            }.first()
            commit()
        }
        return model!!
    }
}