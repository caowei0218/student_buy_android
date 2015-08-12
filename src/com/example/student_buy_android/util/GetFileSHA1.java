package com.example.student_buy_android.util;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class GetFileSHA1 {
	/**
	 * 获取文件SHA1摘要值
	 * 
	 * @param fileInputStream
	 * @return
	 */
	public static String getSHA1(InputStream fileInputStream) {
		// 缓冲区大小
		int bufferSize = 256 * 1024;
		DigestInputStream digestInputStream = null;
		try {
			// 拿到一个SHA1转换器（这里可以换成MD5）
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			// 使用DigestInputStream
			digestInputStream = new DigestInputStream(fileInputStream,
					messageDigest);
			// read的过程中进行SHA1处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0)
				;
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 把字节数组转换成字符串
			return byteArrayToHex(resultByteArray);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				digestInputStream.close();
				fileInputStream.close();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 将字节数组换成成16进制的字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}
}