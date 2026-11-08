
import java.util.concurrent.atomic.AtomicIntegerArray;

class Unfair extends CriticalSection_Base {

	AtomicIntegerArray starts;
	AtomicIntegerArray ends;
	AtomicIntegerArray dones;

	AtomicIntegerArray flags;
	volatile boolean inCS = false;

	int n = 0;

	Unfair (Integer max_threads) {

		starts = new AtomicIntegerArray(max_threads);
		ends = new AtomicIntegerArray(max_threads);
		dones = new AtomicIntegerArray(max_threads);

		flags = new AtomicIntegerArray(max_threads);

		n = max_threads;

		for(int i = 0; i < max_threads; i++) {
			dones.set(i,0);
			flags.set(i,0);
		}
	}


	@Override
	void EntrySection (Worker thread) {
	if(dones.get(thread.id) == 0){
		starts.set(thread.id,(int)System.currentTimeMillis());
		}
		flags.set(thread.id,1);
		while(inCS){};
		for(int i = 0; i < thread.id; i++) {
			while(flags.get(i) > 0 || inCS) {};
		}
		inCS = true;
	}

	@Override
	void ExitSection (Worker thread) {
		flags.set(thread.id,0);
		inCS = false;

				if(dones.get(thread.id) >= 4) {
		ends.set(thread.id,(int)System.currentTimeMillis());
		}
		dones.set(thread.id,dones.get(thread.id)+1);


	}
};
