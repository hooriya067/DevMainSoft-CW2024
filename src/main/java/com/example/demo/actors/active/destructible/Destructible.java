package com.example.demo.actors.active.destructible;

/**
 * The {@code Destructible} interface defines the basic behavior for objects that
 * can take damage and be destroyed. It is implemented by classes representing
 * game elements with health or durability properties.
 */
public interface Destructible {

	/**
	 * Applies damage to the object, reducing its health or durability.
	 * The exact behavior depends on the implementing class.
	 */
	void takeDamage();

	/**
	 * Marks the object as destroyed. Implementing classes should define
	 * the specific logic for handling destruction, such as removing the
	 * object from the game or triggering animations.
	 */
	void destroy();
}
