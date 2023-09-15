package business;
import java.util.ArrayList;

import dataaccess.MovieSaver;

public class Movie extends RentableItem {

	private String genre;
	private String producer;
	private ArrayList<String> actors;
	
	public Movie(String name, int itemNo, boolean isRented, String genre, String producer, ArrayList<String> actors) {
		super(name, itemNo, isRented);
		setGenre(genre);
		setProducer(producer);
		setActors(actors);
	}
	
	@Override
	public void store(String format) {
		MovieSaver ms = new MovieSaver();
		if(format.equals("json")) {
			ms.saverManyJson(this);
		}else {
			ms.saverXml(this);
		}
		
	}
	

	public String getGenre() {
		return genre;
	}

	public String getProducer() {
		return producer;
	}

	public ArrayList<String> getActors() {
		return actors;
	}

	private void setGenre(String genre) {
		this.genre = genre;
	}

	private void setProducer(String producer) {
		this.producer = producer;
	}

	private void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}

	@Override
	public String getTextToSearchOn(String attribute) {
		String text = "";
		switch(attribute) {
			case "genre":
				text = getGenre();
				break;
			case "producer":
				text =  getProducer();
				break;
			case "name":
				text = getName();
				break;
			case "actor":
				for (String actor: getActors()) {
					text += actor + " ";
				}
				break;
			default:
				text = null;
				break;
		}
		return text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie [genre=").append(genre).append(", producer=").append(producer).append(", actors=")
				.append(actors).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
	

}
