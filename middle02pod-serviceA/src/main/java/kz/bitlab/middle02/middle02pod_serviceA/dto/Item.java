package kz.bitlab.middle02.middle02pod_serviceA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private Long id;
    private String name;
    private double price;
    private int amount;
}
