package cn.catkin.wechatest.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Catkin_nice
 *
 */
@RestController
public class WechatCallbackapi {
	
	private final static String TOKEN = "test";
	
	@RequestMapping(value = "/wechat", method = {RequestMethod.GET, RequestMethod.POST})
	public void callback(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(required = false) String signature, 
			@RequestParam(required = false) String timestamp, 
			@RequestParam(required = false) String nonce, 
			@RequestParam(required = false) String echostr) throws Exception {
		String[] params = {TOKEN, timestamp, nonce};
		Arrays.sort(params);
		
		String paramStr = "";
		for (String param : params) {
			paramStr += param;
		}
		
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(paramStr.getBytes());
		byte[] codedBytes = sha1.digest();
		String codedString = new BigInteger(1, codedBytes).toString(16);
		
		if (codedString.equals(signature)) {
			try (PrintWriter writer = response.getWriter()) {
				writer.write(echostr);
				writer.flush();
			}
		}
	}

}
