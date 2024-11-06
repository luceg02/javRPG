/**
 * Abstract base class for all weapons in the game
 */
public abstract class Weapon {
    protected final String name;
    protected final int damage;
    protected final int price;

    public Weapon(String name, int damage, int price) {
        this.name = name;
        this.damage = damage;
        this.price = price;
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getPrice() { return price; }
}