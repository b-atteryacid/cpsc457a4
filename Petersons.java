
import java.util.concurrent.atomic.AtomicIntegerArray;

class Petersons extends CriticalSection_Base {

	AtomicIntegerArray flags;
	AtomicIntegerArray turns;

		AtomicIntegerArray starts;
	AtomicIntegerArray ends;
	AtomicIntegerArray dones;

	int n = 0;

	Petersons (Integer max_threads) {
		flags = new AtomicIntegerArray(max_threads);
		turns = new AtomicIntegerArray(max_threads);

		starts = new AtomicIntegerArray(max_threads);
		ends = new AtomicIntegerArray(max_threads);
		dones = new AtomicIntegerArray(max_threads);

		n = max_threads;
		for (int i = 0; i < max_threads; i++) {
			flags.set(i,-1);
			turns.set(i,0);
			dones.set(i,0);
		}

		for(int i = 0; i < max_threads; i++) {
			new Worker(this,i).start();
		}


		int sumDurs = 0;

		for(int i = 0; i < max_threads; i++) {
			while(dones.get(i) == 0) {};
			int thisDur = ends.get(i)-starts.get(i);
			System.out.println("thread "+i+" took "+thisDur+"ms\n");
			sumDurs += thisDur;
		}
		System.out.println("average: "+sumDurs/max_threads+"ms for "+max_threads+" threads!\n");

	}

	void WaitForTrue(int i, int k) {
		boolean exists;
		do {
			exists = false;
			for(int j = 0; j < n; j++) {
				if(j!=i) {
					if(flags.get(j) >= k) {
					exists = true;
					break;
					}
				}
			}

		} while (exists && turns.get(k) == i);
	}

	@Override
	void EntrySection (Worker thread) {
		starts.set(thread.id,(int)System.currentTimeMillis());
		for(int i = 0; i < n-1; i++) {
			flags.set(thread.id,i);
			turns.set(i,thread.id);
			WaitForTrue(thread.id,i);
		}

	}

	@Override
	void ExitSection (Worker thread) {
		ends.set(thread.id,(int)System.currentTimeMillis());
		flags.set(thread.id,-1);
		dones.set(thread.id,1);
		//System.out.print("thread "+thread.id+" exit\n");

	}
};
