package dev.xkmc.l2weaponry.init.materials.capability;

public interface IShieldData {

	double getShieldDefense();

	void setShieldDefense(double i);

	int getReflectTimer();

	boolean canReflect();

	double popRetain();
}
