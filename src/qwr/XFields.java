package qwr;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * предназначен для работы с параметрами классов через меню
 */
public class XFields {
	private static int count=0;

	public enum Typ{INT,STRING,ENUM}
	int idMenu;
	int id;
	Typ typ;
	String title;
	String descript;
	String values;
	String[] arrValue;
	int		valuei;
	int		valMin;
	int		valMax;
	boolean	modify;//использовать при модификации записи или оставить без изменения данное поле

	public XFields(int idMenu, Typ typ, String title, String descript, int valMin, int valMax) {
		this.id = count++;
		this.idMenu = idMenu;
		this.typ = typ;
		this.title = title;
		this.descript = descript;
		this.values = "";
		this.valuei = 0;
		this.valMin = valMin;
		this.valMax = valMax;
		this.modify = false;
		this.arrValue=null;
	}//XFields=======================================================================

	public XFields(int idMenu, Typ typ, String title, String descript, String ...arrVal) {
		this.id = count++;
		this.idMenu = idMenu;
		this.typ = typ;
		this.title = title;
		this.descript = descript;
		this.values = "";
		this.valuei = 0;
		this.valMin = 1;
		this.modify = false;
		arrValue= arrVal;
		this.valMax = arrVal.length;
	}//XFields=======================================================================

	/**
	 * Метод показа списка параметров элемента и
	 * дальнейшего вызова метода editValue() редактирования выбранного параметра
	 * Вызывается через одноименный метод типа элементов из Dialog.uConsol(Scanner con, List<Item> list)
	 * @param con консоль
	 * @param uField список полей
	 * @param titul текст для вывода в шапке
	 * @return действие после перебора параметров:
	 * истина - выход на верхний уровень меню, лож- продолжение редактирования списка
	 */
	public static boolean uConsol(Scanner con, XFields[] uField, String titul) {
		Loger.logs("");
		Loger.prnq(titul+"\nЗначения полей элемента");
		int q;
		while (true){
			printDefine(uField);//печать списка параметров
			Loger.prnq("Выберете номер поля или 0,/- для выхода в главное меню, 9,*,+,-- вернуться ("+(uField.length)+")\n");
			try {
				int y = con.nextInt();
				if (y==0) return false;
				if (y==9) return false;
				if(y>0 && y<=uField.length){
					do {
						q = editValue(con,uField[y-1]);
						if (q==-1) break;
						y++;
					} while (q==1 && y<uField.length);
				} else Loger.prnq("Ошибка выбора. Повторите.");
			} catch (InputMismatchException ex) {
				switch (con.next().charAt(0)) {
					case 'q':
						return true;
					case '/':
					case '*':
					case '-':
					case '+':
						return false;
					default:
						Loger.prne("(" + con.next() + "(" + (int) con.next().charAt(0) + ") Error, повторите ввод 'q'\n");
				}//switch
			}//catch
		}//while
	}//uConsol-----------------------------------------------------------------------

	/**
	 * Метод редактирования ранее выбранного параметра элемента XFields
	 * Вызывается из локальной uConsol(Scanner con, XFields[] uField, String titul)
	 * @param con консоль
	 * @param uField выбранный параметр элемента
	 * @return -1=выход из редактирования параметров, 0=выход к списку параметров, 1=следующий параметр
	 */
	private static int editValue(Scanner con, XFields uField){
		Loger.logs(" "+uField.typ);
		switch (uField.typ){
			case INT -> {
				Loger.prnq("Введите значение "+uField.title+" в диапазоне от "+uField.valMin
						+" до "+uField.valMax+" или :" +
						"\n/- для выхода, *-выход к списку, --отключение и следующий, +- включение и следующий");
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.modify ? "Да" : "НЕТ"));
				Loger.prnq("текущее значение :"+uField.valuei);
				Loger.prnq("Описание параметра: "+uField.descript);
//				Loger.prnq("{ /- для выхода, *-отключение и выход к списку, --отключение и следующий, +- включение и следующий }"+"\n------- ?");
				while (true) {
					try {
						int y = con.nextInt();
						Loger.logs("="+y);
						if (y >= uField.valMin && y <= uField.valMax) {
							uField.modify = true;
							uField.valuei = y;
							return 0;
						}
						Loger.prnq("Значение выходит за допустимый диапазон. Повторите ввод.");
					} catch (InputMismatchException ex) {
						switch (con.next().charAt(0)) {
							case 'q':

							case '/':
								return -1;//выход из редактирования параметров
							case '*':
									return 0;//выход к списку параметров
							case '+':
									uField.modify = true;
									return 1;//следующий параметр
							case '-':
									uField.modify = false;
									return 1;//следующий параметр
							default:
						}//switch
					}//catch
				}//while
			}//case INT
			case STRING -> {
				Loger.prnq("Введите текст "+uField.title+" длинной от "+uField.valMin
						+" до "+uField.valMax+" или :" +
						"\n/- для выхода, *-выход к списку, --отключение и следующий, +- включение и следующий");
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.modify ? "Да" : "НЕТ"));
				Loger.prnq("текущее значение :"+uField.values);
				Loger.prnq("Описание параметра: "+uField.descript);
				while (true){
					String y = con.nextLine();
					if (y.isBlank()) continue;

					switch (y.charAt(0)) {
						case 'q':
							return -1;
						case '/':
						case '*':
							return 0;
						case '+':
							uField.modify = true;
							return 1;
						case '-':
							uField.modify = false;
							return 1;
						default:
					}//switch
					if (y.length() >= uField.valMin && y.length() <= uField.valMax) {
						uField.modify = true;
						uField.values = y;
						return 0;
					}
					Loger.prnq("Длина текста выходит за допустимый диапазон. Повторите ввод.");
				}//while
			}//case STRING
			case ENUM -> {
				Loger.prnq("Выберете значение параметра "+uField.title+" из списка  или :" +
						"\n/- для выхода, *-выход к списку, --отключение и следующий, +- включение и следующий");
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.modify ? "Да" : "НЕТ"));
				Loger.prnq("текущее значение :"+uField.arrValue[uField.valuei].toString());
				Loger.prnq("Описание параметра: "+uField.descript+"\n Список допустимых значений:");
				for (int i = 0; i < uField.arrValue.length; i++) {
					Loger.prnq((i+1)+".\t"+uField.arrValue[i].toString());
				}
				while (true) {
					try {
						int y = con.nextInt();
						Loger.logs("="+y);
						if (y==0) return -1;
						if (y >= uField.valMin && y <= uField.valMax) {
							uField.modify = true;
							uField.valuei = y-1;
							return 0;
						}
						Loger.prnq("Значения не существует. Повторите ввод.");
					} catch (InputMismatchException ex) {
						switch (con.next().charAt(0)) {
							case 'q':
								return -1;
							case '/':
							case '*':
								return 0;
							case '+':
								uField.modify = true;
								return 1;
							case '-':
								uField.modify = false;
								return 1;
							default:
						}//switch
					}//catch
				}//while
			}//case ENUM
		}//switch
		return 0;
	}//editValue --------------------------------------------------------------------

	/**
	 * Печать списка параметров элемента. Вызывается из локальной uConsol()
	 * @param uField список параметров элемента
	 */
	public static void printDefine(XFields[] uField) {//печать одной странички из списка
		assert uField.length>0 : "Начата обработка пустого списка параметров элемента (XFields.printSelect)";
//		if (uField.length<1) return;
		Loger.prnq(" ( №, Используется, Значение, Наименование параметра )");
		for (int i = 0; i < uField.length; i++) {
			assert uField[i] !=null : "Зарезервировано больше, чем описано параметров элемента ";
			Loger.prnq((i+1)+".\t"+(uField[i].modify ? "<+>" : "(-)")+"   "+uField[i].putValue()+" :\t"+uField[i].title);
		}
//		Loger.prnq("Выберете поле для корректировки или 0 для выхода в главное меню, 9- вернуться"+(uField.length-1)+"\n");
	}//printNext -------------------------------------------------------------------------
	private String putValue(){
		switch (typ){
			case INT -> { return String.valueOf(valuei); }
			case STRING -> {return values;}
			case ENUM -> {
				if (arrValue.length==1)	return "";
				else return arrValue[valuei<arrValue.length && valuei>0 ? valuei : 0];
			}
		}//switch
		return "";
	}//putValue ------------------------------------------------------------------------

	public void let(String x){ values=x; }
	public String lets(){ return values; }
	public void let(int x){ valuei=x; }
	public int let(){return valuei;}
	public boolean modify(){ return modify; }
}//Fields
