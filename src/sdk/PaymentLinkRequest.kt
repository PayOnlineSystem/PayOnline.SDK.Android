package sdk

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.Locale

class PaymentLinkRequest {

    constructor(merchantId: Int, privateSecurityKey: String, orderId: String, amount: Double, cardNumber: String, cardExpDate: String, cardCvv: String, cardHolderName: String, currency:String) {
        this.amount = amount
        this.merchantId = merchantId
        this.orderId = orderId
        this.privateSecurityKey = privateSecurityKey
        this.currency = currency
    }

    val merchantId: Int
    val orderId: String
    val amount: Double
    val currency: String
    var validUntil: String? = null
    var orderDescription: String? = null
    val privateSecurityKey: String
    var email: String? = null
    var returnUrl: String? = null
        @Throws(PaymentClientException::class)
        set(value) = try {
            field = URLEncoder.encode(value, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw PaymentClientException("Can't encoding value of 'ReturnUrl' parameter", e)
        }
    var failUrl: String? = null
        @Throws(PaymentClientException::class)
        set(value) = try {
            field = URLEncoder.encode(value, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw PaymentClientException("Can't encoding value of 'FailUrl' parameter", e)
        }
    var customData: String? = null

    fun getAmount(): String {
        return String.format(Locale.US, "%.2f", this.amount)
    }
}