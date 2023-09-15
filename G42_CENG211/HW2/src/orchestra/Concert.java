package orchestra;

import java.util.ArrayList;

import fileaccess.FileIn;

public class Concert {
	
	private Maestro maestro;
	private ArrayList<Musician> musicians;

	public Concert() {
		maestro = new Maestro();
		musicians = new ArrayList<>();
		musicians.add(new Violinist());
		musicians.add(new Celloist());
		musicians.add(new Violist());
		musicians.add(new Flutist());
		musicians.add(new Drummer());
		musicians.add(new BellPlayer());
	}
	public void Start() {	
		play("piece1.txt");
		play("piece2.txt");
		play("piece3.txt");
		play("piece4.txt");
	}
	
	
	private void play(String fileName) {
		FileIn pieceInput = new FileIn();
		Piece piece = createPiece(pieceInput.readPiece(fileName));
		String pieceId = fileName.substring(5,fileName.length()-4);
		System.out.println("Piece " + pieceId + " is played " + piece.getTempoChange());
		
		for (Musician musician: musicians) {	
			if(!musician.playPiece(piece).equals("")) {
				System.out.println(musician.playPiece(piece));
			}
		}
		System.out.println("--------------------------------------------------------------");
	}
	
	private Piece createPiece(String[][] piece2dArr) {
		Part[] pieceArr = new Part[piece2dArr.length];
		for (int i=0; i<piece2dArr.length; i++) {
			Part part = new Part(piece2dArr[i]);
			part.setTempo(maestro.partsTempo(part));
			pieceArr[i] = part;
		}
		Piece piece = new Piece(pieceArr);
		piece.setTempoChange(maestro.changeInTempo(piece));
		return piece;
	}
	
}
