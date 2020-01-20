package sdk

import java.net.URI

interface IPaymentSetting {

    val host: URI
    var language: String
    fun setHost(value: URI) : IPaymentSetting
}