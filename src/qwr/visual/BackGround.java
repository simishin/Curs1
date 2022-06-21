package qwr.visual;

import qwr.sysType.ObjectStatus;
import qwr.sysType.TypeObject;
import qwr.sysType.TypeSystem;

/**
 * определяет расположение объекта на чертеже, рисунке
 */
public abstract class BackGround {//подложка чертежа
	int sheet;
	String pathGif;
	int	xn;//слева
	int	yn;//сверху
	int	xd;//ширина
	int yd;//высота
}//class Vizual

