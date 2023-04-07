package com.office.core.domain.service

import com.office.core.domain.dto.ActivationCodeDto
import com.office.core.domain.entity.MActivationCode
import com.office.core.domain.po.TActivationCode
import com.office.core.domain.repository.ActivationCodeRepository
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * 激活码 业务类
 */
class ActivationCodeService {
    private val activationCodeRepository: ActivationCodeRepository = ActivationCodeRepository()

    /**
     * 添加数据
     * @param dto 数据
     * @return id
     */
    fun add(dto: ActivationCodeDto): Long {
        val model = MActivationCode(
            code = dto.code,
            createTime = System.currentTimeMillis(),
            status = 1,
        )
        return activationCodeRepository.add(model)
    }

//    /**
//     * 查询数据
//     * @return 数据列表
//     */
//    fun findAll(where: Op<Boolean>): List<SsrDto> {
//        var dtos: List<SsrDto>? = null
//        transaction {
//            dtos = ssrRepository.findAll().map {
//                SsrDto(
//                    id = it.id,
//                    remarks = it.remarks,
//                    url = it.url,
//                    addDate = it.addDate,
//                    status = it.status,
//                )
//            }
//        }
//
//        return dtos!!
//    }
    /**
     * 查询数据
     * @param code 激活码
     * @return 数据
     */
    fun find(code: String): ActivationCodeDto? {
        val models = activationCodeRepository.findAll(Op.build { TActivationCode.code.eq(code) and TActivationCode.status.eq(1)  })
        return if (models.isNotEmpty()) {
            ActivationCodeDto(
                id = models[0].id,
                createTime = models[0].createTime,
                status = models[0].status,
                code = models[0].code
            )
        } else {
            null
        }


    }

    fun updateStatus(id: Long, status: Int) {
        val model = activationCodeRepository.find(id)
        model.status = status
        activationCodeRepository.update(model)
    }

//    /**
//     * 分页查询
//     * @param pageIndex 第几页
//     * @param pageSize 每页几个
//     * @return 列表
//     */
//    fun findAll(
//        pageIndex: Int,
//        pageSize: Int,
//        xbbContractId: String,
//        ownerName: String,
//        addDateStart: Long,
//        addDateEnd: Long,
//        saasMark: Int
//    ): List<ContractDto> {
//        var dtos: List<ContractDto>? = null
//        transaction {
//            var where: Op<Boolean> = Op.build { TContract.id.isNotNull() }
//            if (!xbbContractId.isNullOrEmpty()) {
//                where = where.and(TContract.xbbContractId.like(xbbContractId))
//            }
//            if (!ownerName.isNullOrEmpty()) {
//                where = where.and(TContract.ownerName.like(ownerName))
//            }
//            if (addDateStart != 0L) {
//                where = where.and(TContract.addDate.between(addDateStart, Long.MAX_VALUE))
//            }
//            if (addDateEnd != 0L) {
//                where = where.and(TContract.addDate.between(0, addDateEnd))
//            }
//            if (saasMark != 0) {
//                where = where.and(TContract.saasMark.eq(saasMark))
//            }
//            dtos = contractRepository.findAll(where, pageSize, (pageIndex - 1) * pageSize).map {
//                ContractDto(
//                    id = it.id,
//                    zbjContractId = it.zbjContractId,
//                    downloadUrl = it.downloadUrl,
//                    addDate = it.addDate,
//                    status = it.status,
//                    createName = it.createName,
//                    ownerName = it.ownerName,
//                    saasMark = it.saasMark,
//                    signDate = it.signDate,
//                    xbbContractId = it.xbbContractId
//                )
//            }
//        }
//
//
//        return dtos!!
//    }
//
//    /**
//     * 查询数据
//     * @param id
//     * @return 数据
//     */
//    fun find(id: Int): ContractDto {
//        val model = contractRepository.find(id)
//        return ContractDto(
//            zbjContractId = model.zbjContractId
//        )
//
//    }
}