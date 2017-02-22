package mode;

import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
public class Index implements Comparable<Index>{
    private String term;
    private List<TermPos> posList;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<TermPos> getPosList() {
        return posList;
    }

    public void setPosList(List<TermPos> posList) {
        this.posList = posList;
    }

    @Override
    public int compareTo(Index o) {
        return this.term.compareTo(o.getTerm());
    }
}

