package oop;

import oop.ex4.data_structures.AvlTree;
import oop.ex4.data_structures.MyNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BTreePrinter {
    int[] array;
    //
    public static void main(String[] args) {

        AvlTree tree = new AvlTree();

        tree.add(5);
        System.out.println(tree.add(5));
//        tree.add(20);
//        tree.delete(100);
//        System.out.println("bi");
//        tree.add(100);
//        tree.add(50);
//        tree.add(150);
//        tree.add(25);
//        tree.add(40);
//        tree.add(250);
//        tree.add(120);
//        tree.add(110);
//        tree.add(130);
//        tree.add(300);

        System.out.println(tree.size());
        System.out.println(tree.contains(150));

        printNode(tree.getTheRott());
    }

    public static void printNode(MyNode root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<MyNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<MyNode> newNodes = new ArrayList<>();
        for (MyNode node : nodes) {
            if (node != null) {
                System.out.print(node.getData());
                newNodes.add(node.getLeftNode());
                newNodes.add(node.getRightNode());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeftNode() != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRightNode() != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(MyNode node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getLeftNode()), BTreePrinter.maxLevel(node.getRightNode())) + 1;
    }

    private static boolean isAllElementsNull(List<MyNode> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }



}
