/**
 * Created by nadav vitri on 26/04/2017.
 */
public class test {

    public static void main(String[] args){
//        String[] thisIsAStringArray = new String[17];
//        thisIsAStringArray[0] = "AAA";
//        thisIsAStringArray[1] = "BBB";
//        thisIsAStringArray[2] = "CCC";
//        thisIsAStringArray[3] = "DDD";
//        thisIsAStringArray[4] = "EEE";
//        thisIsAStringArray[5] = "FFF";
//        thisIsAStringArray[6] = "GGG";
//        thisIsAStringArray[7] = "HHH";
//        thisIsAStringArray[8] = "JJJ";
//        thisIsAStringArray[9] = "KKK";
//        thisIsAStringArray[10] = "LLL";
//        thisIsAStringArray[11] = "PtPP";
//        thisIsAStringArray[12] = "EEyE";
//        thisIsAStringArray[13] = "PePP";
//        thisIsAStringArray[14] = "PrPP";
//        thisIsAStringArray[15] = "LLL";
//        thisIsAStringArray[16] = "PqPP";
        ClosedHashSet ar = new ClosedHashSet(0.7f, 0.1f);
        System.out.println("first capacity: " + ar.capacity());
        ar.add("Hi");
        ar.add("Hello");
        ar.add("By");
//        ar.add("By");
        ar.add("Caio");
//        ar.add("Hi");
        ar.add("1");
        ar.add("2");
        ar.add("3");
        ar.add("4");
        ar.add("5");
        ar.add("6");
        ar.add("7");
        ar.add("8");
        ar.add("8");
//        ar.add("p");
//        ar.add("q");
        // total: 17 elements
        System.out.println("capacity after adding: " + ar.capacity());
        System.out.println("element's before deleting: " + ar.size());
//        ar.delete("h");
//        ar.delete("i");
//        ar.delete("j");
//        ar.delete("k");
//        ar.delete("l");
//        ar.delete("m");
//        ar.delete("n");
//        ar.delete("o");
//        ar.delete("p");
        // total: 9 elements

//        ar.delete("q");
        System.out.println("capacity after deleting: " + ar.capacity());
        System.out.println("elements in hash table: " + ar.size());

//        int i = 0;
//        int h = 3 + i;
//        while (h < 5){
//            System.out.println(h);
//            i ++;
//        }


    }

}
