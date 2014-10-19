import java.util.ArrayList;


public class ObjectRepoPairFinder {

	public Object[][] output;
	
	public static void main(String[] args){
		ObjectRepoPairFinder Finder = new ObjectRepoPairFinder("TRPSObjectRepository.xls");
		ExcelWriter myWriter = new ExcelWriter();
		myWriter.tableWriter(Finder.output, "TRPSObjectGeneratedPairs.xls");
	}
	
	
	public ObjectRepoPairFinder(String fileName){
		ExcelExtractor extract = new ExcelExtractor();
		Object[][] temp = extract.tableGetter(fileName);
		
		ArrayList<String> RepoObjs = new ArrayList<String>();
		for(int i = 0; i < 2; i++){
			for(int j = 1; j < temp.length; j++){
				if(temp[j][i] != null){
					RepoObjs.add((String)temp[j][i]);
				}
			}
		}
		
		String[] RepoList = new String[RepoObjs.size()];
		for(int i = 0; i < RepoList.length; i++){
			RepoList[i] = RepoObjs.remove(0);
		}
		
		
		output = new Object[300][3];
		for(int i = 0; i < output.length; i++){
			int randomNum = (int)(Math.random()*(RepoList.length));
			output[i][0] = RepoList[randomNum];
			output[i][1] = "and";
			int randomNum2 = (int)(Math.random()*(RepoList.length));
			while(randomNum2 == randomNum){
				randomNum2 = (int)(Math.random()*(RepoList.length));
			}
			output[i][2] = RepoList[randomNum2];
		}
	}
	
}



//for(int i = 0; i < temp.length; i++){
//	for(int j = 0; j < temp[0].length; j++){
//		System.out.print(temp[i][j]);
//	}
//	System.out.println();
//
//}		