package com.caju.authorizer.controllers

import com.caju.authorizer.models.Merchant
import com.caju.authorizer.models.dtos.TransactionDTORequest
import com.caju.authorizer.service.MerchantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/merchants/")
class MerchantController(private val merchantService: MerchantService){

  @GetMapping
  fun getMerchantByName(@RequestBody transaction: TransactionDTORequest): ResponseEntity<Merchant> {
    val result = merchantService.getMerchant(transaction.merchant)
    return if (result != null) {
      ResponseEntity(result, HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

}
