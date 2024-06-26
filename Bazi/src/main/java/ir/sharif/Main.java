package ir.sharif;

import org.json.JSONObject;

class CardNameConverter {
    public static String convertCardName(String cardName) {
        return cardName.replace(" ", "_").replace(":", "").toUpperCase();
    }

    public static void convertAndPrintCardNames(String[] cardNames) {
        for (String cardName : cardNames) {
            String convertedName = convertCardName(cardName);
            System.out.println(convertedName);
        }
    }

    public static void main(String[] args) {
        String[] cardNames = {
                "Blueboy Lugos",
                "Clan Tordarroch Armorsmith",
                "Holger Blackhand",
                "Iorveth",
                "Filavandrel",
                "Mahakaman Defender",
                "Barclay Els",
                "Blue Stripes Commando",
                "Dethmold",
                "Keira Metz",
                "Sheldon Skaggs",
                "Trebuchet",
                "Shilard Fitz-Oesterlen",
                "Assire var Anahid",
                "Heavy Zerrikanian Fire Scorpion",
                "Albrich",
                "Letho of Gulet",
                "Rainfarn",
                "Imlerith",
                "Crone: Brewess",
                "Frightener",
                "Botchling",
                "Celaeno Harpy",
                "Gargoyle",
                "Skellige Storm",
                "Clear Weather",
                "Scorch",
                "Commander’s horn",
                "Cow",
                "Geralt of Rivia",
                "Vesemir"
        };

        convertAndPrintCardNames(cardNames);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}