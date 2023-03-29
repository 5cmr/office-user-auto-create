package com.prprpr.core.domain.repository

import com.prprpr.core.domain.po.TSsr
import com.prprpr.core.domain.entity.MSsr
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * ssr 数据访问类
 */
class SsrRepository {
    /**
     * 添加数据
     * @param model 模型
     * @return id
     */
    fun add(model: MSsr): Int {
        var id = 0
        transaction {
            id = TSsr.insert {
                it[remarks] = model.remarks
                it[url] = model.url
                it[addDate] = model.addDate
                it[status] = model.status
            } [TSsr.id]
        }
        return id
    }

    /**
     * 查询数据
     * @param where 查询条件
     * @return 数据
     */
    fun findAll(where: Op<Boolean> = Op.build { TSsr.id.isNotNull() }): List<MSsr> {
        var models: List<MSsr>? = null
        transaction {
            var query = TSsr.select { where }
            models = query.map {
                MSsr(
                    id = it[TSsr.id],
                    url = it[TSsr.url],
                    remarks = it[TSsr.remarks],
                    addDate = it[TSsr.addDate],
                    status = it[TSsr.status]
                )
            }
        }

        return models!!
    }
//    /**
//     * 查询数据
//     * @param id
//     * @return 数据
//     */
//    fun find(id: Int): MContract {
//        var model: MContract? = null
//        transaction {
//            model = TContract.select(TContract.id.eq(id)).map { MContract(
//                zbjContractId = it[TContract.zbjContractId]
//            ) }.first()
//        }
//        return model!!
//    }
//    /**
//     * 查询数据
//     * @param n 每页数量
//     * @param offset 偏移量，从第几个开始
//     * @return 数据
//     */
//    fun findAll(where: Op<Boolean>, n: Int = 0, offset: Int = 0): List<MContract> {
//        var models: List<MContract>? = null
//        transaction {
//            var query = TContract.select{where}
//            if (n != 0) {
//                query = query.limit(n, offset)
//            }
//            models = query.map {
//                MContract(
//                    id = it[TContract.id],
//                    zbjContractId = it[TContract.zbjContractId],
//                    downloadUrl = it[TContract.downloadUrl],
//                    addDate = it[TContract.addDate],
//                    status = it[TContract.status],
//                    createName = it[TContract.createName],
//                    ownerName = it[TContract.ownerName],
//                    saasMark = it[TContract.saasMark],
//                    signDate = it[TContract.signDate],
//                    xbbContractId = it[TContract.xbbContractId]
//                )
//            }
//        }
//
//        return models!!
//    }
}