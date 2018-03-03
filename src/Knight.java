/* Andrew J Wood
   COP3252 Java
   February 26, 2018

   The Knight class provides two constructors to creat a knight and holds all of its
   attributes.

   The attributes consist of:
   -Name
   -Weapon Type
   -HitPoints (Initialized to 100, basically a health value)
   -Armor Type

   The methods are:
   Fight(Knight) - Fights the knight indicated as an argument.  Uses the weapon assigned to the fighting knight
   toString() - Outputs all the attributes
   setName(String name) - sets the Name
   setWeaponType(*enum weapon) - sets the Weapon Type
   setHitPoints(int hitPoints)
   getHitPoints()
   setArmorType(*enum armor)

   Note that the armor type is selected at random as this was not specified in the program requirements.
 */

import java.security.SecureRandom;

public class Knight
{
    //create enum class that enumerates the weapon type
    //implicitly static and final by rule of enums
    public enum WeaponType
    {
        //Modify damage by strength of weapons
        LONG_SWORD(1.0),
        BATTLE_AXE(1.2),
        SPEAR(0.9),
        WAR_HAMMER(1.8);

        private double damageModifier;

        //constructor
        WeaponType(double damage)
        {
            this.damageModifier = damage;
        }

        private double getDamageModifier()
        {
            return this.damageModifier;
        }
    }

    //create ArmorType class that enumerates the armor type
    //implicitly static and final by rule of enums
    public enum ArmorType
    {
        METAL(1.0),
        LEATHER(0.7),
        TITANIUM(1.6),
        UNOBTANIUM(2.2);

        private double defenseModifier;

        //constructor
        ArmorType(double defense)
        {
            this.defenseModifier = defense;
        }

        private double getDefenseModifier()
        {
            return this.defenseModifier;
        }
    }

    //static array with names to be used as defaults if none is specified
    private static final String[] defaultNames = {"Sir Andrew","Sir Arthur","Sir Blake","Sir Cody","Madam Jane","Madam Joseline",
        "Sir Phillip","Madam Elizabeth","Sir Tyson","Sir Javs","Madam Dorthy"};

    //Knight instance Variables
    private String name;
    private WeaponType weapon;
    private ArmorType armor;
    private int hitPoints;
    private int lastHit;
    private final int HIT_POINTS_MAX = 100;

    //default constructor when no arguments are passed
    public Knight ()
    {
        SecureRandom random = new SecureRandom();

        //select a random number that corresponds to weapon type
        int weaponSelect = random.nextInt((WeaponType.values()).length);
        this.weapon = WeaponType.values()[weaponSelect];

        //select a random number that corresponds to armor type
        int armorSelect = random.nextInt((WeaponType.values()).length);
        this.armor = ArmorType.values()[armorSelect];

        int nameSelect = random.nextInt(defaultNames.length);
        this.name = defaultNames[nameSelect];

        this.hitPoints = random.nextInt(HIT_POINTS_MAX);
        this.lastHit = 0; //always starts with 0

    }

    //constructor to be used when arguments are specified
    public Knight(WeaponType weapon, ArmorType armor, String name)
    {
        SecureRandom random = new SecureRandom();

        this.weapon = weapon;
        this.armor = armor;
        this.name = name;

        //user cannot choose the initial HitPoints as per specification
        this.hitPoints = random.nextInt(HIT_POINTS_MAX);
        this.lastHit = 0; //always starts with 0
    }

    //returns the knight's weapon type
    public WeaponType getWeaponType() {
        return this.weapon;
    }

    //sets the knights weapon type
    public void setWeaponType(WeaponType weapon)
    {
        //assigns this knight's weapon to reference of passed weapon
        this.weapon = weapon;
    }

    //returns the knight's armor type
    public ArmorType getArmorType()
    {
        return this.armor;
    }

    //sets the knight's armor type
    public void setArmorType(ArmorType armor)
    {
        this.armor = armor;
    }

    //gets the knight's name
    public String getName()
    {
        return this.name;
    }

    //sets the knight's name
    public void setName(String name)
    {
        this.name = name;
    }

    //gets the knight's hitpoints
    public int getHitPoints()
    {
        return hitPoints;
    }

    //sets the knight's hitpoints
    public void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }

    private int getLastHit()
    {
        return lastHit;
    }

    private void setLastHit(int lastHit)
    {
        this.lastHit = lastHit;
    }

    //Outputs the knight's statistics
    @Override
    public String toString()
    {
        String s = "Knight Attributes\n";
        s       += "=================\n";
        s       += "Name: " + this.getName() + "\n";
        s       += "Hit Points: " + this.getHitPoints() + "\n";
        s       += "Weapon Type: " + this.getWeaponType() + "\n";
        s       += "Armor Type: " + this.getArmorType() + "\n";
        s       += "Last Hit Damage: " + this.getLastHit();
        return s;
    }

    //Fights the knight specified by the argument.  Note that the caller "this" is fighting the specified argument.
    public void fight(Knight knight)
    {
        //constant specifying maximum damage amount
        final int DAMAGE_RANGE = 21;
        SecureRandom random = new SecureRandom();

        //select a base amount of damage to deal ranging from 0 to DAMAGE_RANGE - 1.  0 indicates a "miss".
        int hitPointsReduce = random.nextInt(DAMAGE_RANGE);

        //multiply hitPoints reduction by damage modifier associated with the weapon. This will increase
        //or reduce the damage dealt based on the weapon type
        //NOTE - the result is truncated.
        hitPointsReduce *= (this.getWeaponType().damageModifier);

        //divide the hitPoints reduction by the armor modifier.  This will reduce or increase the damage
        //dealt based on the defending knight's armor type
        //NOTE - the result is truncated.
        hitPointsReduce /= (knight.getArmorType().defenseModifier);

        //assign last hit to hitPointsReduce of the knight being fought
        knight.setLastHit(hitPointsReduce);

        //calculates the new value for the knight's hitpoints
        int newHitPoints = knight.getHitPoints() - hitPointsReduce;

        //Sets the new hitpoints value
        knight.setHitPoints(newHitPoints);
    }
} //end class Knight
