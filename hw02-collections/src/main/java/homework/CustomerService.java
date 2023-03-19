package homework;


import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer,String> customers = new TreeMap<>();
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны


    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return copyOrNull(customers.firstEntry()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Objects.requireNonNull(customer);
        return copyOrNull(customers.higherEntry(customer)); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customers.put(customer,data);
    }
    private Map.Entry<Customer, String> copyOrNull(Map.Entry<Customer, String> entry){
        if (entry == null)return null;
        Customer copy = new Customer(
                entry.getKey().getId(),
                entry.getKey().getName(),
                entry.getKey().getScores());
        return Map.entry(copy, entry.getValue());
    }
}