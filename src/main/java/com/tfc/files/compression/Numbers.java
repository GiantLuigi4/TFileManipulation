package com.tfc.files.compression;

/**
 * A basic helper class for compression numbers
 */
public class Numbers {
	//this makes it so that the result is larger than the initial value
	private static final int amp = 128;
	
	/**
	 * compress a string that contains numbers using multiplication
	 * this is a very ineffective method, but it's better than nothing
	 * @param toCompress the string to compress
	 * @return the compressed string
	 */
	public static String compressMultiplication(String toCompress) {
		String out = toCompress;
		boolean replaced = true;
		while (replaced) {
//			int[] largest = new int[2];
			String oldStr = "";
			String newStr = "";
			replaced = false;
			for (long i = 2; i < 2560; i++) {
				for (long i1 = 2; i1 < 2560; i1++) {
					String oldS = ("" + (((i * amp) + (i1 * amp)) * amp));
					String newS = (((char) 1) + "" + i + "*" + i1 + "" + ((char) 1));
//					if (oldS.length() > newS.length()) {
//						System.out.println(oldS);
//					}
					if (out.contains(oldS)) {
//						System.out.println(oldS);
//						System.out.println(newS);
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
	 * @param toDecompress the string to decompress
	 * @return the decompressed string
	 */
	public static String decompress(String toDecompress) {
		while (toDecompress.contains(""+((char)1))) {
			String parse = toDecompress.substring(toDecompress.indexOf((char)1)+1);
			parse = parse.substring(0,parse.indexOf((char)1));
			String num1 = parse.substring(0,parse.indexOf("*"));
			String num2 = parse.substring(parse.indexOf("*")+1);
			System.out.println(num2);
			long val1 = Long.parseLong(num1);
			long val2 = Long.parseLong(num2);
			long result = ((val1*amp)*(val2*amp))*amp;
			toDecompress = toDecompress.replace((""+((char)1))+parse+(""+((char)1)),""+result);
		}
		return toDecompress;
	}
}
