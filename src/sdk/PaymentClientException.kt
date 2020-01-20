package sdk

class PaymentClientException : Exception {

    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, e: Exception) : super(message, e) {}
}