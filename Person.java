public class Person {
	String name;
	int health;
	boolean isDead;
	boolean hasDysentery;
	
	
	public Person(String name, int health) {
		this.name = name;
		this.health = health;
		isDead = false;
		hasDysentery = false;
	}
	
	public String toString() {
		return name+" has "+health+" health.";
	}

}
