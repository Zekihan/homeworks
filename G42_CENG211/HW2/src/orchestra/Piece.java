package orchestra;


public class Piece {

	
	private Part[] piece;
	private String tempoChange;

	public Piece(Part[] piece) {
		setPiece(piece);
		setTempoChange(null);
	}
		
	public void setTempoChange(String tempoChange) {
		this.tempoChange = tempoChange;
	}

	public Part[] getPiece() {
		return piece;
	}
	
	public Part getPart(int i) {
		return piece[i];
	}
	
	public String getTempoChange() {
		return tempoChange;
	}

	public void setPiece(Part[] piece) {
		this.piece = piece;
	}

	public int getSize() {
		return piece.length;
	}
	


	
	

	
	
}
