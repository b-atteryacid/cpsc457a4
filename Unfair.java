
import java.util.concurrent.atomic.AtomicIntegerArray;

class Unfair extends CriticalSection_Base {

	Unfair (Integer max_threads) {

		for(int i = 0; i < max_threads; i++) {
			new Worker(this,i).start();
		}
	}

	@Override
	void EntrySection (Worker thread) {
		System.out.print("thread "+thread.id+"entry\n");
	}

	@Override
	void ExitSection (Worker thread) {
		System.out.print("thread "+thread.id+" exit\n");
	}
};
