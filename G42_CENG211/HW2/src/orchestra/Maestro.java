package orchestra;

public class Maestro {

	public String partsTempo(Part part) {
		double beatsCount = 0;
		for(int i=0; i<part.getSize(); i++) {
			 beatsCount += part.getScore(i).getBeat();
		}
		if(beatsCount < 8) {
			return "Prestissimo";
		}else if(8 <= beatsCount && beatsCount < 16) {
			return "Vivace";
		}else if(16 <= beatsCount && beatsCount < 18) {
			return "Allegretto";
		}else if(18 <= beatsCount && beatsCount < 22) {
			return "Moderato";
		}else if(22 <= beatsCount && beatsCount < 23) {
			return "Adagietto";
		}else if(23 <= beatsCount && beatsCount < 24) {
			return "Andante";
		}else if(24 <= beatsCount && beatsCount < 27) {
			return "Larghetto";
		}else if(27 <= beatsCount && beatsCount < 29) {
			return "Lento";
		}else if(29 <= beatsCount && beatsCount < 33) {
			return "Grave";
		}else if(33 <= beatsCount && beatsCount < 37) {
			return "Larghissimo";
		}else {
			System.out.println("INVALID TEMPO");
			return null;
		}	
	}
	
	public String changeInTempo(Piece piece){
		double beatsCount = 0;
		for(int i=0; i<piece.getSize(); i++) {
			Part part = piece.getPart(i);
			for(int j=0; j<part.getSize(); j++) {
				 beatsCount += part.getScore(j).getBeat();
			}
		}
		if(beatsCount < 83) {
			return "Letando";
		}else if(83 <= beatsCount && beatsCount < 125) {
			return "Ritenuto";
		}else if(125 <= beatsCount && beatsCount < 132) {
			return "Stretto";
		}else if(132 <= beatsCount && beatsCount < 152) {
			return "Accelerando";
		}else {
			
			System.out.println("INVALID TEMPO" + beatsCount);
			return null;
		}
			
		

	}
}
