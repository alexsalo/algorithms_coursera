package games;


import java.util.ArrayList;

public class HanoiTower {	
	static int N = 3;
	
	static ArrayList<Integer> peg1 = new ArrayList<Integer>();
	static ArrayList<Integer> peg2 = new ArrayList<Integer>();
	static ArrayList<Integer> peg3 = new ArrayList<Integer>();

	public static void main(String[] args) {	
		//init
		for (int i = N; i > 0; i--){
			peg1.add(i);
		}
		
		printPegs();
		move(N, peg1, peg2, peg3);
	}
	
	static void moveDisk(ArrayList<Integer> from, ArrayList<Integer> to){	
		int disk = from.get(from.size() - 1);
		from.remove(from.size() - 1);
		to.add(disk);
		printPegs();
	}
	
	static void move(int n, ArrayList<Integer> from, ArrayList<Integer> to, ArrayList<Integer> via){		
		if (n > 1){
			move(n - 1, from, via, to);
		}
		moveDisk(from, to);
		if (n > 1){
			move(n - 1, via, to, from);
		}
	}
	
	static void printPegs(){
		System.out.println(peg1);
		System.out.println(peg2);
		System.out.println(peg3);
		System.out.println("------------");
	}


}
