package com.tfc.files.test;

import com.tfc.files.tfile.TFile;
import com.tfc.files.tfile.Writer;

import java.io.File;

public class Test {
	private static final String dir = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		TFile file = new TFile(("" +
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
				"").replace("_",""+TFile.getIndentChar())
		);
		System.out.println(file.toString());
		write(file,"Source");
		file.addFile("hello1.txt","" +
				"this is a test file\n" +
				"file created from in java" +
				"");
		write(file,"Modified");
	}
	
	private static void write(TFile file,String postFix) {
		try {
			String dirWrite = dir+"\\test"+postFix+".tfile";
			File f = new File(dirWrite);
			if (!f.exists()) f.createNewFile();
			Writer.write(file,dirWrite);
		} catch (Throwable ignored) {}
	}
}
