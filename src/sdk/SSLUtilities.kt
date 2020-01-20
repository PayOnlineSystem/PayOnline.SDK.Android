package sdk

import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * This class provide various static methods that relax X509 certificate and
 * hostname verification while using the SSL over the HTTP protocol.
 *
 * @author    Francis Labrie
 */
object SSLUtilities {

    /**
     * Hostname verifier for the Sun's deprecated API.
     *
     */
    @Deprecated("see {@link #_hostnameVerifier}.")
    private var __hostnameVerifier: com.sun.net.ssl.HostnameVerifier? = null
    /**
     * Thrust managers for the Sun's deprecated API.
     *
     */
    @Deprecated("see {@link #_trustManagers}.")
    private var __trustManagers: Array<com.sun.net.ssl.TrustManager>? = null
    /**
     * Hostname verifier.
     */
    private var _hostnameVerifier: HostnameVerifier? = null
    /**
     * Thrust managers.
     */
    private var _trustManagers: Array<TrustManager>? = null

    /**
     * Return true if the protocol handler property java.
     * protocol.handler.pkgs is set to the Sun's com.sun.net.ssl.
     * internal.www.protocol deprecated one, false
     * otherwise.
     *
     * @return                true if the protocol handler
     * property is set to the Sun's deprecated one, false
     * otherwise.
     */
    private val isDeprecatedSSLProtocol: Boolean
        get() =
            "com.sun.net.ssl.internal.www.protocol" == System.getProperty("java.protocol.handler.pkgs") // isDeprecatedSSLProtocol


    /**
     * Set the default Hostname Verifier to an instance of a fake class that
     * trust all hostnames. This method uses the old deprecated API from the
     * com.sun.ssl package.
     *
     */
    @Deprecated("see {@link #_trustAllHostnames()}.")
    private fun __trustAllHostnames() {
        // Create a trust manager that does not validate certificate chains
        if (__hostnameVerifier == null) {
            __hostnameVerifier = _FakeHostnameVerifier()
        } // if
        // Install the all-trusting host name verifier
        com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(__hostnameVerifier!!)
    } // __trustAllHttpsCertificates

    /**
     * Set the default X509 Trust Manager to an instance of a fake class that
     * trust all certificates, even the self-signed ones. This method uses the
     * old deprecated API from the com.sun.ssl package.
     *
     */
    @Deprecated("see {@link #_trustAllHttpsCertificates()}.")
    private fun __trustAllHttpsCertificates() {
        val context: com.sun.net.ssl.SSLContext

        // Create a trust manager that does not validate certificate chains
        if (__trustManagers == null) {
            __trustManagers = arrayOf<com.sun.net.ssl.TrustManager>(_FakeX509TrustManager())
        } // if
        // Install the all-trusting trust manager
        try {
            context = com.sun.net.ssl.SSLContext.getInstance("SSL")
            context.init(null, __trustManagers, SecureRandom())
        } catch (gse: GeneralSecurityException) {
            throw IllegalStateException(gse.message)
        }
        // catch
        com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory())
    } // __trustAllHttpsCertificates

    /**
     * Set the default Hostname Verifier to an instance of a fake class that
     * trust all hostnames.
     */
    private fun _trustAllHostnames() {
        // Create a trust manager that does not validate certificate chains
        if (_hostnameVerifier == null) {
            _hostnameVerifier = FakeHostnameVerifier()
        } // if
        // Install the all-trusting host name verifier:
        HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier!!)
    } // _trustAllHttpsCertificates

    /**
     * Set the default X509 Trust Manager to an instance of a fake class that
     * trust all certificates, even the self-signed ones.
     */
    private fun _trustAllHttpsCertificates() {
        val context: SSLContext

        // Create a trust manager that does not validate certificate chains
        if (_trustManagers == null) {
            _trustManagers = arrayOf(FakeX509TrustManager())
        } // if
        // Install the all-trusting trust manager:
        try {
            context = SSLContext.getInstance("SSL")
            context.init(null, _trustManagers, SecureRandom())
        } catch (gse: GeneralSecurityException) {
            throw IllegalStateException(gse.message)
        }
        // catch
        HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
    } // _trustAllHttpsCertificates

    /**
     * Set the default Hostname Verifier to an instance of a fake class that
     * trust all hostnames.
     */
    fun trustAllHostnames() {
        // Is the deprecated protocol setted?
        if (isDeprecatedSSLProtocol) {
            __trustAllHostnames()
        } else {
            _trustAllHostnames()
        } // else
    } // trustAllHostnames

    /**
     * Set the default X509 Trust Manager to an instance of a fake class that
     * trust all certificates, even the self-signed ones.
     */
    fun trustAllHttpsCertificates() {
        // Is the deprecated protocol setted?
        if (isDeprecatedSSLProtocol) {
            __trustAllHttpsCertificates()
        } else {
            _trustAllHttpsCertificates()
        } // else
    } // trustAllHttpsCertificates

    /**
     * This class implements a fake hostname verificator, trusting any host
     * name. This class uses the old deprecated API from the com.sun.
     * ssl package.
     *
     * @author    Francis Labrie
     *
     */
    @Deprecated("see {@link SSLUtilities.FakeHostnameVerifier}.")
    class _FakeHostnameVerifier : com.sun.net.ssl.HostnameVerifier {

        /**
         * Always return true, indicating that the host name is an
         * acceptable match with the server's authentication scheme.
         *
         * @param hostname        the host name.
         * @param session         the SSL session used on the connection to
         * host.
         * @return                the true boolean value
         * indicating the host name is trusted.
         */
        override fun verify(hostname: String, session: String): Boolean {
            return true
        } // verify
    } // _FakeHostnameVerifier


    /**
     * This class allow any X509 certificates to be used to authenticate the
     * remote side of a secure socket, including self-signed certificates. This
     * class uses the old deprecated API from the com.sun.ssl
     * package.
     *
     * @author    Francis Labrie
     *
     */
    @Deprecated("see {@link SSLUtilities.FakeX509TrustManager}.")
    class _FakeX509TrustManager : com.sun.net.ssl.X509TrustManager {


        /**
         * Always return true, trusting for client SSL
         * chain peer certificate chain.
         *
         * @param chain           the peer certificate chain.
         * @return                the true boolean value
         * indicating the chain is trusted.
         */
        override fun isClientTrusted(chain: Array<X509Certificate>): Boolean {
            return true
        } // checkClientTrusted

        /**
         * Always return true, trusting for server SSL
         * chain peer certificate chain.
         *
         * @param chain           the peer certificate chain.
         * @return                the true boolean value
         * indicating the chain is trusted.
         */
        override fun isServerTrusted(chain: Array<X509Certificate>): Boolean {
            return true
        } // checkServerTrusted

        /**
         * Return an empty array of certificate authority certificates which
         * are trusted for authenticating peers.
         *
         * @return                a empty array of issuer certificates.
         */
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return _AcceptedIssuers
        } // getAcceptedIssuers

        companion object {

            /**
             * Empty array of certificate authority certificates.
             */
            private val _AcceptedIssuers = arrayOf<X509Certificate>()
        }
    } // _FakeX509TrustManager


    /**
     * This class implements a fake hostname verificator, trusting any host
     * name.
     *
     * @author    Francis Labrie
     */
    class FakeHostnameVerifier : HostnameVerifier {

        /**
         * Always return true, indicating that the host name is
         * an acceptable match with the server's authentication scheme.
         *
         * @param hostname        the host name.
         * @param session         the SSL session used on the connection to
         * host.
         * @return                the true boolean value
         * indicating the host name is trusted.
         */
        override fun verify(hostname: String,
                            session: javax.net.ssl.SSLSession): Boolean {
            return true
        } // verify
    } // FakeHostnameVerifier


    /**
     * This class allow any X509 certificates to be used to authenticate the
     * remote side of a secure socket, including self-signed certificates.
     *
     * @author    Francis Labrie
     */
    class FakeX509TrustManager : X509TrustManager {


        /**
         * Always trust for client SSL chain peer certificate
         * chain with any authType authentication types.
         *
         * @param chain           the peer certificate chain.
         * @param authType        the authentication type based on the client
         * certificate.
         */
        override fun checkClientTrusted(chain: Array<X509Certificate>,
                                        authType: String) {
        } // checkClientTrusted

        /**
         * Always trust for server SSL chain peer certificate
         * chain with any authType exchange algorithm types.
         *
         * @param chain           the peer certificate chain.
         * @param authType        the key exchange algorithm used.
         */
        override fun checkServerTrusted(chain: Array<X509Certificate>,
                                        authType: String) {
        } // checkServerTrusted

        /**
         * Return an empty array of certificate authority certificates which
         * are trusted for authenticating peers.
         *
         * @return                a empty array of issuer certificates.
         */
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return _AcceptedIssuers
        } // getAcceptedIssuers

        companion object {

            /**
             * Empty array of certificate authority certificates.
             */
            private val _AcceptedIssuers = arrayOf<X509Certificate>()
        }
    } // FakeX509TrustManager
} // SSLUtilities