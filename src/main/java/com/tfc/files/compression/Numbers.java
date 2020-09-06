package com.tfc.files.compression;

/**
 * A basic helper class for compression numbers
 */
public class Numbers {
	//this makes it so that the result is larger than the initial value
	private static final int ampM = 128;
	private static final int ampD = 64;
	
	private static int iterationCount = 2560;
	
	public static void setIterationCount(int amount) {
		iterationCount = amount;
	}
	
	/**
	 * helper method for amplifying a value so that it's larger than it should be
	 * without this the two numbers always multiply to be smaller than the two numbers being multiplied
	 *
	 * @param i  number1
	 * @param i1 number2
	 * @return amplified result of multiplying those two values together
	 */
	private static long amp(long i, long i1) {
		return (((i * ampM) + (i1 * ampM)) * ampM);
	}
	
	/**
	 * compress a string that contains numbers using multiplication
	 * this is a very ineffective method, but it's better than nothing
	 *
	 * @param toCompress the string to compress
	 * @return the compressed string
	 */
	public static String compressMultiplication(String toCompress) {
		String out = toCompress;
		boolean replaced = true;
		while (true) {
			String oldStr = "23432649327492";
			String newStr = "2347323247324";
			replaced = false;
			for (long i = 2; i < iterationCount; i++) {
				for (long i1 = 2; i1 < iterationCount; i1++) {
					String oldS = ("" + amp(i, i1));
					String newS = (((char) 1) + "" + i + "*" + i1 + "" + ((char) 1));
					if (oldS.length() > newS.length() && (oldStr + newStr).length() > (oldS + newS).length() && out.contains(oldS)) {
						oldStr = oldS;
						newStr = newS;
						replaced = true;
					}
				}
				if (replaced) break;
			}
			if (!replaced) {
				break;
			} else if (!oldStr.equals("23432649327492") && !newStr.equals("2347323247324")) {
				out = out.replace(oldStr, newStr);
			}
		}
		return out;
	}
	
	/**
	 * compress a string that contains numbers using division
	 * this is a very ineffective method, but it's better than nothing
	 *
	 * @param toCompress the string to compress
	 * @return the compressed string
	 */
	public static String compressDivision(String toCompress) {
		String out = toCompress;
		boolean replaced = true;
		while (true) {
			String oldStr = "23432649327492";
			String newStr = "2347323247324";
			replaced = false;
			for (long i = 1; i < iterationCount; i++) {
				for (long i1 = 3; i1 < iterationCount; i1++) {
					String oldS = ("" + ((float) i / (float) (i1 * ampD)));
					oldS = oldS.replace(".", "");
					while (oldS.startsWith("0")) oldS = oldS.substring(1);
					String newS = (((char) 2) + "" + i + "/" + i1 + "" + ((char) 2));
					if (oldS.length() > newS.length() && (oldStr + newStr).length() > (oldS + newS).length() && out.contains(oldS)) {
						oldStr = oldS;
						newStr = newS;
						replaced = true;
					}
				}
			}
			if (!replaced) {
				break;
			} else {
				out = out.replace(oldStr, newStr);
			}
		}
		return out;
	}
	
	/**
	 * decompress a string that has been compressed using the Multiplication compression
	 *
	 * @param toDecompress the string to decompress
	 * @return the decompressed string
	 */
	public static String decompressMultiplication(String toDecompress) {
		while (toDecompress.contains("" + ((char) 1))) {
			String parse = toDecompress.substring(toDecompress.indexOf((char) 1) + 1);
			parse = parse.substring(0, parse.indexOf((char) 1));
			String num1 = parse.substring(0, parse.indexOf("*"));
			String num2 = parse.substring(parse.indexOf("*") + 1);
			long val1 = Long.parseLong(num1);
			long val2 = Long.parseLong(num2);
			long result = amp(val1, val2);
			toDecompress = toDecompress.replace(("" + ((char) 1)) + parse + ("" + ((char) 1)), "" + result);
		}
		return toDecompress;
	}
	
	/**
	 * decompress a string that has been compressed using the Division compression
	 *
	 * @param toDecompress the string to decompress
	 * @return the decompressed string
	 */
	public static String decompressDivision(String toDecompress) {
		while (toDecompress.contains("" + ((char) 2))) {
			String parse = toDecompress.substring(toDecompress.indexOf((char) 2) + 1);
			parse = parse.substring(0, parse.indexOf((char) 2));
			String num1 = parse.substring(0, parse.indexOf("/"));
			String num2 = parse.substring(parse.indexOf("/") + 1);
			int val1 = Integer.parseInt(num1);
			int val2 = Integer.parseInt(num2);
			float result = ((float) val1 / (float) val2);
			String res = "" + result;
			res = res.replace(".", "");
			toDecompress = toDecompress.replace(("" + ((char) 2)) + parse + ("" + ((char) 2)), res);
		}
		return toDecompress;
	}
	
	//Note to self: this should be oldest to newest
	
	/**
	 * compress a string using optional number compression methods
	 * if you don't want to use number compression, don't call this method at all, lol
	 *
	 * @param toCompress        the string to compress
	 * @param useMultiplication whether or not it should use multiplication
	 * @param useDivision       whether or not it should use division
	 * @return the compressed string
	 */
	public static String compress(String toCompress, boolean useMultiplication, boolean useDivision) {
		if (useMultiplication) toCompress = compressMultiplication(toCompress);
		if (useDivision) toCompress = compressDivision(toCompress);
		return toCompress;
	}
	
	//Note to self: this should be newest to oldest
	
	/**
	 * decompress a string, make sure you use the same series of true false as you used for compressing
	 * if the string wasn't compressed with {@link Number} don't call this
	 *
	 * @param toDecompress      the string to decompress
	 * @param useMultiplication whether or not it was compressed with multiplication
	 * @param useDivision       whether or not it was compressed with division
	 * @return the decompressed string
	 */
	public static String decompress(String toDecompress, boolean useMultiplication, boolean useDivision) {
		if (useDivision) toDecompress = decompressDivision(toDecompress);
		if (useMultiplication) toDecompress = decompressMultiplication(toDecompress);
		return toDecompress;
	}
	
//	/**
//	 * idk, probably lossy
//	 *
//	 * @param toCompress the string to compress
//	 * @return the compressed string
//	 */
//	public static String lumpCompress(String toCompress) {
//		int index = toCompress.length();
//		for (int i = 1; i <= 9; i++) {
//			int val = toCompress.indexOf(""+i);
//			if (val != -1) index = Math.min(index, val);
//		}
//		while (index != -1) {
//			int lastIndex = index;
//			int point = 0;
//			long number = 0;
//			boolean isNum = true;
//			if (toCompress.charAt(index+1) == (char)5) {
//				break;
//			}
//			while (isNum && point <= 4) {
//				isNum = false;
//				if (index + point >= toCompress.length()) break;
//				char c1 = toCompress.charAt(index + point);
//				for (int i = 0; i <= 9; i++) {
//					if (("" + c1).equals("" + i)) {
//						number *= 10;
//						number += i;
//					}
//				}
//				point++;
//				if (index + point >= toCompress.length()) break;
//				char c = toCompress.charAt(index + point);
//				isNum = c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
//			}
//			if (number > 99) {
//				toCompress = toCompress.replace("" + number, (""+(char)5)+(""+((char)number))+(""+(char)5));
//				index += 3;
//			} else {
//				index += ("" + number).length();
//			}
//			index = toCompress.length();
//			String read = toCompress.substring(lastIndex);
//			for (int i = 1; i <= 9; i++) {
//				int val = read.indexOf(""+i)+lastIndex;
//				if (val != -1) index = Math.min(index, val);
//			}
//			if (index == toCompress.length()) {
//				break;
//			}
//		}
//		return toCompress;
//	}
//
//	public static String lumpDecompress(String text) {
//		String char5 = (""+((char)5));
//		String threeChar5 = char5+char5+char5;
//		text = text.replace(threeChar5,"5");
//		while (text.contains(char5)) {
//			String parse = text.substring(text.indexOf(char5)+1);
//			int index = 0;
//			int val = 0;
//			StringBuilder textReplace = new StringBuilder(char5);
//			while (!(""+parse.charAt(index)).equals(char5)) {
//				val += parse.charAt(index);
//				textReplace.append(parse.charAt(index));
//				index+=1;
//			}
//			textReplace.append(char5);
//			text = text.replace(textReplace.toString(),""+val);
////			System.out.println(text);
//		}
//		return text;
//	}
}
