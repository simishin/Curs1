package qwr;

class Placement {//размещение
	int	idSensor;//ИД датчика
	int	idArea;//ИД зона
	int	idRoom;	//ИД помещения
	LocRoom loc;
}//class Location
enum LocRoom{DOOR,
	WINDOWS1,WINDOWS2,WINDOWS3,WINDOWS4,WINDOWS5,
	CEILING1,CEILING2,CEILING3,CEILING4,CEILING5,
 	CORNER1,CORNER2,CORNER3,CORNER4,CORNER5,CORNER6,
	WALL1,WALL2,WALL3,WALL4,WALL5,WALL6,
	PIER1,PIER2,PIER3,PIER4,
	FRONT1,FRONT2,FRONT3,FRONT4,TERRITORY
}//enum LocRoom
