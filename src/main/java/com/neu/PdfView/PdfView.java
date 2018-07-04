package com.neu.PdfView;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.neu.POJO.User;

public class PdfView extends AbstractPdfView 
{
	 @Override
	 protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request, HttpServletResponse response) throws Exception
	 {
		 
		  Font  helvetica_18_blue = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE);
		  
		  List<User> userList = (List<User>) model.get("userList");
		  
		  Paragraph title = new Paragraph("This is the User List", helvetica_18_blue);
		  
		  
		  PdfPTable table = new PdfPTable(3);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {3.0f, 2.0f, 1.0f});
	        table.setSpacingBefore(10);
	        
	       PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.WHITE);
	        cell.setPadding(5);
	        
	      cell.setPhrase(new Phrase("First Name", helvetica_18_blue));
	        table.addCell(cell);
	        
	       cell.setPhrase(new Phrase("Last Name", helvetica_18_blue));
	        table.addCell(cell);

	      cell.setPhrase(new Phrase("Email", helvetica_18_blue));
	        table.addCell(cell);
	        
	        for (User user : userList)
	        {
	            table.addCell(user.getFirstName());
	            table.addCell(user.getLastName());
	            table.addCell(user.getEmail());
	            
	           
	        }
	        
	        pdfdoc.add(title);
	        pdfdoc.add(table);
	      
		 
		 
	 }

}

