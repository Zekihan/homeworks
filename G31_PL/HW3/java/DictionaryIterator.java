import java.util.ArrayList;
import java.util.List;

public class DictionaryIterator<E extends Comparable <E>> {

	private List<E> list;

	public DictionaryIterator(List<E> list){
		setList(sort(list));
	}

	public boolean hasNext(){
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public E next(){
		E nextStr = list.get(0);
		list.remove(nextStr);
		return nextStr;
	}
	
	private List<E> sort(List<E> list){
		int n = list.size();
		
		for (int i = 0; i < n; i++){
			for (int j = i + 1; j < n; j++){
				if (list.get(i).compareTo(list.get(j))>0){
					E temp = list.get(i);
					list.set(i,list.get(j));
					list.set(j, temp);
				}
			}
		}
		return list;	
	}

	private void setList(List<E> list) {
		this.list = list;
	}

	//RUN THIS METHOD
	public static void main(String[] args) {
		List<String> inputList = new ArrayList<>();
		inputList.add("pencil");
		inputList.add("brother");
		inputList.add("apple");
		inputList.add("phone");

		System.out.println("Input List: " + inputList);
		
		DictionaryIterator<String> iterator = new DictionaryIterator(inputList);
		List<String> outputList = new ArrayList<>();

		while(iterator.hasNext()) {
			outputList.add(iterator.next());
		}
		System.out.println("Output List: " + outputList);		
	}
	
}