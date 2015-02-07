
package structures;

import java.util.*;

public class RosterIterator<K> implements Iterator<K>
{
        private Node<K> curr;
        private Roster<K> roster;
        
        public RosterIterator(Roster<K> roster){
            this.roster = roster;
            this.curr = roster.getHead();
        }

        public RosterIterator(Roster<K> roster, K item){
            this.roster = roster;
            this.curr = roster.get(item);
            if (item == null){
                this.curr = roster.getHead();
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

        public boolean set(K item){
            // set iterator such that calling .next() will return item
            Node<K> node = roster.get(item);
            if (node == null){
                return false;
            }
            this.curr = node.prev();
            return true;
        }

        public void remove(){
            if (this.curr == null){
                return;
            }
            roster.remove(this.curr.get());
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

