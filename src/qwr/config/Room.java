package qwr.config;

import qwr.Loger;
import qwr.XFields;
import qwr.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс определяет место установки оборудования на объекте
 */
public class Room implements Item {

	String[] uClassRom ={"А","Б","В","Г"};
	int kClassRom;	//класс пожарной безопасности
	int idRoom;
	int floor;//этаж
	String titleRoom;//наименование помещения
	String	bild;//здание

	static String kBild;//здание
	static int kFloor;//этаж
	static int kClassRm;

	static public List<Item> list = new ArrayList<>();
	static public void init(){ list.add(new Room(0)); }//init

	static public XFields[] uField = new XFields[3];//список полей для генерации элементов
	{//инициализация класса
		uField[0] =new XFields(1, XFields.Typ.STRING,"Здание","",1,35);
		uField[1] =new XFields(1, XFields.Typ.INT,"Этаж","",-9,25);
		uField[2] =new XFields(1, XFields.Typ.ENUM,"Класс пожарной безопасности","", uClassRom);
	}//инициализация класса
//	private static String enumToString(){
//		String z=""; for (ClassRom y: ClassRom.values() ) { z = z +y +"\t"; }
//		return z;
//	}
	//---------------------------------------------------------------
//	public Room(int id) { idRoom=id; }

	public Room(int id){
		idRoom=id;
		this.floor = uField[1].let();
		this.titleRoom = "";
		this.kClassRom =uField[2].let();
		this.bild = uField[0].let("");
	}

	//для тестирования
	public Room(int idRoom, int floor, int kClassRom, String bild, String titleRoom) {
		this.idRoom = idRoom;
		this.floor = floor;
		this.titleRoom = titleRoom;
		this.kClassRom = kClassRom;
		this.bild = bild;
	}//---------------------------------------------------------------
	@Override
	public void printDefine(){ XFields.printDefine(uField); }
	@Override
	public boolean uConsol(Scanner con) {
		Loger.logs(" idRoom:"+this.idRoom);
//		uField[0].let(kBild);
//		uField[1].let(kFloor);
//		uField[2].let(kClassRm);
		return XFields.uConsol(con,uField,"");
//		kBild=uField[0].let(kBild);
//		kFloor=uField[1].let(kFloor);
//		kClassRm = uField[2].let(kClassRm);
	}//uConsol---------------------------------------------------------

	public static void test() {//создание набора данных для тестирования
		list.add(new Room(1,1,2,"16a","101 Трансформаторная"));
		list.add(new Room(2,1,2,"16a","111 Прачка"));
		list.add(new Room(3,1,2,"16a","103 Ателье"));
		list.add(new Room(4,2,3,"16a","201 Зоопарк"));
		list.add(new Room(5,2,4,"16a","221 Аптека"));
		list.add(new Room(6,2,2,"16a","231 Салон"));
		list.add(new Room(7,2,4,"16a","212 Баня"));
		list.add(new Room(8,3,1,"16a","301 Курилка"));
	}//test

	@Override
	public String printLn() {
		return idRoom+"\t"+floor+"\t"+ titleRoom +"\t"+uClassRom[kClassRom]+"\t"+bild; }

	@Override
	public String printHd() { return "#\tЭтаж\tнаименование\tКласс\tздание"; }

	@Override
	public String printTitle() { return "Помещения объекта"; }

	@Override
	public int idItem() { return idRoom; }

	@Override
//	public Item addID(int id) { return null; }
	public Item addID(int id) { return new Room(id); }
	@Override
	public String title() { return titleRoom; }

	@Override
	public void setTitle(String x) { titleRoom =x; }

}//class Room
