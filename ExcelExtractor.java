import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelExtractor{
	
	//http://poi.apache.org/apidocs/index.html
	
	public Object[][] myItems;
	public int rowSize;
	public int colSize;
	
	public static void main(String[] args){
		//ExcelExtractor f = new ExcelExtractor();
		//f.tableGetter("sample.xls");
		//f.printItems();
	}
	
	public Object[][] tableGetter(String file){
		try {
			FileInputStream InputStream = new FileInputStream(file);
		    POIFSFileSystem fs = new POIFSFileSystem(InputStream);
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;
	
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
	
		    int cols = 0; // No of columns
		    
		    //int tmp = 0;
		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    /*for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }
		    */
		    
		    cols = sheet.getRow(0).getPhysicalNumberOfCells();
//		    for(int i = 1; i < rows; i++){
//		    	if(sheet.getRow(i).getPhysicalNumberOfCells() != cols){
//		    		throw new IllegalStateException("rows different sizes" + cols + " " + sheet.getRow(i).getPhysicalNumberOfCells());
//		    	}
//		    }
//		    
		    myItems = new Object[rows][cols];
		    rowSize = rows;
		    colSize = cols;
		    
//		    System.out.println(rows + " " + cols);
		    
		    for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell(c);
		                if(cell != null && (cell.getCellType() == 0)) {
		                    myItems[r][c] = (int) cell.getNumericCellValue();
		                } else if(cell != null && (cell.getCellType() == 1)){
		                	myItems[r][c] = cell.getRichStringCellValue().getString();
		                }
		                //System.out.println(r + ", " + c + " " + cell.getCellType());
		            }
		        }
		    }
		    InputStream.close();
		    
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
		return myItems;
	}
	
	
	public void printItems(){
		for(int i = 0; i < rowSize; i ++){
			
			for(int j = 0; j < colSize; j++){
				System.out.print(myItems[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}