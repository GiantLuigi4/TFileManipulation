package com.tfc.files.compression;

/**
 * A basic helper class for compression numbers
 */
public class Numbers {
	//this makes it so that the result is larger than the initial value
	private static final int amp = 128;
	
	/**
	 * helper method for amplifying a value so that it's larger than it should be
	 * without this the two numbers always multiply to be smaller than the two numbers being multiplied
	 *
	 * @param i  number1
	 * @param i1 number2
	 * @return amplified result of multiplying those two values together
	 */
	private static long amp(long i, long i1) {
		return (((i * amp) + (i1 * amp)) * amp);
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
		while (replaced) {
			String oldStr = "";
			String newStr = "";
			replaced = false;
			for (long i = 2; i < 2560; i++) {
				for (long i1 = 2; i1 < 2560; i1++) {
					String oldS = ("" + amp(i, i1));
					String newS = (((char) 1) + "" + i + "*" + i1 + "" + ((char) 1));
					if (out.contains(oldS)) {
						if (oldS.length() > newS.length()) {
							oldStr = oldS;
							newStr = newS;
							replaced = true;
						}
					}
				}
			}
			if (!oldStr.equals("") && !newStr.equals("")) {
				out = out.replace(oldStr, newStr);
			}
		}
		return out;
	}
	
	/**
	 * decompress a string that has been compressed using the Number compression
	 *
	 * @param toDecompress the string to decompress
	 * @return the decompressed string
	 */
	public static String decompress(String toDecompress) {
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
}
