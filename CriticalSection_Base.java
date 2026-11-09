abstract class CriticalSection_Base {

	abstract void EntrySection (Worker thread);
	abstract void ExitSection (Worker thread);

	void CriticalSection (Worker thread) throws InterruptedException {
		Thread.sleep (50);
	}
};
