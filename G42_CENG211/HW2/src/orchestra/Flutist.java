package orchestra;

public class Flutist extends WoodwindInstrumentMusician{

	
	public Flutist() {
		super();
	}

	@Override
	public String playPiece(Piece piece) {
		super.playPiece(piece);
		Integer[] indexOfPlay= super.getPlayed();
		String print = "";
		for (int i = 0; i<indexOfPlay.length; i++ ) {
			Part part = piece.getPart(indexOfPlay[i]);
			String tempo = part.getTempo();
			print +="Part " + (indexOfPlay[i] + 1) +":";
			for (Score score : part.getPart()) {
				print += " " + score.getType();
			}
			print += " -- " + tempo + System.lineSeparator();
		}
		if(print.equals("")) {
			return "";
		}
		return "Flutist Played"+System.lineSeparator()+print;
	}
}
