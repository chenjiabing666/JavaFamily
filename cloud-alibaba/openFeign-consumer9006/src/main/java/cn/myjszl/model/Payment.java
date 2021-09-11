package cn.myjszl.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private Long id;
    private Long money;
}
