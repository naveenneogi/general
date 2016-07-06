package com.company;

import java.util.*;
import java.util.HashMap;

/**
 * Created by naveenmurthy on 7/2/16.
 */
public class Tree<T extends Comparable<T>>  {

    private class Node<T extends Comparable<T>> {
        T data;
        Node<T> left, right;

        public Node(T val) {
            data = val;
            left = right = null;
        }
    }

    private Node<T> root;

    public Tree() {
        root = null;
    }

    public Tree(T val) {
        root = new Node<T>(val);
    }

    public void addIteratively(T val) throws Exception {
        Node<T> newNode = new Node<T>(val);
        Node<T> node = root;
        Node<T> parent = null;
        boolean left=false, right=false;
        //System.out.println("add " + val);
        while(node != null) {
            parent = node;
            left = right = false;
            //System.out.println(node.data);
            if(val.compareTo(node.data) <= 0) {
                node = node.left;
                left = true;
                //System.out.println("LEFT");
            } else {
                node = node.right;
                right = true;
                //System.out.println("RIGHT");
            }
        }

        if (root == null || parent == null) throw new Exception("Exception while adding node for " + val);

        if (left) {
            parent.left = newNode;
            //System.out.println(val + " LEFT OF " + parent.data);
        }
        else if (right) {
            parent.right = newNode;
            //System.out.println(val + " RIGHT OF " + parent.data);

        }
        return;
    }

    public void add(T val) throws Exception {
        if (root == null) root = new Node<T>(val);
        else addRecursively(root, val);
    }

    private void addRecursively(Node<T> node, T val) throws Exception {

        if (node == null) throw new Exception("Exception while adding node for " + val);
        if (val.compareTo(node.data) <= 0) {
            if(node.left == null) node.left = new Node<T>(val);
            else addRecursively(node.left, val);
        } else {
            if(node.right == null) node.right = new Node<T>(val);
            else addRecursively(node.right, val);
        }
    }

    public void printInOrder() throws Exception {
        inOrderTraversal(root);
    }

    private void mirrorTraversal(Node<T> node) {
        if (node == null) return;

        mirrorTraversal(node.left);
        mirrorTraversal(node.right);

        Node<T> temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    public void mirror() throws Exception {
        mirrorTraversal(root);
    }

    private void inOrderTraversal(Node<T> node) throws Exception {
        if (node == null) return;

        inOrderTraversal(node.left);
        System.out.println(node.data);
        inOrderTraversal(node.right);
    }

    public void levelOrder() throws Exception {
        levelOrderTraversal(root);
    }

    private void levelOrderTraversal(Node<T> node) {
        if(node == null) return;

        LinkedList<Node<T>> que= new LinkedList<Node<T>>();
        que.addLast(node);
        while(!que.isEmpty()) {
            Node<T> qNode = que.removeFirst();
            System.out.println(qNode.data);
            if(qNode.left != null) que.addLast(qNode.left);
            if(qNode.right!= null) que.addLast(qNode.right);
        }
    }

    public void columnOrderTraversal() throws Exception {

        //hashmap to maintain node and its column index, instead we could just alter the Node<T> structure to also carry the col index
        HashMap<Node<T>, Integer> colIndexMap = new HashMap<>();
        //hashmap to maintain the reverse index of col index to nodes (or nodes.data)
        HashMap<Integer, List<T>> colIndexReverseMap = new HashMap<>();
        //que to hold nodes while we loop through all the nodes and calcuate their col indexes
        LinkedList<Node<T>> que= new LinkedList<Node<T>>();

        //add root into the colIndex and que to begin with
        colIndexMap.put(root, 0);
        que.addLast(root);

        while(!que.isEmpty()) {
            Node<T> qNode = que.removeFirst();

            // append to the reverse map: col index->list of nodes
            Integer colIndex = colIndexMap.get(qNode);
            if (!colIndexReverseMap.containsKey(colIndex)) {
                colIndexReverseMap.put(colIndex, new ArrayList<T>(Arrays.asList(qNode.data)));
            } else {
                List<T> list = colIndexReverseMap.get(colIndex);
                list.add(qNode.data);
                colIndexReverseMap.put(colIndex, list);
            }

            //System.out.println(colIndex + " -> " + qNode.data);

            //loop through all nodes in bfs order, while also updating their col indexes
            if(qNode.left != null) {
                colIndexMap.put(qNode.left, colIndex-1);
                que.addLast(qNode.left);
            }
            if(qNode.right!= null) {
                colIndexMap.put(qNode.right, colIndex+1);
                que.addLast(qNode.right);
            }
        }

        // walk through the reverse index to get the nodes in column order
        colIndexReverseMap.forEach((Integer colIndex, List<T> list) -> {
            System.out.println(colIndex + " -> " + list);
        });

    }

}
