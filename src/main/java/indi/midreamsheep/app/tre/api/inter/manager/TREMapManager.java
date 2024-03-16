package indi.midreamsheep.app.tre.api.inter.manager;

import java.util.Map;

public interface TREMapManager <K,V>{
    V get(K key);
    V get(String command);
}
