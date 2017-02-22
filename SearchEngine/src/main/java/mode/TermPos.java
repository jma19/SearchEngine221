package mode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by junm5 on 2/22/17.
 */
public class TermPos {
    //document id
    private int id;
    private Set<Integer> pos;

    public TermPos() {
        super();
    }

    public TermPos(int id) {
        this.id = id;
        pos = new HashSet();
    }

    public void addPos(Integer pos) {
        this.pos.add(pos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getPos() {
        return pos;
    }

    public void setPos(Set<Integer> pos) {
        this.pos = pos;
    }
}
