package mypack;
import java.util.*;
public class NumberGuess {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number =(int) (Math.random()*101);
		System.out.println(number);
		int count = 0;
		System.out.println("Enter your number:");
		int numGuessed = sc.nextInt();
		while(count < 10 && numGuessed != number)
		{
			if(numGuessed > number)
			{
				System.out.println("The number you entered is GREATER!");
				System.out.println("Try entering a SMALLER number:");
				numGuessed = sc.nextInt();
				count++;
			}
			
			else{
				System.out.println("The number you entered is SMALLER!");
				System.out.println("Try entering a GREATER number:");
				numGuessed = sc.nextInt();
				count++;
			}
		}
		if(count>10)
		{
			System.out.println("Sorry! Your chances are expired.."+"\nBetter Luck next time.");
		}
		else {
			System.out.println("Finally you have guessed correctly!");
			System.out.println("The number is: "+number);
			System.out.println("You have taken "+count+" chances.");
			System.out.print("You have scored:");
			if(count<3)
			{
				System.out.print("5 out of 5.");
			}
			else if(count<=5)
			{
				System.out.print("4 out of 5.");
			}
			else if(count<=7)
			{
				System.out.print("3 out of 5.");
			}
			else if(count<9)
			{
				System.out.print("2 out of 5.");
			}
			else {
				System.out.print("1 out of 5.");
			}
		}
		sc.close();
	}

}
