/**
 * Mage character class with magic damage bonus
 */
public class Mage extends Character {
    private static final int MAGE_HP = 80;
    private static final int MAGE_MANA = 150;
    private static final int MANA_COST = 10;
    private static final int MAGIC_BONUS = 5;

    public Mage(String name) {
        super(name, MAGE_HP, MAGE_MANA);
    }

    @Override
    public int attack() {
        if (mana >= MANA_COST) {
            mana -= MANA_COST;
            int damage = super.attack() + MAGIC_BONUS;
            System.out.println("Used " + MANA_COST + " mana for magic attack boost! (" + mana + "/" + maxMana + " mana remaining)");
            return damage;
        }
        System.out.println("Not enough mana for magic boost!");
        return super.attack();
    }
}