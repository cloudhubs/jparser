package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@JsonDeserialize(using = OperationDeserializer.class)
public interface IOp {
    String getOp();
}

/** We custom deserialize */
class OperationDeserializer extends JsonDeserializer<IOp> {
    /** Generate a constant, static mapping of all the operations */
    private static final Map<String, Op> ops = new HashMap<>();
    static {
        for (var op : Op.values()) {
            ops.put(op.toString(), op);
        }
    }

    /** If we find it, use it; otherwise make a new one */
    @Override
    public IOp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").textValue(); // Get the value out of the tree
        if (!ops.containsKey(type)) {
            if (node.get("op") == null) return null;
            String value = node.get("op").textValue();
            return new OtherOp(value);
        } else {
            return ops.get(type);
        }
    }
}
