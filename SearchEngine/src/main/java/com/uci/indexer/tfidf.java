import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import java.util.Iterator;

class TermPos{

	public int id;
	public List<Integer> pos;
	public double score;

public TermPos(int id) {
        this.id = id;
        this.pos = new ArrayList<Integer>();
        this.score = score;
    }
}

public class tfidf{
	
public static 
void calculateTFScore(int docsize, TreeMap<String, List<TermPos>> map){
	TreeMap indexMap = map;
	int size = docsize;
	Iterator<Entry<String, List<TermPos>>> iterator =  indexMap.entrySet().iterator();
	while(iterator.hasNext()) {
		Entry<String, List<TermPos>> entry = iterator.next();
		int df = entry.getValue().size();
		List<TermPos> list = entry.getValue();
		for(TermPos tp : list){
			int tf = tp.pos.size();
			double score = (1 + Math.log10(tf)) * Math.log10((double)size/df);
			tp.score = score;
		}
	}
}

public static void main(String[] args){
	TreeMap<String, List<TermPos>> indexMap = new TreeMap<>();
	TermPos t = new TermPos(1);
	t.id = 1;
	t.pos = new ArrayList<Integer>(Arrays.asList(3,4));
	t.score = 0;
	List<TermPos> al = new ArrayList<TermPos>();
	al.add(t);
	//indexMap.put("hello", Lists.new ArrayList(new TermPos({"id":1,"pos":[3],"score":0},{"id":2,"pos":[3],"score":0})));
	indexMap.put("a", al);
	int docsize = 1000;
	calculateTFScore(docsize, indexMap);
	System.out.println(indexMap.get("a").get(0).score);

}
}