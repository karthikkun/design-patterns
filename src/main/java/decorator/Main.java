package decorator;

public class Main {
    /**
     * usecase
     * Hotel base room charge per night
     * decorate with extra services laundry, room service, food etc..
     */
    public static void main(String[] args) {
        Burger burger = new ZingerBurger();
        // decorator = runtime polymorphism
        burger = new ExtraCheeseBurger(burger);
        // decorator = runtime polymorphism
        burger = new ExtraMayoBurger(burger);
        System.out.println(burger.getDescription());
        System.out.println(burger.getCost());
    }
}
