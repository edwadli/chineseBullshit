
package structures;

import java.util.*;

class Roster<K>
{
    private HashMap<K,Node<K>> hash;
    private Node<K> head;
    
    public Roster(){
        this.hash = new HashMap<K,Node<K>>();
    }

    public Roster(Collection<K> ks){
        for (K item: ks){
            this.add(item);
        }
    }

    public void add(K item){
        if (this.head == null){
            this.head = this.hash.put(item, new Node<K>(item));
        }
        else {
            this.head = this.hash.put(item, new Node<K>(item, head, head.prev()));
        }
    }

    public K remove(K item){
        Node<K> node = this.hash.remove(item);
        if (node == null){
            return null;
        }
        else if (this.hash.size()==0){
            this.head = null;
            return node.get();
        }

        Node<K> nextNode = node.next();
        Node<K> prevNode = node.prev();
        nextNode.setPrev(prevNode);
        prevNode.setNext(nextNode);
        this.head = nextNode;

        return node.get();
    }

    public void clear(){
        this.hash.clear();
        this.head = null;
    }

    public RosterIterator iterator(){
        return new RosterIterator();
    }

    private class RosterIterator implements Iterator<K>
    {
        private Node<K> curr;
        
        public RosterIterator(){
            this.curr = Roster.this.head;
        }

        public RosterIterator(K item){
            this.curr = Roster.this.hash.get(item);
            if (item == null){
                this.curr = Roster.this.head;
            }
        }

        public K next(){
            if (this.curr == null){
                return null;
            }
            this.curr = this.curr.next();
            return this.curr.get();
        }

        public K prev(){
            if (this.curr == null){
                return null;
            }
            this.curr = this.curr.prev();
            return this.curr.get();
        }

        public boolean setPosition(K item){
            Node<K> node = Roster.this.hash.get(item);
            if (node == null){
                return false;
            }
            this.curr = node;
            return true;
        }

        public void remove(){
            if (this.curr == null){
                return;
            }
            Roster.this.remove(this.curr.get());
            return;
        }

        public boolean hasNext(){
            if (this.curr == null){
                return false;
            }
            else {
                return true;
            }
        }
    }
}

