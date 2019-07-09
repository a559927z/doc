//package net.chinahrd.utils.wrod2pdf;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import org.apache.log4j.Level;
//import org.docx4j.Docx4jProperties;
//import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
//import org.docx4j.fonts.IdentityPlusMapper;
//import org.docx4j.fonts.Mapper;
//import org.docx4j.fonts.PhysicalFont;
//import org.docx4j.fonts.PhysicalFonts;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.utils.Log4jConfigurator;
//
//public class Word2Pdf {
//
//	public static void go(InputStream pis) {
//		try {
//
//			long start = System.currentTimeMillis();
//
//			InputStream is = pis;
//			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
//			List sections = wordMLPackage.getDocumentModel().getSections();
//			for (int i = 0; i < sections.size(); i++) {
//
//				System.out.println("sections Size" + sections.size());
//				wordMLPackage.getDocumentModel().getSections().get(i).getPageDimensions().setHeaderExtent(3000);
//			}
//			Mapper fontMapper = new IdentityPlusMapper();
//
//			PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Comic Sans MS");
//
//			fontMapper.getFontMappings().put("Algerian", font);
//
//			wordMLPackage.setFontMapper(fontMapper);
//			PdfSettings pdfSettings = new PdfSettings();
//			org.docx4j.convert.out.pdf.PdfConversion conversion = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
//					wordMLPackage);
//
//			Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "true");
//			Log4jConfigurator.configure();
//			org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
//
//			OutputStream out = new FileOutputStream(new File("D:\\\\Log\\1.pdf"));
//			conversion.output(out, pdfSettings);
//			System.err.println("Time taken to Generate pdf  " + (System.currentTimeMillis() - start) + "ms");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static void main(String[] args) {
//		try {
//
//			long start = System.currentTimeMillis();
//
//			InputStream is = new FileInputStream(new File("D:\\Log\\1.docx"));
//			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
//			List sections = wordMLPackage.getDocumentModel().getSections();
//			for (int i = 0; i < sections.size(); i++) {
//
//				System.out.println("sections Size" + sections.size());
//				wordMLPackage.getDocumentModel().getSections().get(i).getPageDimensions().setHeaderExtent(3000);
//			}
//			Mapper fontMapper = new IdentityPlusMapper();
//
//			PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Comic Sans MS");
//
//			fontMapper.getFontMappings().put("Algerian", font);
//
//			wordMLPackage.setFontMapper(fontMapper);
//			PdfSettings pdfSettings = new PdfSettings();
//			org.docx4j.convert.out.pdf.PdfConversion conversion = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
//					wordMLPackage);
//
//			Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "true");
//			Log4jConfigurator.configure();
//			org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
//
//			OutputStream out = new FileOutputStream(new File("D:\\\\Log\\1.pdf"));
//			conversion.output(out, pdfSettings);
//			System.err.println("Time taken to Generate pdf  " + (System.currentTimeMillis() - start) + "ms");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}