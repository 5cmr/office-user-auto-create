package com.office.core.domain.repository

import com.office.core.domain.entity.MActivationCode
import com.office.core.domain.po.TActivationCode
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * 激活码 数据访问类
 */
class ActivationCodeRepository {
    /**
     * 添加数据
     * @param model 模型
     * @return id
     */
    fun add(model: MActivationCode): Long {
        var id: Long = 0
        transaction {
            id = TActivationCode.insert {
                it[code] = model.code
                it[addTime] = model.addTime
                it[status] = model.status
            } [TActivationCode.id]
            commit()
        }
        return id
    }

    /**
     * 查询数据
     * @param where 查询条件
     * @return 数据
     */
    fun findAll(where: Op<Boolean> = Op.build { TActivationCode.id.isNotNull() }): List<MActivationCode> {
        var models: List<MActivationCode>? = null
        transaction {
            val query = TActivationCode.select { where }
            models = query.map {
                MActivationCode(
                    id = it[TActivationCode.id],
                    code = it[TActivationCode.code],
                    addTime = it[TActivationCode.addTime],
                    status = it[TActivationCode.status]
                )
            }
            commit()
        }

        return models!!
    }
    /**
     * 查询数据
     * @param id
     * @return 数据
     */
    fun find(id: Long): MActivationCode {
        var model: MActivationCode? = null
        transaction {
            model = TActivationCode.select(TActivationCode.id.eq(id)).map { MActivationCode(
                code = it[TActivationCode.code],
                id = it[TActivationCode.id],
                addTime = it[TActivationCode.addTime],
                status = it[TActivationCode.status]
            ) }.first()
            commit()
        }
        return model!!
    }

    fun update(model: MActivationCode) {
        transaction {
            TActivationCode.update({TActivationCode.id eq model.id} ) {
                it[code] = model.code
                it[addTime] = model.addTime
                it[status] = model.status
            }
            commit()
        }
    }
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