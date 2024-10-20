package com.caju.authorizer.controllers

import com.caju.authorizer.models.Transaction
import com.caju.authorizer.models.dtos.TransactionDTORequest
import com.caju.authorizer.models.dtos.TransactionDTOResponse
import com.caju.authorizer.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/transactions/")
class TransactionController(private val transactionService: TransactionService){

  //get all transactions
  @GetMapping
  fun getAllTransactions(): List<TransactionDTOResponse?> =  transactionService.getAllTransactions()
  
  //create transaction
  @PostMapping
  fun createTransaction(@RequestBody transaction: TransactionDTORequest): ResponseEntity<TransactionDTOResponse> {
    val saveTransaction = transactionService.createTransaction(transaction)
    return ResponseEntity(saveTransaction, HttpStatus.CREATED)
  }

  //get transaction by id
  @GetMapping("/{id}")
  fun getTransactionById(@PathVariable("id") transactionId: Long): ResponseEntity<TransactionDTOResponse> {
    val transaction = transactionService.getTransaction(transactionId)
    return if (transaction != null) {
      ResponseEntity(transaction, HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  //update transaction
  @PutMapping("/{id}")
  fun updateTransactionById(@PathVariable("id") transactionId: Long, @RequestBody transaction: TransactionDTORequest): ResponseEntity<TransactionDTOResponse> {
    val existingTransaction = transactionService.getTransaction(transactionId)
      ?: return ResponseEntity(HttpStatus.NOT_FOUND)

    val updatedTransaction = existingTransaction.copy(
      amount = transaction.amount,
      merchant = transaction.merchant
    )
    transactionService.updateTransaction(transactionId, transaction)
    return ResponseEntity(updatedTransaction, HttpStatus.OK)
  }

  //delete transaction
  @DeleteMapping("/{id}")
  fun deleteTransactionById(@PathVariable("id") transactionId: Long): ResponseEntity<Transaction> {
    val existingTransaction = transactionService.getTransaction(transactionId)
      ?: return ResponseEntity(HttpStatus.NOT_FOUND)

    if (Objects.isNull(existingTransaction)){
      transactionService.deleteTransaction(transactionId)
    }
    return ResponseEntity(HttpStatus.NO_CONTENT)
  }
}
