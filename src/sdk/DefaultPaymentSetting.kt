package sdk

import java.net.URI
import java.net.URISyntaxException

class DefaultPaymentSetting : IPaymentSetting {

    override lateinit var language: String

    override lateinit var host: URI

    override fun setHost(value: URI): IPaymentSetting {
        this.host = value
        return this;
    }

    @Throws(PaymentClientException::class)
    constructor() {
        try {
            this.host = URI("https://secure.payonlinesystem.com/")
        } catch (e: URISyntaxException) {
            throw PaymentClientException("Invalid value of 'host' parameter")
        }

        this.language = "ru"
    }

    constructor(host: URI) {
        this.host = host
        this.language = "ru"
    }

    constructor(language: String) {
        this.language = language
    }

    constructor(host: URI, language: String) {
        this.host = host
        this.language = language
    }

}