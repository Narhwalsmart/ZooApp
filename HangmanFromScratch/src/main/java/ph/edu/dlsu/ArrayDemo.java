package ph.edu.dlsu;

public class ArrayDemo {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 1, 1, 3, 4, 2};
        printOccurence(numbers, 8);
    }

    public static void printOccurence(int[] numbers, int number) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                count++;
            }
        }
        System.out.println(number + " occurs " + count + " times");
    }
}
