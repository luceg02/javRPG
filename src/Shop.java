import java.util.ArrayList;
import java.util.Scanner;

/**
 * Shop class for buying weapons
 */
public class Shop {
    private final ArrayList<Weapon> inventory;
    private final Scanner scanner;

    public Shop() {
        inventory = new ArrayList<>();
        inventory.add(new Sword());
        inventory.add(new Axe());
        inventory.add(new Bow());
        scanner = new Scanner(System.in);
    }

    public void enter(Character player) {
        while(true) {
            System.out.println("\n=== WEAPON SHOP ===");
            System.out.println("Available gold: " + player.getMoney());
            System.out.println("1. View weapons");
            System.out.println("2. Leave shop");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 2) break;
                if (choice == 1) {
                    handleWeaponPurchase(player);
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void handleWeaponPurchase(Character player) {
        displayWeapons();
        System.out.println("Choose a weapon (0 to cancel):");

        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice == -1) return;

            if (choice >= 0 && choice < inventory.size()) {
                Weapon weapon = inventory.get(choice);
                if (player.getMoney() >= weapon.getPrice()) {
                    player.buyWeapon(weapon);
                } else {
                    System.out.println("Not enough gold! You need " +
                            (weapon.getPrice() - player.getMoney()) + " more gold.");
                }
            } else {
                System.out.println("Invalid weapon choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
    }

    private void displayWeapons() {
        System.out.println("\nAvailable weapons:");
        for (int i = 0; i < inventory.size(); i++) {
            Weapon w = inventory.get(i);
            System.out.printf("%d. %-10s (Damage: %-3d | Price: %-3d gold)%n",
                    i + 1, w.getName(), w.getDamage(), w.getPrice());
        }
    }
}