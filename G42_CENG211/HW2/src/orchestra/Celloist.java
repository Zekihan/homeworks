package orchestra;

public class Celloist extends StringInstrumentMusician{

	private Part[] shouldPlay;

	public Celloist() {
		super();
	}

	@Override
	public String playPiece(Piece piece) {
		super.playPiece(piece);
		shouldPlay = super.getPlayed();
		String print = "";
		for (int i = 0; i<shouldPlay.length; i++ ) {
			Part part = shouldPlay[i];
			String tempo = part.getTempo();
			print +="Part " + (i+1) +":";
			for (Score score : part.getPart()) {
				print += " " + score.getType();
			}
			print += " -- " + tempo + System.lineSeparator();
		}
		if(print.equals("")) {
			return "";
		}
		return "Celloist Played"+System.lineSeparator()+print;
	}
}
