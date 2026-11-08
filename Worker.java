
class Worker extends Thread {
	Integer id;
	CriticalSection_Base critical_section;


	public Worker (CriticalSection_Base critical_section, Integer id) {
		this.id = id;
		this.critical_section = critical_section;
	}

	public void run () {
	for(int i = 0; i < 5; i++) {
		critical_section.EntrySection (this);
		try {
			critical_section.CriticalSection (this);
		}
		catch (InterruptedException e) {
		}
		critical_section.ExitSection (this);
		}
	}
}
