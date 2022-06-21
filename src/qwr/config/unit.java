package qwr.config;

import qwr.sysType.ObjectStatus;

abstract public class unit {
	static int	count;
	int 	id;//порядковый номер устройства
	String 	titul;
	ObjectStatus vstatus;
	public unit() {
		id=count++;
		titul="";
		vstatus=ObjectStatus.NOTUSE;
	}
}//class unit
