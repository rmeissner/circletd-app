package de.thegerman.circletd.upgrades;

public class UpgradeLevel<T> {
	int price;
	T value;
	
	public UpgradeLevel(int price, T value) {
		this.price = price;
		this.value = value;
	}
	public int getPrice() {
		return price;
	}
	public T getValue() {
		return value;
	}
}
