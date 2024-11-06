/**
 * Bow weapon
 */
public class Bow extends Weapon {
    private static final String NAME = "Bow";
    private static final int DAMAGE = 12;
    private static final int PRICE = 120;

    public Bow() {
        super(NAME, DAMAGE, PRICE);
    }
}