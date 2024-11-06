/**
 * Elf character class with chance for double attack
 */
public class Elf extends Character {
    private static final int ELF_HP = 90;
    private static final int ELF_MANA = 100;
    private static final double DOUBLE_ATTACK_CHANCE = 0.2;

    public Elf(String name) {
        super(name, ELF_HP, ELF_MANA);
    }

    @Override
    public int attack() {
        if (Math.random() < DOUBLE_ATTACK_CHANCE) {
            int damage = super.attack() * 2;
            System.out.println("Double attack activated!");
            return damage;
        }
        return super.attack();
    }
}