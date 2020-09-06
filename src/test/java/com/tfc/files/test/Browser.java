package com.tfc.files.test;

import com.tfc.files.tfile.TFile;
import com.tfc.files.tfile.reader.Window;

import java.io.File;
import java.io.IOException;

public class Browser {
	public static void main(String[] args) throws IOException {
		String file = ("" +
				"_input.txt\n" +
				"hello\n" +
				"how are you?\n" +
				"_output.txt\n" +
				"Hi!\n" +
				"I'm doing fine!\n" +
				"__inner1.txt\n" +
				"inner.file\n" +
				"__inner2.txt\n" +
				"inner.file2\n" +
				"").replace("_", "" + TFile.getIndentChar());
		new Window(new TFile(new File("D:\\Programs\\Minecraft\\modSources\\TFileManipulation\\test.tfile"))).start();
	}
}
