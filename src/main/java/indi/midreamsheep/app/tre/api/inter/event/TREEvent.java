package indi.midreamsheep.app.tre.api.inter.event;

import indi.midreamsheep.app.tre.context.TREContext;

public interface TREEvent {
    void handle(TREContext context);
}
