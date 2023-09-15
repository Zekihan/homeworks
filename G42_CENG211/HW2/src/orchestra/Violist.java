package orchestra;

public class Violist extends StringInstrumentMusician{
	
	private Part[] shouldPlay;

	public Violist() {
		super();
	}

	@Override
	public String playPiece(Piece piece) {
		super.playPiece(piece);
		shouldPlay = super.getPlayed();
		String print = "";
		String tempoChange = piece.getTempoChange();
		if ((tempoChange.equals("Ritenuto"))) {
			print +="Part " + "1"+":";
			for (Score score : shouldPlay[0].getPart()) {
				print += " " + score.getType();
			}
			String tempo = shouldPlay[0].getTempo();
			print += " -- " + tempo + System.lineSeparator();
		}
		else {
			for (int i = 0; i<shouldPlay.length; i++ ) {
				Part part = shouldPlay[i];
				print +="Part " + (i+1) +":";
				String tempo = part.getTempo();
				for (Score score : part.getPart()) {
					print += " " + score.getType();
				}
				print += " -- " + tempo + System.lineSeparator();
			}
		}
		if(print.equals("")) {
			return "";
		}
		return "Violist Played"+ System.lineSeparator()+print;
	}
}
