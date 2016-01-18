package ds;


public interface Container {

	public int size();
	public boolean isEmpty();
	public Traveller traveller();
	public void add(Object o);
	public void remove(Object o);
	public boolean contains(Object o);
	public void clear();
	public Object get(Object obj);
	
}
