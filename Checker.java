import java.util.HashMap;


public class Checker {
	
	public Object[][] generatedScenes;
	public Object[][] originalTRPS;
	public Object[][] objectPairs;
	public Object[][] originalObjectPairs;
									
	
	public Object[][] output;
	
	
	public static void main(String[] args){
		Checker myChecker = new Checker("generatedScenesfinal.xls", "OriginalTRPS.xls", "TRPSObjectGeneratedPairs.xls", "TRPSObjectRepository.xls");
		ExcelWriter myWriter = new ExcelWriter();
		myWriter.tableWriter(myChecker.output, "TRPSObjectStats.xls");
		
		myWriter.tableWriter(myChecker.generatedScenes, "UpdatedGeneratedScenes.xls");
		
	}
	
	
	
	
	public Checker(String genScenes, String origTRPS, String objPairs, String origPairs){
		ExcelExtractor extract = new ExcelExtractor();
		generatedScenes = extract.tableGetter(genScenes); // Checks to see if TRPS are in Generated Scenes
		originalTRPS = extract.tableGetter(origTRPS);
		
		//System.out.println("The generated Scenes do not include the original TRPS: " + independentScenes());
		
		
		objectPairs = extract.tableGetter(objPairs); // Checks object pairings and counts
		originalObjectPairs = extract.tableGetter(origPairs);
		
		//System.out.println("The generated object pairs do not include the original object pairs: " + independentObjectPairs());
		
		
		output = new Object[4][150];
		
		//first row indexes
		for(int i = 1; i < output[0].length; i++){
			output[0][i] = i+1;
		}
		
		
		//second row, name of all objects
		HashMap<String, Integer> check = new HashMap<String, Integer>(); 
		int index = 1;
		for(int i = 1; i < objectPairs.length; i++){
			if(!check.containsKey(objectPairs[i][0])){
				check.put((String)objectPairs[i][0], index);
				output[1][index] = (String)objectPairs[i][0];
				index++;
			}
			if(!check.containsKey(objectPairs[i][2])){
				check.put((String)objectPairs[i][2], index);
				output[1][index] = (String)objectPairs[i][2];
				index++;
			}
		}

		//fills third row with 0s
		for(int i = 0; i < output[0].length; i++){
			output[2][i] = 0;
		}
		//counts number of times objects occur
		for(int i = 1; i < objectPairs.length; i++){
			int place = check.get(objectPairs[i][0]);
			int temp = (int)output[2][place];
			output[2][place] = temp+1;
			
			place = check.get(objectPairs[i][2]);
			temp = (int)output[2][place];
			output[2][place] = temp+1;
		}
		
		//fill out average and places on fourth row
		int sum = 0;
		int count = 0;
		//System.out.println(objectPairs.length);
		while((int)output[2][count] != 0){
			sum += (int) output[2][count];
			count ++;
		}
		output[3][1] = "average amount: " + sum/(count-1);
		output[3][2] = "total: " + sum;
		
		
		output[0][0] = "feature 'number'";
		output[1][0] = "feature name";
		output[2][0] = "number of occurances";
		output[3][0] = "other stats";
		
		
		//changing the original scenes
		
		Object[][] Opt = extract.tableGetter("R.opt.xls");
		Object[][] Descrip = extract.tableGetter("R.descrip.xls");

		
		for(int i = 2; i < generatedScenes.length; i++){
			generatedScenes[i][0] = i; 
		}
		
		System.out.println(Opt.length);
		
		for(int j = 1; j <= Opt.length; j++){
			generatedScenes[0][j] = j + " : " + Descrip[j-1][0] + " : " + Opt[j-1][0];
		}
		
		
		
	}
	

	//5, feature and 7, ground
	
	public boolean independentObjectPairs(){
		int lim = 1;
		while(originalObjectPairs[lim][5] != null){
			boolean samePair = true;
			int compare = 0;
			while(objectPairs[compare][0] != null){
				if(!((String)objectPairs[compare][0] != (String)originalObjectPairs[lim][5] && (String)objectPairs[compare][0] == (String)originalObjectPairs[lim][7]) ||
						(String)objectPairs[compare][2] != (String)originalObjectPairs[lim][5] || (String)objectPairs[compare][2] != (String)originalObjectPairs[lim][7]){
					samePair = false;
					break;
				}
				
				compare ++;
			}
			if(samePair){
				return false;
			}
			
			lim ++;	
		}
		return true;
	}
	
	
	public boolean independentScenes(){
		int lim = 0;
		while(lim < originalTRPS.length){
			for(int i = 1; i < generatedScenes.length; i++){
				boolean sameScene = true;
				for(int j = 0; j < originalTRPS.length; j++){
					if((int)generatedScenes[i][j+1] != (int)originalTRPS[lim][j]){
						sameScene = false;
						break;// not the same
					}
				}
				if(sameScene){
					return false;
				}
			}
			
			lim++;
		}
		return true;
	}
	
}
