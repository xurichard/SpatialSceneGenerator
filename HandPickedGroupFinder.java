import java.awt.Point;
import java.util.ArrayList;


public class HandPickedGroupFinder {
	
	public Object[][] inputTable;
	public static int[][] outputTable;

	public static void main(String[] args){
		HandPickedGroupFinder myFinder = new HandPickedGroupFinder("allResp.xls");
		int[][] temp = myFinder.find("R.opt.xls", "R.descrip.xls");
		ExcelWriter myWriter = new ExcelWriter();
		myWriter.tableIntWriter(outputTable, "finalMutexGroups.xls");
	}
	
	public HandPickedGroupFinder(String fileName){
		ExcelExtractor extract = new ExcelExtractor();
		Object[][] temp = extract.tableGetter(fileName);
		inputTable = new Object[temp.length][temp[0].length];
		outputTable = new int[temp.length][temp[0].length];
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[0].length; j++){
				inputTable[i][j] = temp[i][j];
			}
		}
	}
	
	public int[][] find(String optFileName, String decripFileName){
		int first = 0;
		ExcelExtractor extract = new ExcelExtractor();
		Object[][] tempOpt = extract.tableGetter(optFileName);
		Object[][] tempDecrip = extract.tableGetter(decripFileName);
//		System.out.println(tempOpt.length + " " + tempOpt[0].length);
//		System.out.println(inputTable.length + " " + inputTable[0].length);
//		System.out.println(inputTable[0][5]);
		for(int i = 1; i < inputTable.length; i++){
			if(((Integer)inputTable[i][3]) == 0 && ((Integer)inputTable[i][4]) == 0){
				int featureOneNum = findFeatureNum((String)inputTable[i][0], tempOpt, tempDecrip);
				int featureTwoNum = findFeatureNum((String)inputTable[i][2], tempOpt, tempDecrip);
				outputTable[first][0] = featureOneNum;
				outputTable[first][1] = featureTwoNum;
				first++;
			}
		}
		return outputTable;
	}
	
	
	private int findFeatureNum(String feature, Object[][] tempOpt, Object[][] tempDecrip){
		for(int i = 0; i < tempDecrip.length; i++){
			String newDescrip = ((String)tempDecrip[i][0]).split("'")[1];
			String newOpt = ((String)tempOpt[i][0]).split("'")[1];
			String featureDescrip = feature.split(":")[0];
			String featureOpt = feature.split(":")[1];
//			featureDescrip = featureDescrip.split(" ")[1];
//			featureOpt = featureOpt.split(" ")[1];
			featureDescrip = featureDescrip.substring(1);
			featureOpt = featureOpt.substring(1,featureOpt.length()-1);
//			System.out.println(featureOpt.equals(newOpt));
//			System.out.println(featureOpt + ".." + newOpt);// + ".." + featureDescrip + ".." + featureOpt + "..........");
			if(newDescrip.equals(featureDescrip) && newOpt.equals(featureOpt)){
				return i;
			}
		}
		return -1;
	}
}






