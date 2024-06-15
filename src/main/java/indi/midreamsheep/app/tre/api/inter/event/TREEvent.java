package indi.midreamsheep.app.tre.api.inter.event;

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext;

public interface TREEvent {
    void handle(TREWindowContext context);
}
