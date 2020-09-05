package com.tfc.files.tfile;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A helper class for writng TFiles to disk
 */
public class Writer {
	/**
	 * write a TFile to disk
	 * @param out the file to write
	 * @param dir the file to write it to
	 * @throws IOException if something goes wrong while writing it, IE, the  file doesn't exist
	 */
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
