package driveDataFromExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DriveDataExcel {
	
	
	public static void main(String[] args) throws IOException {
		DriveDataExcel data = new DriveDataExcel();
		System.out.println(data.getTestData("Purchase","sheet1"));
	}
	
	//function for getting testdata from excel by providing testCaseName
	public ArrayList<String> getTestData(String testCaseName,String sheetName) throws IOException {
	
		ArrayList<String> testData = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(new File("E:\\pratik30\\Software testing\\API Testing\\driveDataFromExcel.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int sheetCount = workbook.getNumberOfSheets();
		
		for(int i=0;i<sheetCount;i++) {
			if(workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowsIterator=sheet.iterator();
				
				Row firstRow = rowsIterator.next();
				Iterator<Cell> cellIterator = firstRow.iterator();
				int k=0;
				int columnNo=0;
				while(cellIterator.hasNext()) {
					Cell cell =cellIterator.next();
					String cellValue = cell.getStringCellValue();
					if(cellValue.equalsIgnoreCase("TestCases")) {
						columnNo=k;
						break;
					}
					k++;
				}
//				System.out.println(columnNo);
				while(rowsIterator.hasNext()) {
					Row row= rowsIterator.next();
					if(row.getCell(columnNo).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> cellIterator2 = row.iterator();
						while(cellIterator2.hasNext()) {
							Cell cell=cellIterator2.next();
							if(cell.getCellType()==CellType.STRING) {
							testData.add(cell.getStringCellValue());
							}else {
								testData.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
							}
						}
					}
				}
				
				break;
			}
		}
		return testData;
	}

}
