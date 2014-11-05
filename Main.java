import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {
	
	static Person persons[];
	static int food, water, money, distance;
	static Scanner input = new Scanner(System.in);
	static Random random = new Random();
	static int numPlayers;
	
	//public static void say(Object text) {System.out.println(text);}//remove when done
	
	public static void openShop() {
		String noMonies = "";
		boolean exit;
		do {
			exit = false;
			switch (JOptionPane.showOptionDialog(null, noMonies+"\nMonies: "+money+"\nWater: "+water+"\nFood: "+food+"\nSelect an option", "Orange Trail - Shop", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Buy Water ($3)", "Buy Food ($4)", "Exit Shop"}, "Exit Shop")) {
			case JOptionPane.YES_OPTION:
				if (money >= 20) {
					noMonies = "";
					money-=20;
					water++;
				} else {
					noMonies = "you do not have enough Monies to buy water.\n";
				}
				break;
			case JOptionPane.NO_OPTION:
				if (money >= 10) {
					noMonies = "";
					money-=10;
					food++;
				} else {
					noMonies = "you do not have enough Monies to buy food.\n";
				}
				break;
			case JOptionPane.CANCEL_OPTION:
				exit = true;
				break;
			default:
			}
		} while (!exit);
	}
	
	public static void step() throws Exception {
		Display.setText("");
		int traveled = random.nextInt(11) + 10;
		distance+=traveled;
		Display.addText("Your group traveled "+traveled+" meters.");
		int totalDead = 0;
		for (Person a : persons) {
			if (!a.isDead) {
				if (a.hasDysentery) {
					if (random.nextInt(100) + 1 <= 10) {
						a.hasDysentery = false;
						Display.addText(a.name+" has been cured of dysentery.");
					} else {
						Display.addText(a.name+" has dysentery.");
						a.health-=1;
						if (a.health <= 0) {
						a.isDead = true;
						totalDead++;
						Display.addText(a.name+" has died from dysentery.");
						}
					}
				}
				if (water > 0) {//if water
					water-=random.nextInt(1) + 2;//2-3
				} else {//if no water
					Display.addText(a.name+" is dehydrated.");
					a.health-=2;
					if (a.health <= 0) {
						a.isDead = true;
						totalDead++;
						Display.addText(a.name+" has died from lack of water.");
					} else {
						if (food > 0) {
							food-=random.nextInt(1) + 2;//1-2
						} else {
							Display.addText(a.name+" is starving.");
							a.health--;
							if (a.health <= 0) {
								a.isDead = true;
								totalDead++;
								Display.addText(a.name+" has died from lack of water.");
							}
						}
					}
				}
				if (!a.isDead) {
					Display.addText(a.toString());
				}
			} else {
				totalDead++;
				Display.addText(a.name+", DEAD");
			}
			
			if (totalDead >= persons.length) {
				
				int finalScore = distance-numPlayers*5;
				if (finalScore < 0) {
					finalScore = 0;
				}
				
				JOptionPane.showMessageDialog(null, "All your characters have died. Game over.\nYou score is "+finalScore, "Orange Trail - UR DED M8", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		if (water < 0) {water = 0;}
		if (food < 0) {food = 0;}
		Display.addText("\nMonies: "+money+"\nWater: "+water+"\nFood: "+food);
		
		int randomSelection = random.nextInt(100) + 1;
		
		if (randomSelection <= 10) {//town
			if (JOptionPane.showConfirmDialog(null, "You come up to a town. Do you wish to visit it?", "Orange Trail - Town", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				for (Person a : persons) {
					if (!a.isDead) {
						a.health+= 10;
						if (a.health > 20) {a.health = 20;}
					}
				}
				String personsHealth = "";
				for (Person a : persons) {
					personsHealth = personsHealth+a.toString()+"\n";
				}
				
				if (JOptionPane.showConfirmDialog(null, "You stop at a town, get some rest.\n"+personsHealth+"Do you want to visit the shop?", "Orange Trail - Shop", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					openShop();
				}
				JOptionPane.showMessageDialog(null, "You continue on your way", "Orange Trail", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (randomSelection >= 11 && randomSelection <= 20) {//waterfall
			if (JOptionPane.showConfirmDialog(null, "You come up to a waterfall. Do you wish to visit it?", "Orange Trail - Waterfall", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (random.nextInt(100) + 1 <= 30) {
					Person victim;
					if (persons.length == 1) {
						victim = persons[0];
					} else {
						boolean exit = false;
						do {
							victim = persons[random.nextInt(persons.length-1)];
							if (!victim.isDead) {
								exit = true;
							}
						} while(!exit);
					}
					JOptionPane.showMessageDialog(null, victim.name+" Fell down the waterfall!", "Orange Trail - Waterfall", JOptionPane.WARNING_MESSAGE);
					victim.health-=random.nextInt(5)+1;
					if (victim.health <= 0) {
						victim.health = 0;
						victim.isDead = true;
					}
				} else {
					int waterToAdd = random.nextInt(5) + 1;
					water+=waterToAdd;
					JOptionPane.showMessageDialog(null, "You collected "+waterToAdd+" water.", "Orange Trail - Waterfall", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (randomSelection >= 21 && randomSelection <= 30) {//hunting hunt injured-10
			if (JOptionPane.showConfirmDialog(null, "You come to a hunting ground. Do you wish to hunt?", "Orange Trail - Hunting Ground", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (random.nextInt(100) + 1 <= 30) {
					Person victim;
					if (persons.length == 1) {
						victim = persons[0];
					} else {
						boolean exit = false;
						do {
							victim = persons[random.nextInt(persons.length-1)];
							if (!victim.isDead) {
								exit = true;
							}
						} while(!exit);
					}
					JOptionPane.showMessageDialog(null, victim.name+" was skewered by a lion!", "Orange Trail - Hunting Ground", JOptionPane.WARNING_MESSAGE);
					victim.health-=random.nextInt(5)+1;
					if (victim.health <= 0) {
						victim.health = 0;
						victim.isDead = true;
					}
				} else {
					int foodToAdd = random.nextInt(5) + 1;
					food+=foodToAdd;
					JOptionPane.showMessageDialog(null, "You collected "+foodToAdd+" food.", "Orange Trail - Hunting Ground", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (randomSelection >= 31 && randomSelection <= 40) {//river
			if (JOptionPane.showOptionDialog(null, "You come to a river. Do you wish to trudge through the river or create a raft out of water?", "Orange Trail - River", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Trudge", "Raft"}, "Trudge") == JOptionPane.YES_OPTION) {
				if (random.nextInt(100) + 1 <= 30) {
					Person victim;
					if (persons.length == 1) {
						victim = persons[0];
					} else {
						boolean exit = false;
						do {
							victim = persons[random.nextInt(persons.length-1)];
							if (!victim.isDead) {
								exit = true;
							}
						} while(!exit);
					}
					JOptionPane.showMessageDialog(null, victim.name+" drowned!", "Orange Trail - River", JOptionPane.WARNING_MESSAGE);
					victim.health-=random.nextInt(5)+1;
					if (victim.health <= 0) {
						victim.health = 0;
						victim.isDead = true;
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "You made it safely across.", "Orange Trail - River", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				int waterToLose = random.nextInt(5) + 1;
				food-=waterToLose;
				if (food < 0) {food = 0;}
				JOptionPane.showMessageDialog(null, "You used "+waterToLose+" water to create a raft.", "Orange Trail - River", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (randomSelection >= 41 && randomSelection <= 50) {//monies break legs or miny
			if (JOptionPane.showConfirmDialog(null, "You stop and find some Monies on the ground. Do you wish to pick it up?", "Orange Trail - Ground Monies", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (random.nextInt(100) + 1 <= 30) {
					Person victim;
					if (persons.length == 1) {
						victim = persons[0];
					} else {
						boolean exit = false;
						do {
							victim = persons[random.nextInt(persons.length-1)];
							if (!victim.isDead) {
								exit = true;
							}
						} while(!exit);
					}
					JOptionPane.showMessageDialog(null, victim.name+" broke their legs trying to get the Monies!", "Orange Trail - Ground Monies", JOptionPane.WARNING_MESSAGE);
					victim.health-=random.nextInt(5)+1;
					if (victim.health <= 0) {
						victim.health = 0;
						victim.isDead = true;
					}
				} else {
					int moneysToAdd = random.nextInt(5) + 1;
					money+=moneysToAdd;
					JOptionPane.showMessageDialog(null, "You collected "+moneysToAdd+" Monies.", "Orange Trail - Ground Monies", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (randomSelection >= 51 && randomSelection <= 60) {//dysentary snake take off health   dysentery
			Person victim;
			if (persons.length == 1) {
				victim = persons[0];
			} else {
				boolean exit = false;
				do {
					victim = persons[random.nextInt(persons.length-1)];
					if (!victim.isDead) {
						exit = true;
					}
				} while(!exit);
			}
			JOptionPane.showMessageDialog(null, victim.name+" got bitten by a Dysentery Snake!", "Orange Trail - Dysentery Snake", JOptionPane.WARNING_MESSAGE);
			victim.hasDysentery = true;
		}
		
			
			
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//money = 500;
		food = 0;
		water = 0;
		distance = 0;
		
		Object numbers[] = new Object[10];
		numbers[0] = "1 Character";
		for (int i = 1; i < numbers.length; i++) {
			numbers[i] = (i+1)+" Characters";
		}
		
		boolean exit;
		String selectedPlayers;
		do {
			exit = false;
			selectedPlayers = (String)JOptionPane.showInputDialog(null, "How many characters would you like?","Orange Trail - Number of Characters", JOptionPane.QUESTION_MESSAGE ,null, numbers, "3 Characters");
			if (selectedPlayers != null && selectedPlayers.length() > 0) {
				exit = true;
			}
		} while (!exit);
	
		numPlayers = Integer.parseInt(selectedPlayers.split(" ")[0]);
		money = numPlayers*100;
		persons = new Person[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			String playerName = (String)JOptionPane.showInputDialog(null, "Enter Character "+(i+1)+" Name:","Orange Trail - Character "+(i+1), JOptionPane.QUESTION_MESSAGE ,null, null, "");
			if (playerName == null) {
				persons[i] = new Person("Person "+(i+1), 20);
			} else {
				if (!playerName.equals("")) {
					persons[i] = new Person(playerName, 20);
				} else {
					persons[i] = new Person("Person "+(i+1), 20);
				}
			}

		}
		
		String characters = "";
		
		for (Person a : persons) {
			characters = characters+a.toString()+"\n";
		}
		JOptionPane.showMessageDialog(null, characters+"You go shopping for supplies", "Orange Trail", JOptionPane.INFORMATION_MESSAGE);
		openShop();
		JOptionPane.showMessageDialog(null, "The journey commences!", "Orange Trail", JOptionPane.INFORMATION_MESSAGE);
		Display.start();
		Thread.sleep(1000);
		do {
		step();
		Thread.sleep(1000);
		} while (true);
		//input.close();
	}
 
}
