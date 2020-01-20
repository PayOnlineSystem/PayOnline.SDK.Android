package sdk

class Process3DsRequest {
    constructor(merchantId: Int, privateSecurityKey: String, transactionId: Long, pares: String, pd: String) {
        this.merchantId = merchantId
        this.privateSecurityKey = privateSecurityKey
        this.transactionId = transactionId
        this.paRes = pares
        this.pd = pd
    }

    val merchantId: Int
    val transactionId: Long
    val paRes: String
    val pd: String
    val privateSecurityKey: String
    var customData: String? = null
}