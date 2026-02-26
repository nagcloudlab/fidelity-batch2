package com.example;

class Box implements Iterable<String> {
    private String[] items = new String[10];
    private int count = 0;

    public void addItem(String item) {
        if (count < items.length) {
            items[count] = item;
            count++;
        }
    }

    public String getItem(int index) {
        if (index >= 0 && index < count) {
            return items[index];
        }
        return null;
    }

    @Override
    public java.util.Iterator<String> iterator() {
        return new java.util.Iterator<String>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < count;
            }

            @Override
            public String next() {
                if (currentIndex >= count) {
                    throw new java.util.NoSuchElementException();
                }
                return items[currentIndex++];
            }
        };
    }
}

public class Example4 {

    public static void main(String[] args) {

        Box box = new Box();
        box.addItem("Apple");
        box.addItem("Banana");
        box.addItem("Cherry");

        for (String item : box) {
            System.out.println(item);
        }

    }

}
