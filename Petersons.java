
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
		n = max_threads;
		for (int i = 0; i < max_threads; i++) {
			flags[i] = -1;
			turns[i] = 0;
			dones[i] = 0;
		}

		for(int i = 0; i < max_threads; i++) {
					new Worker(this,i).start();
		}

	}


	@Override
	void EntrySection (Worker thread) {
		starts.set(thread.id,(int)System.currentTimeMillis());
		for(int i = 0; i < n-1; i++) {
			flags.set(thread.id,i);
			turns.set(k,thread.id);
		}

	}

	@Override
	void ExitSection (Worker thread) {
		ends.set(thread.id,(int)System.currentTimeMillis());
		mut = 0;
		dones.set(thread.id,1);
		System.out.print("thread "+thread.id+" exit\n");

	}
};
