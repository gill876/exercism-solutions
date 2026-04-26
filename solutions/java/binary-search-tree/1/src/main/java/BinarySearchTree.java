import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    void insert(T value) {
        if (root == null) {
            this.root = new Node<T>(value);
        } else {
            this.root.insert(value);
        }
    }

    List<T> getAsSortedList() {
        List<T> output = new ArrayList<>();
        traverse(this.root, output);

        return output;
    }

    List<T> getAsLevelOrderList() {
        List<T> output = new ArrayList<>();
        if (root == null) {
            return output;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            output.add(node.getData());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }

        return output;
    }

    private void traverse(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        traverse(node.getLeft(), result);
        result.add(node.getData());
        traverse(node.getRight(), result);
    }

    Node<T> getRoot() {
        return this.root;
    }

    static class Node<T extends Comparable<T>> {
        private T root;
        private Node<T> lhs;
        private Node<T> rhs;

        public Node(T obj) {
            this.root = obj;
        }

        public void insert(T obj) {
            int compareToVal = this.root.compareTo(obj);

            if (compareToVal == 0) {
                if (this.lhs == null) {
                    this.lhs = new Node<T>(obj);
                } else {
                    this.lhs.insert(obj);
                }
            } else if (compareToVal > 0) {
                if (this.lhs == null) {
                    this.lhs = new Node<T>(obj);
                } else {
                    this.lhs.insert(obj);
                }
            } else if (compareToVal < 0) {
                if (this.rhs == null) {
                    this.rhs = new Node<T>(obj);
                } else {
                    this.rhs.insert(obj);
                }
            } else {
                throw new UnsupportedOperationException("Welp... I dunno man.");
            }
        }

        Node<T> getLeft() {
            return this.lhs;
        }

        Node<T> getRight() {
            return this.rhs;
        }

        T getData() {
            return this.root;
        }
    }
}
