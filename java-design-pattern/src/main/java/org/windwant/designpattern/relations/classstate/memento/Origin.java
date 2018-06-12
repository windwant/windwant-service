package org.windwant.designpattern.relations.classstate.memento;

/**
 * Created by aayongche on 2016/9/23.
 */
public class Origin {
    private String state;

    private int index;

    public Memento setState(String state) {
        this.state = state;
        index++;
        return createMemento();
    }

    protected Memento createMemento(){
        return new SelfMemento(state, index);
    }

    public void reverseMemento(Memento memento){
        SelfMemento selfMemento = (SelfMemento) memento;
        this.state = selfMemento.getState();
        this.index = selfMemento.getIndex();
    }

    @Override
    public String toString() {
        return "Origin{" +
                "state='" + state + '\'' +
                ", index=" + index +
                '}';
    }

    private class SelfMemento implements Memento{
        private String state;

        private int index;

        public SelfMemento() {
        }

        public SelfMemento(String state, int index) {
            this.state = state;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
