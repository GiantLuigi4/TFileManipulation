package com.tfc.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {
	public static void write(TFile out, String dir) throws IOException {
		String fileString = out.toString();
		FileOutputStream stream = new FileOutputStream(dir);
		char[] chars = fileString.toCharArray();
		byte[] bytes = new byte[fileString.length()];
		for (int i=0;i<chars.length;i++) bytes[i] = (byte)chars[i];
		stream.write(bytes);
		stream.close();
	}
}
