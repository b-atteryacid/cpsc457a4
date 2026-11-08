
class Server {


	static void main () {
		//Unfair unfair = new Unfair(10);

		int[] avdurs = new int[100];

		for (int i = 2; i < 100; i++) {
			Unfair cs = new Unfair(i);
			for(int j = 0; j < cs.n; j++) {
				new Worker(cs,j).start();
			}

					int sumDurs = 0;

		for(int j = 0; j < cs.n; j++) {
			while(cs.dones.get(j) < 5) {};
			int thisDur = cs.ends.get(j)-cs.starts.get(j);
			System.out.println("thread "+j+" took "+thisDur+"ms turnaround!\n");
			sumDurs += thisDur;
		}
		System.out.println("average: "+sumDurs/cs.n+"ms for "+cs.n+" threads!\n");
		avdurs[i] = sumDurs/cs.n;
		}

		for(int i = 0; i < 100; i++) {
			System.out.print("RESULT FOR "+i+": AV "+avdurs[i]+"ms\n");
		}

	}
}
