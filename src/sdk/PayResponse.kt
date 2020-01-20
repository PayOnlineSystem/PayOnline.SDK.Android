package sdk

import java.io.Serializable

open class PayResponse(v: String): Serializable {

    var id: Long = 0
        private set
    var operation: String? = null
        private set
    var code: Int = 0
        private set
    var result: String? = null
        private set
    var status: String? = null
        private set
    var errorCode: Int = 0
        private set
    var message: String? = null
        private set
    var rebillAnchor: String? = null
        private set
    var ipCountry: String? = null
        private set
    var binCountry: String? = null
        private set
    var specialConditions: String? = null
        private set
    var paReq: String? = null
        private set
    var pd: String? = null
        private set
    var acsUrl: String? = null
        private set
    var termUrl: String? = null
        private set

    init {
        val map = PayOnlineUtils.parseQueryString(v)
        for ((key, value) in map) {
            if (key.equals("Id", ignoreCase = true))
                this.id = java.lang.Long.parseLong(value.trim { it <= ' ' })
            if (key.equals("Operation", ignoreCase = true))
                this.operation = value.trim { it <= ' ' }
            if (key.equals("Code", ignoreCase = true))
                this.code = Integer.parseInt(value.trim { it <= ' ' })
            if (key.equals("Result", ignoreCase = true))
                this.result = value.trim { it <= ' ' }
            if (key.equals("Status", ignoreCase = true))
                this.status = value.trim { it <= ' ' }
            if (key.equals("errorCode", ignoreCase = true))
                this.errorCode = Integer.parseInt(value.trim { it <= ' ' })
            if (key.equals("Message", ignoreCase = true))
                this.message = value.trim { it <= ' ' }
            if (key.equals("rebillAnchor", ignoreCase = true))
                this.rebillAnchor = value.trim { it <= ' ' }
            if (key.equals("ipCountry", ignoreCase = true))
                this.ipCountry = value.trim { it <= ' ' }
            if (key.equals("binCountry", ignoreCase = true))
                this.binCountry = value.trim { it <= ' ' }
            if (key.equals("SpecialConditions", ignoreCase = true))
                this.specialConditions = value.trim { it <= ' ' }
            if (key.equals("PaReq", ignoreCase = true))
                this.paReq = value.trim { it <= ' ' }
            if (key.equals("PD", ignoreCase = true))
                this.pd = value.trim { it <= ' ' }
            if (key.equals("AcsUrl", ignoreCase = true))
                this.acsUrl = value.trim { it <= ' ' }
            if (key.equals("TermUrl", ignoreCase = true))
                this.termUrl = value.trim { it <= ' ' }
        }
    }
}