package structures;


public class UF {
	private int[] id;
	
	public static void main(String[] args) {
		UF quickFind = new UF(10);
		quickFind.parseUnions("4-7 7-9 7-2 0-5 7-3 9-8");
		System.out.println(quickFind.toString());
	}
	
	public UF(int N){  // Init ~N
		id = new int[N];
		for (int i = 0; i < N; i++){
			id[i] = i;
		}
	}
	
	public void union(int p, int q){  // Union ~N
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++){
			if (id[i] == pid){
				id[i] = qid;
			}
		}
	}
	
	public boolean isConnected(int p, int q){  // Find ~1
		return id[p] == id[q];
	}
	
	public void parseUnions(String command){	
		String[] cmds = command.split(" ");
		for (String s : cmds)
			union(Character.getNumericValue(s.charAt(0)), Character.getNumericValue(s.charAt(2)));
	}
	
	public String toString(){
		String s = "";
		for (int i : id)
			s += String.valueOf(i) + " ";
		return s;
	}

}
