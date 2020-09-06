package com.tfc.files.test;

import com.tfc.files.compression.Numbers;

public class NumberCompressionTest {
	public static void main(String[] args) {
//		String input = generate(1);
		String input = "164167681646592016449536164823041051852810399744103915521039564810379264103505921035468810326016328497329432487632943248327498326473294712739127312532523423423412431241284781294761124671235123621123561246324623864923462312362194372149216498132646325792635212379218794267983265872693569327865923423217421946271896432148721984673975184351256712958172514871294791827321343724973294873294672394726666667";
		String output = Numbers.compress(input,true,true);
		System.out.println("Source:" + input);
		System.out.println("Decompressed:" + Numbers.decompress(output,true,true));
		System.out.println("Compressed:" + output);
		System.out.println("Saved bytes:" + (input.length() - output.length()));
	}
	
	public static String generate(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			for (int i2 = 0; i2 <= 9; i2++) {
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
