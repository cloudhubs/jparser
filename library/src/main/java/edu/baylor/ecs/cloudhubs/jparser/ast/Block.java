package edu.baylor.ecs.cloudhubs.jparser.ast;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Block extends Node {
    private List<Node> nodes;
}
