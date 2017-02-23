package mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by junm5 on 2/22/17.
 */
public class TermPos implements Comparable<TermPos> {
    //document id
    private int id;
    private List<Integer> pos;

    public TermPos() {
        super();
    }

    public TermPos(int id) {
        this.id = id;
        pos = new ArrayList();
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

    public List<Integer> getPos() {
        return pos;
    }

    public void setPos(List<Integer> pos) {
        this.pos = pos;
    }

    @Override
    public int compareTo(TermPos o) {
        return id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TermPos termPos = (TermPos) o;

        return id == termPos.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
