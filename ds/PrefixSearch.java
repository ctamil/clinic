package ds;

public interface PrefixSearch {
	
	Traveller prefix(String number);
	void add(Object obj, String key);
	void remove(String key);
}
