package com.tfc.files.test;

import com.tfc.files.compression.Numbers;

public class NumberCompressionTest {
	public static void main(String[] args) {
//		String input = generate(1);
		String input = "164167681646592016449536164823041051852810399744103915521039564810379264103505921035468810326016";
		String output = Numbers.compressMultiplication(input);
		System.out.println(input);
		System.out.println(output);
		System.out.println(input.length()-output.length());
		System.out.println(Numbers.decompress(output));
	}
	
	public static String generate(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i=0;i<length;i++) {
			for (int i2 = 0; i2<=9; i2 ++) {
				for (int i1 = i2; i1 <= 9; i1++) {
					builder.append(i1);
				}
				for (int i1 = 9; i1 >= i2; i1--) {
					builder.append(i1);
				}
			}
		}
		return builder.toString();
	}
}
