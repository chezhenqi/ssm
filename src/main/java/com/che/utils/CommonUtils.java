package com.che.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 公共工具类
 * 
 * @author wangya
 * @date 2017年4月6日
 */
@SuppressWarnings(value = { "restriction" })
public class CommonUtils {
	private static final Logger logger = Logger.getLogger(CommonUtils.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random ran = new Random();

	/**
	 * 得到32位的随机数
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("\\-", "");
	}

	/**
	 * 获取唯一订单号 订单号规则：时间 + 4位随机码 + 时间戳后4位
	 * 
	 * @return
	 */
	public static String getOrderId() {
		String a = String.valueOf(System.currentTimeMillis());
		return sdf.format(new Date()) + random(4) + a.substring(a.length() - 4);
	}

	/**
	 * 获取长度为1~10以内的随机数
	 * 
	 * @param n
	 * @return
	 */
	public static String random(int n) {
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n + " bit number");
		}
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		int bitField = 0;
		char[] chs = new char[n];
		for (int i = 0; i < n; i++) {
			while (true) {
				int k = ran.nextInt(10);
				if ((bitField & (1 << k)) == 0) {
					bitField |= 1 << k;
					chs[i] = (char) (k + '0');
					break;
				}
			}
		}
		return new String(chs);
	}

	public static JSONObject getJSONObject(HttpServletRequest request) {
		String reqStr = readReqStr(request);
		if (reqStr == null || reqStr.equals("")) {
			reqStr = "{}";
		}
		logger.info("获取参数:" + reqStr);
		return JSONObject.parseObject(reqStr);
	}

	/**
	 * 
	 * @param request
	 * @param desc
	 *            方法描述
	 * @return
	 */
	public static JSONObject getJSONObject(HttpServletRequest request, String desc) {
		String reqStr = readReqStr(request);
		if (reqStr == null || reqStr.equals("")) {
			reqStr = "{}";
		}
		logger.info(desc + ":获取参数:" + reqStr);
		return JSONObject.parseObject(reqStr);
	}

	/**
	 * 读取request流
	 * 
	 * @param req
	 * @return
	 * @author guoyx
	 */
	public static String readReqStr(HttpServletRequest request) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根据用户名的不同长度，来进行替换 ，达到保密效果
	 *
	 * @param userName
	 *            用户名
	 * @return 替换后的用户名
	 */
	public static String strReplaceWithStar(String userName) {
		String userNameAfterReplaced = "";

		if (userName == null) {
			userName = "";
		}

		int nameLength = userName.length();

		if (nameLength <= 1) {
			userNameAfterReplaced = "*";
		} else if (nameLength == 2) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{0})\\d(?=\\d{1})");
		} else if (nameLength >= 3 && nameLength <= 6) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{1})");
		} else if (nameLength == 7) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{2})");
		} else if (nameLength == 8) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{2})");
		} else if (nameLength == 9) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{3})");
		} else if (nameLength == 10) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{3})");
		} else if (nameLength >= 11) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{4})");
		}

		return userNameAfterReplaced;

	}

	/**
	 * 实际替换动作
	 *
	 * @param username
	 *            username
	 * @param regular
	 *            正则
	 * @return
	 */
	private static String replaceAction(String username, String regular) {

		return username.replaceAll(regular, "*");
	}

	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return byte[]
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}

	public static void main(String[] args) {
		System.out.println(CommonUtils.decode("eyIxIjoiYzE3YmEwZWM1ODdhNGZjYTkyYTE4NWE3ZjAyMmI0NzMifQ=="));
	}
}
