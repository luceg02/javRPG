/**
 * Base class for monsters and obstacles
 */
public abstract class Entity extends GameObject {
    protected int attackPower;

    public Entity(String name, int hp, int attackPower) {
        super(name, hp);
        this.attackPower = attackPower;
    }

    public int attack() {
        return attackPower;
    }
}
