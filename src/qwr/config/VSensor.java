package qwr.config;

import qwr.sysType.ObjectStatus;
import qwr.sysType.TypeObject;
import qwr.sysType.TypeSystem;

public abstract class VSensor extends unit{
	int		timeInclud;
	boolean dk;
	TypeSystem		vsystem;//превратить в интерфейс
	TypeObject		vobj;
}//class Sensor
