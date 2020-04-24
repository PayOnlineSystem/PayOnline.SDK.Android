package sdk

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.Locale

class GooglePayRequest {

    private val ENCODING = "UTF-8"
    /* Gets */
    /* Sets */
    var merchantId: Int = 0
    var orderId: String? = null
    private var amount: Double = 0.toDouble()
    var currency: String? = null
    var orderDescription: String? = null
    var privateSecurityKey: String? = null
    var email: String? = null
    var ip: String? = null
    var cardHolderName: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var cardNumber: String? = null
    var cardExpDate: String? = null
    var cardCvv: String? = null
    var country: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var city: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var address: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var state: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var googleMerchantId: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var googlePaymentToken: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var zip: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }
    var phone: String? = null
        set(value) = try {
            field = URLEncoder.encode(PayOnlineUtils.toUTF8(value.toString()), this.ENCODING)
        } catch (e: UnsupportedEncodingException) {
            field = null
        }
    var issuer: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }

    /**
     * accountId, posTransactionId, serviceType for mtt merchants.
     *
     */
    var accountId: String? = null
    var posTransactionId: String? = null
    var serviceType: String? = null
    var returnUrl: String? = null

    var custom: String? = null
        set(value) {
            field = PayOnlineUtils.toUTF8(value.toString())
        }

    fun getAmount(): String {
        return String.format(Locale.US, "%.2f", this.amount)
    }

    fun setAmount(value: Double) {
        this.amount = value
    }
}