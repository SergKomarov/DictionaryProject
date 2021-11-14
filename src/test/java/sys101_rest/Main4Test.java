package sys101_rest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.*;
import static sys101_rest.Main4.dictionaryList;


public class Main4Test {

    @Before
    public void setUp() {
        List<DictionaryList> dateListTest = new ArrayList<>();

        LocalDate dateDefault = LocalDate.of(2000, 01, 01);
        List<LocalDate> datesList = new ArrayList<>();

        LocalDate currentDate;

        for (int i = 0; i < 4; i++) {
            currentDate = dateDefault.plusYears(i);
            datesList.add(currentDate);
        }

        //родитель
        UUID uuidId = UUID.randomUUID();
        DictionaryList element1 = new DictionaryList(
                uuidId,
                "001",
                UUID.randomUUID(),
                "Умышленные действия по уничтожению (повреждению) имущества, нанесению вреда здоровью человека при помощи огня (поджог)",
                UUID.randomUUID(),
                dateDefault
        );
        dateListTest.add(element1);
        //System.out.println(element1);

        //родитель
        uuidId = UUID.randomUUID();
        DictionaryList element2 = new DictionaryList(
                uuidId,
                "",
                UUID.randomUUID(),
                "НЕИСПРАВНОСТЬ ПРОИЗВОДСТВЕННОГО ОБОРУДОВАНИЯ, НАРУШЕНИЕ -  ТЕХНОЛОГИЧЕСКОГО ПРОЦЕССА ПРОИЗВОДСТВА" + "-" + dateDefault,
                UUID.randomUUID(),
                dateDefault
        );
        dateListTest.add(element2);
//       System.out.println(element2);

        //предок
        UUID id = UUID.randomUUID();
        DictionaryList element3 = new DictionaryList(
                id,
                "002",
                UUID.randomUUID(),
                "Недостаток конструкции, изготовления и монтажа производственного оборудования" + "-" + dateDefault,
                uuidId,
                dateDefault
        );
        dateListTest.add(element3);
        //      System.out.println(element3);

        DictionaryList element = null;
        for (LocalDate dates : datesList
        ) {
            for (DictionaryList list :
                    dateListTest) {
                if (dates.equals(dateDefault)) {
                    element = new DictionaryList(
                            list.getId(),
                            list.getCode(),
                            list.getServiceCode(),
                            list.getName() + "-" + dates.toString(),
                            list.getParent(),
                            list.getActualDate()
                    );
                } else {
                    element = new DictionaryList(
                            UUID.randomUUID(),
                            list.getCode(),
                            list.getServiceCode(),
                            list.getName() + "-" + dates.toString(),
                            list.getParent(),
                            dates
                    );
                }
                dictionaryList.add(element);
            }
        }
    }

    @Test
    public void testGetActualRecordByServiceCodeAndDateNotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();
        dictionaryList.get(0).getServiceCode();

        DictionaryList dict = main4.getActualRecordByServiceCodeAndDate(dictionaryList.get(0).getServiceCode(), dateDefault);
        assertTrue(dict != null);
        assertEquals(dictionaryList.get(6), dict);

        dateDefault = LocalDate.of(2000, 01, 01);
        dict = main4.getActualRecordByServiceCodeAndDate(dictionaryList.get(0).getServiceCode(), dateDefault);
        assertTrue(dict != null);
        assertEquals(dictionaryList.get(0), dict);

        dateDefault = LocalDate.of(1990, 01, 01);
        dict = main4.getActualRecordByServiceCodeAndDate(dictionaryList.get(0).getServiceCode(), dateDefault);
        assertNull(dict);

     }

    @Test
    public void testGetAllActualRecordByDateNotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();
        TreeSet<DictionaryList> treeSet = main4.getAllActualRecordByDate(dateDefault);

        assertTrue(treeSet.size() != 0);
        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(2000, 01, 01);
        treeSet = main4.getAllActualRecordByDate(dateDefault);
        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(2005, 01, 01);
        treeSet = main4.getAllActualRecordByDate(dateDefault);
        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(1990, 01, 01);
        treeSet = main4.getAllActualRecordByDate(dateDefault);
        assertTrue(treeSet.size() == 0);

        List<DictionaryList> arrayLists = new ArrayList<>();
        arrayLists.addAll(dictionaryList);

    }

    @Test
    public void testGetAllActualRecordsByParentAndLocalDateNotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();
        UUID searchParent = dictionaryList.get(0).getParent();
        TreeSet<DictionaryList> treeSet = main4.getAllActualRecordsByParentAndLocalDate(searchParent, dateDefault);
        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(1990, 01, 01);
        treeSet = main4.getAllActualRecordsByParentAndLocalDate(searchParent, dateDefault);
        assertTrue(treeSet.size() == 0);
    }

    @Test
    public void testGetAllActualRecordsByParentsAndLocalDate1NotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();


        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(dictionaryList.get(0).getParent());
        uuidList.add(dictionaryList.get(1).getParent());
        TreeSet<DictionaryList> treeSet = main4.getAllActualRecordsByParentsAndLocalDate1(uuidList, dateDefault);


        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(1990, 01, 01);
        treeSet = main4.getAllActualRecordsByParentsAndLocalDate1(uuidList, dateDefault);
        assertTrue(treeSet.size() == 0);
    }

    //аналогичный метод
    @Test
    public void testGetAllActualRecordsByParentsAndLocalDate2NotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();


        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(dictionaryList.get(0).getParent());
        uuidList.add(dictionaryList.get(1).getParent());
        TreeSet<DictionaryList> treeSet = main4.getAllActualRecordsByParentsAndLocalDate2(uuidList, dateDefault);


        assertTrue(treeSet.contains(dictionaryList.get(0)));

        dateDefault = LocalDate.of(1990, 01, 01);
        treeSet = main4.getAllActualRecordsByParentsAndLocalDate2(uuidList, dateDefault);
        assertTrue(treeSet.size() == 0);
    }

    @Test
    public void testGetActualRecordByParentsAndLocalDateNotStatic() throws Exception {
        LocalDate dateDefault = LocalDate.of(2002, 01, 01);
        Main4 main4 = new Main4();

        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(dictionaryList.get(0).getParent());
        uuidList.add(dictionaryList.get(1).getParent());
        DictionaryList dict = main4.getActualRecordByParentsAndLocalDate(uuidList, dateDefault);
        //тут мы просто знаем, что объект при добавлении был с индексом 6
        assertEquals(dict, dictionaryList.get(6));

        dateDefault = LocalDate.of(1990, 01, 01);
        dict = main4.getActualRecordByParentsAndLocalDate(uuidList, dateDefault);
        assertNull(dict);
    }

}

