
import java.util.concurrent.atomic.AtomicIntegerArray;

class Unfair extends CriticalSection_Base {

	AtomicIntegerArray starts;
	AtomicIntegerArray ends;
	AtomicIntegerArray dones;

	volatile int mut = 0;
	Unfair (Integer max_threads) {

		starts = new AtomicIntegerArray(max_threads);
		ends = new AtomicIntegerArray(max_threads);
		dones = new AtomicIntegerArray(max_threads);

		for(int i = 0; i < max_threads; i++) {
			dones.set(i,0);
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

	@Override
	void EntrySection (Worker thread) {
		starts.set(thread.id,(int)System.currentTimeMillis());
		while(mut == 1) {};
		mut = 1;
		System.out.print("thread "+thread.id+"entry\n");
	}

	@Override
	void ExitSection (Worker thread) {
		ends.set(thread.id,(int)System.currentTimeMillis());
		mut = 0;
		dones.set(thread.id,1);
		System.out.print("thread "+thread.id+" exit\n");

	}
};
