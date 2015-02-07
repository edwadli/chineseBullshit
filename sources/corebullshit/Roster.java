
package structures;

import java.util.*;

public class Roster<K> implements Iterable<K>
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
        Node<K> node;
        if (this.head == null){
            node = new Node<K>(item);
            this.hash.put(item, node);
        }
        else {
            node = new Node<K>(item, this.head, this.head.prev());
            node.prev().setNext(node);
            node.next().setPrev(node);
            this.hash.put(item, node);
        }
        this.head = node;
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

    public boolean contains(K item){
        return this.hash.containsKey(item);
    }

    public void clear(){
        this.hash.clear();
        this.head = null;
    }

    public int size(){
        return this.hash.size();
    }

    public Set<K> getKeys(){
        return this.hash.keySet();
    }

    protected Node<K> getHead(){
        return this.head;
    }

    protected Node<K> get(K item){
        return this.hash.get(item);
    }

    public RosterIterator<K> iterator(){
        return new RosterIterator<K>(this);
    }
    
    public RosterIterator<K> iterator(K item){
        return new RosterIterator<K>(this, item);
    }

}

