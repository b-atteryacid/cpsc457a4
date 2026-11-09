
class Server {


	static void main () {
		int[] avdurs = new int[100];

		for (int i = 0; i < 100; i++) {
			avdurs[i] = 0;
		}


		for (int i = 2; i < 100; i++) {
			// change strategy here
			Tournament cs = new Tournament(i);

			// start the threads
			for(int j = 0; j < cs.n; j++) {
				new Worker(cs,j).start();
			}

			int sumDurs = 0;

			// wait for all threads to get their 5 turns
			for(int j = 0; j < cs.n; j++) {
				while(cs.dones.get(j) < 5) {};
				int thisDur = cs.ends.get(j)-cs.starts.get(j);
				sumDurs += thisDur;
			}
			avdurs[i] = sumDurs/cs.n;
		}


		// print all average turnaround times
		for(int i = 2; i < 100; i++) {
			System.out.print("RESULT FOR "+i+": AV "+avdurs[i]+"ms\n");
		}

	}
}
