import java.util.ArrayList;

public class SceneFinder {
	
	public int[][] featurePairs;
	public Object[][] output;
	public Object[][] mutexNums;
	
	public static void main(String[] args){
		ExcelWriter myWriter = new ExcelWriter();
		//SceneFinder myFinder = new SceneFinder("mutuallyExclusiveGroups.xls");
		SceneFinder myFinder = new SceneFinder("finalMutexGroups.xls");
		myWriter.tableWriter(myFinder.output, "generatedScenesfinal.xls");
		myWriter.tableWriter(myFinder.mutexNums, "genScenesMutexNums.xls");
	}
	
	public SceneFinder(String fileName){ // fileName should be the name of the file with the Mutually Exclusive Pairs
		//Extracts Mutually exclusive exclusive pairs and stores them in featurePairs
		ExcelExtractor extract = new ExcelExtractor();
		Object[][] temp = extract.tableGetter(fileName);
		featurePairs = new int[temp.length][temp[0].length];
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[0].length; j++){
				if(temp[i][j] instanceof Integer){
					featurePairs[i][j] = (int) temp[i][j];
				} else{
					featurePairs[i][j] = -1; // doesn't exist case, shouldn't occur
				}
			}
		}
		
		output = new Object[301][60];
		for(int i = 0; i < output.length; i++){
			output[i][0] = "scene " + i;
		}
		for(int i = 0; i < output[0].length; i++){
			output[0][i] = "feature " + i;
		}
		output[0][0] = "Every Line of the Excel file is one scene (There are 300 scenes in total)";

		
		
		for(int i = 1; i < 301; i++){
			String tempRet = getRandomPath();
			int[] line = getFeatures(tempRet);
			for(int j = 0; j < line.length; j++){
				output[i][j+1] = line[j];
			}
		}
		
		
		mutexNums = new Object[3][60];
		for(int i = 0; i < mutexNums.length; i++){
			for(int j = 0; j < mutexNums[0].length; j++){
				mutexNums[i][j] = 0;
			}
		}
		for(int i = 1; i < output.length; i++){
			for(int j = 1; j < output[0].length; j++){
				if(i == 1){
					mutexNums[i-1][j] = (Object)(j);
				} else{
					if((int)output[i][j] == 1){
						int num = (int)mutexNums[1][j] + 1;
						mutexNums[1][j] = (Object)num;
					}
				}
			}
		}
		
		for(int i = 0; i < temp.length; i++){
			mutexNums[2][(int)temp[i][0]+1] = (Object)((int) mutexNums[2][(int)temp[i][0]+1] + 1);
			mutexNums[2][(int)temp[i][1]+1] = (Object)((int) mutexNums[2][(int)temp[i][1]+1] + 1);	
		}
		
		mutexNums[0][0] = "Feature: ";
		mutexNums[1][0] = "How many times they appeared in the new scenes: ";
		mutexNums[2][0] = "How many mutual exclusions they have: ";
	}

	public String getRandomPath(){
		String path = "";
		for(int i = 0; i < 59; i++){
			path += "a";
		}
		for(int i = 0; i < 59; i ++){
			int place = (int)(Math.random() * 59);
			while(place > 59){
				place = (int)(Math.random() * 59);
			}
			
			if(path.charAt(place) == 'a'){
				boolean isExcluded = false;
				for(int group : groupFind(place)){
					if(path.charAt(group) == '1'){
						isExcluded = true;
					}
				}
				if(!isExcluded && Math.random() > 0.5){
					path  = path.substring(0, place) + "1" + path.substring(place+1, 59);
				} else{
					path  = path.substring(0, place) + "0" + path.substring(place+1, 59);
				}
			} else{
				i--;
			}
		}
		return path;
	}
	
	
	
	public int[] getFeatures(String path){
		int[] returnArr = new int[path.length()];
		for(int i = 0; i < path.length(); i++){
			String feature = path.substring(i, i+1);
			returnArr[i] = Integer.parseInt(feature);
		}
		return returnArr;
	}
	
	
	public ArrayList<Integer> groupFind(int featureOne){
		ArrayList<Integer> myGroups = new ArrayList<Integer>();
		for(int i = 0; i < featurePairs.length; i++){
			if(featurePairs[i][0] == featureOne){
				myGroups.add(featurePairs[i][1]);
			}else if(featurePairs[i][1] == featureOne){
				myGroups.add(featurePairs[i][0]);
			}
		}
		return myGroups;
	}

}