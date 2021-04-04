package tech.kicky.common

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * Retrofit instance
 * author: yidong
 * 2021/1/2
 */
object RetrofitManager {
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
//                .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("www.baidu.com", 8888)))
//                .authenticator(object : Authenticator {
//                    override fun authenticate(route: Route?, response: Response): Request? {
//                        System.out.println("Authenticating for response: " + response);
//                        //challenges()方法可以得到第一次请求失败的授权相关的信息
//                        //如果request 里面有没有 Authorization 的 http header，或者用户密码不对，则返回 http code 401 页面给客户端
//                        System.out.println("Challenges: " + response.challenges());
//                        val credential: String = Credentials.basic("*** name ***", "*** password ***");
//                        // 第5次认证失败返回null
//                        if (responseCount(response) >= 5) {
//                            System.out.println("认证失败，返回null");
//                            return null; // If we've failed 5 times, give up.
//                        }
//                        //如果用户名密码有问题，那么Okhttp会一直用这个错误的登录信息尝试登录，我们应该判断如果之前已经用该用户名密码登录失败了，就不应该再次登录，
//                        // 这种情况下需要让Authenticator对象的authenticate()方法返回null，这就避免了没必要的重复尝试
//                        if (credential.equals(response.request().header("Authorization"))) {
//                            return null;
//                        }
//                        return response.request().newBuilder().header("Authorization", credential).build();
//                    }
//                })
//                .dns(object : Dns {
//                    override fun lookup(hostname: String): MutableList<InetAddress> {
//                        TODO("Not yet implemented")
//                    }
//
//                })
                .build()
    }

    private fun responseCount(response: Response): Int {
        var response = response
        var result = 1
        while (response.priorResponse().also { response = it!! } != null) {
            result++
        }
        return result
    }

    private val retrofitance: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    val wanAndroidApi: WanAndroidApi by lazy {
        retrofitance.create(WanAndroidApi::class.java)
    }

}