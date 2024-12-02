package kz.bitlab.middle02.middle02pod_serviceA.service;

import kz.bitlab.middle02.middle02pod_serviceA.dto.Item;
import org.apache.logging.log4j.message.ExitMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private List<Item> items;
    public ItemService(){
        items = new ArrayList<>();
        items.add(new Item(1L, "fdsf", 1111, 132));
        items.add(new Item(2L, "fdsf", 2222, 132));
        items.add(new Item(3L, "fdsf", 3333, 132));
    }

    public List<Item> getItems(){
        return items;
    }
}
