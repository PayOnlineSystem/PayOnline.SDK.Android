package sdk

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import sdk.*
import java.io.IOException
import java.net.URI
import java.util.LinkedHashMap

class PaymentClient {

    constructor(host: String){
        this.settings = DefaultPaymentSetting(URI(host))
    }

    private var settings: IPaymentSetting

    @Throws(PaymentClientException::class)
    fun getPaymentLink(request: PaymentLinkRequest): String {
        return generatePaymentFormUrl(request)
    }

    @Throws(PaymentClientException::class)
    fun pay(request: PayRequest): PayResponse {
        return makePay(request)
    }

    @Throws(PaymentClientException::class)
    fun googlePay(request: GooglePayRequest): PayResponse {
        return makeGooglePay(request)
    }

    @Throws(PaymentClientException::class)
    fun process3Ds(request: Process3DsRequest): Process3DsResponse {
        return finish3Ds(request)
    }

    @Throws(PaymentClientException::class)
    fun createFiscalPayload(request: FiscalRequest): FiscalResponse {
        return fiscal(request)
    }

    @Throws(PaymentClientException::class)
    private fun generatePaymentFormUrl(request: PaymentLinkRequest): String {
        val query = LinkedHashMap<String, String>()
        query["MerchantId"] = request.merchantId.toString()
        query["OrderId"] = request.orderId.toString()
        query["Amount"] = request.getAmount()
        query["Currency"] = request.currency

        val signature = LinkedHashMap(query)
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.validUntil)) {
            signature["ValidUntil"] = request.validUntil
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            signature["OrderDescription"] = request.orderDescription
        }
        signature["PrivateSecurityKey"] = request.privateSecurityKey

        val secureParams = PayOnlineUtils.createQueryString(signature)
        val securityKey = PayOnlineUtils.getMD5Hash(secureParams)

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.validUntil)) {
            query["ValidUntil"] = PayOnlineUtils.urlEncodeUTF8(request.validUntil!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            query["OrderDescription"] = PayOnlineUtils.urlEncodeUTF8(request.orderDescription!!)
        }

        query["SecurityKey"] = securityKey

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.email)) {
            query["Email"] = request.email!!
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.returnUrl)) {
            query["ReturnUrl"] = request.returnUrl!!
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.failUrl)) {
            query["FailUrl"] = request.failUrl!!
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.customData)) {
            val customData = PayOnlineUtils.parseQueryString(request.customData!!)
            query.putAll(customData)
        }

        val url = StringBuilder()
        url.append(this.settings.host)
        url.append(this.settings.language)
        url.append("/payment/?")
        url.append(PayOnlineUtils.createQueryString(query))

        val uri: URI
        try {
            uri = URI(url.toString())
        } catch (e: Exception) {
            throw PaymentClientException("There was an error creating URI", e)
        }

        return uri.toString()
    }

    @Throws(PaymentClientException::class)
    private fun makePay(request: PayRequest): PayResponse {
        val query = LinkedHashMap<String, String>()
        query["MerchantId"] = request.merchantId.toString()
        query["OrderId"] = request.orderId
        query["Amount"] = request.getAmount()
        query["Currency"] = PayOnlineUtils.urlEncodeUTF8(request.currency)

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.accountId)) {
            query["AccountId"] = request.accountId.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.posTransactionId)) {
            query["PosTransactionId"] = request.posTransactionId.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.serviceType)) {
            query["ServiceType"] = request.serviceType.toString()
        }

        val signature = LinkedHashMap(query)
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            signature["OrderDescription"] = request.orderDescription
        }
        signature["PrivateSecurityKey"] = request.privateSecurityKey

        val secureParams = PayOnlineUtils.createQueryString(signature)
        val securityKey = PayOnlineUtils.getMD5Hash(secureParams)

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            query["OrderDescription"] = PayOnlineUtils.urlEncodeUTF8(request.orderDescription!!)
        }

        query["SecurityKey"] = securityKey

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.ip)) {
            query["Ip"] = PayOnlineUtils.urlEncodeUTF8(request.ip!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.email)) {
            query["Email"] = PayOnlineUtils.urlEncodeUTF8(request.email!!)
        }

        query["CardHolderName"] = PayOnlineUtils.urlEncodeUTF8(request.cardHolderName)
        query["CardNumber"] = request.cardNumber
        query["CardExpDate"] = request.cardExpDate
        query["CardCvv"] = request.cardCvv

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.country)) {
            query["Country"] = PayOnlineUtils.urlEncodeUTF8(request.country!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.city)) {
            query["City"] = PayOnlineUtils.urlEncodeUTF8(request.city!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.address)) {
            query["Address"] = PayOnlineUtils.urlEncodeUTF8(request.address!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.zip)) {
            query["Zip"] = PayOnlineUtils.urlEncodeUTF8(request.zip!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.state)) {
            query["State"] = PayOnlineUtils.urlEncodeUTF8(request.state!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.phone)) {
            query["Phone"] = PayOnlineUtils.urlEncodeUTF8(request.phone!!)
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.issuer)) {
            query["Issuer"] = PayOnlineUtils.urlEncodeUTF8(request.issuer!!)
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.returnUrl)) {
            query["ReturnUrl"] = request.returnUrl.toString()
        }

        val url = StringBuilder()
        url.append(this.settings.host)
        url.append("payment/transaction/auth/")

        val uri: URI
        try {
            uri = URI(url.toString())
        } catch (e: Exception) {
            throw PaymentClientException("There was an error creating API request", e)
        }

        val result = getResult(uri, query)

        return PayResponse(result)
    }

    @Throws(PaymentClientException::class)
    private fun makeGooglePay(request: GooglePayRequest): PayResponse {
        val query = LinkedHashMap<String, String>()
        query["MerchantId"] = request.merchantId.toString()
        query["OrderId"] = request.orderId.toString()
        query["Amount"] = request.getAmount()
        query["Currency"] = request.currency.toString()

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.googlePaymentToken)) {
            query["PaymentToken"] = request.googlePaymentToken.toString()
        }

        val signature = LinkedHashMap(query)
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            signature["OrderDescription"] = request.orderDescription
        }
        signature["PrivateSecurityKey"] = request.privateSecurityKey

        val secureParams = PayOnlineUtils.createQueryString(signature)
        val securityKey = PayOnlineUtils.getMD5Hash(secureParams)

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.orderDescription)) {
            query["OrderDescription"] = PayOnlineUtils.urlEncodeUTF8(request.orderDescription.toString())
        }

        query["SecurityKey"] = securityKey

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.ip)) {
            query["Ip"] = request.ip.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.email)) {
            query["Email"] = request.email.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.country)) {
            query["Country"] = request.country.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.city)) {
            query["City"] = request.city.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.address)) {
            query["Address"] = request.address.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.zip)) {
            query["Zip"] = request.zip.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.state)) {
            query["State"] = request.state.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.phone)) {
            query["Phone"] = request.phone.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.issuer)) {
            query["Issuer"] = request.issuer.toString()
        }
        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.googleMerchantId)) {
            query["GoogleMerchantId"] = request.googleMerchantId.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.accountId)) {
            query["AccountId"] = request.accountId.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.posTransactionId)) {
            query["PosTransactionId"] = request.posTransactionId.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.serviceType)) {
            query["ServiceType"] = request.serviceType.toString()
        }

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.returnUrl)) {
            query["ReturnUrl"] = request.returnUrl.toString()
        }

        val url = StringBuilder()
        url.append(this.settings.host)
        url.append("payment/transaction/googlepay/")

        val uri: URI
        try {
            uri = URI(url.toString())
        } catch (e: Exception) {
            throw PaymentClientException("Error creating URI")
        }
        //PayOnlineUtils.createQueryString(
        //val result = AsyncApi().execute(uri.toString(), PayOnlineUtils.createQueryString(query));
        val result = getResult(uri, query)
        //val result = PayOnlineUtils.callApi(uri, query = );

        return PayResponse(result) //.get() for async
    }

    @Throws(PaymentClientException::class)
    private fun fiscal(request: FiscalRequest): FiscalResponse {
        var requestBody = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
            .toJson(request) //Json(JsonConfiguration(encodeDefaults = false)).stringify(FiscalRequest.serializer(), request)
        val signature = LinkedHashMap<String, String>()
        signature["RequestBody"] = requestBody
        signature["MerchantId"] = request.merchantId.toString()
        signature["PrivateSecurityKey"] = request.securityKey

        val secureParams = PayOnlineUtils.createQueryString(signature)
        val securityKey = PayOnlineUtils.getMD5Hash(secureParams)

        request.securityKey = securityKey

        val url = StringBuilder()
        url.append(this.settings.host)
        url.append("Services/Fiscal/Request.ashx?MerchantId=${request.merchantId}&SecurityKey=${request.securityKey}")

        val uri: URI
        try {
            uri = URI(url.toString())
        } catch (e: Exception) {
            throw PaymentClientException("There was an error creating API request", e)
        }

        val result = getResult(uri, GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
            .toJson(request))

        return Gson().fromJson(result, FiscalResponse::class.java) //Json.parse(FiscalResponse.serializer(), result)
    }

    @Throws(PaymentClientException::class)
    private fun finish3Ds(request: Process3DsRequest): Process3DsResponse {
        val query = LinkedHashMap<String, String>()
        query["MerchantId"] = request.merchantId.toString()
        query["TransactionId"] = request.transactionId.toString()
        query["PaRes"] = request.paRes
        query["PD"] = request.pd

        val signature = LinkedHashMap(query)
        signature["PrivateSecurityKey"] = request.privateSecurityKey

        val secureParams = PayOnlineUtils.createQueryString(signature)
        val securityKey = PayOnlineUtils.getMD5Hash(secureParams)
        query["SecurityKey"] = securityKey

        if (!PayOnlineUtils.stringIsNullOrWhiteSpace(request.customData)) {
            val customData = PayOnlineUtils.parseQueryString(request.customData!!)
            query.putAll(customData)
        }

        val url = StringBuilder()
        url.append(this.settings.host)
        url.append("payment/transaction/auth/3ds/")

        val uri: URI
        try {
            uri = URI(url.toString())
        } catch (e: Exception) {
            throw PaymentClientException("There was an error creating API request", e)
        }

        val result = getResult(uri, query)

        return Process3DsResponse(result)
    }

    @Throws(PaymentClientException::class)
    private fun getResult(uri: URI, query: Map<String, String>): String {
        try {
            return PayOnlineUtils.callApi(uri.toString(), PayOnlineUtils.createQueryString(query))
        } catch (e: IOException) {
            e.printStackTrace()
            throw PaymentClientException("There was an error calling API service", e)
        }

    }
    @Throws(PaymentClientException::class)
    private fun getResult(uri: URI, json: String): String {
        try {
            return PayOnlineUtils.callApi(uri.toString(), json, true)
        } catch (e: IOException) {
            e.printStackTrace()
            throw PaymentClientException("There was an error calling API service", e)
        }

    }

}