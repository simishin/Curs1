package qwr;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * предназначен для работы с параметрами классов через меню
 */
public class XFields {
	private static int countId =0; //Счетчик для элементов
	static int count=0;//указатель положения при печати списка значений
	public enum Typ{INT,STRING,ENUM,LIST}
	private final int idMenu;
	private final int id;
	private final Typ typ;
	private final String title;
	private final String descript;
	private final int		valMin;
	private final int		valMax;
	private final String[] arrValue;
	private List<Item> list;
	private String values;
	private int		valuei;
	private boolean use;//использовать при модификации записи или оставить без изменения данное поле

	public XFields(int idMenu, Typ typ, String title, String descript, int valMin, int valMax) {
		this.id = countId++;
		this.idMenu = idMenu;
		this.typ = typ;
		this.title = title;
		this.descript = descript;
		this.valMin = valMin;
		this.valMax = valMax;
		this.arrValue=null;
		this.values = "";
		this.valuei = 0;
		this.use = false;
	}//XFields=======================================================================
	public XFields(int idMenu, String title, String descript, int valMin, int valMax, int valuei) {
		this.id = countId++;
		this.idMenu = idMenu;
		this.typ = Typ.INT;
		this.title = title;
		this.descript = descript;
		this.valMin = valMin;
		this.valMax = valMax;
		this.arrValue=null;
		this.values = "";
		this.valuei = valuei;
		this.use = false;
	}//XFields=======================================================================
	public XFields(int idMenu, String title, String descript, int valMin, int valMax, String values) {
		this.id = countId++;
		this.idMenu = idMenu;
		this.typ = Typ.STRING;
		this.title = title;
		this.descript = descript;
		this.valMin = valMin;
		this.valMax = valMax;
		this.arrValue=null;
		this.values = values;
		this.valuei = 0;
		this.use = false;
	}//XFields=======================================================================

	public XFields(int idMenu, String title, String descript, String ...arrVal) {
		this.id = countId++;
		this.idMenu = idMenu;
		this.typ = Typ.ENUM;
		this.title = title;
		this.descript = descript;
		this.valMin = 1;
		this.valMax = arrVal.length;
		arrValue= arrVal;
		this.values = "";
		this.valuei = 0;
		this.use = false;
	}//XFields=======================================================================
	public XFields(int idMenu, String title, String descript, List<Item> list) {
		this.id = countId++;
		this.idMenu = idMenu;
		this.typ = Typ.LIST;
		this.title = title;
		this.descript = descript;
		this.valMin = 1;
		this.valMax = list.size();
		arrValue= null;
		this.list = list;
		this.values = "";
		this.valuei = 0;
		this.use = false;
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
		Loger.prnq(titul);
		int q;
		String	qs;
		while (true){
			printSetting(uField);//печать списка параметров
			Loger.prnq("n,(0+-*/),? :");
			qs=con.nextLine();
			if (qs.isBlank()) continue;
			int y = Dialog.conInt(qs);
			switch (qs.charAt(0)) {
				case '?':
						Loger.prnq("Выберете номер поля или 0,/- для выхода в главное меню, " +
								"9,*,+,-- вернуться ("+(uField.length)+")\n");
					break;
				case 'q':
					return true;
				case '%':
				case '+':
				case '-':
				case '/':
				case '*':
					return false;
				case ' ':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					if (y==0) return false;
					if(y>0 && y<=uField.length){
						do {
							q = editValue(con,uField[y-1]);
							if (q==-1) break;
							y++;
						} while (q==1 && y<uField.length);
					} else Loger.prnq("Ошибка выбора. Повторите.");
					break;
				default:Loger.prnq("~?~");
			}//switch
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
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.use ? "Да" : "НЕТ"));
				Loger.prnq("текущее значение :"+uField.valuei);
				Loger.prnq("Описание параметра: "+uField.descript);
//				Loger.prnq("{ /- для выхода, *-отключение и выход к списку, --отключение и следующий, +- включение и следующий }"+"\n------- ?");
				while (true) {
					try {
						int y = con.nextInt();
						Loger.logs("="+y);
						if (y >= uField.valMin && y <= uField.valMax) {
							uField.use = true;
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
									uField.use = true;
									return 1;//следующий параметр
							case '-':
									uField.use = false;
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
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.use ? "Да" : "НЕТ"));
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
							uField.use = true;
							return 1;
						case '-':
							uField.use = false;
							return 1;
						default:
					}//switch
					if (y.length() >= uField.valMin && y.length() <= uField.valMax) {
						uField.use = true;
						uField.values = y;
						return 0;
					}
					Loger.prnq("Длина текста выходит за допустимый диапазон. Повторите ввод.");
				}//while
			}//case STRING
			case ENUM -> {
				Loger.prnq("Выберете значение параметра "+uField.title+" из списка  или :" +
						"\n/- для выхода, *-выход к списку, --отключение и следующий, +- включение и следующий");
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.use ? "Да" : "НЕТ"));
				Loger.prnq("текущее значение :"+uField.arrValue[uField.valuei].toString());
				Loger.prnq("Описание параметра: "+uField.descript+"\n Список допустимых значений:");
				for (int i = 0; i < uField.arrValue.length; i++) {
					Loger.prnq((i+1)+".\t"+uField.arrValue[i].toString());
				}
				String	qs;
				while (true) {
					qs=con.nextLine();
					if (qs.length() !=0) {
						int y=Dialog.conInt(qs);
						switch (qs.charAt(0)) {
							case 'q':
							case '%':
								return -1;
							case '/':
							case '*':
								return 0;
							case ' ':
								break;
							case '+':
								uField.use = true;
								return 1;
							case '-':
								uField.use = false;
								return 1;
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								if (y==0) return -1;
								if (y >= uField.valMin && y <= uField.valMax) {
									uField.use = true;
									uField.valuei = y-1;
									return 0;
								}
								Loger.prnq("Значения не существует. Повторите ввод.");
								break;
							default:Loger.prnq("~?~");
						}//switch
					} else {//if (qs.length() !=0)

					}
				}//while
			}//case ENUM
			case LIST -> {
				Loger.prnq("Выберете значение параметра "+uField.title+" из списка  или :" +
						"\n/- для выхода, *-выход к списку, --отключение и следующий, +- включение и следующий");
				Loger.prnq("Значение параметра используется при корректировке: "+(uField.use ? "Да" : "НЕТ"));
				if (uField.list.size()<2){
					Loger.prnq("Список значений параметра пустой.");
					return 0;
				}
				for (Item x: uField.list ) if (x.idItem()== uField.valuei) {
					Loger.prnq("текущее значение :"+x.title()); break;
				}
				Loger.prnq("Описание параметра: "+uField.descript+":");
				String	qs;
				int jend=0;
				int z=0;
				label:
				while (true) {
					qs = con.nextLine();
					if (qs.isBlank()) {
						//печатаю очередную порцию данных
						Loger.logs("печатаю очередную порцию данных");
						count=Dialog.printNext(count ,uField.list);
					} else {
						if (qs.charAt(0)>='0' && qs.charAt(0)<='9') jend = (qs.charAt(0) - '0');
						for (int i = 1; i < qs.length(); i++) {
							if (qs.charAt(i) >= '0' && qs.charAt(i) <= '9')
								jend = jend * 10 + (qs.charAt(i) - '0');
							else  break;
						}//for
						switch (qs.charAt(0)) {
							case '*': z++;
							case '+': z++;
								if (jend==0) break;
//								for (Item x: uField.list ) if (x.idItem()== jend){
//									uField.valuei = jend;
//									return z;
//								}
								if (jend<uField.list.size()){
									uField.valuei=uField.list.get(jend).idItem();
									uField.use=true;
								}
								Loger.logs("valuei "+uField.valuei+" z"+z);
								return z;
							case 'q':
							case '-':
							case '/': return -1;
							case ' ':
//							case '%':
//							case '=':

//							case '?':	printHelp();	break;
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								//								count =jend;
								count=Dialog.printNext(jend ,uField.list);
								break;
							default:
						}//switch
						Loger.prnt("?");
					}
				}//while
			}//case LIST
		}//switch
		return 0;
	}//editValue --------------------------------------------------------------------

	public static void prnStencil(XFields[] uField){
		assert uField.length>0 : "Начата обработка пустого списка параметров элемента (XFields.printSelect)";
		Loger.prnq("(\tИсп.,\tЗначение, Наименование)");
		for (int i = 0; i < uField.length; i++) {
			assert uField[i] !=null : "Зарезервировано больше, чем описано параметров элемента ";
			Loger.prnq("\t"+(uField[i].use ? "<+>" : "(-)")+"   "+uField[i].putValue()+" :\t"+uField[i].title);
		}//for
	}//prnStencil

		/**
		 * Печать списка параметров элемента. Вызывается из локальной uConsol()
		 * @param uField список параметров элемента
		 */
	public static void printSetting(XFields[] uField) {//печать одной странички из списка
		assert uField.length>0 : "Начата обработка пустого списка параметров элемента (XFields.printSelect)";
//		if (uField.length<1) return;
		Loger.prnq("( №,\tИсп.,\tЗначение, Наименование параметра )");
		for (int i = 0; i < uField.length; i++) {
			assert uField[i] !=null : "Зарезервировано больше, чем описано параметров элемента ";
			Loger.prnq((i+1)+".\t"+(uField[i].use ? "<+>" : "(-)")+"   "+uField[i].putValue()+" :\t"+uField[i].title);
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
			case LIST -> {
				for (Item x: this.list ) if (x.idItem()== this.valuei) {
					return "("+String.valueOf(valuei)+") "+x.title();
				}
				return String.valueOf(valuei);
			}
		}//switch
		return "";
	}//putValue ------------------------------------------------------------------------

	public void let(String x){ values=x; }
	public String lets(){ return values; }
	public void let(int x){ valuei=x; }
	public int let(){return valuei;}
	public boolean isUse(){ return use; }//использовать
	public void let(boolean x){use =x;}
}//Fields
