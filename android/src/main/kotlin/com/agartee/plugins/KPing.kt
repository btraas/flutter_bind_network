import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL

class KPing {
    var succeeded: Boolean? = null
    var net: String? = "NO_CONNECTION"
    var host = ""
    var ip = ""
    var dns = Int.MAX_VALUE
    var connectTime = Int.MAX_VALUE

    companion object {
        fun ping(url: URL, ctx: Context): KPing {
            val r = KPing()
            if (true || isNetworkConnected(ctx)) {
                r.net = getNetworkType(ctx)
                try {
                    val hostAddress: String
                    val start = System.currentTimeMillis()
                    hostAddress = InetAddress.getByName(url.host).hostAddress
                    val dnsResolved = System.currentTimeMillis()
                    val socket = Socket(hostAddress, url.port)
                    socket.close()
                    val probeFinish = System.currentTimeMillis()
                    r.dns = (dnsResolved - start).toInt()
                    r.connectTime = (probeFinish - dnsResolved).toInt()
                    r.host = url.host
                    r.ip = hostAddress
                    r.succeeded = true
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    Log.e("Ping", "Unable to ping")
                    r.succeeded = false
                }
            }
            return r
        }

        fun pingHostPort(
            host: String,
            port: Int,
            timeout: Int = 5000,
            ctx: Context,
        ): KPing {
            val r = KPing()
            if (true || isNetworkConnected(ctx)) {
                r.net = getNetworkType(ctx)
                try {
                    var hostAddress: String
                    val start = System.currentTimeMillis()
                    //                hostAddress = InetAddress.getByName(url.getHost()).getHostAddress();
//                    val dnsResolved = System.currentTimeMillis()
                    var finished = false

//                    val thread = scope.launch {
                    try {
                        val address = InetAddress.getByName(host)
//                            val socket = Socket(address, port)
                        val socket = Socket()
                        socket.connect(InetSocketAddress(address, port), timeout)
                        socket.close()

                        finished = true
                        r.succeeded = true
                    } catch (e: Exception) {
                        e.printStackTrace()
                        r.succeeded = false
                        return r
                    }
//                    }
//                    withTimeout(timeout.toLong()) {
//                        r.succeeded = false
//                        thread.cancel(null)
//                    }

//                    thread.join()
//                    if(!finished) {
//                        Log.w("KPing", "Interrupted")
//                        return r
//                    }

//                    thread.start()
                    val probeFinish = System.currentTimeMillis()
                    //                r.dns = (int) (dnsResolved - start);
                    r.connectTime = (probeFinish - start).toInt()
                    r.host = host
                    r.ip = host
                    r.succeeded = true
                } catch (ex: Exception) {
                    Log.e("Ping", "Unable to ping")
                    r.succeeded = false
                }
            }
            return r
        }

        fun isNetworkConnected(context: Context): Boolean {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        fun getNetworkType(context: Context): String? {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork?.typeName
        }
    }

}
