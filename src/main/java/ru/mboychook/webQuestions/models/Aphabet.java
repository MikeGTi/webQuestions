package ru.mboychook.webQuestions.models;

import java.util.Iterator;

class Alphabet implements Iterable<String> {

    private char start;
    private char end;

    public Alphabet(char start, char end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Iterator<String> iterator() {
        return new AlphabetIterator(start, end);
    }

    static class AlphabetIterator implements Iterator<String> {

        private String start;

        private String current;
        private String end;

        public AlphabetIterator(char start, char end) {
            this.current = String.valueOf(--start);
            this.start = String.valueOf(start);
            this.end = String.valueOf(end);
        }

        @Override
        public boolean hasNext() {
            return (current.charAt(0) < end.charAt(0));
        }

        @Override
        public String next() {
            if (current.equals(end)){
                this.current = start;
            }
            char nextChar = current.charAt(0);
            return this.current=String.valueOf(++nextChar);
        }

        /*public static void main (String[] arg){
            for (String str:new Alphabet('а', 'я')){
                System.out.print(str + " ");
            }
        }*/
    }
}
