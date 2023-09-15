package orchestra;

public class Score {

	private double beat;
	private String type;
	
	public Score(double beat, String type) {
		setBeat(beat);
		setType(type);
	}

	public double getBeat() {
		return beat;
	}

	public void setBeat(double beat) {
		this.beat = beat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Score [beat=").append(beat).append(", type=").append(type).append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		Score other = (Score) obj;
		if (other == null) {
			return false;
		}
		if ((this.beat == other.getBeat())&&(this.type.equals(other.getType()))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
}
