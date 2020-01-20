
package sdk

import com.google.gson.annotations.SerializedName

open class FiscalResponse() {

    var status: FiscalStatus? = null
    var payload: FiscalPayload? =null
}

open class FiscalPayload() {

    @SerializedName("name_document")
    var nameDocument: String? = null

    @SerializedName("fiscal_receipt_number")
    var fiscalReceiptNumber: Int = 0

    @SerializedName("fiscal_document_number")
    var fiscalDocumentNumber: Long = 0

    @SerializedName("fiscal_document_attribute")
    var fiscalDocumentAttribute: Long = 0

    @SerializedName("shift_number")
    var shiftNumber: Int = 0

    @SerializedName("fn_number")
    var fnNumber: String? = null

    @SerializedName("ecr_registration_number")
    var ecrRegistrationNumber: String? = null

    @SerializedName("receipt_datetime")
    var receiptDateTime: String? = null

    var recipient: String? = null

    var address: String? = null

    var organization: String? = null

    var inn: String? = null

    @SerializedName("tax_system")
    var taxSystem: String? = null
    var operation: String? = null
    var email: String? = null
    var clientName: String? = null
    var clientInn: String? = null
    var agentType: String? = null
    var total: Double = 0.0
    @SerializedName("form_payment")
    var formPayment: String? = null

    @SerializedName("fns_site")
    var fnsSite: String? = null

    @SerializedName("employee_name")
    var employeeName: String? = null

    @SerializedName("employee_position")
    var employeePosition: String? = null


    @SerializedName("tax_amount")
    var taxAmount: Double = 0.0

}

open class FiscalStatus() {

    var code: Int = 0
    var text: String? = null
}

//доделать все поля