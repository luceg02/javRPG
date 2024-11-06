/**
 * Basic sword weapon
 */
public class Sword extends Weapon {
    private static final String NAME = "Sword";
    private static final int DAMAGE = 15;
    private static final int PRICE = 100;

    public Sword() {
        super(NAME, DAMAGE, PRICE);
    }
}
