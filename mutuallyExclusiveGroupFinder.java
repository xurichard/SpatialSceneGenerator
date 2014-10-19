import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class mutuallyExclusiveGroupFinder {
	
	public int[][] inputTable;
	public int[][] outputTable;
	public ArrayList<Point> allResults = new ArrayList<Point>();
	
	public static void main(String[] args){
		mutuallyExclusiveGroupFinder myFinder = new mutuallyExclusiveGroupFinder("Rcohere.xls");
		int[][] temp = myFinder.findGroups(0);
		ExcelWriter myWriter = new ExcelWriter();
		//myWriter.tableIntWriter(temp, "mutuallyExclusiveGroups.xls");
		
		int n = 0;
		for(int i = 0 ; i < temp.length; i++){
			if(temp[i][0] == 16 || temp[i][1] == 16){
				n+=1;
			}
		}
		System.out.println(n);
		
		Hashtable<Integer, Integer> keeper = new Hashtable<Integer, Integer>(); // checks for same relationships a -> b && b -> a
		int[][] tempCount = new int[60][2];
		for(int i = 0 ; i < tempCount.length; i++){
			tempCount[i][0] = i;
			tempCount[i][1] = 0;
		}
		for(int i = 0 ; i < temp.length; i++){
			if(keeper.contains(temp[i][0])){
				if(!(keeper.get(temp[i][0]) == temp[i][1])){
					tempCount[temp[i][0]][1] += 1;
					tempCount[temp[i][1]][1] += 1;
				}
			} else{
				tempCount[temp[i][0]][1] += 1;
				tempCount[temp[i][1]][1] += 1;
				keeper.put(temp[i][0], temp[i][1]);
				keeper.put(temp[i][1], temp[i][0]);
			}
		}
		myWriter.tableIntWriter(tempCount, "mutuallyExclusiveGroupCount.xls");
	}
	
	public mutuallyExclusiveGroupFinder(String fileName){
		ExcelExtractor extract = new ExcelExtractor();
		Object[][] temp = extract.tableGetter(fileName);
		inputTable = new int[temp.length][temp[0].length];
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[0].length; j++){
				if(temp[i][j] instanceof Integer){
					inputTable[i][j] = (int) temp[i][j];
				}
			}
		}
	}
	
	public int findLargestInt(){
		if(inputTable.length != inputTable[0].length){
			throw new IllegalStateException("number of rows and columns don't match");
		}
		int soFar = 0;
		for(int i = 0; i < inputTable.length; i++){
			for(int j = 0; j < i; j++){
				if(inputTable[i][j] > soFar){
					soFar = inputTable[i][j];
				}
			}
		}
		//System.out.println(soFar);
		return soFar;
	}
	
	public int[][] findGroups(int upperPercentLimit){
		int largest = findLargestInt();
		int size = 0;
		for(int i = 0; i < inputTable.length; i++){
			for(int j = 0; j < i; j++){
				if(inputTable[i][j]/largest <= upperPercentLimit){
					allResults.add(new Point(i + 1, j + 1));
					size++;
					//System.out.println(i + " " + j);
				}
			}
		}
		outputTable = new int[size][2];
		Iterator<Point> myIter = allResults.iterator();
		int index = 0;
		while(myIter.hasNext()){
			Point point = myIter.next();
			//System.out.println(key + " " + allResults.get(key));
			outputTable[index][0] = (int) point.getX();
			outputTable[index][1] = (int) point.getY();
			index++;
		}
		return outputTable;
	}

	
}
