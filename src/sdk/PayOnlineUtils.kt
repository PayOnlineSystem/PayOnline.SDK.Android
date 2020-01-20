package sdk

import javax.net.ssl.HttpsURLConnection

import java.io.*
import java.math.BigInteger
import java.net.URL
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*



object PayOnlineUtils {

    @Throws(PaymentClientException::class)
    fun getMD5Hash(str: String): String {
        val m: MessageDigest
        try {
            m = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            throw PaymentClientException("Can't receive hash", e)
        }

        m.reset()
        m.update(str.toByteArray())

        val digest = m.digest()
        val bigInt = BigInteger(1, digest)
        val hash = StringBuilder(bigInt.toString(16))

        while (hash.length < 32) {
            hash.insert(0, "0")
        }

        return hash.toString()
    }

    @Throws(IOException::class)
    fun callApi(url: String, query: String, isJson: Boolean = false): String {
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();

        val obj = URL(url)
        val con = obj.openConnection() as HttpsURLConnection
        con.requestMethod = "POST"
        con.doOutput = true
        val contentType = if (isJson) "application/json" else "application/x-www-form-urlencoded; charset=utf-8"
        con.setRequestProperty("Content-Type", contentType);

        try{
            val wr = DataOutputStream(con.outputStream)
            wr.writeBytes(query)
            wr.flush()
            wr.close()
        }
        catch(ex:Exception){
            val e = ex;
            //(ex.toString());
        }


        val in_ = BufferedReader(InputStreamReader(con.inputStream))

        var inputLine: String
        val response = StringBuilder()

        in_.forEachLine {
            response.append(it)
        }

        in_.close()

        return response.toString()
    }

    fun parseQueryString(query: String): Map<String, String> {
        val params = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val map = LinkedHashMap<String, String>()
        for (param in params) {
            val index = param.indexOf("=")
            val name = param.substring(0, index)
            val value = param.substring(index + 1)
            map[name] = value
        }
        return map
    }

    fun createQueryString(map: Map<*, *>): String {
        val sb = StringBuilder()
        for ((key, value) in map) {
            if (sb.length > 0) {
                sb.append("&")
            }
            sb.append(
                String.format(
                    "%s=%s",
                    key.toString(),
                    value.toString()
                )
            )
        }

        return sb.toString()
    }

    @Throws(PaymentClientException::class)
    fun toUTF8(s: String): String {
        try {
            return if (stringIsNullOrWhiteSpace(s)) {
                throw PaymentClientException("Can't make url encoding for string")
            } else String(s.toByteArray(charset("UTF-8")), charset("ISO-8859-1"))

        } catch (e: UnsupportedEncodingException) {
            throw PaymentClientException("Can't convert string to UTF-8", e)
        }

    }

    @Throws(PaymentClientException::class)
    fun urlEncodeUTF8(s: String): String {
        try {
            return if (stringIsNullOrWhiteSpace(s)) {
                throw PaymentClientException("Can't make url encoding for string")
            } else URLEncoder.encode(s, "UTF-8")

        } catch (e: UnsupportedEncodingException) {
            throw PaymentClientException("Can't make url encoding for string", e)
        }

    }

    fun stringIsNullOrWhiteSpace(s: String?): Boolean {
        return isNullOrEmpty(s) || isNullOrWhitespace(s)
    }

    fun isNullOrEmpty(s: String?): Boolean {
        return s == null || s.length == 0
    }

    fun isNullOrWhitespace(s: String?): Boolean {
        return s == null || isWhitespace(s)
    }

    private fun isWhitespace(s: String): Boolean {
        val length = s.length
        if (length > 0) {
            for (i in 0 until length) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
            }
            return true
        }
        return false
    }
}