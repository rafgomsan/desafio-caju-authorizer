package com.caju.authorizer

import com.caju.authorizer.models.dtos.TransactionDTORequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizerApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun whePostCalled_thenShouldReturnTransactionOk() {
        val result = restTemplate.postForEntity("/api/v1/transactions/",
            TransactionDTORequest(
                account = "123",
                amount = BigDecimal(1.00),
                merchant ="gemini systems",
                mcc = "5411",
                accountId = 1,
                merchantId = 1),
            TransactionDTORequest::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.CREATED, result?.statusCode)
        assertEquals("00", result.body?.code)
    }

    @Test
    fun whenPostCalled_thenShouldReturnTransactionRecused() {
        val result = restTemplate.postForEntity("/api/v1/transactions/",
            TransactionDTORequest(
                account = "123",
                amount = BigDecimal(1000.00),
                merchant ="gemini systems",
                mcc = "5411",
                accountId = 1,
                merchantId = 1),
            TransactionDTORequest::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.CREATED, result?.statusCode)
        assertEquals("51", result.body?.code)
    }

    @Test
    fun whenPostCalled_thenShouldReturnTransactionErrorNoHandle() {

        var result = restTemplate.postForEntity("/api/v1/transactions/",
            TransactionDTORequest(
                account = "",
                amount = BigDecimal(10.00),
                merchant ="systems",
                mcc = "",
                accountId = 2,
                merchantId = 2),
            TransactionDTORequest::class.java)

        assertNotNull(result)
        assertEquals("07", result.body?.code)
        assertEquals(HttpStatus.CREATED, result?.statusCode)

    }

}
