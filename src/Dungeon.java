import java.util.Random;
import java.util.Scanner;

/**
 * Dungeon class that manages the game map and movement
 */
public class Dungeon {
    private final GameObject[][] map;
    private int playerX, playerY;
    private final Character player;
    private final int width, height;
    private final Random random;
    private final Scanner scanner;

    private static final double ENTITY_SPAWN_CHANCE = 0.3;
    private static final int COMBAT_XP_REWARD = 100;

    public Dungeon(int width, int height, Character player) {
        this.width = width;
        this.height = height;
        this.player = player;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.map = new GameObject[height][width];
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i != 0 || j != 0) && (i != height-1 || j != width-1)) {
                    if (random.nextDouble() < ENTITY_SPAWN_CHANCE) {
                        map[i][j] = random.nextBoolean() ?
                                new Monster("Goblin", 20, 10) :
                                new Obstacle("Wall", 30);
                    }
                }
            }
        }
        playerX = 0;
        playerY = 0;
    }

    public void displayMap() {
        System.out.println("\n=== DUNGEON MAP ===");
        System.out.printf("Position: [%d,%d] | HP: %d/%d | Mana: %d/%d%n",
                playerY + 1, playerX + 1,
                player.getHp(), player.getMaxHp(),
                player.getMana(), player.getMaxMana());
        System.out.println("------------------");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("P ");
                } else if (i == height-1 && j == width-1) {
                    System.out.print("E ");
                } else if (map[i][j] != null) {
                    System.out.print(map[i][j] instanceof Monster ? "M " : "O ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------");
    }

    public void movePlayer(char direction) {
        int newX = playerX;
        int newY = playerY;

        switch(direction) {
            case 'w' -> newY--; // up
            case 's' -> newY++; // down
            case 'a' -> newX--; // left
            case 'd' -> newX++; // right
        }

        if (isValidPosition(newX, newY)) {
            handleMovement(newX, newY);
        } else {
            System.out.println("You can't move outside the dungeon!");
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void handleMovement(int newX, int newY) {
        GameObject object = map[newY][newX];
        if (object == null) {
            moveToPosition(newX, newY);
        } else if (object instanceof Monster) {
            if (handleCombat((Monster)object, newY, newX)) {
                moveToPosition(newX, newY);
            }
        } else if (object instanceof Obstacle) {
            if (handleObstacle((Obstacle)object, newY, newX)) {
                moveToPosition(newX, newY);
            }
        }
    }

    private void moveToPosition(int newX, int newY) {
        playerX = newX;
        playerY = newY;
    }

    private boolean handleCombat(Monster monster, int y, int x) {
        System.out.println("\n=== COMBAT ===");
        System.out.printf("You encounter a %s! (HP: %d | Attack: %d)%n",
                monster.getName(), monster.getHp(), monster.attack());
        System.out.println("1. Fight");
        System.out.println("2. Run");

        try {
            if (Integer.parseInt(scanner.nextLine()) == 1) {
                return executeCombat(monster, y, x);
            }
            System.out.println("You run away from the fight!");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice! Retreating...");
            return false;
        }
    }

    private boolean executeCombat(Monster monster, int y, int x) {
        while (!monster.isDead() && !player.isDead()) {
            // Player's turn
            int playerDamage = player.attack();
            monster.takeDamage(playerDamage);
            System.out.printf("%nYou deal %d damage!%n", playerDamage);

            if (!monster.isDead()) {
                // Monster's turn
                int monsterDamage = monster.attack();
                player.takeDamage(monsterDamage);
                System.out.printf("Monster deals %d damage!%n", monsterDamage);

                // Status update
                System.out.printf("%nStatus:%nYour HP: %d/%d%nMonster HP: %d/%d%n",
                        player.getHp(), player.getMaxHp(),
                        monster.getHp(), monster.getMaxHp());
            }
        }

        if (!player.isDead()) {
            System.out.printf("%nVictory! The %s has been defeated!%n", monster.getName());
            player.gainXP(COMBAT_XP_REWARD);
            map[y][x] = null;
            return true;
        }
        return false;
    }

    private boolean handleObstacle(Obstacle obstacle, int y, int x) {
        System.out.println("\n=== OBSTACLE ===");
        System.out.printf("You encounter a %s! (HP: %d)%n",
                obstacle.getName(), obstacle.getHp());
        System.out.println("1. Try to destroy it");
        System.out.println("2. Turn back");

        try {
            if (Integer.parseInt(scanner.nextLine()) == 1) {
                return destroyObstacle(obstacle, y, x);
            }
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return false;
        }
    }

    private boolean destroyObstacle(Obstacle obstacle, int y, int x) {
        int damage = player.attack();
        System.out.printf("%nYou attack the obstacle for %d damage!%n", damage);
        obstacle.takeDamage(damage);

        if (obstacle.isDead()) {
            System.out.printf("The %s has been destroyed!%n", obstacle.getName());
            map[y][x] = null;
            return true;
        } else {
            System.out.printf("The obstacle still stands! (HP: %d/%d)%n",
                    obstacle.getHp(), obstacle.getMaxHp());
            return false;
        }
    }

    public boolean isCompleted() {
        return playerX == width-1 && playerY == height-1;
    }
}