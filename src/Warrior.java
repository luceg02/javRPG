/**
 * Warrior character class with chance for critical hits
 */
public class Warrior extends Character {
    private static final int WARRIOR_HP = 120;
    private static final int WARRIOR_MANA = 50;
    private static final double CRITICAL_CHANCE = 0.1;
    private static final int CRITICAL_MULTIPLIER = 3;

    public Warrior(String name) {
        super(name, WARRIOR_HP, WARRIOR_MANA);
    }

    @Override
    public int attack() {
        if (Math.random() < CRITICAL_CHANCE) {
            int damage = super.attack() * CRITICAL_MULTIPLIER;
            System.out.println("Critical hit! Triple damage!");
            return damage;
        }
        return super.attack();
    }
}
