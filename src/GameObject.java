/**
 * Base class for all objects that can exist on the game map
 */
public abstract class GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;

    public GameObject(String name, int hp) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(int damage) {
        hp = Math.max(hp - damage, 0);
    }
}