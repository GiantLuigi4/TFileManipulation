package com.tfc.files.compression;

import java.math.BigInteger;
import java.util.Random;

public class Idk {
	/**
	 * I do not advise using it, it's rare that it compresses it, and is slow
	 *
	 * @param toCompress the string to compress
	 * @param amount     amount of smth
	 * @return the "compressed" string
	 */
	public static String compress(String toCompress, int amount) {
		StringBuilder byteArray = new StringBuilder();
		for (char c : toCompress.toCharArray()) {
			byteArray.append("" + ((byte) c));
			byteArray.append("000");
		}
		System.out.println(byteArray);
		BigInteger integer = new BigInteger(byteArray.toString());
		String text = "";
		BigInteger value = new BigInteger("0");
		BigInteger add = new BigInteger("432974356974365893465932475961047320143712409873214973120857439075093427593427509824375024735024375024375094237509243750243570234750243750243750237457243057209347562436243264873246837648712638261984619236498732649832698746328795643294234236497234234324234234132131231231243434923874923648723964982364" + generateCompressionKey(amount));
		BigInteger divide = new BigInteger("64");
		while (true) {
			value = value.add(add);
			if (value.compareTo(integer) < 0) {
				text += ("o");
			} else {
				value = value.subtract(add);
				text += (integer.subtract(value));
				break;
			}
			add = add.add(add.multiply(divide));
		}
		return text;
	}
	
	private static long generateCompressionKey(int length) {
		long number = 0;
		for (int i = 0; i < length; i++) {
			Random rand = new Random(number);
			number += Math.abs(rand.nextInt()) + 10;
		}
		return number;
	}
}
