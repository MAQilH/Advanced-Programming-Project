package ir.sharif;

import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;
import ir.sharif.utils.FileSaver;
import ir.sharif.utils.Random;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

    class First{
        private Integer integer = 34;

        public First(){
        }

        private void print(){
            System.out.println(integer);
        }

        private void setInteger(int x){
            integer = x;
        }

        @Override
        public int hashCode() {
            return integer;
        }
    }

public class Main {
    public static void main(String[] args) {
        First first = new First();
        System.out.println(first.hashCode());
        System.out.println(Random.generateNewToken());

        ArrayList<Card> arrayList = new ArrayList<>();
        arrayList.add(CardTypes.ARACHAS.getInstance());
        arrayList.add(CardTypes.DOL_BLATHANNA_ARCHER.getInstance());
        arrayList.add(CardTypes.MARDOEME.getInstance());
//        System.out.println(Random.getRandFromArrayList(arrayList).getName());
    }
}