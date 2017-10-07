/**
 * @author Jessie George
 * @author Karl Xu
 */

package backend;

import java.util.*;

public class SongComparator implements Comparator<Song> {
	
	@Override
	// returns a negative integer, zero, or a positive integer
	// as the first argument is less than, equal to, or greater than the second
	public int compare(Song song1, Song song2) {
		
		int ans = song1.name.compareToIgnoreCase(song2.name);
		if(ans != 0) {
			return ans;
		}
		
		ans = song1.artist.compareToIgnoreCase(song2.artist);
		if(ans != 0) {
			return ans;
		}
		
		return 0;
	}
}