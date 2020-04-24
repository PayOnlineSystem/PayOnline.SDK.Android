package sdk

import java.io.Serializable
import java.text.DecimalFormat
import java.util.Locale

class PayRequest: Serializable {

    constructor(merchantId: Int, privateSecurityKey: String, orderId: String, amount: Double, cardNumber: String, month: Int, year: Int, cardCvv: String, cardHolderName: String, currency:String) {
        this.amount = amount
        this.merchantId = merchantId
        this.orderId = orderId
        this.privateSecurityKey = privateSecurityKey
        this.cardHolderName = cardHolderName
        this.cardNumber = cardNumber
        this.cardExpDate = GenerateExpDate(month, year)
        this.cardCvv = cardCvv
        this.currency = currency
    }

    private fun GenerateExpDate(month: Int, year: Int): String{
        return "${DecimalFormat("00").format(month)}$year";
    }

    val merchantId: Int
    val orderId: String
    val amount: Double
    val currency: String
    var orderDescription: String? = null
    val privateSecurityKey: String
    var email: String? = null
    var ip: String? = null
    val cardHolderName: String
    val cardNumber: String
    val cardExpDate: String
    val cardCvv: String
    var country: String? = null
    var city: String? = null
    var address: String? = null
    var state: String? = null
    var zip: String? = null
    var phone: String? = null
    var issuer: String? = null

    /**
     * accountId, posTransactionId, serviceType for mtt merchants.
     *
     */
    var accountId: String? = null
    var posTransactionId: String? = null
    var serviceType: String? = null
    var returnUrl: String? = null

    var custom: String? = null

    fun getAmount(): String {
        return String.format(Locale.US, "%.2f", this.amount)
    }
}