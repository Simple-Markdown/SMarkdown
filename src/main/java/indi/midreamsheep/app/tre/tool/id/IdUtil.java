package indi.midreamsheep.app.tre.tool.id;

public class IdUtil {
    public static long generateId() {
        return cn.hutool.core.util.IdUtil.getSnowflake().nextId();
    }
}
