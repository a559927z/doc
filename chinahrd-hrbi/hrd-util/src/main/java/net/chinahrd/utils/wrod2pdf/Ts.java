//package net.chinahrd.utils.wrod2pdf;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
//public class Ts {
//
//	static byte[] toBytes(String imgSrc) throws Exception {
//		FileInputStream fin = new FileInputStream(new File(imgSrc));
//		byte[] bytes = new byte[fin.available()];
//		fin.read(bytes);
//
//		fin.close();
//		return bytes;
//	}
//
//	static void toBuff(byte[] b, String tagSrc) throws Exception {
//		FileOutputStream fout = new FileOutputStream(tagSrc);
//		fout.write(b);
//		fout.close();
//	}
//
//	static InputStream toInStream(byte[] b) throws Exception {
//		return new ByteArrayInputStream(b);
//	}
//
//	public static void main(String[] args) {
//
//		try {
//			String sourceFilePath = "D:\\Log\\1.docx";
////			String targetFilePath = "D:\\Log\\2.docx";
//			byte[] bytes = toBytes(sourceFilePath);
//
////			toBuff(bytes, targetFilePath);
//
//			System.out.println(bytes.length);
//
//			InputStream is = toInStream(bytes);
//			Word2Pdf.go(is);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//}
