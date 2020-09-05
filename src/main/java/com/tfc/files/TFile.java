package com.tfc.files;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;

public class TFile {
	private TFile inner = null;
	private final HashMap<String, String> otherFileHashMap = new HashMap<>();
	
	private static final char indentChar = (char)1;
	
	/**
	 * the character that represents an "indent"
	 * single indent = file name
	 * double indent = inner file
	 * @return the character that represents an indent
	 */
	public static char getIndentChar() {
		return indentChar;
	}
	
	/**
	 * create a blank TFile
	 */
	public TFile() {
	}
	
	/**
	 * read a TFile from a string
	 * @param content the string to read from
	 */
	public TFile(@NotNull String content) {
		ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
		String name = "";
		StringBuilder file = new StringBuilder();
		int thisIndent = -1;
		boolean isTFile = false;
		StringBuilder innerFiles = new StringBuilder();
		for (String s : lines) {
			int indent = 0;
			for (char c : s.toCharArray()) {
				if (c == indentChar) {
					indent++;
				} else {
					break;
				}
			}
			if (thisIndent == -1) {
				thisIndent = indent;
			}
			if (isTFile) {
				if (indent == 0) {
					innerFiles.append(s).append("\n");
				} else if (indent < thisIndent) {
					isTFile = false;
				} else {
					innerFiles.append(s).append("\n");
				}
			}
			if (!isTFile) {
				if (thisIndent == indent) {
					if (!name.equals("")) otherFileHashMap.put(name, file.toString());
					file = new StringBuilder();
					name = s.substring(thisIndent);
				} else if (indent > thisIndent) {
					isTFile = true;
					if (innerFiles.toString().equals("")) {
						innerFiles.append(s).append("\n");
					}
				} else {
					if (indent == 0) {
						file.append(s).append("\n");
					} else {
						if (!name.equals("")) otherFileHashMap.put(name, file.toString());
						name = "";
						file = new StringBuilder();
					}
				}
			}
		}
		if (!innerFiles.toString().equals("")) inner = (new TFile(innerFiles.toString()));
		if (!name.equals("")) otherFileHashMap.put(name, file.toString());
	}
	
	/**
	 * Reads and instances a TFile from a file
	 * @param file the file to read
	 * @throws IOException if the file can't be read or doesn't exist
	 */
	public TFile(File file) throws IOException {
		this(readFile(file));
	}
	
	/**
	 * helper method for file constructor
	 * @param file the file to read
	 * @return a string of the contents of the file
	 * @throws IOException if the file can't be read or doesn't exist
	 */
	private static String readFile(File file) throws IOException {
		FileInputStream reader = new FileInputStream(file);
		byte[] bytes = new byte[reader.available()];
		reader.read(bytes);
		StringBuilder builder = new StringBuilder();
		for (byte b:bytes) builder.append((char)b);
		return builder.toString();
	}
	
	/**
	 * this is for saving
	 * @return the string of the tfile
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		otherFileHashMap.forEach((name, content) -> {
			builder.append(indentChar).append(name).append("\n");
			for (String s : content.split("\n")) {
				builder.append(s).append("\n");
			}
		});
		if (inner != null) {
			for (String s : inner.toString().split("\n")) {
				if (s.startsWith(""+indentChar)) {
					builder.append(indentChar);
				}
				builder.append(s).append("\n");
			}
		}
		return builder.toString();
	}
	
	/**
	 * @return the inner tfile of this file
	 */
	public TFile getInner() {
		return inner;
	}
	
	/**
	 * get the contents of a file
	 * @param name the name of the file
	 * @return the text of the file
	 */
	public String get(String name) {
		return otherFileHashMap.get(name);
	}
	
	/**
	 * get the contents of a file
	 * @param name the name of the file
	 * @return the text of the file, or if it doesn't exist, return the default
	 */
	public String getOrDefault(String name, String defaultVal) {
		return otherFileHashMap.getOrDefault(name, defaultVal);
	}
	
	/**
	 * get the contents of a file
	 * @param name the name of the file
	 * @return the text of the file, or if it doesn't exist, return the default
	 */
	public String getIfPresent(String name) {
		return otherFileHashMap.getOrDefault(name, "");
	}
	
	/**
	 * add a new file
	 * @param name the name of the file
	 * @param text the contents of the file
	 */
	public void addFile(String name, String text) {
		this.otherFileHashMap.put(name,text);
	}
	
	/**
	 * overwrite a file
	 * @param name the name of the file
	 * @param text the new contents of the file
	 */
	public void replaceFile(String name, String text) {
		this.otherFileHashMap.replace(name,text);
	}
	
	/**
	 * add or overwrite a file
	 * @param name the name of the file
	 * @param text the new contents of the file
	 */
	public void addOrReplaceFile(String name, String text) {
		if (!otherFileHashMap.containsKey(name)) otherFileHashMap.put(name,text);
		else this.otherFileHashMap.replace(name,text);
	}
	
	/**
	 * for if you want to make a class loader that uses tfiles for some reason, here ya go
	 * (I do not know if this will actually work for class loaders)
	 * @param name the name of the file
	 * @return an input stream of the contents of the file
	 */
	public InputStream getAsStream(String name) {
		char[] chars = otherFileHashMap.get(name).toCharArray();
		byte[] bytes = new byte[chars.length];
		for (int i=0;i<chars.length;i++) bytes[i] = (byte)chars[i];
		return new ByteArrayInputStream(bytes);
	}
	
	/**
	 * Creates a new TFile inside this file
	 */
	public TFile createInnerTFile() {
		return this.inner = new TFile();
	}
	
	/**
	 * @return a set of all names in this file
	 */
	public Set<String> listAllNames() {
		return otherFileHashMap.keySet();
	}
	
	/**
	 * run a consumer for all files
	 * @param forEach the function to call on all files
	 */
	public void forEach(BiConsumer<String, String> forEach) {
		otherFileHashMap.forEach(forEach);
	}
}
