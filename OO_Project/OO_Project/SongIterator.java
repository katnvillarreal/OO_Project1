package OO_Project;

import java.util.*;

public class SongIterator <Song> implements Iterator <Song>{
	private int position; //Always points to the next value
	private Song [] values;
	private int count;
	
	
	public SongIterator(Song[] theValues, int aCount) {
		position = 0;
		values = theValues;
		count = aCount;
	}
	
	public boolean hasNext() { 
		return position < count;
	}
	
	public Song next() { 
		if (position >= count)
			throw new NoSuchElementException("Past " + count + " elements");
		position++;
		return values[position - 1];
    }
}
