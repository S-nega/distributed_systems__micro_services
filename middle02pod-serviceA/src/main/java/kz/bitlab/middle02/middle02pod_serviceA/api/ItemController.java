package kz.bitlab.middle02.middle02pod_serviceA.api;

import kz.bitlab.middle02.middle02pod_serviceA.dto.Item;
import kz.bitlab.middle02.middle02pod_serviceA.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getItem(){
        return itemService.getItems();
    }
}
