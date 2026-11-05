abstract class CriticalSection_Base {

	abstract void EntrySection (Worker thread);
	abstract void ExitSection (Worker thread);

	void CriticalSection (Worker thread) throws InterruptedException {
	System.out.print("thread "+ thread.id + " CSstart\n");
		Thread.sleep (50);
		System.out.print("thread "+ thread.id + " CSend\n");
	}
};
