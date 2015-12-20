import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Program {
	static Map<Integer, Double> GoldWeightInGramsHMap;
	static Map<Integer, Double> GoldWeightInGramsStartStateHMap;
	static Map<Integer, Double> g1; //group1 
	static Map<Integer, Double> g2; //group2
	static Map<Integer, Double> g3; //group3
	static Map<Integer, Double> gr; //group for remained golds after division
	static int stepCount;
	private static Scanner in;
	public static void main(String[] args) {
		GoldWeightInGramsHMap = new HashMap<Integer, Double>();
		GoldWeightInGramsStartStateHMap = new HashMap<Integer, Double>();
		stepCount = 1;
		g1 = new HashMap<Integer, Double>();
		g2 = new HashMap<Integer, Double>();
		g3 = new HashMap<Integer, Double>();
		gr = new HashMap<Integer, Double>();
		
		// User can enter at least 3 
		Random r = new Random();
		in = new Scanner(System.in);
		System.out.print("Please enter the number of gold (at least 3): ");
		int numOfGold = in.nextInt();
		boolean secenekDogruMu = false;
		int secim;
		do{
			System.out.println("Press 1 to write standart weight of golds, press 2 to  continue without entering (random)  and press the Enter");
			secim = in.nextInt();
			if(secim == 1 || secim == 2)
				secenekDogruMu = true;
			else
				System.out.println("Wrong choice, please try again \n\n");
		}while(!secenekDogruMu);
		Double gramaj;
		if(secim == 1){
			System.out.print("Please enter the weight(as xx,xx ): ");
			gramaj = in.nextDouble();
		} else
		{
			gramaj = r.nextDouble();
			System.out.println("The weight of gold determined by randomly. Weight: " + gramaj);
		}

		for (int i = 0; i < numOfGold	; i++) {
			GoldWeightInGramsHMap.put(i, gramaj);
		}

		
		int rastgeleIndis = r.nextInt(numOfGold);
		Double randWeight;
		do {

			randWeight = r.nextDouble();
		} while (randWeight == gramaj);
		GoldWeightInGramsHMap.put(rastgeleIndis, randWeight);

		for (int key : GoldWeightInGramsHMap.keySet()) {
			System.out.printf("[%3d]: %3.2f \n", key, GoldWeightInGramsHMap.get(key));
		}
		GoldWeightInGramsStartStateHMap.putAll(GoldWeightInGramsHMap);
		int numOfGolds ;
		do {
			DivideGoldsAndFindDifferentGroup(GoldWeightInGramsHMap);			
			numOfGolds = GoldWeightInGramsHMap.size();
		} while (numOfGolds > 3);
		if(numOfGolds < 3)
			FindDifferentGoldInGr(GoldWeightInGramsHMap, gr.get(0));
		else
			FindDifferentGold(GoldWeightInGramsHMap);

	}
	public static void DivideGoldsAndFindDifferentGroup(Map<Integer, Double> GoldWeightInGramsHMap2){
		int numOfGolds = GoldWeightInGramsHMap2.size();
		int IndiceInGr = 0;
		if (numOfGolds %3 == 0) {
			DivideGoldsInto3Group(GoldWeightInGramsHMap2);
			GoldWeightInGramsHMap = FindDifferentGroup(g1, g2, g3);
		} else
		{

			int numOfRemainGolds = numOfGolds%3;
			//we add remain golds to Gr
			if(numOfRemainGolds == 2){
				gr.put(IndiceInGr, GoldWeightInGramsHMap2.get(numOfGolds-1));
				IndiceInGr++;
				gr.put(IndiceInGr, GoldWeightInGramsHMap2.get(numOfGolds-2));
				GoldWeightInGramsHMap2.remove(numOfGolds-1);
				GoldWeightInGramsHMap2.remove(numOfGolds-2);
			} else if(numOfRemainGolds == 1){
				gr.put(IndiceInGr, GoldWeightInGramsHMap2.get(numOfGolds-1));
				IndiceInGr++;
				GoldWeightInGramsHMap2.remove(numOfGolds-1);
			}

			DivideGoldsInto3Group(GoldWeightInGramsHMap2);
			GoldWeightInGramsHMap =FindTheContaingGroupofDifferentGold(g1, g2, g3,gr);

		}
	}
	public static void DivideGoldsInto3Group(Map<Integer, Double> goldGroup){

		int numofGolds = goldGroup.size();
		g1 = new HashMap<Integer, Double>();
		g2 = new HashMap<Integer, Double>();
		g3 = new HashMap<Integer, Double>();
		int g2Indice = 0;
		int g3Indice = 0;

		for(int i = 0; i < numofGolds/3; i++){
			g1.put(i,goldGroup.get(i));
		}

		for(int i = numofGolds/3; i<((2*numofGolds)/3); i++){
			g2.put(g2Indice, goldGroup.get(i));
			g2Indice++;
		}
		for(int i = (2*numofGolds)/3 ; i<numofGolds; i++){
			g3.put(g3Indice, goldGroup.get(i));
			g3Indice++;
		}
	}
	public static Map<Integer, Double> FindDifferentGroup(Map<Integer, Double> g1, Map<Integer, Double>  g2, Map<Integer, Double> g3){
		Map<Integer, Double> gk = new HashMap<Integer, Double>();
		return FindTheContaingGroupofDifferentGold(g1, g2, g3, gk);
	}

	public static Map<Integer, Double> FindTheContaingGroupofDifferentGold(Map<Integer, Double> g1, Map<Integer, Double>  g2, Map<Integer, Double> g3, Map<Integer, Double> gk){
		double WeigOfg1 = 0;
		double WeigOfg2 = 0; 
		double WeigOfg3 = 0;
		for (int  key : g1.keySet()) {
			WeigOfg1 += g1.get(key);
		}
		for(int key: g2.keySet()){
			WeigOfg2 += g2.get(key);
		}
		for (int key : g3.keySet()) {
			WeigOfg3 += g3.get(key);
		}

		if(WeigOfg1 == WeigOfg2 && WeigOfg2 == WeigOfg3)
		{
			System.out.println("Number of step is : " + stepCount + ". The different gold group is gr!");
			stepCount++;
			FindDifferentGoldInGr(gk, g1.get(0));
			System.exit(1);
		}
		else if(WeigOfg1 == WeigOfg2){
			System.out.println("Number of step is : " + stepCount +  ". The different gold group is g3!");
			stepCount++;
			return g3;
		}
		else if ( WeigOfg1 == WeigOfg3){
			System.out.println("Number of step is : " + stepCount +". The different gold group is g2!");
			stepCount++;
			return g2;
		}

		System.out.println("Number of step is : " + stepCount +". The different gold group is g1!");
		stepCount++;


		return g1;
	}

	public static void FindDifferentGoldInGr(Map<Integer, Double>gk, Double normalWeight){
		int numOfGolds = gk.size();
		//Completing gk to 3 (if it has less than 2 golds). 
		for (int i = numOfGolds; i<3; i++) {
			gk.put(i, normalWeight);
		}
		FindDifferentGold(gk);
	}

	
	public static void FindDifferentGold(Map<Integer, Double> gk2){
		int KeyOfPreGoldsWeight;
		Double ValOfPreGoldWeight ;
		int KeyOfNextGoldsWieght;
		Double ValOfNextGolds;


		for(int key = 1; key < gk2.size() -1; key++){
			KeyOfPreGoldsWeight = key -1;
			ValOfPreGoldWeight = gk2.get(KeyOfPreGoldsWeight);

			KeyOfNextGoldsWieght = key +1;
			ValOfNextGolds = gk2.get(KeyOfNextGoldsWieght);

			if(gk2.get(key) != ValOfPreGoldWeight ){
				if(ValOfPreGoldWeight == ValOfNextGolds)
				{
					System.out.print("The different weighted gold is found! Its weight is : " + gk2.get(key) + " indice: " + findIndiceofFoundGold(gk2.get(key)));
					break;
				}
				else {
					System.out.print("The different weighted gold is found! Its weight is :" + gk2.get(KeyOfPreGoldsWeight) + " indice: " + findIndiceofFoundGold(gk2.get(KeyOfPreGoldsWeight)));
					break;
				}
			} else if( ValOfPreGoldWeight != ValOfNextGolds)
			{
				System.out.print("The different weighted gold is found! Its weight is: " + gk2.get(KeyOfNextGoldsWieght) + " indice: " + findIndiceofFoundGold(gk2.get(KeyOfNextGoldsWieght)));
				break;
			}

		}
	}
	public static int findIndiceofFoundGold(Double weight){
		int a = 0 ;
		for (int key : GoldWeightInGramsStartStateHMap.keySet()) {
			if (weight == GoldWeightInGramsStartStateHMap.get(key)) {
				a = key ;
				break;
			}
			else 	a = 0;
		}
		return a;

	}
}
