package qwr.visual;

import qwr.sysType.TypeObject;
import qwr.sysType.TypeSystem;

abstract class Position {//позиция
	int jSensor;//порядковый номер датчика
	int sheet;
	int xn;//слева
	int yn;//сверху
	int xe;//вправо
	int ye;//вниз
	TypeObject tobj;
	TypeSystem tsys;
}//class Position
