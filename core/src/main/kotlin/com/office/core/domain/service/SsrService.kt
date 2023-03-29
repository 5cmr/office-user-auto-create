package com.prprpr.core.domain.service

import com.prprpr.core.domain.dto.SsrDto
import com.prprpr.core.domain.entity.MSsr
import com.prprpr.core.domain.repository.SsrRepository
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * ssr 业务类
 */
class SsrService {
    private val ssrRepository: SsrRepository = SsrRepository()

    /**
     * 添加数据
     * @param dto 数据
     * @return id
     */
    fun add(dto: SsrDto): Int {
        val model = MSsr(
            remarks = dto.remarks,
            url = dto.url,
            addDate = System.currentTimeMillis(),
            status = 1,
        )
        return ssrRepository.add(model)
    }

    /**
     * 查询数据
     * @return 数据列表
     */
    fun findAll(): List<SsrDto> {
        var dtos: List<SsrDto>? = null
        transaction {
            dtos = ssrRepository.findAll().map {
                SsrDto(
                    id = it.id,
                    remarks = it.remarks,
                    url = it.url,
                    addDate = it.addDate,
                    status = it.status,
                )
            }
        }

        return dtos!!
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