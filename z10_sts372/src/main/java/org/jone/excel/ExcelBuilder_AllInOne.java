package org.jone.excel;
//...과제24-2.N. 엑셀파일 생성뷰를 하나로 만들기.
/*
 * ...참조_자료 : 클래스 인스턴스 정보 얻기(리플렉션 : .class, getClass(), Class.forName())
 *                 http://m.blog.naver.com/javaking75/220725455759
 *                 클래스 멤버 정보 얻기(리플렉션 : Constructor, Method, Field)
 *                 http://m.blog.naver.com/javaking75/220725484940
 *                 동적으로 class , method 실행 시킬 수 있는 reflection 사용법
 *                 http://shonm.tistory.com/489
 *                 How do I read a private field in Java?
 *                 http://stackoverflow.com/questions/1196192/how-do-i-read-a-private-field-in-java
 *                 리플렉션을 사용해서 객체생성하는 법 설명 잘되어 있음.
 *                 http://hiddenviewer.tistory.com/115
 *                 [Java] 리플렉션 > 클래스 멤버의 정보를 얻기 ( Constructor | Method | Field ) : 네이버 블로그
 *                 http://m.blog.naver.com/javaking75/220725484940
 *                 리플렉션 > 클래스 멤버의 정보를 얻기 ( Constructor | Method | Field ) : 네이버 블로그
 *                 http://m.blog.naver.com/javaking75/220725484940
 *                 리플렉션 > 메소드 및 필드를 동적으로 호출하기 (Java 6+）( Method | invoke | Field ) : 네이버 블로그
 *                 http://m.blog.naver.com/javaking75/220727784474
 *                 
 */

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.jone.zdomain.EmpVO;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelBuilder_AllInOne extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model
												, HSSFWorkbook workbook
												, HttpServletRequest request
												, HttpServletResponse response) throws Exception {        

        //...S.과제24-2.N. 엑셀파일 생성뷰를 하나로 만들기.
        // get data model which is passed by the Spring container        
		String title = (String) model.get("title");
		
		switch(title)
		{
			case "listAll_close009":
				List<EmpVO> worksheetList = (List<EmpVO>) model.get("listAll");
				
		        // create a new Excel sheet
		        HSSFSheet sheet = workbook.createSheet(title);
		        sheet.setDefaultColumnWidth(30);
		         
		        // create style for header cells
		        CellStyle style = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setFontName("Arial");
		        style.setFillForegroundColor(HSSFColor.BLUE.index);
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        font.setColor(HSSFColor.WHITE.index);
		        style.setFont(font);
		         
		        // create header row
		        HSSFRow header = sheet.createRow(0);

		        Field[] arrFields = (Field[]) model.get("arrFields");
		        String[] arrStrFields = (String[]) model.get("arrStrFields"); 
		        int nCol = 0;
				for(Field zField:arrFields)
		        {
		            //fld1.setAccessible(true);
		            //System.out.println("field :"+zField.getName());
		            //System.out.println("type :"+zField.getType());
		            header.createCell(nCol).setCellValue(zField.getName());
		            header.getCell(nCol).setCellStyle(style);
		            nCol++;
		        }
				/*
		        header.createCell(0).setCellValue("EMPNO");
		        header.getCell(0).setCellStyle(style);
		         
		        header.createCell(1).setCellValue("ENAME");
		        header.getCell(1).setCellStyle(style);
		        ...... 
		        */
		        
		        // create data rows
		        int rowCount = 1;
		         
		        for (EmpVO rowData : worksheetList) {
		            HSSFRow aRow = sheet.createRow(rowCount++);
		            aRow.createCell(0).setCellValue(rowData.getEMPNO());
		            aRow.createCell(1).setCellValue(rowData.getENAME());
		            aRow.createCell(2).setCellValue(rowData.getJOB());
		        }		        
		        
				break;
				
			case "listAll_close008":
				List<EmpVO> worksheetList_008 = (List<EmpVO>) model.get("listAll");
				break;
				
			default :
				List<EmpVO> worksheetList_default = (List<EmpVO>) model.get("listAll");
				break;
		}
        //...E.과제24-2.N. 엑셀파일 생성뷰를 하나로 만들기.        
        
       
    }

}
