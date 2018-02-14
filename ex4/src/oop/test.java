package oop.ex4.data_structures;
/**
 * Created by nadav vitri on 08/05/2017.
 */
public class test {

    public static void main(String[] args){
        AvlTree ar = new AvlTree();
        ar.add(1);
        System.out.println(ar.getRoot());
        ar.add(4);
//        ar.add(0);
        System.out.println(ar.getRight());
//        ar.add(0);
//        System.out.println(ar.getLeft());
//        System.out.println(ar.contains(2));
        ar.add(6);
//        ar.add(10);
//        System.out.println(ar.contains(5));
    }

}

