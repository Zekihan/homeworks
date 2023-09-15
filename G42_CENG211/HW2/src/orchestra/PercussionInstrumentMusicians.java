package orchestra;

public class PercussionInstrumentMusicians implements Musician{

	private Part[] played;
	public PercussionInstrumentMusicians() {
		this.played = new Part[2];
	}



	@Override
	public String playPiece(Piece piece) {
		int size = piece.getSize();
		Part lastSecond = piece.getPart((size-2));
		Part lastFirst = piece.getPart(size-1);
		played[0] = lastSecond;
		played[1] = lastFirst;
		return null;
	}

	public Part[] getPlayed() {
		return played;
	}
	

}
