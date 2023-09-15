package orchestra;

public class StringInstrumentMusician implements Musician{
	
	private Part[] played;
	
	public StringInstrumentMusician() {
		this.played = new Part[2];
	}

	@Override
	public String playPiece(Piece piece) {
		played = piece.getPiece();
		return null;
		
	}
	
	public Part[] getPlayed() {
		return played;
	}

}
