package orchestra;

import java.util.HashSet;
import java.util.Set;

public class WoodwindInstrumentMusician implements Musician{

	private Integer[] played;
	
	public WoodwindInstrumentMusician() {
		this.played = new Integer[2];
	}

	@Override
	public String playPiece(Piece piece) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i<piece.getSize(); i++) {
			for (int j = 0; j<piece.getSize(); j++ ) {
				Part part = piece.getPart(j);
				if((i!=j)&&(part.equals(piece.getPart(i)))) {
					set.add(i);
				}
			}
		}
		played = set.toArray(new Integer[set.size()]);
		return null;
	}

	public Integer[] getPlayed() {
		return played;
	}
}
