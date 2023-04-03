package dev.xkmc.l2weaponry.content.capability;

public interface IShieldData {

	double getShieldDefense();

	void setShieldDefense(double i);

	int getReflectTimer();

	boolean canReflect();

	double popRetain();
}
