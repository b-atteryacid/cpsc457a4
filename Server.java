
class Server {


	static void main () {
		//Unfair unfair = new Unfair(10);
		Petersons peterson = new Petersons(10);
		for(int i = 0; i < peterson.n; i++) {
			new Worker(peterson,i).start();
		}

		int sumDurs = 0;

		for(int i = 0; i < peterson.n; i++) {
			while(peterson.dones.get(i) == 0) {};
			int thisDur = peterson.ends.get(i)-peterson.starts.get(i);
			System.out.println("thread "+i+" took "+thisDur+"ms turnaround!\n");
			sumDurs += thisDur;
		}
		System.out.println("average: "+sumDurs/peterson.n+"ms for "+peterson.n+" threads!\n");
	}
}
