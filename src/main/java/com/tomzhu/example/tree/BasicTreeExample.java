package com.tomzhu.example.tree;

import com.tomzhu.tree.BasicTree;

/**
 * Created by tomzhu on 17-7-17.
 */
public class BasicTreeExample {


    /**
     * create a tree :
     *                               5
     *                         /     \      \
     *                        3      9      11
     *                      /  \    / \     / \
     *                     2   4   7  10   10 12
     *                            /
     *                           6
     * and then print the tree.
     * @param args
     */
    public static void main(String args[]) {
        // initialize the tree.
        BasicTree<Integer> basicTree = new BasicTree<Integer>(5);
        Integer[] layer1 = {3 , 9 , 11};
        basicTree.addChildren(basicTree.getRoot() , layer1);
        basicTree.getFirstChild(basicTree.getRoot()).addChild(2);
        basicTree.getFirstChild(basicTree.getRoot()).addChild(4);
        Integer[] layer2 = {7 , 10};
        basicTree.getChild(basicTree.getRoot() , 1).addChildren(layer2);
        basicTree.getChild(basicTree.getRoot() , 1).getFirstChild().addChild(6);
        layer2 = new Integer[]{10 , 12};
        basicTree.getLastChild(basicTree.getRoot()).addChildren(layer2);

        // print the tree.
        System.out.println("root : " + basicTree.getRoot().getValue());
        System.out.println("layer1 : " + basicTree.getRoot().getFirstChild().getValue()
         + " , " + basicTree.getRoot().getChild(1).getValue()
         + " , " + basicTree.getRoot().getLastChild().getValue()
        );

        System.out.println("layer2 : " + basicTree.getRoot().getFirstChild().getFirstChild().getValue()
        + " , " + basicTree.getRoot().getFirstChild().getLastChild().getValue() + " | "
                + basicTree.getRoot().getChild(1).getFirstChild().getValue() + " , "
                + basicTree.getRoot().getChild(1).getLastChild().getValue() + " | "
                + basicTree.getRoot().getChild(2).getFirstChild().getValue() + " , "
                + basicTree.getRoot().getChild(2).getLastChild().getValue()
        );

        System.out.println("layer3 : " +
                basicTree.getRoot().getChild(1).getFirstChild().getFirstChild().getValue());
    }

}
