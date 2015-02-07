
package structures;

public class Node<T>{
    private T member;
    private Node<T> nextNode;
    private Node<T> prevNode;

    public Node(T member){
        this.member = member;
        this.nextNode = this;
        this.prevNode = this;
    }
    public Node(T member, Node<T> next, Node<T> prev){
        this.member = member;
        this.nextNode = next;
        this.prevNode = prev;
    }

    public Node<T> next(){
        return this.nextNode;
    }

    public Node<T> prev(){
        return this.prevNode;
    }

    public void setNext(Node<T> node){
        this.nextNode = node;
    }

    public void setPrev(Node<T> node){
        this.prevNode = node;
    }

    public T get(){
        return this.member;
    }

    public void set(T item){
        this.member = item;
    }
}

