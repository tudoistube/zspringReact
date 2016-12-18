package org.jone.excel;
//...과제25_1.B. SQL SELECT문의 조회컬럼과 조회데이터를 엑셀파일로 변환함.

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
import org.jone.zdomain.Close009VO;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelBuilder_Close009 extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        // get data model which is passed by the Spring container
        List<Close009VO> worksheetList = (List<Close009VO>) model.get("listAll");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("listAll_close009");
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
         
        header.createCell(0).setCellValue("소속");
        header.getCell(0).setCellStyle(style);
        
        header.createCell(1).setCellValue("사번");
        header.getCell(1).setCellStyle(style);        

        header.createCell(2).setCellValue("교사");
        header.getCell(2).setCellStyle(style);
        
        header.createCell(3).setCellValue("직책");
        header.getCell(3).setCellStyle(style);        

        header.createCell(4).setCellValue("교실");
        header.getCell(4).setCellStyle(style);             

        header.createCell(5).setCellValue("방문코드");
        header.getCell(5).setCellStyle(style);             

        header.createCell(6).setCellValue("요일");
        header.getCell(6).setCellStyle(style);   
        
        header.createCell(7).setCellValue("한자");
        header.getCell(7).setCellStyle(style);   
        
        header.createCell(8).setCellValue("중국어");
        header.getCell(8).setCellStyle(style);   
        
        header.createCell(9).setCellValue("책아이");
        header.getCell(9).setCellStyle(style);   
        
        header.createCell(10).setCellValue("신_책아이");
        header.getCell(10).setCellStyle(style);   
        
        header.createCell(11).setCellValue("맞춤");
        header.getCell(11).setCellStyle(style);   
        
        header.createCell(12).setCellValue("교과");
        header.getCell(12).setCellStyle(style);   
        
        header.createCell(13).setCellValue("국어");
        header.getCell(13).setCellStyle(style);   
        
        header.createCell(14).setCellValue("영어");
        header.getCell(14).setCellStyle(style);   
        
        header.createCell(15).setCellValue("북천지");
        header.getCell(15).setCellStyle(style);   
        
        header.createCell(16).setCellValue("일본어");
        header.getCell(16).setCellStyle(style);   
        
        header.createCell(17).setCellValue("장원급제 ");
        header.getCell(17).setCellStyle(style);   
        
        header.createCell(18).setCellValue("세이펜영어");
        header.getCell(18).setCellStyle(style);   
        
        header.createCell(19).setCellValue("장원한국사");
        header.getCell(19).setCellStyle(style);   
        
        header.createCell(20).setCellValue("화상영어");
        header.getCell(20).setCellStyle(style);   
        
        header.createCell(21).setCellValue("구좌수");
        header.getCell(21).setCellStyle(style);   
        
        header.createCell(22).setCellValue("회원수");
        header.getCell(22).setCellStyle(style);   
        
        header.createCell(23).setCellValue("가구수");
        header.getCell(23).setCellStyle(style);  
       
               
        // create data rows
        int rowCount = 1;
         
        for (Close009VO rowData : worksheetList) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(rowData.getBE_NM());            
            aRow.createCell(1).setCellValue(rowData.getCUR_TEACHER());            
            aRow.createCell(2).setCellValue(rowData.getTEACHER_NM());            
            aRow.createCell(3).setCellValue(rowData.getPOSI_CD_TEXT());            
            aRow.createCell(4).setCellValue(rowData.getCLASS_NM());            
            aRow.createCell(5).setCellValue(rowData.getVISIT_CD());
            aRow.createCell(6).setCellValue(rowData.getVISIT_CD_NM());
            
            aRow.createCell(7).setCellValue(rowData.getCD_TP_LEVEL_H()); //...7...한자
            aRow.createCell(8).setCellValue(rowData.getCD_TP_LEVEL_CH()); //...8...중국어
            aRow.createCell(9).setCellValue(rowData.getCD_TP_LEVEL_B()); //...9...책아이
            aRow.createCell(10).setCellValue(rowData.getCD_TP_LEVEL_BN()); //...10...신_책아이
            aRow.createCell(11).setCellValue(rowData.getCD_TP_LEVEL_M()); //...11...맞춤
            aRow.createCell(12).setCellValue(rowData.getCD_TP_LEVEL_MI()); //...12...교과
            aRow.createCell(13).setCellValue(rowData.getCD_TP_LEVEL_K()); //...13...국어
            aRow.createCell(14).setCellValue(rowData.getCD_TP_LEVEL_E()); //...14...영어
            aRow.createCell(15).setCellValue(rowData.getCD_TP_LEVEL_BI()); //...15...북천지
            aRow.createCell(16).setCellValue(rowData.getCD_TP_LEVEL_JP()); //...16...일본어
            aRow.createCell(17).setCellValue(rowData.getCD_TP_LEVEL_HG()); //...17...장원급제 
            aRow.createCell(18).setCellValue(rowData.getCD_TP_LEVEL_EP()); //...18...세이펜영어
            aRow.createCell(19).setCellValue(rowData.getCD_TP_LEVEL_NH()); //...19...장원한국사
            aRow.createCell(20).setCellValue(rowData.getCD_TP_LEVEL_EL()); //...20...화상영어
            
            aRow.createCell(21).setCellValue(rowData.getITEM_CD()); //...21...구좌수
            aRow.createCell(22).setCellValue(rowData.getMEMBER_ID()); //...22...회원수
            aRow.createCell(23).setCellValue(rowData.getHS_NUM()); //...23...가구수            
            
        }
        
    }

}




















