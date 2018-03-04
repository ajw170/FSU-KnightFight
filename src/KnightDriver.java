/* Andrew J Wood
   COP3252 Java
   February 26, 2018
 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.SecureRandom;

public class KnightDriver {

    public static void main(String[] args) {

        final char EXIT_LOWER = 'x';
        final char EXIT_UPPER = 'X';
        char exitFlag = '0';

        while(exitFlag != EXIT_LOWER && exitFlag != EXIT_UPPER) {
            System.out.println("Welcome to Knight Fight!");
            System.out.println("========================");
            System.out.println(); //extra line

            Knight playerKnight = enterCustomKnight();

            //Now ask the player if the opponent should be randomly generated.
            //If yes, use default knight constructor.  If no, repeat ask process.
            Scanner input = new Scanner(System.in);
            System.out.print("Would you like to auto-generate your opponent? (y|n): ");
            char opponentGen = input.next().charAt(0);
            System.out.print("\n");

            //exits program if y or n is not entered
            if (opponentGen != 'y' && opponentGen != 'n' && opponentGen != 'Y' && opponentGen != 'N') {
                System.out.println("Invalid response.  Exiting.");
                System.exit(0);
            }

            //create reference to opponent Knight
            Knight opponentKnight;

            //assign the opponent Knight reference to a valid knight
            switch (opponentGen) {
                case 'Y':
                case 'y':
                    opponentKnight = new Knight(); //assigns random attributes
                    break;
                default:
                    opponentKnight = enterCustomKnight();
            }

            //Now we have both Knights established; begin the battle!
            //Determine which player goes first, either 0 (Player) or 1 (Opponent)
            //Then, display the initial attributes in order
            //Then begin battle!
            SecureRandom random = new SecureRandom();
            int whoGoes = random.nextInt(2); //result is either 0 or 1
            Knight winningKnight;

            System.out.println("Here are our illustrious Knights:");
            System.out.print(playerKnight.toString());
            System.out.print(opponentKnight.toString());
            System.out.print("Press any key to start OR press N to quit: ");
            String readGo = input.next();
            System.out.println();
            if (readGo.equals("N") || readGo.equals("n")) {
                System.out.println("Thanks for using KnightFight!");
                System.exit(0);
            }

            if (whoGoes == 0) //player goes first
            {
                System.out.println("The order has been decided! " + playerKnight.getName() + " will go first!\n");
                System.out.println("\nLet the battle begin!\n");
                winningKnight = fightKnights(playerKnight, opponentKnight);
            } else //opponent goes first
            {
                System.out.println("The order has been decided! " + opponentKnight.getName() + " will go first!\n");
                System.out.println("\nLet the battle begin!\n");
                winningKnight = fightKnights(opponentKnight, playerKnight);
            }

            //Display winner
            System.out.printf("%nAnd the winner is...%s!%n", winningKnight.getName());
            //Ask if they'd like to play again?
            System.out.print("Press any key to play again (or press X to exit): ");
            exitFlag = input.next().charAt(0);
        } //end while
        System.out.println("Thanks for playing KnightFight!");
    } //end main

    //allows the knights to fight back and forth until one is declared the winner, returns
    //the winning night
    private static Knight fightKnights (Knight knight1, Knight knight2)
    {
        int counter = 1;
        //continue looping until a winning knight is returned
        while (true)
        {
            System.out.println("\nRound " + counter + ": " + knight1.getName() + " is attacking " + knight2.getName() + "!");
            //Knight 1 fights Knight 2
            knight1.fight(knight2);
            //did knight 1 win?
            if (knight2.getHitPoints() <= 0) {
                //display attributes and return winner
                System.out.printf("%s%s", knight1.toString(), knight2.toString());
                return knight1;

            }
            System.out.printf("%s%s", knight1.toString(), knight2.toString());
            ++counter;

            System.out.println("\nRound " + counter + ": " + knight2.getName() + " is attacking " + knight1.getName() + "!");
            //Knight 2 fights Knight 1
            knight2.fight(knight1);
            //did knight 2 win?
            if (knight1.getHitPoints() <= 0) {
                //display attributes and return winner
                System.out.printf("%s%s", knight1.toString(), knight2.toString());
                return knight2;
            }
            //Neither has won yet, so display atributes of both
            System.out.printf("%s%s", knight1.toString(), knight2.toString());
            ++counter;
        }

    }

    private static Knight enterCustomKnight()
    {
        int weaponChoice = 0;
        int armorChoice = 0;
        Knight.WeaponType weapon = null;
        Knight.ArmorType armor = null;

        //put the loop here but just write the program once for POC purposes
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the name of the Knight: ");
        String name = input.nextLine();

        System.out.println("Now select the weapon! (Choose Number)");
        System.out.println("1) Long Sword");
        System.out.println("2) Battle Axe");
        System.out.println("3) Spear");
        System.out.println("4) Warhammer");
        System.out.print("Your choice my liege? : ");

        //try block for choice of input
        try
        {
            weaponChoice = input.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.printf("Exception while choosing weapon: %s%n",e);
            System.exit(0);  //abandon program
        }
        System.out.print("\n");

        System.out.println("Now select the armor! (Choose Number)");
        System.out.println("1) Metal");
        System.out.println("2) Leather");
        System.out.println("3) Titanium");
        System.out.println("4) Unobtanium");
        System.out.print("Your choice my liege? : ");

        //try block for choice of input
        try
        {
            armorChoice = input.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.printf("Exception while choosing armor: %s%n",e);
            System.exit(0);
        }
        System.out.print("\n");

        //parse to determine weapon type
        switch(weaponChoice)
        {
            case 1:
                weapon = Knight.WeaponType.LONG_SWORD;
                break;
            case 2:
                weapon = Knight.WeaponType.BATTLE_AXE;
                break;
            case 3:
                weapon = Knight.WeaponType.SPEAR;
                break;
            case 4:
                weapon = Knight.WeaponType.WAR_HAMMER;
                break;
            default:
                System.out.println("Invalid weapon type selected.");
                System.exit(0);
                break;
        } //end weapon choice

        //Parse to determine armor type
        switch(armorChoice)
        {
            case 1:
                armor = Knight.ArmorType.METAL;
                break;
            case 2:
                armor = Knight.ArmorType.LEATHER;
                break;
            case 3:
                armor = Knight.ArmorType.TITANIUM;
                break;
            case 4:
                armor = Knight.ArmorType.UNOBTANIUM;
                break;
            default:
                System.out.println("Invalid armor type selected.");
                System.exit(0);
                break;
        } //end armor choice

        //Now construct the knight with chosen attributes
        Knight customKnight = new Knight(weapon,armor,name);

        return customKnight;
    }
}
