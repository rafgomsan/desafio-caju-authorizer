package com.caju.authorizer.service

import com.caju.authorizer.enums.EnumTransactionResult
import com.caju.authorizer.models.Account
import com.caju.authorizer.models.Mcc
import com.caju.authorizer.models.Merchant
import com.caju.authorizer.models.dtos.TransactionDTORequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

@Service

class AuthorizerService {

    lateinit var transaction: TransactionDTORequest

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var merchantService: MerchantService

    @Autowired
    lateinit var mccService: MccService

    private var merchant : Merchant? = null
    private var updatedAccount: Account? = null

    private var balanceFood: BigDecimal = BigDecimal(0.0)
    private var balanceMeal: BigDecimal = BigDecimal(0.0)
    private var balanceCash: BigDecimal = BigDecimal(0.0)

    var code : String = EnumTransactionResult.OTHERS.result
        get() {
            return field
        }
        set(value) {
            field = value
        }

    fun setTransactionRequest(transaction: TransactionDTORequest) : AuthorizerService {
        this.transaction = transaction
        return this
    }

    /**
     * Get account.
     *
     * @param
     * @return
     * @throws
     */

    fun getAccountId(): Long? {
        return this.updatedAccount?.accountId
    }

    /**
     * Get merchant.
     *
     * @param
     * @return
     * @throws
     */

    fun getMerchantId(): Long? {
        return this.merchant?.merchantId;
    }

    /**
     * Check if is valid mcc.
     *
     * @param
     * @return
     * @throws
     */

    fun getValidMcc(): String {
        val mcc: Mcc? = mccService.getMcc(transaction.mcc)
        return mcc?.assignedTo ?: "CASH"
    }

    /**
     * Check if is valid merchant.
     *
     * @param
     * @return
     * @throws
     */

    fun isValidMerchant(): Boolean {
        merchant = merchantService.getMerchant(transaction.merchant)
        return Objects.nonNull(merchant)
    }

    /**
     * Copy/Clone balances to use on calc.
     *
     * @param
     * @return
     * @throws
     */

    fun initialize() : AuthorizerService  {
        updatedAccount = accountService.getAccount(transaction.account)
        balanceFood = updatedAccount?.balanceFood ?: BigDecimal(0)
        balanceMeal = updatedAccount?.balanceMeal ?: BigDecimal(0)
        balanceCash = updatedAccount?.balanceCash ?: BigDecimal(0)

        return this
    }

    /**
     * Calc debit amount balance_food after checked defined rules.
     *
     * @param
     * @return
     * @throws
     */

    fun debitBalanceFood() {
        if (transaction.amount <= balanceFood) {
            code = EnumTransactionResult.APPROVED.result
            balanceFood -= transaction.amount
        }
    }

    /**
     * Calc debit amount balance_meal after checked defined rules.
     *
     * @param
     * @return
     * @throws
     */

    fun debitBalanceMeal() {
        if (transaction.amount <= balanceMeal) {
            code = EnumTransactionResult.APPROVED.result
            balanceMeal -= transaction.amount
        }
    }

    /**
     * Calc debit amount balance_cash after checked defined rules.
     *
     * @param
     * @return
     * @throws
     */

    fun debitBalanceCash() {
        if (transaction.amount <= balanceCash) {
            code = EnumTransactionResult.APPROVED.result
            balanceCash -= transaction.amount
        }
    }

    /**
     * Prepare debit balance
     *
     * @param
     * @return
     * @throws
     */

    fun debit() : AuthorizerService {
        code = EnumTransactionResult.REJECTED.result
        when (getValidMcc()) {
            "FOOD" -> debitBalanceFood()
            "MEAL" -> debitBalanceMeal()
            else   -> debitBalanceCash()
        }

        return this
    }

    /**
     * Commit/Update balance
     *
     * @param
     * @return
     * @throws
     */

    fun update() : AuthorizerService {

        val isEqual:Boolean = code == EnumTransactionResult.APPROVED.result

        if( isEqual ) {
            val timeStamp = Timestamp(System.currentTimeMillis())
            accountService.updateAccountNumber(
                Account(
                    accountId     = updatedAccount!!.accountId,
                    balanceFood   = balanceFood,
                    balanceMeal   = balanceMeal,
                    balanceCash   = balanceCash,
                    accountNumber = updatedAccount!!.accountNumber,
                    createdAt     = timeStamp,
                    updatedAt     = timeStamp
                )
            )
        }

        return this
    }


     /**
     * Calc result balanceDebit after checked defined rules.
     *
     * @param
     * @return
     * @throws
     */

    fun process(): AuthorizerService {
        if (isValidMerchant()) {
             initialize()
             .debit()
             .update()
        } else {
            code = EnumTransactionResult.OTHERS.result
        }
        return this
    }
}

