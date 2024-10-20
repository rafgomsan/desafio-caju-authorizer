package com.caju.authorizer.service

import com.caju.authorizer.models.Transaction
import com.caju.authorizer.models.dtos.TransactionDTORequest
import com.caju.authorizer.models.dtos.TransactionDTOResponse
import com.caju.authorizer.repositories.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import kotlin.jvm.optionals.getOrNull

@Service
class TransactionService(@Autowired var repository: TransactionRepository) {

    @Autowired
    lateinit var authorizerService: AuthorizerService

    lateinit var resultTransactionProcess: AuthorizerService

    var timeStamp = Timestamp(System.currentTimeMillis())

    fun createTransaction(newTransaction: TransactionDTORequest): TransactionDTOResponse {

        resultTransactionProcess = authorizerService.setTransactionRequest(newTransaction).process()

        val save = repository.save(
            Transaction(
                transactionId = null,
                merchantId = resultTransactionProcess.getMerchantId(),
                accountId = resultTransactionProcess.getAccountId(),
                account = newTransaction.account,
                amount = newTransaction.amount,
                mcc = newTransaction.mcc,
                merchant = newTransaction.merchant,
                code = resultTransactionProcess.code,
                createdAt =  timeStamp,
                updatedAt =  timeStamp
            )
        )
        return TransactionDTOResponse(id = save.transactionId!!,
                    account  = save.account,
                    merchant = save.merchant,
                    amount = save.amount,
                    mcc = save.mcc,
                    code = save.code)

    }

    fun getAllTransactions(): List<TransactionDTOResponse?>  {
            return repository.findAll().stream()
                   .map {
                       TransactionDTOResponse(
                           id = it.transactionId!!,
                           account = it.account,
                           merchant = it.merchant,
                           amount = it.amount,
                           mcc = it.mcc,
                           code = it.code
                       )
                   }.toList()
    }

    fun getTransaction(id: Long): TransactionDTOResponse? {
        return repository.findById(id)
            .map { TransactionDTOResponse(
                    id = it.transactionId!!,
                    account = it.account,
                    merchant = it.merchant,
                    amount = it.amount,
                    mcc = it.mcc,
                    code = it.code
               )
            }
            .getOrNull()
    }

    fun deleteTransaction(id: Long) {
        return repository.deleteById(id)
    }

    fun updateTransaction(id: Long, updatedTransaction: TransactionDTORequest): TransactionDTOResponse? {
        return repository.findById(id).map {
            val save = repository.save(
                Transaction(
                    transactionId = it.transactionId,
                    merchantId = updatedTransaction.merchantId,
                    accountId = updatedTransaction.accountId,
                    account = updatedTransaction.account,
                    merchant = updatedTransaction.merchant,
                    amount = updatedTransaction.amount,
                    mcc = updatedTransaction.mcc,
                    code = updatedTransaction.code,
                    updatedAt =  timeStamp)
            )

            TransactionDTOResponse(id = save.transactionId!!,
                account = save.account,
                merchant = save.merchant,
                amount = save.amount,
                mcc = save.mcc,
                code = save.code
            )
        }.orElseGet(null)
    }
}