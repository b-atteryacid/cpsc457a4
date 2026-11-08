
import java.util.concurrent.atomic.AtomicIntegerArray;

class Tournament extends CriticalSection_Base {


	Petersons[] cs;
	AtomicIntegerArray pids;

	int n = 0;
	int ceillog2n = 0;

	Tournament (Integer max_threads) {
		cs = new Petersons(max_threads-1);
		n = max_threads;
		ceillog2n = Math.ceil(Math.log(n)/Math.log(2));
		pids = new AtomicIntegerArray(n*ceillog2n); // cant have 2d of this array so just multiply into 1d
	}

	@Override
	void EntrySection (Worker thread) {
		int csid = thread.id + n;

		for(int lev = 0; lev < ceillog2n; lev++) {
			pids.set(thread.id*n+lev,csid%2);

		}
	}
//
	@Override
	void ExitSection (Worker thread) {
		/* <<TODO>> */
	}
};
