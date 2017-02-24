package mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
public class TermPos{
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
    public String toString() {
        return "TermPos{" +
                "id=" + id +
                ", pos=" + pos +
                '}';
    }
}
