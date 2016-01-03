package util;

/**
 * Created by Shukla, Sachin. on 12/26/15.
 */
public class BinarySearchTree {

    private Node root = null;

    public BinarySearchTree(){

    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(2);
        bst.insert(4);
        bst.insert(46);
        bst.insert(454);
        bst.insert(43);
        bst.insert(78);
        bst.insert(30);
        bst.insert(12);
        bst.insert(45);

        System.out.println("Min = "+bst.findMin(bst.getRoot()).getData());
        System.out.println("Max = "+bst.findMax(bst.getRoot()).getData());

        bst.inOrderTraverse(bst.getRoot());

        bst.remove(46, bst.getRoot());

        bst.inOrderTraverse(bst.getRoot());
    }


    public Node findMin(Node e){
        if(e.getLeft() == null){
            return e;
        }
        return findMin(e.getLeft());
    }
    public Node findMax(Node e){
        if(e.getRight() == null){
            return e;
        }
        return findMax(e.getRight());
    }

    public  void insert(int e){
        Node n = new Node();
        n.setData(e);
        insert(n, root);
    }
    private  void insert(Node e, Node p){
        if(root == null){
            root = e;
            return;
        }

        if(e.getData() < p.getData()){
            if(p.getLeft() == null){
                p.setLeft(e);
                System.out.println("Node inserted successfully...left");
                return;
            }
            insert(e, p.getLeft());
        }else if(e.getData() > p.getData()){
            if(p.getRight() == null){
                p.setRight(e);
                System.out.println("Node inserted successfully...right");
                return;
            }
            insert(e, p.getRight());
        }else{
            System.out.println("elem not added, its already there..");
        }
    }

    public void inOrderTraverse(Node n){
        if(n.getLeft() != null){
            inOrderTraverse(n.getLeft());
        }

        System.out.println(n.getData());

        if(n.getRight() != null){
            inOrderTraverse(n.getRight());
        }
    }

    public void remove(int e, Node n){

        if(n == null){
            System.out.println("Element with value - "+e+", is not found in tree..Not removed..");
            return;
        }
        if(e < n.getData()){
            remove(e, n.getLeft());
        }else if (e > n.getData()){
            remove(e, n.getRight());
        }else{
            System.out.println("Elem found in tree, going to be removed..");
            if(n.isLeafNode()){
                n.getParent().remove(n);
                System.out.println("Elem was a leave node, Removed...");
                return;
            }
            if(n.hasOnlyOneChild()){
                    Node childNode = (n.getLeft() != null) ? n.getLeft() : n.getRight();
                if(n.isLeftChildOrParent()){
                    n.getParent().setLeft(childNode);
                }else if(n.isRightChildOrParent()){
                    n.getParent().setRight(childNode);
                }
                System.out.println("Elem was having only ONE child node, Removed...");
                return;
            }
            else if(n.hasTwoChildren()){
                Node maxFromLeft = findMax(n.getLeft());
                if(n.getLeft().getRight() != null){
                    maxFromLeft.getParent().setRight(maxFromLeft.getLeft()); //max node won't have right as right would be the max.
                    maxFromLeft.setLeft(n.getLeft());
                }

                maxFromLeft.setRight(n.getRight());
                if(n.isRightChildOrParent()){
                    n.getParent().setRight(maxFromLeft);
                }else if (n.isLeftChildOrParent()){
                    n.getParent().setLeft(maxFromLeft);
                }


                n.setRight(null);
                n.setLeft(null);

                System.out.println("Elem was having Two children node, Removed...");
                return;
            }
            return;
        }
    }


    public static class Node{
        int data = 0;
        Node left = null;
        Node right = null;
        Node parent = null;

        public boolean isLeftChildOrParent(){
         if(this.getParent().getLeft() == this){
             return true;
         }
            return false;
        }
        public boolean isRightChildOrParent(){
            if(this.getParent().getRight() == this){
                return true;
            }
            return false;
        }
        public boolean remove(Node e){
            if(this.getLeft() == e){
                this.setLeft(null);
                return true;
            }
            if(this.getRight() == e){
                this.setRight(null);
                return true;
            }
            return false;
        }
        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
            if(this.getLeft() != null){
                this.getLeft().setParent(this);
            }

        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
            if(this.getRight() != null){
                this.getRight().setParent(this);
            }

        }

        public boolean isLeafNode(){
            return (this.getLeft() == null) && (this.getRight() == null);
        }

        public boolean hasOnlyOneChild(){
            boolean a = (this.getLeft() == null) && (this.getRight() != null);
            boolean b = (this.getLeft() != null) && (this.getRight() == null);
            return a || b;
        }
        public boolean hasTwoChildren(){
            return (this.getLeft() != null) && (this.getRight() != null);
        }
    }
}
