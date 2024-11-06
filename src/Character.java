import java.util.ArrayList;

/**
 * Base class for all player characters
 */
public abstract class Character extends GameObject {
    protected int money;
    protected int xp;
    protected int mana;
    protected int maxMana;
    protected ArrayList<Weapon> inventory;
    protected Weapon equippedWeapon;

    protected static final int STARTING_MONEY = 200;

    public Character(String name, int hp, int mana) {
        super(name, hp);
        this.maxMana = mana;
        this.mana = mana;
        this.money = STARTING_MONEY;
        this.xp = 0;
        this.inventory = new ArrayList<>();
        this.equippedWeapon = new Sword();
        this.inventory.add(this.equippedWeapon);
    }

    public void buyWeapon(Weapon weapon) {
        if (money >= weapon.getPrice()) {
            money -= weapon.getPrice();
            inventory.add(weapon);
            equippedWeapon = weapon;
            System.out.println("Equipped " + weapon.getName() + "!");
        }
    }

    public void heal(int amount) {
        hp = Math.min(hp + amount, maxHp);
    }

    public void restoreMana(int amount) {
        mana = Math.min(mana + amount, maxMana);
    }

    public int attack() {
        return equippedWeapon.getDamage();
    }

    public void gainXP(int amount) {
        xp += amount;
        System.out.println("Gained " + amount + " XP! Total XP: " + xp);
    }

    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getMoney() { return money; }
    public int getXp() { return xp; }

    public void displayInventory() {
        System.out.println("\n=== INVENTORY ===");
        System.out.println("Name: " + name);
        System.out.println("Class: " + this.getClass().getSimpleName());
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Mana: " + mana + "/" + maxMana);
        System.out.println("XP: " + xp);
        System.out.println("Gold: " + money);
        System.out.println("\nEquipped weapon: " + equippedWeapon.getName());
        System.out.println("Current damage: " + equippedWeapon.getDamage());

        if (!inventory.isEmpty()) {
            System.out.println("\nOwned weapons:");
            for (Weapon w : inventory) {
                System.out.println("- " + w.getName() + " (Damage: " + w.getDamage() + ")");
            }
        }
    }
}
