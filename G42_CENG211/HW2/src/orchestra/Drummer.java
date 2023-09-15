package orchestra;

public class Drummer extends PercussionInstrumentMusicians{

	private Part[] shouldPlay;
	
	public Drummer() {
		super();
	}

	@Override
	public String playPiece(Piece piece) {
		super.playPiece(piece);
		shouldPlay = super.getPlayed();
		String print = "";
		for (int i = 0; i<shouldPlay.length; i++ ) {
			Part part = shouldPlay[i];
			String tempo =	part.getTempo();
			if ((tempo.equals("Prestissimo"))||(tempo.equals("Vivace"))||(tempo.equals("Allegretto"))) {
				print +="Part " + (piece.getSize()-2+i) +":";
				for (Score score : part.getPart()) {
					if((score.getType().equals("C"))||(score.getType().equals("D"))||(score.getType().equals("E"))) {
						print += " " + score.getType();
					}
					else {
						print += " X";
					}
				}
				print += " -- " + tempo + System.lineSeparator();
			}
			
		}
		if(print.equals("")) {
			return "";
		}
		return "Drummer Played"+System.lineSeparator()+print;
	}

}
