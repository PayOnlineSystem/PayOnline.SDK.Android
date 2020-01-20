package sdk

import com.google.gson.annotations.Expose
import java.text.DecimalFormat
import java.util.Locale

class FiscalRequest {

    constructor(merchantId: Int, privateSecurityKey: String, operation: FiscalOperation, total: Double, transactionId: String, paymentSystemType: FiscalPaymentSystemType, items: List<FiscalItem>, clientPhone: String, email: String) {
        this.totalAmount = total
        this.merchantId = merchantId
        this.operation = operation
        this.securityKey = privateSecurityKey
        this.transactionId = transactionId
        this.paymentSystemType = paymentSystemType
        this.goods = items
        this.clientPhone = clientPhone
        this.email = email
    }

    private fun GenerateExpDate(month: Int, year: Int): String{
        return "${DecimalFormat("00").format(month)}$year";
    }


    var merchantId: Int = 0
    @Expose
    val operation: FiscalOperation
    @Expose
    val totalAmount: Double
    @Expose
    val paymentSystemType: FiscalPaymentSystemType
    @Expose
    var goods: List<FiscalItem>

    var securityKey: String = ""
    @Expose
    val transactionId: String
    @Expose
    var email: String = ""
    @Expose
    var clientPhone: String = ""
    @Expose
    var AgentType: String? = null
    @Expose
    var ClientInn: String? = null
    @Expose
    var TypeOfPayment: String? = null
    @Expose
    var TypeOfProcessing: String? = null

    fun getTotal(): String {
        return String.format(Locale.US, "%.2f", this.totalAmount)
    }

}


class FiscalItem {

    constructor(description: String, quantity: Int, tax: FiscalTax, amount: Double) {
        this.amount = amount
        this.description = description
        this.tax = tax
        this.quantity = quantity
    }

    private fun GenerateExpDate(month: Int, year: Int): String{
        return "${DecimalFormat("00").format(month)}$year";
    }
    @Expose
    val description: String
    @Expose
    val tax: FiscalTax
    @Expose
    val amount: Double
    @Expose
    val quantity: Int

    fun getAmount(): String {
        return String.format(Locale.US, "%.2f", this.amount)
    }
}

public enum class FiscalOperation{
    Benefit,
    Charge
}

enum class FiscalPaymentSystemType {
    card,
    wm,
    yd,
    qiwi,
    custom
}

enum class FiscalTax {
    none,
    vat0,
    vat10,
    vat18,
    vat20,
    vat110,
    vat118,
    vat120
}