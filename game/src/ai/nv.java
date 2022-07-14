package ai;

public class nv {
	int value;
	public nv() {
		value = 0;
	}
	public boolean checkValue(int v) {
		if(value >= v) return true;
		else return false;
	}
	public boolean checkLowValue(int v) {
		if(value < v) {
			return true;
		}
		return false;
	}
	public void addValue(int i) {
		this.value++;
	}
	
}
