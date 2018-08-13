package com.tengyue360.sms;



import java.io.IOException;

/**
 * unicode转换工具�?
 * 
 * @author guoxl
 * 
 */
public class UnicodeUtil {

	public static void main(String[] args) throws IOException {
//		String path = "E:/a.txt";
//		File file = null;
//		BufferedReader br = null;
//		file = new File(path);
//		br = new BufferedReader(new InputStreamReader(
//				new FileInputStream(file), "gbk"));
//		StringBuilder sb = new StringBuilder();
//		String length = "";
//		while ((length = br.readLine()) != null) {
//			sb.append(length);
//		}
		String str= "\u5c0a\u656c\u7684\u5ba2\u6237\u4f60\u597d\uff0c\u4f60\u4e8e\u4e1c\u7814\u96c6\u56e2\u8d2d\u4e70\u7684\u5206\u671f\u8d2d\u4ea7\u54c1\u5df2\u53d1\u8d27\uff0c\u8ba2\u5355\u53f7\u4e3a\uff1a#[orderCode],\u795d\u4f60\u8d2d\u7269\u6109\u5feb\uff01";
		System.out.println(ascii2Native(str));

	}

	// unicode转为本地
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf("\\u");
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf("\\u", begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!"\\u".equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}
}
