package sys101_rest;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main4 {
    public static List<DictionaryList> dictionaryList = new ArrayList<>();
    static UUID uuidId;

    public static void main(String[] args) throws UnsupportedEncodingException {

        List<DictionaryList> dateListTest = new ArrayList<>();

        LocalDate dateDefault = LocalDate.of(2000, 01, 01);
        List<LocalDate> datesList = new ArrayList<>();

        LocalDate currentDate;

        for (int i = 0; i < 4; i++) {
            currentDate = dateDefault.plusYears(i);
            datesList.add(currentDate);
        }

        //родитель
        uuidId = UUID.randomUUID();
        DictionaryList element1 = new DictionaryList(
                uuidId,
                "001",
                UUID.randomUUID(),
                "Умышленные действия по уничтожению (повреждению) имущества, нанесению вреда здоровью человека при помощи огня (поджог)",
                UUID.randomUUID(),
                dateDefault
        );
        dateListTest.add(element1);

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

        for (DictionaryList list :
                dictionaryList) {
            System.out.println();
            System.out.println(list);
        }

        /*
        тестирование методов тут убрать !!! static методы убраны
        */
        UUID searchServiceCode = dictionaryList.get(0).getServiceCode();
        Main4 main4 = new Main4();

        DictionaryList method1 = main4.getActualRecordByServiceCodeAndDate(searchServiceCode, LocalDate.of(2002, 11, 01));
        TreeSet<DictionaryList> method2 = main4.getAllActualRecordByDate(LocalDate.of(2002, 11, 01));

        UUID searchParent = dictionaryList.get(0).getParent();
        TreeSet<DictionaryList> method3 = main4.getAllActualRecordsByParentAndLocalDate(searchParent, LocalDate.of(2002, 01, 01));

        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(dictionaryList.get(0).getParent());
        uuidList.add(dictionaryList.get(1).getParent());

        TreeSet<DictionaryList> method4 = main4.getAllActualRecordsByParentsAndLocalDate1(uuidList, LocalDate.of(2002, 01, 01));
        TreeSet<DictionaryList> method5 = main4.getAllActualRecordsByParentsAndLocalDate2(uuidList, LocalDate.of(2002, 01, 01));
        DictionaryList method6 = main4.getActualRecordByParentsAndLocalDate(uuidList, LocalDate.of(2002, 01, 01));

        System.out.println();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    //Ищем последнюю с конца запись, а не первую!!!!!!!
    //Объект из списка parents и localDate, дата с конца интервала
    public DictionaryList getActualRecordByServiceCodeAndDate(UUID serviceCode, LocalDate date) {

        List<DictionaryList> select = dictionaryList.stream()
                .filter(s1 -> s1.getServiceCode().equals(serviceCode))
                .filter((s) -> s.getActualDate().isBefore(date.plusDays(1L)))
                .collect(Collectors.toList());

        TreeSet<DictionaryList> ts = new TreeSet(new Helper());
        ts.addAll(select);
        TreeSet<DictionaryList> tree = (TreeSet<DictionaryList>) ts.headSet(
                new DictionaryList(
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        date.plusDays(1l)
                )
        );
        return tree.isEmpty() ? null : tree.last();

    }

    public TreeSet<DictionaryList> getAllActualRecordByDate(LocalDate date) {
        TreeSet<DictionaryList> ts = new TreeSet(new Helper());
        ts.addAll(dictionaryList);
        TreeSet<DictionaryList> tree = (TreeSet<DictionaryList>) ts.headSet(
                new DictionaryList(
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        date.plusDays(1l)
                )
        );
        return tree;
    }

    public TreeSet<DictionaryList> getAllActualRecordsByParentAndLocalDate(UUID parent, LocalDate localDate) {

        List<DictionaryList> select = dictionaryList.stream()
                .filter(s -> s.getParent().equals(parent))
                .filter((s) -> s.getActualDate().isBefore(localDate.plusDays(1L)))
                .collect(Collectors.toList());

        TreeSet<DictionaryList> ts = new TreeSet(new Helper());
        ts.addAll(select);
        return ts;
    }

    public TreeSet<DictionaryList> getAllActualRecordsByParentsAndLocalDate1(List<UUID> parent, LocalDate localDate) {
        return dictionaryList.stream()
                .filter(s1 -> parent.contains(s1.getParent()))
                .filter((s) -> s.getActualDate().isBefore(localDate.plusDays(1L)))
                .collect(Collectors.toCollection(() -> new TreeSet<>(new Helper())));

    }

    //аналогичный метод
    public TreeSet<DictionaryList> getAllActualRecordsByParentsAndLocalDate2(List<UUID> parent, LocalDate localDate) {

        List<DictionaryList> select = dictionaryList.stream().
                filter(s1 -> parent.contains(s1.getParent())).collect(Collectors.toList());

        TreeSet<DictionaryList> ts = new TreeSet(new Helper());
        ts.addAll(select);

        TreeSet<DictionaryList> tree = (TreeSet<DictionaryList>) ts.headSet(
                new DictionaryList(
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        localDate.plusDays(1l)
                )
        );

        return tree;
    }

    //Ищем последнюю с конца запись, а не первую!!!!!!!
    //Объект из списка parents и localDate, дата с конца интервала
    public DictionaryList getActualRecordByParentsAndLocalDate(List<UUID> parent, LocalDate localDate) {

        List<DictionaryList> select = dictionaryList.stream()
                .filter(s1 -> parent.contains(s1.getParent())).
                        collect(Collectors.toList());

        TreeSet<DictionaryList> ts = new TreeSet(new Helper());
        ts.addAll(select);

        TreeSet<DictionaryList> tree = (TreeSet<DictionaryList>) ts.headSet(
                new DictionaryList(
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        "",
                        new UUID(Long.MAX_VALUE, Long.MAX_VALUE),
                        localDate.plusDays(1l)
                )
        );
        return tree.isEmpty() ? null : tree.last();

    }

}
