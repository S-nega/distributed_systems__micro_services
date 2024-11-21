package kz.bitlab.rabbit.middle02rabbit.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private String restaurant;
    private String courier;
    private List<String> foods;
    private String status;
    private String region;
}
