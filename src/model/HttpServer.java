package model;

import java.net.CookieManager;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpServer {
	// 生成静态的client
	public static OkHttpClient client = new OkHttpClient();
	// 设置cookie
	static {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

		client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();

	}

	// 创建让其他类调用客户端的方法
	public static OkHttpClient getSharedClient() {
		return client;
}

	// 创建调用服务器的api方法，例如 api=hello
	public static String serverAddress = "http:/192.168.123.141:8080/membercenter/"; 
	public static Request.Builder requestBuilderWithApi(String api) {
		return new Request.Builder().url(serverAddress+"api/" + api);
	}

}
