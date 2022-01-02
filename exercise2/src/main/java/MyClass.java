import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MyClass {
    private Date m_time;
    private String m_name;
    private List<Long> m_numbers;
    private List<String> m_strings;

    public MyClass(Date time, String name, List<Long> numbers, List<String>
            strings) {
        m_time = time;
        m_name = name;
        m_numbers = numbers;
        m_strings = strings;
    }

    /**
     * It might throw NPE as m_name can be  NULL
     *
     * @param obj
     * @return
     */
    public boolean equals_error(Object obj) {
        if (obj instanceof MyClass) {
            return m_name.equals(((MyClass) obj).m_name);
        }
        return false;
    }

    /**
     * I assume here the developer consider two objects are equal if and only if m_name are equal
     * Use this formula to compare --> (a == b) || (a != null && a.equals(b));
     * Which is equivalent to Objects.equals(a, b)
     *
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj instanceof MyClass) {
            return Objects.equals(m_name, ((MyClass) obj).m_name);
        }
        return false;
    }

    /**
     * Problems:
     * a- m_name might be a NULL
     * b- m_numbers might contain NULL items so convert it to long item will cause NPE
     * c- String concatenation cause a performance problem to JVM as it keep creating new string and add them to JVM string pool
     *
     * @return
     */
    public String toString_error() {
        String out = m_name;
        for (long item : m_numbers) {
            out += " " + item;
        }
        return out;
    }


    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(m_name);

        if (m_numbers != null) {
            for (Long item : m_numbers) {
                result.append(" ").append(item);
            }
        }
        return result.toString();
    }

    /**
     * Problems:
     * a- Possibility for NPE for m_strings
     * b- Possibility for NPE for any item inside m_strings like ["1", null, "2", "3"]
     * c- Method will not delete all occurrence of the input in the list. The reason mainly because every time an element is deleted then shifting any subsequent elements to the left
     * Ex. ["1", "1", "2"] and try to delete "1" --> Result ["1", "2"]
     *
     * @param str
     */
    public void removeString_error(String str) {
        for (int i = 0; i < m_strings.size(); i++) {
            if (m_strings.get(i).equals(str)) {
                m_strings.remove(i);
            }
        }
    }


    public void removeString_using_java8(String str) {
        if (m_strings != null) {
            m_strings.removeIf(item -> Objects.equals(item, str));
        }
    }

    public void removeString_using_iterator(String str) {
        if (m_strings != null) {
            Iterator<String> iterator = m_strings.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (Objects.equals(item, str)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Problems:
     * a- Possibility of NPE for m_numbers
     * b- Possibility of NPE for an item inside m_numbers
     *
     * @param number
     * @return
     */
    public boolean containsNumber_error(long number) {
        for (long num : m_numbers) {
            if (num == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param number
     * @return
     */
    public boolean containsNumber(long number) {
        if (m_numbers != null)
            return m_numbers.contains(number);
        return false;
    }

    /**
     * Problems:
     * a- m_time NPE
     * b- new Date() --> using UTC time zone where m_time timezone might be different
     *
     * @return
     */
    public boolean isHistoric_error() {
        return m_time.before(new Date());
    }

    public boolean isHistoric() {
        if (m_time != null)
            return m_time.getTime() < new Date().getTime();
        return false;
    }
}
