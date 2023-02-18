package order;

public class OrderGenerator {

    public static Order getWithBlack() {
        return new Order("Сергей", "Пашков", "пр. Культуры, 20", "4", "+78004454748", "5", "2023-03-10", "Позвонить за час до приезда", new String[]{"BLACK"});
    }

    public static Order getWithGrey() {
        return new Order("Сергей", "Пашков", "пр. Культуры, 20", "4", "+78004454748", "5", "2023-03-10", "Позвонить за час до приезда", new String[]{"GREY"});
    }

    public static Order getWithBlackAndGrey() {
        return new Order("Сергей", "Пашков", "пр. Культуры, 20", "4", "+78004454748", "5", "2023-03-10", "Позвонить за час до приезда", new String[]{"BLACK", "GREY"});
    }

    public static Order getWithoutColours() {
        return new Order("Сергей", "Пашков", "пр. Культуры, 20", "4", "+78004454748", "5", "2023-03-10", "Позвонить за час до приезда", null);
    }
}