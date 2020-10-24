package com.tfc.files.tfile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class ImageCompressionMethod {
	public static void main(String[] args) throws IOException {
		System.out.println(Arrays.toString(ImageIO.getWriterFormatNames()));
		File input = new File("test.txt");
		File input2 = new File("test2.png");
		File output = new File("test.png");
		File output2 = new File("decompressed.txt");
		File output3 = new File("decompressed2.txt");
		FileInputStream stream = new FileInputStream(input);
		FileInputStream stream2 = new FileInputStream(input2);
		FileOutputStream out = new FileOutputStream(output);
		FileOutputStream out2 = new FileOutputStream(output2);
		FileOutputStream out3 = new FileOutputStream(output3);
		byte[] bytes = new byte[stream.available()];
		byte[] bytes1 = new byte[stream2.available()];
		stream.read(bytes);
		stream2.read(bytes1);
		stream.close();
		stream2.close();
		byte[] compressed = compressRGB(bytes);
		out.write(compressed);
		out.close();
		out2.write(decompressRGB(compressed));
		out2.close();
		out3.write(decompressRGB(bytes1));
		out3.close();
	}
	
	public static byte[] decompressRGB(byte[] compressed) throws IOException {
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(compressed));
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			for (int x = 0; x < image.getHeight(); x++) {
				for (int y = 0; y < image.getWidth(); y++) {
					Color c = new Color(image.getRGB(y, x), true);
					byte[] bytes = new byte[3];
					if (c.getAlpha() >= 25) bytes[0] = (byte) (c.getRed() - 128);
					if (c.getAlpha() >= 80) bytes[1] = (byte) (c.getGreen() - 128);
					if (c.getAlpha() >= 160) bytes[2] = (byte) (c.getBlue() - 128);
					if (c.getAlpha() == 0) break;
					stream.write(bytes);
				}
			}
		} catch (Throwable ignored) {
		}
		byte[] out = stream.toByteArray();
		stream.close();
		return out;
	}
	
	public static byte[] decompressRGBA(byte[] compressed) throws IOException {
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(compressed));
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			for (int x = 0; x < image.getHeight(); x++) {
				for (int y = 0; y < image.getWidth(); y++) {
					Color c = new Color(image.getRGB(y, x), true);
					byte[] bytes = new byte[4];
					bytes[0] = (byte) (c.getRed() - 128);
					bytes[1] = (byte) (c.getGreen() - 128);
					bytes[2] = (byte) (c.getBlue() - 128);
					bytes[3] = (byte) (c.getAlpha() - 128);
					System.out.print((char)bytes[0]);
					System.out.print((char)bytes[1]);
					System.out.print((char)bytes[2]);
					System.out.print((char)bytes[3]);
					stream.write(bytes);
					if (c.getAlpha() == 0) break;
				}
			}
		} catch (Throwable ignored) {
		}
		System.out.println();
		byte[] out = stream.toByteArray();
		stream.close();
		return out;
	}
	
	public static byte[] compressRGB(String text) throws IOException {
		return compressRGB(text.getBytes());
	}
	
	public static byte[] compressRGBA(String text) throws IOException {
		return compressRGBA(text.getBytes());
	}
	
	public static byte[] compressRGB(byte[] bytes) throws IOException {
		BufferedImage image = new BufferedImage(
				(int) Math.ceil(Math.sqrt(bytes.length) / 3f),
				(int) Math.floor(Math.sqrt(bytes.length) / 1f),
				BufferedImage.TYPE_INT_ARGB
		);
		int x = 0;
		int y = 0;
		Graphics g = image.getGraphics();
		try {
			for (int i = 0; i < bytes.length; i += 3) {
				int b1 = bytes[i] + 128;
				int b2 = 0;
				int b3 = 0;
				int a = 63;
				if (i + 1 < bytes.length) {
					b2 = bytes[i + 1] + 128;
					a = 128;
				}
				if (i + 2 < bytes.length) {
					b3 = bytes[i + 2] + 128;
					a = 255;
				}
				g.setColor(new Color(b1, b2, b3, a));
				g.fillRect(x, y, 1, 1);
				x++;
				if (x >= image.getWidth()) {
					x = 0;
					y++;
				}
			}
		} catch (Throwable ignored) {
			System.out.println(x + "," + y);
			System.out.println(image.getWidth() + "," + image.getHeight());
			ignored.printStackTrace();
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		byte[] out = outputStream.toByteArray();
		outputStream.close();
		return out;
	}
	
	public static byte[] compressRGBA(byte[] bytes) throws IOException {
		BufferedImage image = new BufferedImage(
				(int) Math.ceil(Math.sqrt(bytes.length) / 3),
				(int) Math.floor(Math.sqrt(bytes.length) / 1f)-3,
				BufferedImage.TYPE_INT_ARGB
		);
		int x = 0;
		int y = 0;
		Graphics g = image.getGraphics();
		try {
			for (int i = 0; i < bytes.length; i += 4) {
				int b1 = bytes[i] + 128;
				int b2 = 0;
				int b3 = 0;
				int a = 0;
				if (i + 1 < bytes.length) b2 = bytes[i + 1] + 128;
				if (i + 2 < bytes.length) b3 = bytes[i + 2] + 128;
				if (i + 3 < bytes.length) a = bytes[i + 3] + 128;
				g.setColor(new Color(b1, b2, b3, a));
				g.fillRect(x, y, 1, 1);
				x++;
				if (x >= image.getWidth()) {
					x = 0;
					y++;
				}
			}
		} catch (Throwable ignored) {
			System.out.println(x + "," + y);
			System.out.println(image.getWidth() + "," + image.getHeight());
			ignored.printStackTrace();
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		byte[] out = outputStream.toByteArray();
		outputStream.close();
		return out;
	}
}
