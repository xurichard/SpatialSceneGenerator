import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelWriter {
	
	
	public static void main(String[] args){
//		ExcelWriter temp = new ExcelWriter();
//		Object[][] tempItems = new Object[2][2];
//		tempItems[0][0] = 1;
//		tempItems[0][1] = "hi";
//		tempItems[1][0] = 3;
//		tempItems[1][1] = "hi";
//		temp.tableWriter(tempItems, "tempFileOutput.xls");
	}
	
	public void tableWriter(Object[][] items, String fileName){
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("testSheet");
		
		int cols = items.length;
		int rows = items[0].length;
		
		HSSFRow[] myRows = new HSSFRow[cols];
		
		for(int i = 0; i < cols; i++){
			myRows[i] = worksheet.createRow(i);
		}
		for(int i = 0; i < cols; i++){
			for(int j = 0; j < rows; j++){
				if(items[i][j] == null){
					//do Nothing
				} else if(items[i][j] instanceof Integer){
					HSSFCell temp = myRows[i].createCell(j, 0); // numeric
					int tempInt = (int) items[i][j];
					temp.setCellValue((double) tempInt);
				} else if(items[i][j] instanceof String){
					HSSFCell temp = myRows[i].createCell(j, 1); // String
					temp.setCellValue((String)items[i][j]);
				}
			}
		}

		try {
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void tableIntWriter(int[][] items, String fileName){
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("testSheet");
		
		int cols = items.length;
		int rows = items[0].length;
		
//		for(int i = 0; i < cols; i ++){
//			for(int j = 0; j < rows; j++){
//				System.out.println(items[i][j]);
//			}
//		}
//		
		HSSFRow[] myRows = new HSSFRow[cols];
		
		for(int i = 0; i < cols; i++){
			myRows[i] = worksheet.createRow(i);
		}
		//System.out.println(rows + " " + cols);
		//System.out.println(items.length + " " + items[0].length);
		
		for(int i = 0; i < cols; i++){
			for(int j = 0; j < rows; j++){
				HSSFCell temp = myRows[i].createCell(j, 0);
				int tempInt = items[i][j];
				//System.out.println(items[i][j]);
				temp.setCellValue((double) tempInt);
			}
		}

		try {
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
