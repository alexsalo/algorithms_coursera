

public class WeightedUF {
	private int[] id;
	private int[] sz;

	public static void main(String[] args) {
		WeightedUF weightedUF = new WeightedUF(10);
		weightedUF.parseUnions("1-4 6-0 3-6 8-2 3-7 7-5 2-1 6-1 9-4");
		System.out.println(weightedUF.toString());
		System.out.println(weightedUF.isConnected(1, 9));
	}

	public WeightedUF(int N) { // Init ~N
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	private int root(int p) { // ~lgN
		while (p != id[p]){
			p = id[p];
			id[p] = id[id[p]];
		}
		return p;
	}

	public void union(int p, int q) { // ~lgN
		int i = root(p);
		int j = root(q);
		if (i == j) return;
		if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
		else			   { id[j] = i; sz[i] += sz[j]; }
		
	}

	public boolean isConnected(int p, int q) { // ~lgN
		return root(p) == root(q);
	}
	
	public void parseUnions(String command){	
		String[] cmds = command.split(" ");
		for (String s : cmds)
			union(Character.getNumericValue(s.charAt(0)), Character.getNumericValue(s.charAt(2)));
	}

	public String toString() {
		String s = "id: ";
		for (int i : id)
			s += String.valueOf(i) + " ";
		s += "\nsz: ";
		for (int i : sz)
			s += String.valueOf(i) + " ";
		return s;
	}

}
