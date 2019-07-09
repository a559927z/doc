//package net.chinahrd.utils.wrod2pdf;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.List;
//
//import org.docx4j.Docx4J;
//import org.docx4j.fonts.IdentityPlusMapper;
//import org.docx4j.fonts.Mapper;
//import org.docx4j.model.structure.SectionWrapper;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//
///**
// * This converts Docx file to PDF Author: Naveen - Javadomain.in
// */
//public class WordToPDFConvert {
//	public static void main(String[] args) {
//		String inputWordPath = "d:\\Log\\1.docx";
//		String outputPDFPath = "d:\\Log\\1.pdf";
//		try {
//			System.err.println("Word Document to PDF Convert Begins!");
//			InputStream is = new FileInputStream(new File(inputWordPath));
//			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
//			List sections = wordMLPackage.getDocumentModel().getSections();
//			for (int i = 0; i < sections.size(); i++) {
//				wordMLPackage.getDocumentModel().getSections().get(i).getPageDimensions().setHeaderExtent(3000);
//			}
//			Mapper fontMapper = new IdentityPlusMapper();
//			// For font specific, enable the below lines
//			// PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Comic
//			// Sans MS");
//			// fontMapper.getFontMappings().put("Algerian", font);
//			wordMLPackage.setFontMapper(fontMapper);
//			Docx4J.toPDF(wordMLPackage, new FileOutputStream(outputPDFPath));
//			System.err.println("Your Word Document Converted to PDF Successfully!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}