package com.kalo.easpay.utils.reptile;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 09:52:18
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable {

    private String name;

    private String code;

    private String dataFromUrl;

    private List<Node> nodes;
}
