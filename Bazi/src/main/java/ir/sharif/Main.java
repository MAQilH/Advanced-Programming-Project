package ir.sharif;

import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;
import ir.sharif.utils.FileSaver;
import ir.sharif.utils.Random;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class First{
        private Integer integer = 34;

        public First(){
        }

        public void print(){
            System.out.println(integer);
        }

        public void setInteger(int x){
            integer = x;
        }

        @Override
        public int hashCode() {
            return integer;
        }
    }

public class Main {
    public static void main(String[] args) {

        HashMap<Integer, First> hashMap = new HashMap<>();
        ArrayList<First> arrayList = new ArrayList<>();
        hashMap.put(2, new First());
        First first = hashMap.get(2);
        first.setInteger(56);
        first.print();
        hashMap.get(2).print();

        arrayList.add(new First());
        First sec = arrayList.get(0);
        sec.print();
        sec.setInteger(4);
        arrayList.get(0).print();
    }
}