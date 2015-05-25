package de.schreibvehler.timeit.biz;

public interface TestSet {
	
	Test[] getTests();
	
	void init();
	
	long getDefaultTimes();
	
	String getTitle();

	String getDescription();
	
}
