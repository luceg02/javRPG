/**
 * Main game class that manages the game loop and user interactions
 */

import java.util.Scanner;

public class Game {
    private final Dungeon dungeon;
    private final Character player;
    private final Shop shop;
    private final Scanner scanner;
    private boolean gameRunning;

    private static final String INVALID_INPUT_MESSAGE = "Invalid input! Please try again.";
    private static final int DUNGEON_SIZE = 8;

    public Game() {
        scanner = new Scanner(System.in);
        shop = new Shop();
        gameRunning = true;

        showWelcome();
        player = createCharacter();
        showCharacterInfo();
        initialShop();
        waitForEnter();
        dungeon = new Dungeon(DUNGEON_SIZE, DUNGEON_SIZE, player);

        startGame();
    }

    private void showWelcome() {
        System.out.println("========================================");
        System.out.println("         Welcome to Dungeon RPG");
        System.out.println("========================================");
        System.out.println("\nGame Objectives:");
        System.out.println("- Explore the dungeon");
        System.out.println("- Fight monsters and destroy obstacles");
        System.out.println("- Reach the exit (E) in the bottom right");
        System.out.println("\nControls:");
        System.out.println("W: Move Up");
        System.out.println("S: Move Down");
        System.out.println("A: Move Left");
        System.out.println("D: Move Right");
        System.out.println("\nMap Legend:");
        System.out.println("P: Your Position");
        System.out.println("M: Monster");
        System.out.println("O: Obstacle");
        System.out.println("E: Exit");
        System.out.println(".: Empty Space");
        System.out.println("========================================\n");
    }

    private Character createCharacter() {
        System.out.println("Character Creation");
        System.out.println("-----------------");

        String name = getValidName();
        int classChoice = getValidClassChoice();

        return switch (classChoice) {
            case 1 -> new Mage(name);
            case 2 -> new Elf(name);
            case 3 -> new Warrior(name);
            default -> new Warrior(name);
        };
    }

    private String getValidName() {
        String name;
        do {
            System.out.println("Enter your character name (3-20 characters):");
            name = scanner.nextLine().trim();
        } while (name.length() < 3 || name.length() > 20);
        return name;
    }

    private int getValidClassChoice() {
        while (true) {
            try {
                System.out.println("\nChoose your class:");
                System.out.println("1. Mage    - HP: 80  | Mana: 150 | Special: Magic damage bonus");
                System.out.println("2. Elf     - HP: 90  | Mana: 100 | Special: 20% double attack");
                System.out.println("3. Warrior - HP: 120 | Mana: 50  | Special: 10% critical hit x3");

                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
                System.out.println(INVALID_INPUT_MESSAGE);
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private void showCharacterInfo() {
        System.out.println("\nCharacter Created Successfully!");
        System.out.println("-----------------------------");
        System.out.println("Name: " + player.getName() + "the" +player.getClass().getSimpleName()) ;
        System.out.println("HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("Mana: " + player.getMana() + "/" + player.getMaxMana());
        System.out.println("Starting Gold: " + player.getMoney());
    }

    private void initialShop() {
        System.out.println("\nWelcome to the Shop!");
        System.out.println("Buy your first weapon before starting your adventure.");
        shop.enter(player);
    }

    private void waitForEnter() {
        System.out.println("\nPress ENTER to start your adventure...");
        scanner.nextLine();
    }

    private void startGame() {
        while (gameRunning && !player.isDead() && !dungeon.isCompleted()) {
            gameLoop();
        }
        showGameEndMessage();
    }

    private void gameLoop() {
        dungeon.displayMap();
        System.out.println("\nAction (W/A/S/D to move, 'menu' for main menu):");
        String input = scanner.nextLine().toLowerCase();

        if (input.equals("menu")) {
            showMenu();
        } else if (input.length() == 1 && "wasd".contains(input)) {
            dungeon.movePlayer(input.charAt(0));
        } else {
            System.out.println(INVALID_INPUT_MESSAGE);
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Resume Game");
            System.out.println("2. Show Inventory");
            System.out.println("3. Visit Shop");
            System.out.println("4. Show Controls");
            System.out.println("5. Exit Game");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> { return; }
                    case 2 -> player.displayInventory();
                    case 3 -> shop.enter(player);
                    case 4 -> showControls();
                    case 5 -> {
                        gameRunning = false;
                        return;
                    }
                    default -> System.out.println(INVALID_INPUT_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private void showControls() {
        System.out.println("\n=== CONTROLS ===");
        System.out.println("Movement:");
        System.out.println("W: Move Up");
        System.out.println("S: Move Down");
        System.out.println("A: Move Left");
        System.out.println("D: Move Right");
        System.out.println("\nOther Controls:");
        System.out.println("Type 'menu': Open main menu");
    }

    private void showGameEndMessage() {
        if (dungeon.isCompleted()) {
            System.out.println("\n🎉 CONGRATULATIONS! 🎉");
            System.out.println("You successfully escaped the dungeon!");
            System.out.println("\nFinal Stats:");
            System.out.println("XP Gained: " + player.getXp());
            System.out.println("Gold Remaining: " + player.getMoney());
            System.out.println("HP Remaining: " + player.getHp() + "/" + player.getMaxHp());
        } else if (player.isDead()) {
            System.out.println("\n💀 GAME OVER 💀");
            System.out.println("You have fallen in the dungeon...");
            System.out.println("XP Gained: " + player.getXp());
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}