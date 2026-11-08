
import java.util.concurrent.atomic.AtomicIntegerArray;

class Tournament extends CriticalSection_Base {


	Petersons[] cs;
	AtomicIntegerArray pids;

	int n = 0;
	int ceillog2n = 0;

	AtomicIntegerArray starts;
	AtomicIntegerArray ends;
	AtomicIntegerArray dones;


	Tournament (Integer max_threads) {

		n = max_threads;
		ceillog2n = (int)Math.ceil(Math.log(n)/Math.log(2));
		cs = new Petersons[n];
		pids = new AtomicIntegerArray(n*ceillog2n); // cant have 2d of this array so just multiply into 1d
		for(int i = 0; i < cs.length; i++) {
			cs[i] = new Petersons(2);
		}
		starts = new AtomicIntegerArray(max_threads);
		ends = new AtomicIntegerArray(max_threads);
		dones = new AtomicIntegerArray(max_threads);

		n = max_threads;
		for (int i = 0; i < max_threads; i++) {
			dones.set(i,0);
		}
	}

	@Override
	void EntrySection (Worker thread) {
		int csid = thread.id + n;
	if(dones.get(thread.id) == 0){
		starts.set(thread.id,(int)System.currentTimeMillis());
		}
		for(int lev = 0; lev < ceillog2n; lev++) {
			pids.set(thread.id*ceillog2n+lev,csid%2);
			Worker tempthread = thread;
			int previd = thread.id;
			tempthread.id = pids.get(thread.id*ceillog2n+lev);
			cs[csid/2].EntrySection(tempthread);
			thread.id = previd;
			csid /= 2;
		}
	}
//
	@Override
	void ExitSection (Worker thread) {
		int csid = 1;
				if(dones.get(thread.id) >= 4) {
		ends.set(thread.id,(int)System.currentTimeMillis());
		}
		for(int lev = ceillog2n-1; lev >= 0; lev--) {
					Worker tempthread = thread;
								int previd = thread.id;
			tempthread.id = pids.get(thread.id*ceillog2n+lev);
		cs[csid].ExitSection(tempthread);
		thread.id = previd;
		csid = 2*csid + pids.get(thread.id*ceillog2n+lev);
		dones.set(thread.id,dones.get(thread.id)+1);
		}
	}
};
