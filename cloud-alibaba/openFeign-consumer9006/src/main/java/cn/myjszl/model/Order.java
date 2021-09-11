package cn.myjszl.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private Long id;

    private String name;

    private String num;

    private double money;

}